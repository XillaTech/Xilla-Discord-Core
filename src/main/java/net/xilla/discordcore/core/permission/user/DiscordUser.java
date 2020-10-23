package net.xilla.discordcore.core.permission.user;

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.DiscordAPI;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.command.permission.group.PermissionGroup;
import net.xilla.discordcore.core.command.permission.user.PermissionUser;
import net.xilla.discordcore.core.manager.GuildManagerObject;
import net.xilla.discordcore.core.permission.group.DiscordGroup;

import java.util.ArrayList;
import java.util.List;

public class DiscordUser extends GuildManagerObject implements PermissionUser {

    @Getter
    private ArrayList<PermissionGroup> groups;

    @Getter
    private List<String> permissions = new ArrayList<>();

    @Getter
    private Member member;

    @Deprecated
    public DiscordUser(Member member) {
        super(member.getId(), "Users", member.getGuild());
        this.groups = new ArrayList<>();

        DiscordGroup defaultGroup = DiscordCore.getInstance().getGroupManager().getManager(member.getGuild()).get("default");
        if(defaultGroup != null) {
            this.groups.add(defaultGroup);
        } else {
            Logger.log(LogLevel.WARN, "Could not find the default role!", getClass());
        }

        this.member = member;

        for(Role role : member.getRoles()) {
            DiscordGroup group = DiscordCore.getInstance().getGroupManager().getManager(member.getGuild()).get(role.getId());
            if(group != null) {
                this.groups.add(group);
            }
        }
    }

    public DiscordUser(Guild guild) {
        super("", "Users", guild);
    }

    @Override
    public PermissionGroup getPrimaryGroup() {
        return groups.get(0);
    }

    @Override
    public String getUserIdentifier() {
        return getKey();
    }

    @Override
    public boolean hasPermission(String permission) {
        if(DiscordCore.getInstance().getCoreSetting().isRespectDiscordAdmin()) {
            if(!permission.toLowerCase().startsWith("core.")) {
                if (member.hasPermission(Permission.ADMINISTRATOR)) {
                    return true;
                }
            }
        }

        if(groups != null) {
            for (PermissionGroup group : new ArrayList<>(groups)) {
                if (group.hasPermission(permission)) {
                    return true;
                }
            }
        }

        return permissions.contains(permission.toLowerCase());
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("memberID", member.getId());
        json.put("serverID", getGuildID());
        json.put("permissions", permissions);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        Guild guild = new CoreObject().getGuild(xillaJson.get("serverID"));
        setKey(xillaJson.get("memberID"));
        this.member = guild.getMemberById(xillaJson.get("memberID"));
        this.permissions = xillaJson.get("permissions");

        this.groups = new ArrayList<>();

        DiscordGroup defaultGroup = DiscordCore.getInstance().getGroupManager().getManager(member.getGuild()).get("default");
        if(defaultGroup != null) {
            this.groups.add(defaultGroup);
        } else {
            Logger.log(LogLevel.WARN, "Could not find the default role!", getClass());
        }

        for(Role role : member.getRoles()) {
            Manager<DiscordGroup> manager = DiscordAPI.getGroupManager().getManager(member.getGuild());
            DiscordGroup group = manager.get(role.getId());
            if(group != null) {
                this.groups.add(group);
            }
        }

    }
}

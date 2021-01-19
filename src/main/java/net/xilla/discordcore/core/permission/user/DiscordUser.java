package net.xilla.discordcore.core.permission.user;

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;
import net.xilla.discordcore.library.DiscordAPI;
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
        return getKey().toString();
    }

    @Override
    public boolean hasPermission(Guild guild, String permission) {
        if(guild != null) {
            if(member == null) {
                User user = DiscordAPI.getUser(getKey().toString());
                if (user != null) {
                    this.member = guild.retrieveMember(user).complete();
                }
            }
        }

        if(this.member == null) {
            Logger.log(LogLevel.ERROR, "The bot was unable to find the member for discord user " + member.getId() + " in guild " + member.getGuild().getId(), getClass());
            return false;
        }

        this.groups = new ArrayList<>();
        DiscordGroup defaultGroup = DiscordCore.getInstance().getGroupManager().getManager(member.getGuild()).get("default");
        if(defaultGroup != null) {
            this.groups.add(defaultGroup);
        } else {
            Logger.log(LogLevel.WARN, "Could not find the default role!", getClass());
        }

        for(Role role : member.getRoles()) {
            Manager<String, DiscordGroup> manager = DiscordAPI.getGroupManager().getManager(member.getGuild());
            DiscordGroup group = manager.get(role.getId());
            if(group != null) {
                this.groups.add(group);
            }
        }

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

        for(String perm : permissions) {
            if(perm.equalsIgnoreCase(permission.toLowerCase())) {
                return true;
            }
        }

        String[] temp = permission.split("\\.");
        StringBuilder wildcard = new StringBuilder();
        for(int i = 0; i <= temp.length - 2; i++) {
            wildcard.append(temp[0]);
        }
        wildcard.append(".*");

        for(String perm : permissions) {
            if (perm.equalsIgnoreCase(wildcard.toString())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("memberID", getKey());
        json.put("serverID", getGuildID());
        json.put("permissions", permissions);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        setKey(xillaJson.get("memberID"));
        this.permissions.addAll(xillaJson.get("permissions"));

        Guild guild = DiscordAPI.getBot().getGuildById(xillaJson.get("serverID").toString());
        if(guild == null) {
            Logger.log(LogLevel.ERROR, "Unable to find the guild with ID " + xillaJson.get("serverID").toString(), getClass());
            return;
        }

        this.groups = new ArrayList<>();
        DiscordGroup defaultGroup = DiscordCore.getInstance().getGroupManager().getManager(guild).get("default");
        if(defaultGroup != null) {
            this.groups.add(defaultGroup);
        } else {
            Logger.log(LogLevel.WARN, "Could not find the default role!", getClass());
        }

        this.member = DiscordAPI.getMember(guild, getKey().toString());

        if(member == null) {
            Logger.log(LogLevel.ERROR, "Unable to find the member with ID " + getKey(), getClass());
            return;
        }

        for(Role role : member.getRoles()) {
            Manager<String, DiscordGroup> manager = DiscordAPI.getGroupManager().getManager(member.getGuild());
            DiscordGroup group = manager.get(role.getId());
            if(group != null) {
                this.groups.add(group);
            }
        }

    }
}

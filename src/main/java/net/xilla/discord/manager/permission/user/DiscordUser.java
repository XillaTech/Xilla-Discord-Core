package net.xilla.discord.manager.permission.user;

import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.StoredData;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.api.permission.PermissionEntity;
import net.xilla.discord.api.permission.PermissionGroup;
import net.xilla.discord.api.permission.PermissionUser;
import net.xilla.discord.manager.permission.group.DiscordGroup;
import net.xilla.discord.setting.DiscordSettings;

import java.util.*;

public class DiscordUser extends ManagerObject implements PermissionUser {

    @Getter
    @StoredData
    private List<String> permissions = new Vector<>();

    @Getter
    @StoredData
    private String guildID;

    public DiscordUser(Member member) {
        super(member.getId(), "DiscordUser");

        this.guildID = member.getGuild().getId();
    }

    public DiscordUser() {}

    @Override
    public boolean hasPermission(String neededPerm) {
        Member member = getMember();
        if(member != null) {
            DiscordSettings discordSettings = DiscordCore.getInstance().getSettings();
            if(discordSettings.getRespectOwner()) {
                if (member.isOwner()) {
                    return true;
                }
            }
            if(discordSettings.getRespectAdmin()) {
                if (member.hasPermission(Permission.ADMINISTRATOR)) {
                    return true;
                }
            }
        }

        // Checking groups for permissions
        for(PermissionGroup group : getGroups()) {
            if(group.hasPermission(neededPerm)) return true;
        }

        // Passing to local permission check
        return PermissionEntity.hasPermission(getPermissions(), neededPerm);
    }

    @Override
    public String getId() {
        return toString();
    }

    @Override
    public List<PermissionGroup> getGroups() {
        Member member = getMember();

        List<PermissionGroup> groups = new ArrayList<>();

        if(member == null) return groups;

        for(Role role : member.getRoles()) {
            PermissionGroup group = DiscordCore.getInstance().getGroupProcessor().get(role.getId());
            if(group != null) {
                groups.add(group);
            }
        }

        return groups;
    }

    @Override
    public Member getMember() {

        Guild guild = DiscordCore.getInstance().getJda().getGuildById(guildID);

        if(guild == null) return null;

        return guild.getMemberById(getId());
    }

}

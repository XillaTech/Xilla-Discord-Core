package net.xilla.discord.manager.permission.group;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.StoredData;
import net.xilla.discord.DiscordCore;
import net.xilla.discord.api.permission.PermissionGroup;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class DiscordGroup extends ManagerObject implements PermissionGroup {

    @Getter
    @StoredData
    private List<String> permissions = new Vector<>();

    @Getter
    @StoredData
    private String guildID;

    public DiscordGroup(Role role) {
        super(role.getId(), "DiscordGroup");

        this.guildID = role.getGuild().getId();
    }

    public DiscordGroup() {}

    @Override
    public String getId() {
        return toString();
    }

    @Override
    public Role getRole() {
        Guild guild = DiscordCore.getInstance().getJda().getGuildById(guildID);

        if(guild == null) return null;

        return guild.getRoleById(getId());
    }

}

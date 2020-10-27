package net.xilla.discordcore.core.manager;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ManagerObject;

public abstract class GuildManagerObject extends ManagerObject {

    @Getter
    @Setter
    private String guildID;

    public GuildManagerObject(String key, Manager manager, Guild guild) {
        super(key, manager);
        this.guildID = guild.getId();
    }

    public GuildManagerObject(String key, Manager manager, String guildID) {
        super(key, manager);
        this.guildID = guildID;
    }

    public GuildManagerObject(String key, String manager, Guild guild) {
        super(key, manager);
        this.guildID = guild.getId();
    }

    public GuildManagerObject(String key, String manager, String guildID) {
        super(key, manager);
        this.guildID = guildID;
    }

}

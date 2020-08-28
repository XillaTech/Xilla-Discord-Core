package net.xilla.discordcore.core.server;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.discordcore.DiscordCore;

public class CoreServer extends ManagerObject {

    private Guild guild;
    private int members;
    private long lastUpdated;

    public CoreServer(Guild guild) {
        super(guild.getId());
        this.members = guild.getMemberCount();
        this.lastUpdated = System.currentTimeMillis();
    }

    public CoreServer(String id, int members, long lastUpdated) {
        super(id);
        this.guild = DiscordCore.getInstance().getBot().getGuildById(id);
        if(guild != null) {
            this.members = guild.getMemberCount();
            this.lastUpdated = System.currentTimeMillis();
        } else {
            this.members = members;
            this.lastUpdated = lastUpdated;
        }
    }

    public void update(Guild guild) {
        this.members = guild.getMemberCount();
        this.lastUpdated = System.currentTimeMillis();
    }

    public int getMembers() {
        return members;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public Guild getGuild() {
        return guild;
    }
}

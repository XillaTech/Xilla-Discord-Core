package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.xilla.discordcore.api.module.ModuleManager;
import net.xilla.discordcore.command.CommandSettings;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.staff.GroupManager;

import java.awt.*;

public class CoreObject extends TobiasObject {

    public DiscordCore getDiscordCore() {
        return DiscordCore.getInstance();
    }

    public GroupManager getGroupManager() {
        return DiscordCore.getInstance().getGroupManager();
    }

    public ModuleManager getModuleManager() {
        return DiscordCore.getInstance().getModuleManager();
    }

    public CoreSettings getCoreSetting() {
        return DiscordCore.getInstance().getSettings();
    }

    public JDA getBot() {
        return DiscordCore.getInstance().getBot();
    }

    public Platform getPlatform() {
        return DiscordCore.getInstance().getPlatform();
    }

    public CommandSettings getCommandSettings() {
        return DiscordCore.getInstance().getCommandSettings();
    }

    public Message getMessage(String channelID, String messageID) {
        TextChannel textChannel = getBot().getTextChannelById(channelID);
        if(textChannel != null) {
            return textChannel.retrieveMessageById(messageID).complete();
        }
        return null;
    }

    public User getUser(String id) {
        User user = null;
        try {
            user = getBot().getUserById(id.replace("<@!", "").replace(">", ""));
        } catch (Exception ignored) {}
        if(user == null) {
            try {
                user = getBot().getUserByTag(id);
            } catch (Exception ignored) {
            }
        }
        if(user == null) {
            try {
                user = getBot().getUsersByName(id, true).get(0);
            } catch (Exception ignored) {
            }
        }
        return user;
    }

    public String getPrefix() {
        return getCoreSetting().getCommandPrefix();

    }

    public Member getMember(Guild guild, String id) {
        User user = getUser(id);

        if(user != null) {
            return guild.getMember(user);
        }

        return null;
    }

    public Color getColor() {
        return Color.decode(getCoreSetting().getEmbedColor());
    }

}

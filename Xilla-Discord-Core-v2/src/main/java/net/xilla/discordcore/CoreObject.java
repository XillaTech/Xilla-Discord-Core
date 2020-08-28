package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.xilla.discordcore.module.ModuleManager;
import net.xilla.discordcore.command.CommandSettings;
import net.xilla.discordcore.command.permission.DiscordUser;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.staff.Group;
import net.xilla.discordcore.core.staff.GroupManager;

import java.awt.*;
import java.util.List;

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

    public void hasPermission(Member user, String permission) {
        new DiscordUser(user).hasPermission(permission);
    }

    public void hasPermission(Guild guild, String user, String permission) {
        new DiscordUser(getMember(guild, user)).hasPermission(permission);
    }

    public User getUser(String id) {
        User user = null;
        String userID = id.replace("<@!", "").replace("<@", "").replace(">", "");
        try {
            user = getBot().getUserById(userID);
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

    public Group getGroup(Guild guild, String name) {
        Group group = getGroupManager().getGroup(name);

        if(group != null) {
            return group;
        }

        List<Group> groups = getGroupManager().getGroupsByName(name);
        if(groups != null) {
            for (Group loopGroup : groups) {
                if (guild == null || loopGroup.getServerID().equalsIgnoreCase(guild.getId())) {
                    return loopGroup;
                }
            }
        }

        return null;
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

    public boolean sendPM(User user, EmbedBuilder embedBuilder) {
        try {
            user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).queue();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean sendPM(User user, String string) {
        try {
            user.openPrivateChannel().complete().sendMessage(string).queue();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}

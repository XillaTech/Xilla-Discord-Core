package net.xilla.discordcore;

import com.tobiassteely.tobiasapi.TobiasAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.xilla.discordcore.command.CommandSettings;
import net.xilla.discordcore.command.permission.DiscordUser;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.staff.Group;
import net.xilla.discordcore.core.staff.GroupManager;
import net.xilla.discordcore.form.form.FormManager;
import net.xilla.discordcore.module.ModuleManager;

import java.awt.*;
import java.util.List;

public class DiscordAPI {

    /**
     * Gets the main Discord Core instance and returns it
     *
     * @return DiscordCore
     */
    private static DiscordCore getDiscordCore() {
        return DiscordCore.getInstance();
    }

    /**
     * Gets the main Tobias Library instance and returns it
     * (Ignore that it's called 'API' it's actually a library
     *
     * @return TobiasAPI
     */
    private static TobiasAPI getAPI() {
        return TobiasAPI.getInstance();
    }

    /**
     * Gets the GroupManager from the Core and returns it
     *
     * @return GroupManager
     */
    public static GroupManager getGroupManager() {
        return DiscordCore.getInstance().getGroupManager();
    }

    /**
     * Gets the FormManager from the Core and returns it
     *
     * @return FormManager
     */
    public static FormManager getFormManager() {
        return DiscordCore.getInstance().getFormManager();
    }

    /**
     * Gets the ModuleManager from the Core and returns it
     *
     * @return ModuleManager
     */
    public static ModuleManager getModuleManager() {
        return DiscordCore.getInstance().getModuleManager();
    }

    /**
     * Gets the CoreSettings from the Core and returns it
     *
     * @return CoreSettings
     */
    public static CoreSettings getCoreSetting() {
        return DiscordCore.getInstance().getSettings();
    }

    /**
     * Gets the GroupManager from the Core and returns it
     *
     * @return GroupManager
     */
    public static JDA getBot() {
        return DiscordCore.getInstance().getBot();
    }

    /**
     * Gets the Platform system from the Core and returns it
     *
     * @return Platform
     */
    public static Platform getPlatform() {
        return DiscordCore.getInstance().getPlatform();
    }

    /**
     * Gets the CommandSettings from the Core and returns it
     *
     * @return CommandSettings
     */
    public static CommandSettings getCommandSettings() {
        return DiscordCore.getInstance().getCommandSettings();
    }

    /**
     * Used to get a message object from a channel and message id
     *
     * @param channelID Channel ID
     * @param messageID Message ID
     *
     * @return Message
     */
    public static Message getMessage(String channelID, String messageID) {
        TextChannel textChannel = getBot().getTextChannelById(channelID);
        if(textChannel != null) {
            return textChannel.retrieveMessageById(messageID).complete();
        }
        return null;
    }

    /**
     * Checks if the user has the permission
     *
     * @param user Discord Member
     * @param permission Permission
     */
    public static void hasPermission(Member user, String permission) {
        new DiscordUser(user).hasPermission(permission);
    }

    /**
     * Checks if the user has the permission
     *
     * @param guild Discord Guild
     * @param user Discord User ID
     * @param permission Permission
     */
    public static void hasPermission(Guild guild, String user, String permission) {
        new DiscordUser(getMember(guild, user)).hasPermission(permission);
    }

    /**
     * Gets the user from a string ID
     *
     * @param id Discord User ID
     *
     * @return User
     */
    public static User getUser(String id) {
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

    /**
     * Gets the prefix from the Core settings and returns it
     *
     * @return String
     */
    public static String getPrefix() {
        return getCoreSetting().getCommandPrefix();

    }

    /**
     * Gets a group from the core with a name or id
     *
     * @param guild Discord Guild
     * @param name Group Name
     *
     * @return Group
     */
    public static Group getGroup(Guild guild, String name) {
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

    /**
     * Gets a member from a guild with a string ID
     *
     * @param guild Discord Guild
     * @param id Member ID
     *
     * @return Member
     */
    public static Member getMember(Guild guild, String id) {
        User user = getUser(id);

        if(user != null) {
            return guild.getMember(user);
        }

        return null;
    }

    /**
     * Returns the built color from the Discord Core for embeds and such
     *
     * @return Color
     */
    public static Color getColor() {
        return Color.decode(getCoreSetting().getEmbedColor());
    }

    /**
     * Send a private message to a user. Responds true if sent, and responds
     * false if it runs into any errors while sending the private message.
     *
     * @param user User
     * @param embedBuilder EmbedBuilder
     *
     * @return boolean
     */
    public static boolean sendPM(User user, EmbedBuilder embedBuilder) {
        try {
            user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).queue();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * Send a private message to a user. Responds true if sent, and responds
     * false if it runs into any errors while sending the private message.
     *
     * @param user User
     * @param string Message
     *
     * @return boolean
     */
    public static boolean sendPM(User user, String string) {
        try {
            user.openPrivateChannel().complete().sendMessage(string).queue();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}

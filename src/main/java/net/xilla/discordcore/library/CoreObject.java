package net.xilla.discordcore.library;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.xilla.core.library.XillaLibrary;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.command.ServerSettings;
import net.xilla.discordcore.core.permission.PermissionAPI;
import net.xilla.discordcore.core.CoreSettings;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.core.permission.group.DiscordGroup;
import net.xilla.discordcore.core.permission.group.GroupManager;
import net.xilla.discordcore.core.permission.user.UserManager;
import net.xilla.discordcore.library.embed.JSONEmbed;
import net.xilla.discordcore.module.ModuleManager;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.Date;
import java.util.List;

public interface CoreObject extends XillaLibrary {

    default DiscordCore getDiscordCore() {
        return DiscordCore.getInstance();
    }

    default GroupManager getGroupManager() {
        return DiscordAPI.getGroupManager();
    }

    default UserManager getUserManager() {
        return DiscordAPI.getUserManager();
    }

    default ModuleManager getModuleManager() {
        return Platform.getInstance().getModuleManager();
    }

    default CoreSettings getCoreSetting() {
        return DiscordCore.getInstance().getSettings();
    }

    default JDA getBot() {
        return DiscordCore.getInstance().getBot();
    }

    default Platform getPlatform() {
        return Platform.getInstance();
    }

    default ServerSettings getServerSettings() {
        return Platform.getInstance().getServerSettings();
    }

    default Message getMessage(String channelID, String messageID) {
        TextChannel textChannel = getBot().getTextChannelById(channelID);
        if(textChannel != null) {
            return textChannel.retrieveMessageById(messageID).complete();
        }
        return null;
    }

    default void hasPermission(Member user, String permission) {
        PermissionAPI.hasPermission(user, permission);
    }

    default void hasPermission(Guild guild, String user, String permission) {
        PermissionAPI.hasPermission(getMember(guild, user), permission);
    }

    default User getUser(String id) {
        return DiscordAPI.getUser(id);
    }

    default Role getRole(String id) {
        Role role = null;
        String roleID = id.replace("<@&", "").replace("<@", "").replace(">", "");
        try {
            role = getBot().getRoleById(roleID);
        } catch (Exception ignored) {}

        if(role == null) {
            try {
                role = getBot().getRolesByName(id, true).get(0);
            } catch (Exception ignored) {
            }
        }
        return role;
    }

    default String getPrefix() {
        return getCoreSetting().getCommandPrefix();

    }

    default DiscordGroup getGroup(Guild guild, String name) {
        DiscordGroup group = getGroupManager().getManager(guild).get(name.replace("<@&", "").replace("<@", "").replace(">", ""));

        if(group != null) {
            return group;
        }

        List<DiscordGroup> groups = getGroupManager().getGroupsByName(name);
        if(groups != null) {
            for (DiscordGroup loopGroup : groups) {
                if (loopGroup.getServerID().equalsIgnoreCase(guild.getId())) {
                    return loopGroup;
                }
            }
        }

        return null;
    }

    default Member getMember(Guild guild, String id) {
        return DiscordAPI.getMember(guild, id);
    }

    /**
     * Deprecated to warn that this is not always 100% accurate and should only be used in admin situations
     */
    @Deprecated
    default Guild getGuild(String name) {
        Guild guild = null;

        try {
            guild = getBot().getGuildById(name);
        } catch (Exception ex) {}

        if(guild != null) {
            return guild;
        }

        List<Guild> guilds = getBot().getGuildsByName(name, false);
        if(guilds.size() > 0) {
            return guilds.get(0);
        }

        guilds = getBot().getGuildsByName(name, true);
        if(guilds.size() > 0) {
            return guilds.get(0);
        }

        return guild;
    }

    default Color getColor() {
        return Color.decode(getCoreSetting().getEmbedColor());
    }

    default Color getColor(String guildID) {
        return Platform.getInstance().getServerSettings().getColor(guildID);
    }

    default Color getColor(Guild guild) {
        return Platform.getInstance().getServerSettings().getColor(guild);
    }

    default boolean sendPM(User user, EmbedBuilder embedBuilder) {
        try {
            user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).queue();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    default boolean sendPM(User user, String string) {
        try {
            user.openPrivateChannel().complete().sendMessage(string).queue();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    default EmbedBuilder getEmbed(MessageReceivedEvent event) {
        JSONEmbed jsonEmbed = new JSONEmbed("default", (JSONObject)getServerSettings().get(event.getGuild(), "core-embed"));
        EmbedBuilder embedBuilder = jsonEmbed.getEmbedBuilder();

        MessageEmbed embed = embedBuilder.build();
        if(embed.getFooter() != null && embed.getFooter().getText() != null) {
            embedBuilder.setFooter(embed.getFooter().getText().replace("%user%", event.getAuthor().getAsTag()).replace("%date%", new Date().toString()));
        }
        embedBuilder.setColor(getColor(event.getGuild()));
        return embedBuilder;
    }

    default EmbedBuilder getEmbed(String user, Guild guild) {
        JSONEmbed jsonEmbed = new JSONEmbed("default", (JSONObject)getServerSettings().get(guild, "core-embed"));
        EmbedBuilder embedBuilder = jsonEmbed.getEmbedBuilder();

        MessageEmbed embed = embedBuilder.build();
        if(embed.getFooter() != null && embed.getFooter().getText() != null) {
            embedBuilder.setFooter(embed.getFooter().getText().replace("%user%", user).replace("%date%", new Date().toString()));
        }
        embedBuilder.setColor(getColor(guild));
        return embedBuilder;
    }

    default EmbedBuilder getEmbed(String user, String guild) {
        JSONEmbed jsonEmbed = new JSONEmbed("default", (JSONObject)getServerSettings().get(guild, "core-embed"));
        EmbedBuilder embedBuilder = jsonEmbed.getEmbedBuilder();

        MessageEmbed embed = embedBuilder.build();
        if(embed.getFooter() != null && embed.getFooter().getText() != null) {
            embedBuilder.setFooter(embed.getFooter().getText().replace("%user%", user).replace("%date%", new Date().toString()));
        }
        embedBuilder.setColor(getColor(guild));
        return embedBuilder;
    }


}

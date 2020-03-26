package net.xilla.discordcore.api;

import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.api.config.ConfigManager;

import java.awt.*;
import java.util.List;

public class Data {

    public String getVersion() {
        return "Xilla Discord Core v1.0.4";
    }

    public String getLineBreak() {
        return "-----------------------------------------------------------------";
    }

    public String parseStringList(int start, List<String> stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.size(); i++) {
            if(i == stringArrayList.size() - 1)
                parsedString.append(stringArrayList.get(i));
            else
                parsedString.append(stringArrayList.get(i)).append(", ");
        }
        return parsedString.toString();
    }

    public String parseStringArray(int start, String[] stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.length; i++) {
            if(i == stringArrayList.length - 1)
                parsedString.append(stringArrayList[i]);
            else
                parsedString.append(stringArrayList[i]).append(", ");
        }
        return parsedString.toString();
    }

    public String parseStringListNoDelimiter(int start, List<String> stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.size(); i++) {
            if(i == stringArrayList.size() - 1)
                parsedString.append(stringArrayList.get(i));
            else
                parsedString.append(stringArrayList.get(i)).append(" ");
        }
        return parsedString.toString();
    }

    public String parseStringArrayNoDelimiter(int start, String[] stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.length; i++) {
            if(i == stringArrayList.length - 1)
                parsedString.append(stringArrayList[i]);
            else
                parsedString.append(stringArrayList[i]).append(" ");
        }
        return parsedString.toString();
    }

    public EmbedBuilder createEmbed(String title, String description) {
        EmbedBuilder myEmbed = new EmbedBuilder();
        myEmbed.setTitle(title);
        if(description != null)
            myEmbed.setDescription(description);
        myEmbed.setColor(Color.decode(DiscordCore.getInstance().getConfigManager().getConfig("settings.json").getString("embedColor")));
        return myEmbed;
    }

}

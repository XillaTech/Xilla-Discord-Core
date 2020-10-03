package com.tobiassteely.utility.punishment;

import com.tobiassteely.tobiasapi.api.worker.Worker;
import com.tobiassteely.utility.UtilityBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.xilla.discordcore.DiscordCore;

import java.awt.*;
import java.util.HashMap;

public class PunishmentWorker extends Worker {

    public PunishmentWorker() {
        super("UTB.Punishment", 60000);
    }

    @Override
    public Boolean runWorker(long start) {

        HashMap<String, Integer> map = new HashMap<>();

        for(Punishment punishment : UtilityBot.getInstance().getPunishmentManager().getList()) {
            if(punishment.getType().equalsIgnoreCase("mute")) {
                if(!map.containsKey(punishment.getUserID())) {
                    map.put(punishment.getUserID(), 1);
                } else {
                    map.put(punishment.getUserID(), map.get(punishment.getUserID()) + 1);
                }
            }
            if(punishment.getDuration() > 0) {
                if(System.currentTimeMillis() - punishment.getStartTime() >= punishment.getDuration() * 1000) {
                    if(punishment.getType().equalsIgnoreCase("mute")) {
                        User user = DiscordCore.getInstance().getBot().getUserById(punishment.getUserID());
                        Guild guild = DiscordCore.getInstance().getBot().getGuildById(punishment.getGuildID());
                        if(user != null && guild != null) {
                            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Unmuted!");
                            embedBuilder.setDescription("Your mute in " + guild.getName() + " has expired!");
                            embedBuilder.setColor(Color.decode(DiscordCore.getInstance().getSettings().getEmbedColor()));
                            user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).queue();
                        }
                        map.put(punishment.getUserID(), map.get(punishment.getUserID()) - 1);
                    } else if(punishment.getType().equalsIgnoreCase("ban")) {
                        Guild guild = DiscordCore.getInstance().getBot().getGuildById(punishment.getGuildID());
                        if(guild != null) {
                            guild.unban(punishment.getUserID()).queue();
                        }
                    }
                    UtilityBot.getInstance().getPunishmentManager().removeObject(punishment.getKey());
                    UtilityBot.getInstance().getPunishmentManager().save();
                }
            }

            for(String userID : map.keySet()) {
                if(map.get(userID) == 0) {
                    UtilityBot.getInstance().getPunishmentSettings().removeMutedUser(userID);
                }
            }
        }

        return true;
    }

}

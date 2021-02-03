package net.xilla.discordcore.startup;

import net.dv8tion.jda.api.JDABuilder;

public interface StartupExecutor {

    JDABuilder run(JDABuilder builder);

}

package net.xilla.discordcore.library.embed.menu;

import net.dv8tion.jda.api.entities.Member;

public interface MenuAction {

    void run(Member member, String input);

}

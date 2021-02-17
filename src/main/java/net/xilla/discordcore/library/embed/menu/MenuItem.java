package net.xilla.discordcore.library.embed.menu;

import lombok.Getter;
import net.xilla.core.library.manager.StoredData;

public class MenuItem {

    @Getter
    @StoredData
    private MenuAction action;

    @Getter
    @StoredData
    private String name;

    @Getter
    @StoredData
    private String emoji;

    public MenuItem(String name, String emoji, MenuAction action) {
        this.name = name;
        this.emoji = emoji;
        this.action = action;
    }

}

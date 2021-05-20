package net.xilla.discord.api.input;

import net.dv8tion.jda.api.entities.User;

public interface UserInput {

    User getUser();

    String getInput();

}
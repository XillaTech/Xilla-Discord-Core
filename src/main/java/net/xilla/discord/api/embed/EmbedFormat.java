package net.xilla.discord.api.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.xilla.core.library.manager.ObjectInterface;

/**
 * An embed format system that allows doe it to
 * be serialized and customized by the end users
 */
public interface EmbedFormat extends ObjectInterface {

    /**
     * Used to get and update the builder.
     *
     * @return Embed Builder
     */
    EmbedBuilder getBuilder();

    /**
     * Used to entirely replace the embed builder.
     *
     * @param builder Embed Builder
     */
    void setBuilder(EmbedBuilder builder);

    /**
     * Returns the name as a string
     *
     * @return Name
     */
    default String getName() {
        return toString();
    }

    /**
     * Returns the built embed format
     *
     * @return Message Embed
     */
    MessageEmbed build();

    /**
     * Returns a built embed format with placeholders
     * applied for the user.
     *
     * @param member Discord Member
     * @return Message Embed
     */
    MessageEmbed buildTemplate(Member member);

}

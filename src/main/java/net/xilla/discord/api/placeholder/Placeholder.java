package net.xilla.discord.api.placeholder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.xilla.core.library.NotNull;
import net.xilla.core.library.Nullable;
import net.xilla.core.library.manager.ObjectInterface;

public interface Placeholder extends ObjectInterface {

    String getName();

    /**
     * Used to automatically inject information into a string.
     *
     * @param member Member (Can be null)
     * @param string Message to inject into
     * @return Injected String
     */
    default String inject(@Nullable Member member, @NotNull String string) {
        if(member == null) {
            return injectSystem(string);
        } else {
            return injectUser(member, string);
        }
    }

    /**
     * Used to automatically inject information into an EmbedBuilder.
     *
     * @param member Member (Can be null)
     * @param builder EmbedBuilder to inject into
     * @return Injected Embed Builder
     */
    default EmbedBuilder inject(@Nullable Member member, @NotNull EmbedBuilder builder) {
        if(member == null) {
            return injectSystem(builder);
        } else {
            return injectUser(member, builder);
        }
    }

    /**
     * Used to safely handle system placeholder injection.
     *
     * @param string Message
     * @return Injected Message
     */
    String injectSystem(@NotNull String string);

    /**
     * Used to safely handle user placeholder injection.
     *
     * @param string Message
     * @return Injected Message
     */
    String injectUser(@NotNull Member member, @NotNull String string);

    /**
     * Used to safely handle system placeholder injection.
     *
     * @param builder Embed Builder
     * @return Injected Embed Builder
     */
    EmbedBuilder injectSystem(@NotNull EmbedBuilder builder);

    /**
     * Used to safely handle user placeholder injection.
     *
     * @param builder Embed Builder
     * @return Injected Embed Builder
     */
    EmbedBuilder injectUser(@NotNull Member member, @NotNull EmbedBuilder builder);

}

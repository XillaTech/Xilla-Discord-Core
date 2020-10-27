package net.xilla.community;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.xilla.community.economy.EconomyManager;
import net.xilla.community.giveaway.GiveawayCommands;
import net.xilla.community.giveaway.GiveawayManager;
import net.xilla.community.invite.InviteManager;
import net.xilla.community.punishment.PunishmentManager;
import net.xilla.community.reaction.ReactionManager;
import net.xilla.community.review.ReviewManager;
import net.xilla.community.suggestion.SuggestionManager;
import net.xilla.discordcore.DiscordCore;
import net.xilla.discordcore.core.Platform;
import net.xilla.discordcore.module.JavaModule;

public class CommunityBot extends JavaModule {

    private static CommunityBot instance;

    public static CommunityBot getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        // Loads the discord library and handlers
        new DiscordCore(Platform.getPlatform.EMBEDDED.name, null, false, "Xilla Community");

        // Starts the bot
        CommunityBot ticketCore = new CommunityBot();
        ticketCore.onEnable();
        ticketCore.getCommandManager().getCommandWorker().start();
    }

    public CommunityBot() {
        super("Xilla Community", "1.0.0");
    }

    @Getter
    private GiveawayManager giveawayManager;

    @Getter
    private EconomyManager economyManager;

    @Getter
    private InviteManager inviteManager;

    @Getter
    private PunishmentManager punishmentManager;

    @Getter
    private ReactionManager reactionManager;

    @Getter
    private ReviewManager reviewManager;

    @Getter
    private SuggestionManager suggestionManager;

    @Override
    public void onEnable() {
        instance = this;
        this.giveawayManager = new GiveawayManager();
        new GiveawayCommands();

        this.economyManager = new EconomyManager();

        this.inviteManager = new InviteManager();

        this.punishmentManager = new PunishmentManager();

        this.reactionManager = new ReactionManager();

        this.reviewManager = new ReviewManager();

        this.suggestionManager = new SuggestionManager();

    }

    @Override
    public void onDisable() {

    }

}

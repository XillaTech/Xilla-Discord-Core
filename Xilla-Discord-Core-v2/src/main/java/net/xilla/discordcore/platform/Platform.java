package net.xilla.discordcore.platform;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import com.tobiassteely.tobiasapi.command.CommandPermissionError;
import com.tobiassteely.tobiasapi.command.response.CommandResponder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.discordcore.CoreObject;
import net.xilla.discordcore.command.cmd.HelpCommand;
import net.xilla.discordcore.command.cmd.ModulesCommand;
import net.xilla.discordcore.command.response.CoreCommandResponder;
import net.xilla.discordcore.command.response.CoreCommandResponse;
import net.xilla.discordcore.command.template.TemplateManager;
import net.xilla.discordcore.staff.StaffManager;
import net.xilla.discordcore.command.cmd.StaffCommand;

import java.awt.*;

public class Platform extends CoreObject {

    private String type;
    private StaffManager staffManager;
    private TemplateManager templateManager;

    public Platform(String type) {
        this.type = type;
        this.staffManager = new StaffManager();

        getCommandManager().setResponder(new CoreCommandResponder());
        this.templateManager = new TemplateManager();

        new ModulesCommand();
        new StaffCommand();
        new HelpCommand();

        getCommandManager().setPermissionError((args, data) -> {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Error!");
            builder.setDescription("You do not have permission for that command!");
            builder.setColor(Color.decode(getCoreSetting().getEmbedColor()));

            return new CoreCommandResponse(data).setEmbed(builder.build());
        });
    }

    public String getType() {
        return type;
    }

    public StaffManager getStaffManager() {
        return staffManager;
    }

    public enum getPlatform {
        // Available platforms
        BUNGEE("BUNGEE"),
        SPIGOT("SPIGOT"),
        STANDALONE("STANDALONE");

        public String name;

        getPlatform(String str) {
            this.name = str;
        }
    }

    public TemplateManager getTemplateManager() {
        return templateManager;
    }
}

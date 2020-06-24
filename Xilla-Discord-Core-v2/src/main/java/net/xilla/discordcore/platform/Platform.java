package net.xilla.discordcore.platform;

import com.tobiassteely.tobiasapi.api.TobiasObject;
import net.xilla.discordcore.command.response.CoreCommandResponder;
import net.xilla.discordcore.command.template.TemplateManager;
import net.xilla.discordcore.module.cmd.ModulesCommand;
import net.xilla.discordcore.staff.StaffManager;
import net.xilla.discordcore.staff.cmd.StaffCommand;

public class Platform extends TobiasObject {

    private String type;
    private StaffManager staffManager;
    private TemplateManager templateManager;

    public Platform(String type) {
        this.type = type;
        this.staffManager = new StaffManager();

        getCommandManager().setResponder(new CoreCommandResponder());
        this.templateManager = new TemplateManager();

        //new ModulesCommand();
        getCommandManager().registerCommand(new StaffCommand().build());
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

package net.xilla.discordcore.core.command.flag;

import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.core.log.LogLevel;
import net.xilla.core.log.Logger;

public class CommandFlag extends ManagerObject {

    private String[] identifier;

    public CommandFlag(String key, String... identifier) {
        super(key, "FlagManager");
        this.identifier = identifier;
        if(identifier.length == 0) {
            Logger.log(LogLevel.ERROR, "Command flag (" + key + ") was created with no identifiers!", this.getClass());
        }
    }

    public String[] getIdentifier() {
        return identifier;
    }

    @Override
    public XillaJson getSerializedData() {
        return null;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {

    }
}

package com.tobiassteely.utility.punishment;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import org.json.simple.JSONObject;

public class PunishmentManager extends ManagerParent<Punishment> {

    public PunishmentManager() {
        super("UTB.Punishment", true, "modules/Utility/punishments.json", new PunishmentManagerHandler());
    }

}

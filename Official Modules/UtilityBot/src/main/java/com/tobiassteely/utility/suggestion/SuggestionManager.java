package com.tobiassteely.utility.suggestion;

import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import org.json.simple.JSONObject;

public class SuggestionManager extends ManagerParent<Suggestion> {

    public SuggestionManager() {
        super("UTB.Suggestion", true, "modules/Utility/suggestions.json", new SuggestionManagerHandler());
    }

}

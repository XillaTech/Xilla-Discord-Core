package com.tobiassteely.utility.suggestion;

import net.xilla.core.library.manager.Manager;
import org.json.simple.JSONObject;

public class SuggestionManager extends Manager<Suggestion> {

    public SuggestionManager() {
        super("UTB.Suggestion", "modules/Utility/suggestions.json");
    }

    @Override
    protected void load() {
        for(Object obj : getData().values()) {
            put(new Suggestion((JSONObject)obj));
        }
    }

    @Override
    protected void objectAdded(Suggestion suggestion) {

    }

    @Override
    protected void objectRemoved(Suggestion suggestion) {

    }

}

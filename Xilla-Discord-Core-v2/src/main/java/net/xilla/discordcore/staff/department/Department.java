package net.xilla.discordcore.staff.department;

import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Department extends ManagerObject {

    private String description;
    private ArrayList<String> groupNames;

    public Department(String name, String description, ArrayList<String> groupNames) {
        super(name);
        this.description = description;

        if(groupNames != null) {
            this.groupNames = new ArrayList<>(groupNames);
        } else {
            this.groupNames = new ArrayList<>();
        }
    }

    public Department(Map<String, String> map) {
        super(map.get("name"));
        this.description = map.get("description");
        this.groupNames = new ArrayList<>();
        this.groupNames.addAll(Arrays.asList(map.get("groupIDs").split(",")));
    }

    public JSONObject toJson() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", getKey());
        map.put("description", description);

        StringBuilder groupIDsParsed = new StringBuilder();
        for(int i = 0; i < groupNames.size(); i++) {
            groupIDsParsed.append(groupNames.get(i));
            if(i != groupNames.size() - 1) {
                groupIDsParsed.append(",");
            }
        }

        map.put("groupIDs", groupIDsParsed.toString());
        return new JSONObject(map);
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getGroupNames() {
        return groupNames;
    }
}

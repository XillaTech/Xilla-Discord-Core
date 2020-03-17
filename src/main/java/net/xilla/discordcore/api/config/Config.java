package net.xilla.discordcore.api.config;

import net.xilla.discordcore.api.Log;
import net.xilla.discordcore.api.manager.ManagerObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Config extends ManagerObject {

    private String file;
    private JSONObject json;

    public Config(String file) {
        super(file, null);
        this.file = file;
        reload();
    }

    private void reload() {
        File fileObject = new File(file);
        json = new JSONObject();
        if(fileObject.exists()) {
            JSONParser parser = new JSONParser();
            try {
                FileReader fileReader = new FileReader(file);
                json = (JSONObject)parser.parse(fileReader);
                fileReader.close();
            } catch (Exception e) {
            }
        } else {
            try {
                if(fileObject.getParentFile() != null)
                    fileObject.getParentFile().mkdirs();
                fileObject.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(formatJSONStr());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFile() {
        return file;
    }

    public boolean loadDefault(String key, String value) {
        if(!json.containsKey(key)) {
            json.put(key, value);
            return true;
        }
        return false;
    }

    public boolean loadDefault(String key, JSONObject value) {
        if(!json.containsKey(key)) {
            json.put(key, value);
            return true;
        }
        return false;
    }

    public boolean loadDefault(String key, int value) {
        if(!json.containsKey(key)) {
            json.put(key, value);
            return true;
        }
        return false;
    }

    public boolean loadDefault(String key, double value) {
        if(!json.containsKey(key)) {
            json.put(key, value);
            return true;
        }
        return false;
    }

    public boolean loadDefault(String key, float value) {
        if(!json.containsKey(key)) {
            json.put(key, value);
            return true;
        }
        return false;
    }

    public boolean loadDefault(String key, long value) {
        if(!json.containsKey(key)) {
            json.put(key, value);
            return true;
        }
        return false;
    }

    public boolean loadDefault(String key, boolean value) {
        if(!json.containsKey(key)) {
            json.put(key, value);
            return true;
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        return json;
    }

    public Object get(String key) {
        return json.get(key);
    }

    public String getString(String key) {
        return (String)json.get(key);
    }

    public int getInt(String key) {
        return (int)json.get(key);
    }

    public double getDouble(String key) {
        return (double)json.get(key);
    }

    public float getFloat(String key) {
        return (float)json.get(key);
    }

    public long getLong(String key) {
        return (long)json.get(key);
    }

    public boolean getBoolean(String key) {
        return (boolean)json.get(key);
    }

    public JSONObject getJSON(String key) {
        return (JSONObject)json.get(key);
    }

    public Map<String, String> getMap(String key) {
        return ((Map<String, String>)json.get(key));
    }

    public void clear() {
        json = new JSONObject();
    }

    private String formatJSONStr() {
        final String json_str = json.toJSONString();
        final int indent_width = 1;
        final char[] chars = json_str.toCharArray();
        final String newline = System.lineSeparator();

        String ret = "";
        boolean begin_quotes = false;

        for (int i = 0, indent = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c == '\"') {
                ret += c;
                begin_quotes = !begin_quotes;
                continue;
            }

            if (!begin_quotes) {
                switch (c) {
                    case '{':
                    case '[':
                        ret += c + newline + String.format("%" + (indent += indent_width) + "s", "");
                        continue;
                    case '}':
                    case ']':
                        ret += newline + ((indent -= indent_width) > 0 ? String.format("%" + indent + "s", "") : "") + c;
                        continue;
                    case ':':
                        ret += c + " ";
                        continue;
                    case ',':
                        ret += c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
                        continue;
                    default:
                        if (Character.isWhitespace(c)) continue;
                }
            }

            ret += c + (c == '\\' ? "" + chars[++i] : "");
        }

        return ret;
    }

    public void set(Object key, Object value) {
        json.put(key, value);
    }
}

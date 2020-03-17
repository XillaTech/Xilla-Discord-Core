package net.xilla.discordcore.api.manager;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerParent {

    private ArrayList<ManagerObject> list = null;
    private HashMap<String, ManagerCache> cacheList = null;

    protected ManagerParent() {
        reload();
    }

    public ArrayList<ManagerObject> getList() {
        return list;
    }

    protected void addCache(String key, ManagerCache cache) {
        cacheList.put(key, cache);
    }

    protected ManagerCache getCache(String key) {
        return cacheList.get(key);
    }

    protected void addObject(ManagerObject object) {
        list.add(object);
        getCache("key").putObject(object.getKey(), object);
    }

    protected Object getObjectWithKey(String key) {
        return getCache("key").getObject(key);
    }

    protected void reload() {
        list = new ArrayList<>();
        cacheList = new HashMap<>();
        cacheList.put("key", new ManagerCache());
    }

    public void removeObject(String key) {
        list.remove(cacheList.get("key").getObject(key));
        cacheList.get("key").removeObject(key);
    }

}

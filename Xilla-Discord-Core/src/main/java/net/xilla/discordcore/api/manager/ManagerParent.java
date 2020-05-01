package net.xilla.discordcore.api.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ManagerParent {

    private Vector<ManagerObject> list = null;
    private ConcurrentHashMap<String, ManagerCache> cacheList = null;

    protected ManagerParent() {
        reload();
    }

    public Vector<ManagerObject> getVector() {
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
        list = new Vector<>();
        cacheList = new ConcurrentHashMap<>();
        cacheList.put("key", new ManagerCache());
    }

    public void removeObject(String key) {
        list.remove(cacheList.get("key").getObject(key));
        cacheList.get("key").removeObject(key);
    }

}

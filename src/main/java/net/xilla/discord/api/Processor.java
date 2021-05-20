package net.xilla.discord.api;

import net.xilla.core.library.Nullable;
import net.xilla.core.library.manager.ObjectInterface;

import java.util.List;

/**
 * A basic interface to add and remove data
 * from a higher-level processor.
 */
public interface Processor<Key, Value extends ObjectInterface> {

    /**
     * Attempts to grab the object from the processor and returns it.
     *
     * @param name Object Name
     * @return Object
     */
    @Nullable
    Value get(String name);

    /**
     * Used to get a list of objects from the processor
     *
     * @return List of Objects
     */
    List<Value> listObjects();

    /**
     * Puts the object into the implementation storage
     *
     * @param object Object
     */
    void putObject(Value object);

    /**
     * Attempts to grab the object from the processor.
     * If the object with that key does not exist, the
     * function returns null. Otherwise it will attempt
     * to remove it from the processor then it will
     * return the item.
     *
     * @param name Object Name
     * @return Object
     */
    @Nullable
    default Value removeObject(String name) {
        Value item = get(name);

        if(item == null) return null;

        removeObject(item);
        return item;
    }

    /**
     * Used to remove an object from the processor
     *
     * @param object Object
     */
    void removeObject(Value object);

    /**
     * Used to save the processor
     */
    void save();

}

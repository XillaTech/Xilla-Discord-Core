package net.xilla.discord.api;

import net.xilla.core.library.Nullable;

import java.util.List;

/**
 * A basic interface to add and remove data
 * from a higher-level processor.
 */
public interface Processor<T> {

    /**
     * Attempts to grab the object from the processor and returns it.
     *
     * @param name Object Name
     * @return Object
     */
    @Nullable
    T get(String name);

    /**
     * Used to get a list of objects from the processor
     *
     * @return List of Objects
     */
    List<T> listObjects();

    /**
     * Puts the object into the implementation storage
     *
     * @param object Object
     */
    void putObject(T object);

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
    default T removeObject(String name) {
        T item = get(name);

        if(item == null) return null;

        removeObject(item);
        return item;
    }

    /**
     * Used to remove an object from the processor
     *
     * @param object Object
     */
    void removeObject(T object);

}

package net.xilla.discordcore.api.worker;

import net.xilla.discordcore.api.Log;

import java.util.Vector;

public class ObjectWorker extends Worker {

    private Vector<Object> queue;

    public boolean processItem(Object object) {
        return false; // OVERRIDE
    }

    public ObjectWorker(long timer) {
        super(timer);
        this.queue = new Vector<>();
    }

    @Override
    public Boolean runWorker(long start) {
        while (true) {

            if(System.currentTimeMillis() - start >= getTimer())
                break;

            if(queue.size() == 0)
                break;

            Object object = queue.get(0);
            if(!processItem(object)) {
                Log.sendMessage(2, "Killing the worker, setup wrong.");
                stopWorker();
                break;
            } else {
                queue.remove(0);
            }
        }
        return true;
    }


    public void addObject(Object object) {
        queue.add(object);
    }

    public Object getObject() {
        if(queue.size() > 0)
            return queue.remove(0);
        else return null;
    }
}

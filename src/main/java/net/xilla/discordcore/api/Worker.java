package net.xilla.discordcore.api;

import java.util.ArrayList;

public class Worker {

    private ArrayList<Object> queue;
    private long timer;
    private Thread thread;
    private long start;
    private ArrayList<Long> ticks;

    public boolean processItem(Object object) {
        return false; // OVERRIDE
    }

    public Worker(long timer) {
        this.queue = new ArrayList<>();
        this.ticks = new ArrayList<>();
        this.timer = timer;
        this.start = 0;
        thread = new Thread(() -> {
            long lastLoop = 0;
            while (true) {
                while (true) {
                    if ((System.currentTimeMillis() - lastLoop) >= timer)
                        break;
                    if (queue.size() == 0)
                        break;

                    processItem(getObject());
                }
                lastLoop = System.currentTimeMillis();

            }
        });
    }

    public void addObject(Object object) {
        queue.add(object);
    }

    public Object getObject() {
        if(queue.size() > 0)
            return queue.remove(0);
        else return null;
    }

    private void tick() {
        if(start == 0) {
            start = System.currentTimeMillis();
        } else {
            ticks.add(System.currentTimeMillis() - start);
            if(ticks.size() > 50)
                ticks.remove(0);
        }
    }

    public double[] getTPS() {
        ArrayList<Long> temp = new ArrayList<>();
        temp.addAll(ticks);

        long totalTime = 0;
        for(long tick : ticks) {
            totalTime += tick;
        }
        double averageTime = (double)totalTime / temp.size();
        return new double[] {1 / averageTime, temp.size()};
    }


    public String getStatus() {
        double[] tpsResults = getTPS();
        return "Running at " + tpsResults[0] + "TPS, expected TPS is " + (1 / timer) + " TPS (n = " + tpsResults[1] + ")";
    }
}

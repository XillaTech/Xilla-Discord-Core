package net.xilla.discordcore.api.worker;

import net.xilla.discordcore.api.Log;

import java.util.ArrayList;

public class Worker extends Thread {

    private long timer;
    private long start;
    private ArrayList<Long> ticks;
    private boolean isAlive;
    private boolean isPaused;

    public Worker(long timer) {
        this.ticks = new ArrayList<>();
        this.timer = timer;
        this.start = 0;
        this.isAlive = false;
        this.isPaused = false;
    }

    public Boolean runWorker(long start) {
        // OVERRIDE
        return false;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    @Override
    public void run() {
        long lastLoop = 0;
        isAlive = true;
        while (true) {
            if(!isAlive)
                break;

            if(isPaused) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {}
                continue;
            }

            if ((System.currentTimeMillis() - lastLoop) < timer) {
                if(!runWorker(System.currentTimeMillis())) {
                    Log.sendMessage(2, "Worker is not overridden! Stopping to preserve CPU.");
                    break;
                }
                tick();
            }
            try {
                long sleep = lastLoop + timer - System.currentTimeMillis() - 1;
                if(sleep > 0)
                    Thread.sleep(sleep);
            } catch (InterruptedException ex) {}

            lastLoop = System.currentTimeMillis();
        }
        isAlive = false;
    }

    public void stopWorker() {
        isAlive = false;
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

    public boolean isWorkerAlive() {
        return isAlive;
    }

    public long getTimer() {
        return timer;
    }
}

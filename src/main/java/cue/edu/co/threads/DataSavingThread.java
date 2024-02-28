package cue.edu.co.threads;

import java.util.concurrent.Callable;

public class DataSavingThread implements Callable<Void> {
    private final Runnable runnable;
    private final int maxRetries;
    private final long retryInterval;

    public DataSavingThread(Runnable runnable, int maxRetries, long retryInterval) {
        this.runnable = runnable;
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
    }

    @Override
    public Void call() throws Exception {
        int retries = 0;
        boolean saved = false;

        while (!saved && retries < maxRetries) {
            try {
                runnable.run();
                saved = true;
            } catch (Exception e) {
                System.err.println("Error saving data: " + e.getMessage());
                retries++;
                if (retries < maxRetries) {
                    System.out.println("Retrying in " + retryInterval + " milliseconds...");
                    Thread.sleep(retryInterval);
                }
            }
        }

        if (!saved) {
            System.err.println("Failed to save data after " + maxRetries + " retries.");
        }

        return null;
    }
}
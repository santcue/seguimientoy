package cue.edu.co.threads;

import cue.edu.co.service.impl.ToyStoreImpl;

public class DataLoadingThread extends Thread {
    private final ToyStoreImpl toyStore;

    public DataLoadingThread(ToyStoreImpl toyStore) {
        this.toyStore = toyStore;
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting data loading...");
            Thread.sleep(2000);
            ToyStoreImpl.loadStore();
            System.out.println("Data loaded successfully.");
        } catch (InterruptedException e) {
            System.err.println("Data loading thread interrupted.");
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}

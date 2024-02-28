package cue.edu.co.threads;

import cue.edu.co.service.impl.ToyStoreImpl;

import java.util.concurrent.Callable;

public class DataLoadingThread implements Callable<Void> {
    private final ToyStoreImpl toyStore;

    public DataLoadingThread (ToyStoreImpl toyStore) {
        this.toyStore = toyStore;
    }

    @Override
    public Void call() throws Exception {
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
        return null;
    }
}

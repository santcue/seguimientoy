package cue.edu.co.threads;

import cue.edu.co.service.impl.ToyStoreImpl;

import java.sql.Connection;
import java.util.concurrent.Callable;

public class DataLoadingThread implements Callable<Void> {
    private final Connection conn;

    public DataLoadingThread(Connection connection) {
        this.conn = connection;
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

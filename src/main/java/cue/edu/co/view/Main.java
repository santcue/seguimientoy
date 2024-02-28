package cue.edu.co.view;

import cue.edu.co.mapping.dtos.ToyDto;
import cue.edu.co.model.Toy;
import cue.edu.co.service.ToyStore;
import cue.edu.co.service.impl.ToyStoreImpl;
import cue.edu.co.threads.DataLoadingThread;
import cue.edu.co.threads.DataSavingThread;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ToyStoreImpl toyStore = ToyStoreImpl.loadStore();
        boolean continueExecution = true;

        ExecutorService executor = Executors.newFixedThreadPool(2);

        DataLoadingThread loadingThread = new DataLoadingThread(toyStore);
        Future<Void> loadingFuture = executor.submit(loadingThread);

        DataSavingThread savingThread = new DataSavingThread(() -> toyStore.saveData(), 3, 1000);
        Future<Void> savingFuture = executor.submit(savingThread);

        while (continueExecution) {
            try {
                String option = JOptionPane.showInputDialog(
                        "Select an option:\n" +
                                "1. Add toy\n" +
                                "2. Show quantity of toys by type\n" +
                                "3. Show total quantity of toys\n" +
                                "4. Show total value of toys\n" +
                                "5. Decrease stock of a toy\n" +
                                "6. Increase stock of a toy\n" +
                                "7. Show type with most toys\n" +
                                "8. Show type with least toys\n" +
                                "9. Get toys with value greater than\n" +
                                "10. Sort stock by type\n" +
                                "11. Exit"
                );

                if (option != null) {
                    switch (option) {
                        case "1":
                            String name = JOptionPane.showInputDialog("Enter the name of the toy:");
                            char type = JOptionPane.showInputDialog("Enter the type of the toy (O, 1, 2):").charAt(0);
                            double price = Double.parseDouble(JOptionPane.showInputDialog("Enter the price of the toy:"));
                            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity of the toy:"));

                            toyStore.addToy(new ToyDto(name, type, price, quantity));
                            break;
                        case "2":
                            Map<Character, Integer> quantityByType = toyStore.countToysByType();
                            StringBuilder message = new StringBuilder("Quantity of toys by type:\n");
                            for (Map.Entry<Character, Integer> entry : quantityByType.entrySet()) {
                                message.append("Type ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, message.toString());
                            break;
                        case "3":
                            int totalQuantity = toyStore.getTotalQuantity();
                            JOptionPane.showMessageDialog(null, "Total quantity of toys: " + totalQuantity);
                            break;
                        case "4":
                            double totalValue = toyStore.getTotalValue();
                            JOptionPane.showMessageDialog(null, "Total value of toys: $" + totalValue);
                            break;
                        case "5":
                            String toyName = JOptionPane.showInputDialog("Enter the name of the toy to decrease stock:");
                            Toy toy = findToy(toyStore, toyName);
                            if (toy != null) {
                                int quantityDecrease = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity to decrease:"));
                                toyStore.decreaseStock(toy, quantityDecrease);
                                JOptionPane.showMessageDialog(null, "Stock decreased successfully.");
                            } else {
                                JOptionPane.showMessageDialog(null, "The toy is not found in the store.");
                            }
                            break;
                        case "6":
                            String toyNameIncrease = JOptionPane.showInputDialog("Enter the name of the toy to increase stock:");
                            Toy toyIncrease = findToy(toyStore, toyNameIncrease);
                            if (toyIncrease != null) {
                                int quantityIncrease = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity to increase:"));
                                toyStore.increaseStock(toyIncrease, quantityIncrease);
                                JOptionPane.showMessageDialog(null, "Stock increased successfully.");
                            } else {
                                JOptionPane.showMessageDialog(null, "The toy is not found in the store.");
                            }
                            break;
                        case "7":
                            char typeWithMostToys = toyStore.getTypeWithMostToys();
                            JOptionPane.showMessageDialog(null, "Type with most toys: " + typeWithMostToys);
                            break;
                        case "8":
                            char typeWithLeastToys = toyStore.getTypeWithLeastToys();
                            JOptionPane.showMessageDialog(null, "Type with least toys: " + typeWithLeastToys);
                            break;
                        case "9":
                            double value = Double.parseDouble(JOptionPane.showInputDialog("Enter the minimum value:"));
                            List<Toy> toysWithValueGreaterThan = toyStore.getToysWithValueGreaterThan(value);
                            StringBuilder messageMinimum = new StringBuilder("Toys with value greater than $" + value + ":\n");
                            for (Toy toyMinimum : toysWithValueGreaterThan) {
                                messageMinimum.append(toyMinimum.toString()).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, messageMinimum.toString());
                            break;
                        case "10":
                            List<Toy> sortedInventory = toyStore.getInventory();
                            Collections.sort(sortedInventory, (t1, t2) -> {
                                if (t1.getType() != t2.getType()) {
                                    return t1.getType() - t2.getType();
                                }
                                return t1.getQuantity() - t2.getQuantity();
                            });

                            StringBuilder messageSorted = new StringBuilder("Inventory sorted by type and quantity:\n");
                            for (Toy toySorted : sortedInventory) {
                                messageSorted.append(toySorted.toString()).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, messageSorted.toString());
                            break;
                        case "11":
                            continueExecution = false;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid option");
                    }
                } else {
                    continueExecution = false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        try {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private static Toy findToy(ToyStore toyStore, String name) {
        for (Toy toy : toyStore.getInventory()) {
            if (toy.getName().equalsIgnoreCase(name)) {
                return toy;
            }
        }
        return null;
    }

}

package cue.edu.co.utils;

import cue.edu.co.model.Toy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FUtils {
    public static void savedToys(File file, List<Toy> list){
        try {
            FileOutputStream exit = new FileOutputStream(file);
            ObjectOutputStream objEx= new ObjectOutputStream(exit);
            objEx.writeObject(list);
            objEx.close();
        } catch (FileNotFoundException e) {
            System.out.println("File doesn´t exist");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static List<Toy> getToys(File file){
        List<Toy> toys = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream(file);
            ObjectInput os = new ObjectInputStream(fs);
            toys = (List<Toy>) os.readObject();
            os.close();
        } catch (FileNotFoundException e){
            System.out.println("Archive doesn´t exist");
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return toys;
    }
}

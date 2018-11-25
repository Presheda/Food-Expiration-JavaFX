package sample.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

/**
 * Created by Precious on 24/11/2018
 */
public class FoodData {
    private static FoodData instance = new FoodData();
    private static String filename = "FoodListItems.txt";
    private static String passwordPath = "Password.txt";
    private ObservableList<FoodItem> foodItems;
    private DateTimeFormatter formatter;

    public static String PASSWORD;

    public static FoodData getInstance(){
        return instance;
    }


    private FoodData(){
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public void addFoodItems(FoodItem item){
        foodItems.add(item);
    }

    public ObservableList<FoodItem> getFoodItems(){
        return foodItems;
    }

    static {
        try {
            writePassword();
            readPassword();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writePassword()throws IOException{
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(passwordPath)))){
            outputStream.writeUTF("FOOD");
        }
    }

    public static void readPassword() throws IOException{
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(passwordPath)))){
            PASSWORD = inputStream.readUTF();
        }
    }


    public void StoreFoodItems() throws IOException{

        Path path = Paths.get(filename);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(filename)))) {
            objectOutputStream.writeInt(foodItems.size());
            for(FoodItem foodItem: foodItems){
                objectOutputStream.writeObject(foodItem);
            }
        }

    }

    public void loadFoodItems() throws Exception{
        foodItems = FXCollections.observableArrayList();
        try(ObjectInputStream objectInputStream  = new ObjectInputStream(new FileInputStream(new File(filename)))) {
            int size = objectInputStream.readInt();
            System.out.println(size);
            for (int i=0; i<size; i++){
                FoodItem newFoodItem = (FoodItem) objectInputStream.readObject();
                foodItems.add(newFoodItem);
            }
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }


    public void deleteFoodItem(FoodItem item){
        foodItems.remove(item);
    }
}

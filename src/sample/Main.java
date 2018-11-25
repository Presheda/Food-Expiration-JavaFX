package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Data.FoodData;
import sample.Data.FoodItem;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        try{
            FoodData.getInstance().StoreFoodItems();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws Exception {
        try{
            FoodData.getInstance().loadFoodItems();
//            ObservableList<FoodItem> items = FoodData.getInstance().getFoodItems();
//            System.out.println(items.size());
//            for(FoodItem foodItem: items){
//                if(foodItem != null){
//                    System.out.println(foodItem.getItemName() + " " + foodItem.getItemCode() + " " + foodItem.getItemExpiration());
//                }
//            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

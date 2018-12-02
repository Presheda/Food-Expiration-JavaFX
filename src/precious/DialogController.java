package sample;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Data.FoodData;
import sample.Data.FoodItem;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Precious on 29/08/2018
 */
public class DialogController {

    @FXML
    private TextField foodItemName;

    @FXML
    private TextField ItemCode;

    @FXML
    private PasswordField adminPassword;



    @FXML
    private DatePicker deadlinePicker;

    public FoodItem processResult(){
        String ItemName = foodItemName.getText().trim();
        String foodItemCode= ItemCode.getText().trim();
        String password = adminPassword.getText().trim();
        LocalDate deadlineValue  = deadlinePicker.getValue();

        if(ItemName.isEmpty() || foodItemCode.isEmpty() || password.isEmpty() ||deadlineValue == null){
         return  null;
        }
        else{
            if(password.matches(FoodData.PASSWORD.trim())) {
                FoodItem newItem = new FoodItem(ItemName,
                        foodItemCode, deadlineValue);
                FoodData.getInstance().addFoodItems(newItem);
                try {
                    FoodData.getInstance().StoreFoodItems();
                    FoodData.getInstance().loadFoodItems();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return newItem;
            }

            return null;
        }


    }
}

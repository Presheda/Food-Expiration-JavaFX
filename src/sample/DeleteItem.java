package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import sample.Data.FoodData;

/**
 * Created by Precious on 24/11/2018
 */
public class DeleteItem {

    @FXML
    private PasswordField password;

    public boolean processDelete(){
        String adminPassword = password.getText().trim();
        System.out.println(FoodData.PASSWORD);

        if(adminPassword.isEmpty()){
            return false;
        }

        else {
            if(adminPassword.matches(FoodData.PASSWORD.trim())){
                return  true;
            }

            return false;
        }
    }
}

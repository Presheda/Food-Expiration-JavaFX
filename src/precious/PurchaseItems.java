package precious;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import precious.Data.FoodData;
import precious.Data.FoodItem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * Created by Precious on 30/11/2018
 */
public class PurchaseItems {


    @FXML
    private Text purchaseFirst, purchaseMessage;

    @FXML
    private TextField checkItemCode;


    int itemsAddedToBasket = 0;


    public void initialize(){

        purchaseFirst.setText("private Enter Item's Code date \n" + "to check for expiration");

    }

    @FXML
    public void checkExpiration(){

        String code = checkItemCode.getText().trim().toUpperCase();

        ObservableList<FoodItem> foodItems = FoodData.getInstance().getFoodItems();

        for(FoodItem newFoodItems: foodItems){
            if(newFoodItems.getItemCode().matches(code)){
                LocalDate todayDate = LocalDate.now();
                LocalDate itemExpired = newFoodItems.getItemExpiration();

                long daysUntil = todayDate.until(itemExpired, ChronoUnit.DAYS);

                if(daysUntil >=1){
                    itemsAddedToBasket++;
                    purchaseMessage.setFill(Color.GREEN);
                    purchaseMessage.setText("Item with code : " + code + " expires in " + daysUntil + "\n Total item in basket in now "
                            + itemsAddedToBasket);
                }

                else {
                    purchaseMessage.setFill(Color.RED);
                    purchaseMessage.setText("Item with code : "  + code + " is expired" );

                }

                break;
            }
        }



    }

    @FXML
    public void Purchase(){
       if (itemsAddedToBasket <= 0){
           purchaseMessage.setVisible(true);
            purchaseMessage.setText("You have " + itemsAddedToBasket + "in your basket");
        }

    }

}

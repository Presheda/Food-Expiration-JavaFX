package precious;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Created by Precious on 30/11/2018
 */
public class SupplyItems {

    @FXML
    private DatePicker deadLinePicker;

    @FXML
    private Text supplyMessage;

    private int itemsAddedToBasket = 0;



    public void initialize(){

    }


    @FXML
    public void checkValidity(){


        if(deadLinePicker != null) {

            LocalDate itemExpirationDate = deadLinePicker.getValue();
            LocalDate todaysDate = LocalDate.now();

            if(itemExpirationDate != null){
                long daysUntill = todaysDate.until(itemExpirationDate, ChronoUnit.DAYS);

                if (daysUntill >= 90) {
                    itemsAddedToBasket++;
                    supplyMessage.setVisible(true);
                    supplyMessage.setFill(Color.GREEN);
                    supplyMessage.setText("1 item added to basket.. \n" + "Total basket size is now " + itemsAddedToBasket);
                } else {
                    supplyMessage.setVisible(true);
                    supplyMessage.setFill(Color.RED);
                    supplyMessage.setText("item was not added added to basket.. \n" + "Item's expiration limit exceeded ");
                }
            }
        }
    }

}

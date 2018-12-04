package precious.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Precious on 24/11/2018
 */
public class FoodItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String itemName, itemCode;
    private LocalDate itemExpiration;

    public FoodItem(String itemName, String itemCode, LocalDate itemExpiration) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.itemExpiration = itemExpiration;
    }

    public String getItemName() {
        return itemName;
    }

//    public SimpleStringProperty itemNameProperty() {
//        return itemName;
//    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

//    public SimpleStringProperty itemCodeProperty() {
//        return itemCode;
//    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public LocalDate getItemExpiration() {
        return itemExpiration;
    }

    public void setItemExpiration(LocalDate itemExpiration) {
        this.itemExpiration = itemExpiration;
    }
}

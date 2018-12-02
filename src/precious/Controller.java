package precious;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import precious.Data.FoodData;
import precious.Data.FoodItem;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {

    @FXML
    private BorderPane mainBorderPane;

    //configure the table and columns
    @FXML
    private TableView<FoodItem> tableView;
    @FXML
    private TableColumn<FoodItem, String> itemNameColumn;
    @FXML
    private TableColumn<FoodItem, String> itemCodeColumn;
    @FXML
    private TableColumn<FoodItem, LocalDate> itemExpirationColumn;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;

    private FilteredList<FoodItem> filteredList;

    private Predicate<FoodItem> wantAllItems;

    private Predicate<FoodItem> wantExpiredItems;


    public void initialize() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //Initialize the delete the delete menu
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                FoodItem item =todoListView.getSelectionModel().getSelectedItem();
//                deleteItem(item);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);

        //sets up column in the table
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("itemName"));
        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("itemCode"));
        itemExpirationColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, LocalDate>("ItemExpiration"));

        //load data


        // tableView.getColumns().get(0).setStyle("-fx-text-fill: red");


        // tableView.setItems(getFoodItem());

        tableView.setItems(sortItemAndFilter(FoodData.getInstance().getFoodItems()));


        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Stock");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            FoodItem position = controller.processResult();
            if (position != null) {

                tableView.refresh();
                tableView.setItems(sortItemAndFilter(FoodData.getInstance().getFoodItems()));

                tableView.getSelectionModel().select(position);

            }
        }
    }

    /**
     * This method will delete the selected items from the table
     */
    public void deleteFoodItem() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Delete Item(s)");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("deleteItem.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        ObservableList<FoodItem> selectedFoodItems;

        selectedFoodItems = tableView.getSelectionModel().getSelectedItems();

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DeleteItem controller = fxmlLoader.getController();
            boolean passwordCorrect = controller.processDelete();
            if (passwordCorrect) {
                for (FoodItem foodItem : selectedFoodItems) {
                    FoodData.getInstance().deleteFoodItem(foodItem);
                }
                try {
                    FoodData.getInstance().StoreFoodItems();
                    FoodData.getInstance().loadFoodItems();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tableView.refresh();
                tableView.setItems(sortItemAndFilter(FoodData.getInstance().getFoodItems()));
            }


        }

    }


    @FXML
    public void handleFilterButton() {
        FoodItem selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (filterToggleButton.isSelected()) {

            filteredList.setPredicate(wantExpiredItems);

//            if(filteredList.isEmpty()){
//            } else if(filteredList.contains(selectedItem)){
//               // tableView.getSelectionModel().select(selectedItem);
//            } else {
//               // tableView.getSelectionModel().selectFirst();
//            }
        } else {
            filteredList.setPredicate(wantAllItems);
            //tableView.getSelectionModel().select(selectedItem);
        }
    }


    /** This is a helper method
     * @param foodItems
     * @return  sortedList of foodItems
     *
     * **/
    public SortedList<FoodItem> sortItemAndFilter(ObservableList<FoodItem> foodItems) {

        tableView.refresh();

        wantAllItems = new Predicate<FoodItem>() {
            @Override
            public boolean test(FoodItem item) {
                return true;
            }
        };

        wantExpiredItems = new Predicate<FoodItem>() {
            @Override
            public boolean test(FoodItem item) {
                return (item.getItemExpiration().isBefore(LocalDate.now().plusDays(1)));
            }
        };

        filteredList = new FilteredList<FoodItem>(foodItems, wantAllItems);

        SortedList<FoodItem> sortedList = new SortedList<FoodItem>(
                filteredList, new Comparator<FoodItem>() {
            @Override
            public int compare(FoodItem o1, FoodItem o2) {

                return o1.getItemExpiration().compareTo(o2.getItemExpiration());


            }
        }
        );



        tableView.setRowFactory(new Callback<TableView<FoodItem>, TableRow<FoodItem>>() {
            @Override
            public TableRow<FoodItem> call(TableView<FoodItem> param) {
                return new TableRow<FoodItem>(){
                    @Override
                    protected void updateItem(FoodItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setStyle("-fx-background-color: white");

                        } else if(item.getItemCode() != null && !(item.getItemCode().matches(""))){
                            System.out.println("update item has been called");

                            LocalDate date = LocalDate.now();
                            LocalDate localDate = item.getItemExpiration();
                            long daysUntil =date.until(localDate, ChronoUnit.DAYS);
                            System.out.println(daysUntil);


                            setStyle("-fx-font-size: 20");

                            if(item.getItemExpiration().isBefore(LocalDate.now().plusDays(1))){
                                setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20");

                            } else if(daysUntil >= 1 && daysUntil <=4 ){
                                setStyle("-fx-background-color: yellow; -fx-text-fill: white; -fx-font-size: 20");
                            } else {
                                setStyle("-fx-text-color: green");
                            }

                        }




                    }
                };
            }
        });

        return sortedList;
    }

    @FXML
    public void handleLogout(){

        Main.mainStage.setScene(Main.login);

    }

    @FXML
    public void showSupplyItemDialog(){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Supply New Stock");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("supplyItems.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

    }

    @FXML
    public void showPurchaseItemDialog(){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Stock");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("PurchaseItems.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

    }


    @FXML
    public void handleExit() {
        Platform.exit();
    }
}

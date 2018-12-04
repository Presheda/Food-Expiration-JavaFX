package precious;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import precious.Data.ManageUser;
import precious.Data.PasswordData;

import java.time.LocalDate;

/**
 * Created by Precious on 02/12/2018
 */
public class LastfiveUsers {

    @FXML
    public TableView<ManageUser> lastLogginTableView;

    @FXML
    private TableColumn<ManageUser, String> userName;
    @FXML
    private TableColumn<ManageUser, String> lastLogged;
    @FXML

    public void initialize(){


        userName.setCellValueFactory(new PropertyValueFactory<ManageUser, String>("UserName"));
        lastLogged.setCellValueFactory(new PropertyValueFactory<ManageUser, String>("LastLoggedIn"));



       // lastLogginTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        lastLogginTableView.setItems(PasswordData.getInstance().getLastFewLogg());

       // lastLogginTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


}

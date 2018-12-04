package precious;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import precious.Data.ManageUser;
import precious.Data.PasswordData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import static precious.Data.PasswordData.getInstance;

/**
 * Created by Precious on 29/11/2018
 */
public class LoginScreen {

    @FXML
    private TextField loginUserName;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Text loginMessage;

    public void initialize(){
        loginMessage.setVisible(false);
        loginMessage.setStyle("-fx-text-color:red");

    }

    @FXML
    public void loginButton(){

        String userName = loginUserName.getText().trim().toUpperCase();
        String password = loginPassword.getText().trim().toUpperCase();

        if(userName.isEmpty() || password.isEmpty()){
            System.out.println("Either one of the field is empty");

            loginMessage.setVisible(true);
            loginMessage.setText("Username or Password cannot be empty");
        }

        else {

           HashMap<String, String> users =  PasswordData.getInstance().getUserDetails();


            if(users.containsKey(userName)){
                String passwordInDatabase = users.get(userName);
                if(passwordInDatabase.equalsIgnoreCase(password)){
                    loginUserName.setText("");
                    loginPassword.setText("");
                    loginMessage.setVisible(false);


                    ManageUser user = new ManageUser(userName, new Date());
                    PasswordData.getInstance().addLastFewLogin(user);
                    try{

                        PasswordData.getInstance().storeLastFewLoggin();
                        PasswordData.getInstance().loadLastFewLoggin();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    Main.mainStage.setScene(Main.hasLoggedInScreen);
                }

                else {
                    loginMessage.setVisible(true);
                    loginMessage.setText("Password is incorrect");
                }
            }

            else {
                loginMessage.setVisible(true);
                loginMessage.setText("Username is not found in the database");
            }


        }

    }




}

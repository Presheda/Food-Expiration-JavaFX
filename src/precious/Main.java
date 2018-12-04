package precious;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import precious.Data.AdminAccount;
import precious.Data.FoodData;
import precious.Data.PasswordData;

import java.io.IOException;
import java.util.List;

public class Main extends Application {


    public static Stage mainStage =  new Stage();

   public static Scene login;
   public static Scene hasLoggedInScreen;

    @Override
    public void start(Stage primaryStage) throws Exception{

        mainStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent secondRoot = FXMLLoader.load(getClass().getResource("loginscreen.fxml"));


        mainStage.setTitle("FOOD EXPIRATION");
        login = new Scene(secondRoot, 500, 400);

        //mainStage.setScene(login);

        hasLoggedInScreen = new Scene(root, 500, 400);

        mainStage.setScene(login);

        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        try{
            FoodData.getInstance().StoreFoodItems();
            PasswordData.getInstance().storeUserAccount();
            PasswordData.getInstance().storeLastFewLoggin();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws Exception {
        try{
            FoodData.getInstance().loadFoodItems();
            PasswordData.getInstance().loadUserAccounts();
            PasswordData.getInstance().loadLastFewLoggin();


            List<AdminAccount> accountList = PasswordData.getInstance().getUserAccount();

//            PasswordData.getInstance().addAdminAccount(new AdminAccount("peter", "favour"));
//            PasswordData.getInstance().addAdminAccount(new AdminAccount("tony", "jackson"));
//            PasswordData.getInstance().addAdminAccount(new AdminAccount("seyi", "jason"));
//            PasswordData.getInstance().addAdminAccount(new AdminAccount("tunde", "icetunez"));
//            PasswordData.getInstance().addAdminAccount(new AdminAccount("ayo", "bumchi"));

            System.out.println(accountList.size());


        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

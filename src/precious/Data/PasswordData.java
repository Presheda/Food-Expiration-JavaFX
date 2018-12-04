package precious.Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Precious on 30/11/2018
 */
public class PasswordData implements Serializable {

    private static final long serialVersionUID = 1L;
    List<AdminAccount> userAccount;
    ObservableList<ManageUser> lastFewLogg;
    HashMap<String, String> userDetails = new HashMap<>();
    HashMap<String, ManageUser> mapLastFewUsers = new HashMap<>();
    private static PasswordData instance = new PasswordData();
    private static final String userDatabase = "UserDatabase.txt";
    private static final String lastFewLoggin = "LastFewLoggin.txt";

    public static PasswordData getInstance() {
        return instance;
    }

    public List<AdminAccount> getUserAccount() {
        return userAccount;
    }

    public HashMap<String, String> getUserDetails() {
        return userDetails;
    }

    public void addAdminAccount(AdminAccount adminAccount) {
        userAccount.add(adminAccount);
    }

    public void addLastFewLogin(ManageUser manageUser) {


        int size =  lastFewLogg.size();

        for(int i=0; i<size-1; i++){
            if(lastFewLogg.get(i).getUserName().equalsIgnoreCase(manageUser.getUserName())){
                lastFewLogg.remove(i);

            }
        }

        lastFewLogg.add(0, manageUser);

        System.out.println("The size of the arrayList is " + lastFewLogg.size());

        //lastFewLogg.add(manageUser);


    }

    public ObservableList<ManageUser> getLastFewLogg() {
       ObservableList<ManageUser> usersToreturn = FXCollections.observableArrayList();

       int size = lastFewLogg.size();

       if(size >= 6) {

           for (int i = 0; i < 5; i++) {
               usersToreturn.add(lastFewLogg.get(i));
           }

       }

       else {
           for (int i = 0; i <size; i++) {
               usersToreturn.add(lastFewLogg.get(i));
           }
       }

        return usersToreturn;
    }


    private PasswordData() {

    }


    public void loadLastFewLoggin() throws Exception {

        lastFewLogg = FXCollections.observableArrayList();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(lastFewLoggin)))) {

            int size = inputStream.readInt();

            for (int i = 0; i < size; i++) {
                ManageUser manageUser = (ManageUser) inputStream.readObject();
                lastFewLogg.add(manageUser);
            }

            if(lastFewLogg.size()>=5){
                for(int i=0; i<5; i++){
                    mapLastFewUsers.put(lastFewLogg.get(i).getUserName(), lastFewLogg.get(i));
                }
            } else {
                for(int i=0; i<lastFewLogg.size(); i++){
                    mapLastFewUsers.put(lastFewLogg.get(i).getUserName(), lastFewLogg.get(i));
                }
            }

        }

    }

    public void storeLastFewLoggin() throws Exception {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(lastFewLoggin))) {
            outputStream.writeInt(lastFewLogg.size());
            for (ManageUser user : lastFewLogg) {
                outputStream.writeObject(user);
            }
        }

    }

    public void loadUserAccounts() throws Exception {


        userAccount = new ArrayList<>();
        userDetails = new HashMap<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(userDatabase)))) {

            int size = inputStream.readInt();

            for (int i = 0; i < size; i++) {
                AdminAccount individualAccount = (AdminAccount) inputStream.readObject();
                userAccount.add(individualAccount);
                userDetails.put(individualAccount.getUserName().toUpperCase(), individualAccount.getPassword().toUpperCase());

            }
        }

    }

    public void storeUserAccount() throws Exception {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(userDatabase))) {
            outputStream.writeInt(userAccount.size());
            for (int i = 0; i < userAccount.size(); i++) {
                outputStream.writeObject(userAccount.get(i));
            }

        }


    }

}

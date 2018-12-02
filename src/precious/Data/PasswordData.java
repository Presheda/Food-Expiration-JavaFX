package precious.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Precious on 30/11/2018
 */
public class PasswordData implements Serializable {

    private static final long serialVersionUID = 1L;
    List<AdminAccount> userAccount;
    HashMap<String, String> userDetails = new HashMap<>();
    private static PasswordData instance  = new PasswordData();
    private static final String userDatabase = "UserDatabase.txt";

    public static PasswordData getInstance() {
        return instance;
    }

    public List<AdminAccount> getUserAccount(){
        return userAccount;
    }

    public HashMap<String, String> getUserDetails(){
        return userDetails;
    }

    public void addAdminAccount(AdminAccount adminAccount){
        userAccount.add(adminAccount);
    }

    private PasswordData(){

    }

    public void loadUserAccounts() throws  Exception{



        userAccount = new ArrayList<>();
        userDetails = new HashMap<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(userDatabase)))) {

           int size =  inputStream.readInt();

           for(int i = 0; i< size; i++){
              AdminAccount individualAccount = (AdminAccount) inputStream.readObject();
               userAccount.add(individualAccount);
               userDetails.put(individualAccount.getUserName(), individualAccount.getPassword());

           }
        }

    }

    public void storeUserAccount() throws Exception{

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(userDatabase))) {
            outputStream.writeInt(userAccount.size());
            for(int i=0; i<userAccount.size(); i++){
                outputStream.writeObject(userAccount.get(i));
            }

        }



    }

}

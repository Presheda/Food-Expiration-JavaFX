package precious.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Precious on 30/11/2018
 */
public class AdminAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    private String UserName;
   private String Password;
   private LocalDate lastLoggedIn;

    public AdminAccount(String userName, String password) {
        UserName = userName;
        Password = password;
        lastLoggedIn = LocalDate.now();
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public LocalDate getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(LocalDate lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }
}

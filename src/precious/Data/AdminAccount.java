package precious.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Precious on 30/11/2018
 *
 *
 * This class represents an admin account
 * An admin account has the following
 * UserName
 * Password
 * Last Logged in
 */
public class AdminAccount implements Serializable {



    private static final long serialVersionUID = 1L;
    private String UserName;
   private String Password;
   private LocalDate lastLoggedIn;


    /**
     *
     * has only one constructor and it's public
     * @param userName
     * @param password
     */
    public AdminAccount(String userName, String password) {
        UserName = userName;
        Password = password;
        lastLoggedIn = LocalDate.now();
    }


    /**
     *
     * @return the adminAccount username
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * sets the userName for the admin account
     * @param userName
     */
    public void setUserName(String userName) {
        UserName = userName;
    }

    /**
     *
     * @return the adminAccount password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * sets the password for the admin account
     * @param password
     */
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

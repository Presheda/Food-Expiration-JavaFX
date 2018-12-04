package precious.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Precious on 02/12/2018
 */
public class ManageUser implements Serializable {

    private static final long serialVersionUID = 2L;

    private String UserName;
    private String LastLoggedIn;



    public ManageUser(String UserName, Date date) {
        this.UserName = UserName;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh.mm aa");
        LastLoggedIn = dateFormat.format(date).toString();
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getLastLoggedIn() {
        return LastLoggedIn;
    }

    public void setLastLoggedIn(String lastLoggedIn) {
        this.LastLoggedIn = lastLoggedIn;
    }
}

package users;

import utils.*;

import java.util.UUID;

public abstract class User {
	private String username;
    private String password;
    private String userID;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userID = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserID() {
        return userID;
    }
    public abstract Location getLocation();
}

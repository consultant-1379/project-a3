package main.Details;

import java.util.Base64;

public final class Details {

    private final String user;
    private final String pass;


    public Details(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return new String(Base64.getDecoder().decode(pass));
    }
}


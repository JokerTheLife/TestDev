package com.example.demo.model;

public class EnterUser {
    String login;
    Long[] rights;

    public void clear() {
        login = null;
        rights = null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long[] getRights() {
        return rights;
    }

    public void setRights(Long[] rights) {
        this.rights = rights;
    }
}

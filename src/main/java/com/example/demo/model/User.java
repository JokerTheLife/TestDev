package com.example.demo.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}

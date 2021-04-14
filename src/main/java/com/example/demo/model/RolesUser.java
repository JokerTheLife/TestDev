package com.example.demo.model;

import javax.persistence.*;

@Entity
public class RolesUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rights_id")
    private Roles rights;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User users;

    public RolesUser() {
    }

    public Long getId() {
        return id;
    }

    public Roles getRights() {
        return rights;
    }

    public User getUsers() {
        return users;
    }

    public RolesUser(Roles rights, User users) {
        this.rights = rights;
        this.users = users;
    }
}

package com.example.dongkyoo.webe.vos;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class User {

    private String id;
    private String password;
    private String email;
    private String name;

    public User() {}

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(User user) {
        this.id = user.id;
        this.password = user.password;
        this.name = user.name;
    }

    public User(String id, String password, String email, String name) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getEmail() {
        return email;
    }
}

package com.BookStoreBE.Model;


import jakarta.persistence.Entity;

@Entity
public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
}

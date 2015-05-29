package com.arek00.xmlReader.entities.userEntity;

import com.arek00.xmlReader.helpers.MD5Generator;

/**
 * User structure in DB
 * ID | Name | Surname | Login | MD5(Name) | Upload ID
 */
public class User {

    private String name, surname, login, hash;

    public User() {
    }

    public User(String name, String surname, String login) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.hash = MD5Generator.generateMD5Hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.hash = MD5Generator.generateMD5Hash(name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}

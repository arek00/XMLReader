package com.arek00.xmlReader.entities.userEntity;

import com.arek00.xmlReader.helpers.MD5Generator;
import org.apache.tomcat.util.security.MD5Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User structure in DB
 * ID | Name | Surname | Login | MD5(Name) | Upload ID
 */
public class User {

    private String name, surname, login, md5;

    public User() {
    }

    public User(String name, String surname, String login) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.md5 = MD5Generator.generateMD5Hash(login);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.md5 = MD5Generator.generateMD5Hash(login);
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

    public String getMD5() {
        return this.md5;
    }

}

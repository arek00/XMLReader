package com.arek00.xmlReader.usersReaders;

import com.arek00.xmlReader.entities.userEntity.User;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class UsersReadingHandler extends DefaultHandler {
    private User userEntity;
    private List<User> entities;

    private String currentTag;
    private String characters;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        onStartTag(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        onEndTag(qName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        setCharacters(new String(ch, start, length));
    }

    private void onStartTag(String tag) {
        if (tag.equalsIgnoreCase(UserFields.USERS.getTagName())) {
            initializeEntities();
        } else if (tag.equalsIgnoreCase(UserFields.USER.getTagName())) {
            initializeEntity();
        }

        this.currentTag = tag;
    }

    private void onEndTag(String tag) {
        if (tag.equalsIgnoreCase(UserFields.USER.getTagName())) {
            addUser();
        } else if (tag.equalsIgnoreCase(UserFields.NAME.getTagName())) {
            setUserName();
        } else if (tag.equalsIgnoreCase(UserFields.SURNAME.getTagName())) {
            setUserSurname();
        } else if (tag.equalsIgnoreCase(UserFields.LOGIN.getTagName())) {
            setUserLogin();
        }
    }


    private void addUser() {
        this.entities.add(userEntity);
    }

    private void setUserName() {
        this.userEntity.setName(characters);
    }

    private void setUserSurname() {
        this.userEntity.setSurname(characters);
    }

    private void setUserLogin() {
        this.userEntity.setLogin(characters);
    }


    private void setCharacters(String characters) {
        this.characters = characters;
    }

    private void initializeEntities() {
        entities = new ArrayList<User>();
    }

    private void initializeEntity() {
        userEntity = new User();
    }

    /**
     * Get Iterator of read users.
     */
    public List<User> getResult() {
        return this.entities;
    }

}

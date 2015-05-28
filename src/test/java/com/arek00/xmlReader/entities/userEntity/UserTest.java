package com.arek00.xmlReader.entities.userEntity;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private static User user;

    @BeforeClass
    public static void initializeTest()
    {
        user = new User("Jan","Kowalski","Jank65");
    }

    @Test
    public void fieldsShouldBeFilled()
    {
        assertFalse(user.getName() == null);
        assertFalse(user.getSurname() == null);
        assertFalse(user.getLogin() == null);
        assertFalse(user.getMD5() == null);

        System.out.println(user.getMD5());
    }



}
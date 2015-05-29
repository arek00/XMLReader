package com.arek00.xmlReader.entities.userEntity;

import com.arek00.xmlReader.db.interfaces.ObjectInsertionStrategy;
import com.arek00.xmlReader.db.interfaces.ObjectSelectionStrategy;
import com.arek00.xmlReader.db.valueObjects.InsertionData;
import com.arek00.xmlReader.helpers.MD5Generator;
import org.apache.tomcat.util.security.MD5Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserToDB<T> implements ObjectInsertionStrategy, ObjectSelectionStrategy {

    private final String LOGIN = "login", NAME = "name", SURNAME = "surname", MD5 = "md5";


    @Override
    public <T> InsertionData generateInsertionData(T element) {
        String[] columns = null;
        String[] values = null;

        if (element instanceof User) {
            columns = new String[]{"Name", "Surname", "Login", "MD5"};
            values = generateValuesArray((User) element);
        }

        return new InsertionData(columns, values);
    }

    private String[] generateValuesArray(User user) {
        byte digestArray[];

        String name, surname, login, md5;
        name = ((User) user).getName();
        surname = ((User) user).getSurname();
        login = ((User) user).getLogin();
        md5 = MD5Generator.generateMD5Hash(name);

        return new String[]{name, surname, login, md5};
    }


    @Override
    public <T> List<T> generateSelectionList(ResultSet selectionResult) {

        List<T> userList = new ArrayList<T>();

        try {
            while (selectionResult.next()) {
                String login, name, surname;

                login = selectionResult.getString(LOGIN);
                name = selectionResult.getString(NAME);
                surname = selectionResult.getString(SURNAME);

                User user = new User(name, surname, login);
                userList.add((T) user);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}

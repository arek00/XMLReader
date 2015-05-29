package com.arek00.xmlReader.entities.userEntity;

import com.arek00.xmlReader.db.interfaces.ObjectInsertionStrategy;
import com.arek00.xmlReader.db.interfaces.ObjectSelectionStrategy;
import com.arek00.xmlReader.db.valueObjects.InsertionData;
import com.arek00.xmlReader.helpers.MD5Generator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserToDB<T> implements ObjectInsertionStrategy, ObjectSelectionStrategy {

    private final String LOGIN = "login", NAME = "name", SURNAME = "surname", MD5 = "md5", SESSION_ID = "session_id";

    @Override
    public <T> InsertionData generateInsertionData(T element) {
        String[] columns = null;
        String[] values = null;

        if (element instanceof User) {
            columns = new String[]{NAME, SURNAME, LOGIN, MD5, SESSION_ID};
            values = generateValuesArray((User) element);
        }

        return new InsertionData(columns, values);
    }

    private String[] generateValuesArray(User user) {
        byte digestArray[];

        String name, surname, login, md5, sessionId;
        name = user.getName();
        surname = user.getSurname();
        login = user.getLogin();
        md5 = MD5Generator.generateMD5Hash(name);
        sessionId = user.getSessionID();

        return new String[]{name, surname, login, md5, sessionId};
    }


    @Override
    public <T> List<T> generateSelectionList(ResultSet selectionResult) {

        List<T> userList = new ArrayList<T>();

        try {
            while (selectionResult.next()) {
                String login, name, surname, sessionID;

                login = selectionResult.getString(LOGIN);
                name = selectionResult.getString(NAME);
                surname = selectionResult.getString(SURNAME);
                sessionID = selectionResult.getString(SESSION_ID);

                User user = new User(name, surname, login);
                user.setSessionID(sessionID);

                userList.add((T) user);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}

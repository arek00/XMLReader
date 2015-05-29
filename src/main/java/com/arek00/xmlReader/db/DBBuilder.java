package com.arek00.xmlReader.db;

import com.arek00.xmlReader.helpers.MyLogger;
import com.arek00.xmlReader.db.EmbeddedDerbyConnectors.EmbeddedDerbyConnector;
import com.arek00.xmlReader.db.EmbeddedDerbyConnectors.EmbeddedDerbyHandler;
import com.arek00.xmlReader.db.interfaces.IDBConnector;
import com.arek00.xmlReader.db.interfaces.IDBHandler;

import java.sql.SQLException;

public class DBBuilder {

    IDBConnector connector;
    IDBHandler handler;


    public DBBuilder() {
        createDB();
    }

    private void createDB() {
        try {
            doSetDBConnector();
            doSetDBHandler();
            doSetDBController();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            doCreateUsersTable();
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                MyLogger.logMessage("CREATED EXISTING TABLE CANCELED", e.getMessage());
            }
        }
    }

    private void doSetDBConnector() throws SQLException {
        connector = new EmbeddedDerbyConnector("derbyDB", "admin", "admin", true);
    }

    private void doSetDBHandler() throws SQLException {
        handler = new EmbeddedDerbyHandler(connector.getConnection());
    }

    private void doSetDBController() {
        DBController.setConnector(connector);
        DBController.setHandler(handler);
    }

    private void doCreateUsersTable() throws SQLException {
        String idField = "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)";
        String nameField = "NAME VARCHAR(30)";
        String surnameField = "SURNAME VARCHAR(30)";
        String loginField = "LOGIN VARCHAR(30)";
        String md5Field = "MD5 VARCHAR(32)";

        String[] columns = {idField, nameField, surnameField, loginField, md5Field};

        handler.createTable("Users", columns);
    }

}

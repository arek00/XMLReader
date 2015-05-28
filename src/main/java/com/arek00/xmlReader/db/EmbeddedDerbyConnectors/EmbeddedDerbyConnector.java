package com.arek00.xmlReader.db.EmbeddedDerbyConnectors;

import com.arek00.xmlReader.db.IDBConnector;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connector to Embedded Apache Derby Database.
 * Capable to create database if does not exist.
 * Returns connection to manipulate this database.
 * Set driver and connection unsupported.
 */
public class EmbeddedDerbyConnector implements IDBConnector {

    private Driver driver;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    private Connection derbyConnection;

    public EmbeddedDerbyConnector(String dbName, String dbUser, String dbPassword, boolean createDB) throws SQLException {
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        doSetDriver();

        this.derbyConnection = createConnection(createDB);
    }


    private void doSetDriver() {
        String driverPackage = EmbeddedDerbyConnectionData.DRIVER_PACKAGE.getURI();
        try {
            this.driver = (Driver) Class.forName(driverPackage).newInstance();
            doRegisterDriver();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doRegisterDriver() throws SQLException {
        DriverManager.registerDriver(this.driver);
    }

    @Override
    public void setDriver(String driverPackage) {
        throw new UnsupportedOperationException("Unsupported setting driver to Apache Derby Embedded DB");
    }

    @Override
    public void setConnection(String connectionURI) {
        throw new UnsupportedOperationException("Unsupported setting connection URI to Apache Derby Embedded DB");
    }

    /**
     * Get connection from this instantion of Embedded Derby DB
     *
     * @return
     */
    @Override
    public Connection getConnection() {
        return this.derbyConnection;
    }

    private Connection createConnection(boolean createDB) throws SQLException {
        String connectionURI = EmbeddedDerbyConnectionData.CONNECTION_URI.getURI() +
                dbName + ";" +
                String.format("create=%s", Boolean.toString(createDB));

        return DriverManager.getConnection(connectionURI, this.dbUser, this.dbPassword);
    }
}

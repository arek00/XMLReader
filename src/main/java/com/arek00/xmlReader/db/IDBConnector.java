package com.arek00.xmlReader.db;


import java.sql.Connection;

public interface IDBConnector {

    public void setDriver(String driverPackage);
    public void setConnection(String connectionURI);
    public Connection getConnection();

}

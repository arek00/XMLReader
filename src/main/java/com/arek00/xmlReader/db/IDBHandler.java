package com.arek00.xmlReader.db;


import com.arek00.xmlReader.db.EmbeddedDerbyConnectors.ObjectSelectionStrategy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IDBHandler {

    /**
     * Each element of array is a line of query that create table
     *
     * @param fields
     * @return return true if
     */
    public boolean createTable(String name, String[] fields) throws SQLException;
    public boolean dropTable(String tableName) throws SQLException;
    public int deleteAllFromTable(String tableName) throws SQLException;
    public int delete(String tableName, String whereStatement) throws SQLException;
    public int insert(String[] columns, String[] values) throws SQLException;
    public <T> int insert(T element, ObjectInsertionStrategy insertionStrategy) throws SQLException;
    public ResultSet selectAll(String from) throws SQLException;
    public ResultSet select(String from, String whereStatement) throws SQLException;
    public <T> List<T> selectAllObjects(String from, ObjectSelectionStrategy strategy) throws SQLException;
    public <T> List<T> selectObjects(String from, String whereStatement, ObjectSelectionStrategy strategy) throws SQLException;
    public void setConnection(Connection connection);

}

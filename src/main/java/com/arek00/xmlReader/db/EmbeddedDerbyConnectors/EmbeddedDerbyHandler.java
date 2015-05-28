package com.arek00.xmlReader.db.EmbeddedDerbyConnectors;

import com.arek00.xmlReader.db.interfaces.IDBHandler;
import com.arek00.xmlReader.db.interfaces.ObjectInsertionStrategy;
import com.arek00.xmlReader.db.helpers.QueryValidator;
import com.arek00.xmlReader.db.interfaces.ObjectSelectionStrategy;
import com.arek00.xmlReader.db.valueObjects.InsertionData;

import java.sql.*;
import java.util.List;

/**
 * Handler to Embedded Derby DB
 * Handler lets you:
 * create/drop table, insert, delete, update, select records.
 */
public class EmbeddedDerbyHandler implements IDBHandler {

    private Connection connection;
    private Statement statement;

    public EmbeddedDerbyHandler(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
    }

    /**
     * Create table with given name.
     * In fields put next lines of query to create table.
     * Commas at end of line will be ignored.
     * Semicolons will bring SQLSyntaxErrorException on.
     *
     * @param name
     * @param fields
     * @return
     */
    @Override
    public boolean createTable(String name, String[] fields) throws SQLException {
        QueryValidator.findSemicolons(name);
        validateQueriesArray(fields);

        String query = generateCreateQuery(name, fields);
        return statement.execute(query);
    }


    @Override
    public boolean dropTable(String tableName) throws SQLException {
        QueryValidator.findSemicolons(tableName);

        String query = String.format("DROP TABLE %s;", tableName);

        return statement.execute(query);
    }

    @Override
    public int deleteAllFromTable(String tableName) throws SQLException {
        QueryValidator.findSemicolons(tableName);

        String query = String.format("DELETE FROM %s", tableName);

        return statement.executeUpdate(query);
    }

    @Override
    public int delete(String tableName, String whereStatement) throws SQLException {
        QueryValidator.findSemicolons(whereStatement);

        String query = String.format("DELETE FROM %s WHERE %s");

        return statement.executeUpdate(query);
    }

    @Override
    public int insert(String[] columns, String[] values) throws SQLException {
        validateQueriesArray(columns);
        validateQueriesArray(values);

        String query = generateInsertQuery(columns, values);

        return statement.executeUpdate(query);
    }

    @Override
    public <T> int insert(T element, ObjectInsertionStrategy strategy) throws SQLException {
        String[] columns = null;
        String[] values = null;

        InsertionData insertionData = strategy.generateInsertionData(element);
        columns = insertionData.getColumns();
        values = insertionData.getValues();

        validateQueriesArray(columns);
        validateQueriesArray(values);

        return insert(columns, values);
    }

    @Override
    public ResultSet selectAll(String from) throws SQLException {
        QueryValidator.findSemicolons(from);

        String query = String.format("SELECT * FROM %s", from);

        return statement.executeQuery(query);
    }

    @Override
    public ResultSet select(String from, String whereStatement) throws SQLException {
        QueryValidator.findSemicolons(from);
        QueryValidator.findSemicolons(whereStatement);

        String query = String.format("SELECT * FROM %s WHERE %s", from, whereStatement);

        return statement.executeQuery(query);
    }

    @Override
    public <T> List<T> selectAllObjects(String from, ObjectSelectionStrategy strategy) throws SQLException {
        QueryValidator.findSemicolons(from);

        String query = String.format("SELECT * FROM %s;", from);
        ResultSet selectionResult = statement.executeQuery(query);

        return strategy.generateSelectionList(selectionResult);
    }

    @Override
    public <T> List<T> selectObjects(String from, String whereStatement, ObjectSelectionStrategy strategy) throws SQLException {
        QueryValidator.findSemicolons(from);
        QueryValidator.findSemicolons(whereStatement);

        String query = String.format("SELECT * FROM %s WHERE %s;", from, whereStatement);
        ResultSet selectionResult = statement.executeQuery(query);

        return strategy.generateSelectionList(selectionResult);
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private String generateCreateQuery(String name, String[] fields) {
        String query = "CREATE TABLE %s (\n";

        for (String line : fields) {
            query += QueryValidator.removeCommasAtLinesEnd(line) + ",";
        }

        query += ");";

        return query;
    }

    private void validateQueriesArray(String[] array) throws SQLSyntaxErrorException {
        for (String element : array) {
            QueryValidator.findSemicolons(element);
        }
    }

    private String generateInsertQuery(String[] columns, String[] values) {
        String query = String.format("INSERT INTO");
        query += arrayToQueryArguments(columns);
        query += " VALUES";
        query += arrayToQueryArguments(values);
        query += ";";

        return query;
    }

    /**
     * For given Strings' array {"A","B","C"} return string in form ('A','B','C')
     *
     * @param arguments
     * @return
     */
    private String arrayToQueryArguments(String[] arguments) {
        String query = "(";

        for (int iterator = 0; iterator < arguments.length; iterator++) {
            query += String.format("'%s'", arguments[iterator]);
            query += (iterator < arguments.length - 1) ? "," : ")";
        }

        return query;
    }
}

package com.arek00.xmlReader.db.EmbeddedDerbyConnectors;

import com.arek00.xmlReader.db.IDBHandler;
import com.arek00.xmlReader.db.ObjectInsertionStrategy;
import com.arek00.xmlReader.db.helpers.QueryValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Admin on 2015-05-28.
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
        String query = generateCreateQuery(name, fields);
        return statement.execute(query);
    }


    @Override
    public boolean dropTable(String tableName) throws SQLException {
        String query = String.format("DROP TABLE %s;", tableName);

        return statement.execute(query);
    }

    @Override
    public int deleteAllFromTable(String tableName) throws SQLException {
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
        String query = generateInsertQuery(columns, values);

        return statement.executeUpdate(query);
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

    @Override
    public <T> int insert(T element, ObjectInsertionStrategy strategy) throws SQLException {
        String[] columns = null;
        String[] values = null;

        strategy.generateInsertionData(element, columns, values);
        return insert(columns, values);
    }

    @Override
    public ResultSet selectAll(String from) throws SQLException {
        String query = String.format("SELECT * FROM %s", from);

        return statement.executeQuery(query);
    }

    @Override
    public ResultSet select(String from, String whereStatement) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE %s", from, whereStatement);

        return statement.executeQuery(query);
    }

    @Override
    public <T> List<T> selectAllObjects(String from, ObjectSelectionStrategy strategy) throws SQLException {
        String query = String.format("SELECT * FROM %s;", from);
        ResultSet selectionResult = statement.executeQuery(query);

        return strategy.generateSelectionList(selectionResult);
    }

    @Override
    public <T> List<T> selectObjects(String from, String whereStatement, ObjectSelectionStrategy strategy) throws SQLException {
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
}

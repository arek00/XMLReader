package com.arek00.xmlReader.db.helpers;

import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.sql.SQLSyntaxErrorException;

import static org.junit.Assert.*;

public class QueryValidatorTest {

    @Test(expected = SQLSyntaxErrorException.class)
    public void shouldFindSemicolon() throws Exception {
        String testQuery = "* from users; drop database derbyDB;";

        QueryValidator.findSemicolons(testQuery);
    }

    @Test
    public void shouldNotFindSemicolon() throws Exception {
        String testQuery = "Users";

        QueryValidator.findSemicolons(testQuery);
        assertTrue(testQuery.equals("Users"));
    }

    @Test
    public void shouldRemoveCommaAtEndOfLine() throws Exception {
        String testQuery = "ID INT AUTOINCREMENT,";

        testQuery = QueryValidator.removeCommasAtLinesEnd(testQuery);
        assertTrue(testQuery.equals("ID INT AUTOINCREMENT"));
    }

    @Test
    public void shouldNotRemoveComma() throws Exception {
        String testQuery = "ID INT AUTOINCREMENT, CREATE TABLE";

        testQuery = QueryValidator.removeCommasAtLinesEnd(testQuery);
        assertTrue(testQuery.equals("ID INT AUTOINCREMENT, CREATE TABLE"));
    }
}
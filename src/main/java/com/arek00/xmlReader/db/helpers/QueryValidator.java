package com.arek00.xmlReader.db.helpers;

import java.sql.SQLSyntaxErrorException;

/**
 * Class that contains methods to validate SQL queries for some conditions.
 */
public class QueryValidator {

    public static void findSemicolons(String argument) throws SQLSyntaxErrorException {
        if (argument.contains(";")) {
            String message = String.format("Argument '%s' contains illegal semicolon(s) which may lead to inject illegal queries.", argument);
            throw new SQLSyntaxErrorException(message);
        }
    }

    public static String removeCommasAtLinesEnd(String line) {
        int length = line.length();

        if (line.charAt(length - 1) == ',') {
            return line.substring(0, length - 2);
        } else {
            return line;
        }
    }

}

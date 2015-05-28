package com.arek00.xmlReader.db;


public interface ObjectInsertionStrategy {
    public <T> void generateInsertionData(T element, String[] columns, String[] values);
}

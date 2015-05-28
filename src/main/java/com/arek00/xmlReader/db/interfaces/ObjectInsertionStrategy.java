package com.arek00.xmlReader.db.interfaces;


import com.arek00.xmlReader.db.valueObjects.InsertionData;

public interface ObjectInsertionStrategy {
    public <T> InsertionData generateInsertionData(T element);
}

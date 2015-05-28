package com.arek00.xmlReader.db.valueObjects;


public class InsertionData {

    String[] columns;
    String[] values;


    public InsertionData(String[] columns, String[] values) {
        this.columns = columns;
        this.values = values;
    }

    public String[] getColumns() {
        return this.columns;
    }

    public String[] getValues() {
        return this.values;
    }


}

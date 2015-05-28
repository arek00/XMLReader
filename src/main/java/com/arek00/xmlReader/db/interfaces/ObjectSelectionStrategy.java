package com.arek00.xmlReader.db.interfaces;

import java.sql.ResultSet;
import java.util.List;


public interface ObjectSelectionStrategy {

    public <T> List<T> generateSelectionList(ResultSet selectionResult);
}

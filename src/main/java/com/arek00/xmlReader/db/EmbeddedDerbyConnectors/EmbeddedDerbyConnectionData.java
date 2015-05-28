package com.arek00.xmlReader.db.EmbeddedDerbyConnectors;

/**
 * Enum that contains constant string useful to create connection with derby db
 */
public enum EmbeddedDerbyConnectionData {
    DRIVER_PACKAGE("org.apache.derby.jdbc.EmbeddedDriver"),
    CONNECTION_URI("jdbc:derby:");

    private String uri;

    public String getURI() {
        return this.uri;
    }

    private EmbeddedDerbyConnectionData(String uri) {
        this.uri = uri;
    }
}

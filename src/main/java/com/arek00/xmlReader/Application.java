package com.arek00.xmlReader;

import com.arek00.xmlReader.db.DBBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        runDatabase();

    }

    public static void runDatabase() {
        DBBuilder starter = new DBBuilder();
    }

}
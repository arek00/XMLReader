package com.arek00.xmlReader.usersReaders;

import com.arek00.xmlReader.entities.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UsersReader {

    private UsersReader() {

    }

    public static List<User> parseUsers(InputStream source, SAXParser parser, UsersReadingHandler handler) throws IOException, SAXException {
        parser.parse(source, handler);

        return handler.getResult();
    }
}

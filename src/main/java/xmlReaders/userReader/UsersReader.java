package xmlReaders.userReader;

import entities.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class UsersReader {

    private UsersReader() {

    }

    public static Iterator<User> parseUsers(InputStream source, SAXParser parser, UsersReadingHandler handler) throws IOException, SAXException {
        parser.parse(source, handler);

        return handler.getResult();
    }
}

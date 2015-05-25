package xmlReaders.userReader;

import entities.User;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class UsersReader {

    private UsersReader()
    {

    }

    public static Iterator<User> parseUsers(SAXParser parser, UsersReadingHandler handler, String source) throws IOException, SAXException {
        InputStream dataStream = new ByteArrayInputStream(source.getBytes());
        parser.parse(dataStream, handler);

        return handler.getResult();
    }
}

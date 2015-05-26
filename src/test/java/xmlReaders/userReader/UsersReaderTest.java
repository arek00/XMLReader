package xmlReaders.userReader;

import entities.User;
import helpers.IteratorHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.validation.constraints.AssertTrue;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static org.junit.Assert.*;

public class UsersReaderTest {

    private final int USERS_COUNT = 3;

    private static String xmlFile = "<users>\n" +
            "<user>\n" +
            "<name>A</name>\n" +
            "<surname>A</surname>\n" +
            "<login>A</login>\n" +
            "</user>\n" +
            "<user>\n" +
            "<name>B</name>\n" +
            "<surname>B</surname>\n" +
            "<login>B</login>\n" +
            "</user>\n" +
            "<user>\n" +
            "<name>C</name>\n" +
            "<surname>C</surname>\n" +
            "<login>C</login>\n" +
            "</user>\n" +
            "</users>";

    private static SAXParser parser;
    private static UsersReadingHandler handler;
    private static InputStream dataStream;

    @BeforeClass
    public static void initializeTest() throws ParserConfigurationException, SAXException, IOException {
        parser = SAXParserFactory.newInstance().newSAXParser();
        handler = new UsersReadingHandler();
        dataStream = new ByteArrayInputStream(xmlFile.getBytes());

        UsersReader.parseUsers(dataStream, parser, handler);
    }

    @Test
    public void shouldReturnThreeUsers() {

        Iterator<User> usersIterator = handler.getResult();
        int iteratorLength = IteratorHelper.length(usersIterator);

        assertTrue(iteratorLength == USERS_COUNT);
    }

    @Test
    public void shouldUsersNamesBeValid() {
        Iterator<User> userIterator = handler.getResult();
        User user = null;
        String[] userNames = {"A", "B", "C"};
        int counter = 0;

        while (userIterator.hasNext()) {
            user = userIterator.next();

            System.out.println(user.getName());

            assertTrue(user.getName()
                    .equals(userNames[counter]));
            assertTrue(user.getSurname()
                    .equals(userNames[counter]));
            assertTrue(user.getLogin()
                    .equals(userNames[counter]));

            ++counter;
        }
    }


}
package com.arek00.xmlReader.controllers;

import com.arek00.xmlReader.db.DBController;
import com.arek00.xmlReader.db.interfaces.IDBHandler;
import com.arek00.xmlReader.db.interfaces.ObjectInsertionStrategy;
import com.arek00.xmlReader.entities.userEntity.User;
import com.arek00.xmlReader.entities.userEntity.UserToDB;
import com.arek00.xmlReader.helpers.MyLogger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;
import com.arek00.xmlReader.usersReaders.UsersReader;
import com.arek00.xmlReader.usersReaders.UsersReadingHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;


@Controller
public class UserUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public
    @ResponseBody
    String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String provideUploadInfo(@RequestParam("name") String name,
                                    @RequestParam("file") MultipartFile file, Model model) {
        /*
        - Valid uploaded file,
        - Get objects from file,
        - Put objects to DB,
        - Display page with table contains uploaded data
         */

        if (!file.isEmpty()) {
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                UsersReadingHandler handler = new UsersReadingHandler();

                parseUsersFromFile(file, parser, handler);

                byte[] bytes = file.getBytes();

                model.addAttribute("users", handler.getResult());
                insertUserToDB(handler.getResult());

                return "upload";
            } catch (ParserConfigurationException e) {

                MyLogger.logError("Parser Configuration Error:", e.getMessage());
                e.printStackTrace();
            } catch (SAXException e) {
                MyLogger.logError("SaxException", e.getMessage());

                e.printStackTrace();
            } catch (IOException e) {
                MyLogger.logError("Input/Output Exception", e.getMessage());

                e.printStackTrace();
            }
        } else {
            MyLogger.logError("UPLOAD FILE ERROR:", "You failed to upload " + name + " because the file was empty.");
        }

        return null;
    }


    private List<User> parseUsersFromFile(MultipartFile file, SAXParser parser, UsersReadingHandler handler) throws IOException, SAXException {
        InputStream stream = file.getInputStream();
        UsersReader.parseUsers(stream, parser, handler);

        return handler.getResult();
    }

    private void validateFile(MultipartFile file) {

    }

    private ModelAndView renderUsersTable(List<User> usersList) {
        ModelAndView mv = new ModelAndView("/upload");
        mv.addObject("users", usersList);

        return mv;
    }

    private String generateHTMLTable(Iterator<User> list) {
        String tableMarkerOpen = "<table>";
        String tableMarkerClose = "</table>";
        String htmlCode = tableMarkerOpen;

        while (list.hasNext()) {
            User currentUser = list.next();

            String record = String.format("<td>%s</td><td>%s</td><td>%s</td>",
                    currentUser.getName(), currentUser.getSurname(), currentUser.getLogin());

            String line = String.format("<tr>%s</tr>", record);
            htmlCode += line;
        }

        htmlCode += tableMarkerClose;

        return htmlCode;
    }

    private void insertUserToDB(List<User> usersList) {
        DBController controller = DBController.getInstance();
        IDBHandler handler = controller.getHandler();
        ObjectInsertionStrategy strategy = new UserToDB<User>();

        for (User user : usersList) {
            try {
                handler.insert("Users",user, strategy);
            } catch (SQLException e) {
                MyLogger.logError("ERROR DURING INSERT USER", e.getMessage());
            }
        }
    }

}

package com.arek00.xmlReader.controllers;

import com.arek00.xmlReader.entities.User;
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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
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
                //
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(name)));
//                stream.write(bytes);
//                stream.close();

                model.addAttribute("users",handler.getResult());

                return "upload";
            } catch (Exception e) {
                //return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            //return "You failed to upload " + name + " because the file was empty.";
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

}

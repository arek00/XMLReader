package web.Controllers;

import entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import xmlReaders.userReader.UsersReader;
import xmlReaders.userReader.UsersReadingHandler;

import javax.xml.parsers.SAXParser;
import java.io.*;
import java.util.Iterator;


@Controller
public class UploadController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String provideUploadInfo(@RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file) {
        /*
        - Valid uploaded file,
        - Get objects from file,
        - Put objects to DB,
        - Display page with table contains uploaded data
         */


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }


    }


    private Iterator<User> parseUsersFromFile(MultipartFile file, SAXParser parser, UsersReadingHandler handler) throws IOException, SAXException {
        InputStream stream = file.getInputStream();
        UsersReader.parseUsers(stream, parser, handler);

        return handler.getResult();
    }

    private void validateFile(MultipartFile file) {

    }

}

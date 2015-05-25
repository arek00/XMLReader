package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Controller
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
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


        validateFile(file);

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();

                return "Uploaded file successfully.";

            } catch (IOException exception) {
                return "Failed to upload file" +
                        exception.getMessage();
            }


        } else {
            return "Failed to upload an empty file.";
        }

    }


    private void validateFile(MultipartFile file)
    {

    }

}

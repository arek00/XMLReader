package com.arek00.xmlReader.controllers;

import com.arek00.xmlReader.db.DBController;
import com.arek00.xmlReader.db.interfaces.IDBHandler;
import com.arek00.xmlReader.entities.userEntity.User;
import com.arek00.xmlReader.entities.userEntity.UserToDB;
import com.arek00.xmlReader.helpers.MyLogger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Page will show all records from users table.
 */

@Controller
public class ShowUsersController {

    @RequestMapping(value = "/showUsers", method = RequestMethod.GET)
    public String provideUsersData(Model model) {
        DBController db = DBController.getInstance();
        IDBHandler handler = db.getHandler();
        UserToDB<User> strategy = new UserToDB<User>();
        List<User> usersList = new ArrayList<User>();

        try {
            usersList = handler.selectAllObjects("Users", strategy);
        } catch (SQLException e) {
            MyLogger.logError("SHOW USERS ERROR:", e.getMessage());
        }
        model.addAttribute("users", usersList);

        return "showUsers";

    }
}

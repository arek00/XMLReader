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
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Page will show all records from users table.
 */

@Controller
public class ShowUsersController {

    @RequestMapping(value = "/showUsers", method = RequestMethod.GET)
    public String provideUsersData(
            @RequestParam(value = "id", required = false) String id,
            Model model) {

        List<User> usersList = (id == null) ? getUsers() : getUsersById(id);

        model.addAttribute("users", usersList);
        return "showUsers";
    }

    private List<User> getUsers() {
        String tableName = "Users";
        List<User> usersList = null;

        DBController db = DBController.getInstance();
        IDBHandler handler = db.getHandler();
        UserToDB<User> strategy = new UserToDB<User>();

        try {
            usersList = handler.selectAllObjects(tableName, strategy);
        } catch (SQLException e) {
            MyLogger.logError("SHOW USERS ERROR:", e.getMessage());
        }

        return usersList;
    }

    private List<User> getUsersById(String id) {
        String tableName = "Users";
        String condition = String.format("session_id='%s'", id);

        List<User> usersList = null;

        DBController db = DBController.getInstance();
        IDBHandler handler = db.getHandler();
        UserToDB<User> strategy = new UserToDB<User>();

        try {
            usersList = handler.selectObjects(tableName, condition, strategy);
        } catch (SQLException e) {
            MyLogger.logError("SHOW USERS ERROR:", e.getMessage());
        }

        return usersList;
    }

}

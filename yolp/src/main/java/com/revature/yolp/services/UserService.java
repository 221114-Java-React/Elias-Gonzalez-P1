package com.revature.yolp.services;

import com.revature.yolp.daos.UserDAO;

// purpose of services is to validate and retrieve data from the Data Access Object
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}

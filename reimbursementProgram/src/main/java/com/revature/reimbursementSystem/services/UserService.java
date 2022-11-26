package com.revature.reimbursementSystem.services;

import com.revature.reimbursementSystem.daos.UserDAO;

/*services validate and retrieve data from DAO (data access objects)*/
//service class is essentially an api
public class UserService {
    /*dependency injection is when a class is dependent on another class*/
    private final UserDAO userDAO;
    //we inject userDAO into UserService to make service dependent on dao
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}

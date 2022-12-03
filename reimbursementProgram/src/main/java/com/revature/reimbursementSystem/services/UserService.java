package com.revature.reimbursementSystem.services;

import com.revature.reimbursementSystem.daos.UserDAO;
import com.revature.reimbursementSystem.dtos.requests.NewLoginRequest;
import com.revature.reimbursementSystem.dtos.requests.NewUserRequest;
import com.revature.reimbursementSystem.dtos.requests.UpdateUserRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.User;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidAuthException;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/*services validate and retrieve data from DAO (data access objects)*/
//service class is essentially an api
public class UserService {
    /*dependency injection is when a class is dependent on another class*/
    private final UserDAO userDAO;

    //we inject userDAO into UserService to make service dependent on dao
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    //methods
    public void saveUser(NewUserRequest req) {
        List<String> usernames = userDAO.findAllUsernames();
        List<String> emails = userDAO.findAllEmails();
        if (!isValidUsername(req.getUsername()))
            throw new InvalidUserException("Username needs to be 8-20 characters long");
        if (usernames.contains(req.getUsername())) throw new InvalidUserException("Username cannot already exist");
        if (!isValidPassword(req.getPassword1()))
            throw new InvalidUserException("Password needs to be minimum eight characters long and at least one character and one number");
        if (!isValidEmail(req.getEmail())) throw new InvalidUserException("Invalid email");
        if (emails.contains((req.getEmail()))) throw new InvalidUserException("Email already exists");
        if (!req.getPassword1().equals(req.getPassword2())) throw new InvalidUserException("Passwords do not match.");
        //after all checks are done
        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), req.getEmail(), req.getPassword1(), req.getGiven_name(), req.getSurname(), false, "0");
        userDAO.save(createdUser);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
    public List<User> getAllInactiveUsers() {
        return userDAO.findAllInactiveUsers();
    }



    public void updateUser(UpdateUserRequest req) {
        List<String> usernames = userDAO.findAllUsernames();
        List<String> emails = userDAO.findAllEmails();
        User userRequestingUpdate = userDAO.getUserByUsernameAndPassword(req.getCurrentUsername(), req.getCurrentPassword());
        //bad input validation
        if (userRequestingUpdate == null) throw new InvalidUserException("Please enter your current username and password");
        if (req.getCurrentUsername().equals("") || req.getCurrentPassword().equals("")) throw new InvalidUserException("Please enter a username and password for user update.");
        if (!isValidUsername(req.getUsername())) throw new InvalidUserException("New username needs to be 8-20 characters long");
        //username validation
        if (usernames.contains(req.getUsername()) && !req.getUsername().equals(req.getCurrentUsername())) throw new InvalidUserException("New username cannot already exist");
        //email validation
        if (!isValidEmail(req.getEmail())) throw new InvalidUserException("Invalid email");
        if (emails.contains((req.getEmail())) && !req.getEmail().equals(userRequestingUpdate.getEmail())) throw new InvalidUserException("Email already exists");
        //password validation
        if (!isValidPassword(req.getPassword1())) throw new InvalidUserException("New password needs to be minimum eight characters long and at least one character and one number");
        if (!req.getPassword1().equals(req.getPassword2())) throw new InvalidUserException("Passwords do not match.");
        //after all checks are done pass req to dao
        userDAO.update(req);
    }


    public Principal login(NewLoginRequest req) {
        User validUser = userDAO.getUserByUsernameAndPassword(req.getUsername(), req.getPassword());
        if (validUser == null) throw new InvalidAuthException("Invalid username or password");
        //logs time at login
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        logger.info(timestamp.toString() + "Login request sent from userservice to authhandler");

        return new Principal(validUser.getUser_id(), req.getUsername(), validUser.getIs_active(), validUser.getRole_id());
    }


    //helper functions
    private boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    private boolean isValidEmail(String email) {
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }



}

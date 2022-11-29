package com.revature.reimbursementSystem.services;

import com.revature.reimbursementSystem.daos.UserDAO;
import com.revature.reimbursementSystem.dtos.requests.NewLoginRequest;
import com.revature.reimbursementSystem.dtos.requests.NewUserRequest;
import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.User;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidAuthException;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;

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
    public void saveUser(NewUserRequest req){
        List<String> usernames = userDAO.findAllUsernames();
        List<String> emails = userDAO.findAllEmails();
        if (!isValidUsername(req.getUsername())) throw new InvalidUserException("Username needs to be 8-20 characters long");
        if(usernames.contains(req.getUsername())) throw new InvalidUserException("Username cannot already exist");
        if (!isValidPassword(req.getPassword1())) throw new InvalidUserException("Password needs to be minimum eight characters long and at least one character and one number");
        if (!isValidEmail(req.getEmail())) throw new InvalidUserException("Invalid email");
        if (emails.contains((req.getEmail()))) throw new InvalidUserException("Email already exists");
        if (!req.getPassword1().equals(req.getPassword2())) throw new InvalidUserException("Passwords do not match.");
        //after all checks are done
        User createdUser = new User(UUID.randomUUID().toString(),req.getUsername(), req.getEmail(), req.getPassword1(), req.getGiven_name(), req.getSurname(), false, "0");
        userDAO.save(createdUser);
    }

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }


    public Principal login(NewLoginRequest req){
        User validUser = userDAO.getUserByUsernameAndPassword(req.getUsername(), req.getPassword());
        if (validUser == null) throw new InvalidAuthException("Invalid username or password");
        return new Principal(validUser.getUser_id(), req.getUsername(), validUser.getIs_active(), validUser.getRole_id());
    }

    private boolean isValidUsername(String username){
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }
    private boolean isValidPassword(String password){
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
    private boolean isValidEmail(String email){
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }
}

package com.revature.reimbursementSystem.daos;

import com.revature.reimbursementSystem.dtos.requests.UpdateUserRequest;
import com.revature.reimbursementSystem.models.User;
import com.revature.reimbursementSystem.utils.ConnectionFactory;
import com.revature.reimbursementSystem.utils.HashBrowns;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*Purpose of a DAO is to return data from the database
* DAO = DATA ACCESS OBJECT*/
public class UserDAO implements CrudDAO<User>{
    private static final HashBrowns hashBrowns = new HashBrowns();

    @Override
    public void save(User obj) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            /*JDBC requires prepared statement, if you need to request from database ALWAYS START WITH PREPARED STATEMENT*/
            PreparedStatement ps = con.prepareStatement("INSERT INTO ers_users (user_id, username, email, password, given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getUser_id());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getGiven_name());
            ps.setString(6, obj.getSurname());
            ps.setBoolean(7, obj.getIs_active());
            ps.setString(8, obj.getRole_id());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public void update(User obj) throws InvalidUserException {
        throw new InvalidUserException("Must have a request to update");
    }


    public void update(UpdateUserRequest req) {
        /*todo update UserDAO.save to take in a user object instead of UpdateUserRequest
         * inherently USER objects encrypt password, while requests encrypt in DAO at ps.setString(3)
         */
        User userRequestingUpdate = getUserByUsernameAndPassword(req.getCurrentUsername(), req.getCurrentPassword());
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE reimbursementsys.ers_users SET username = ?, email = ?, \"password\" = ?, given_name = ?, surname = ?, is_active = ?, role_id = ? WHERE user_id =?");
            ps.setString(1, req.getUsername());
            ps.setString(2, req.getEmail());
            ps.setString(3, hashBrowns.encryptString(req.getPassword1()));
            ps.setString(4, req.getGiven_name());
            ps.setString(5, req.getSurname());
            ps.setBoolean(6, req.getIs_active());
            ps.setString(7, req.getRole_id());
            ps.setString(8, userRequestingUpdate.getUser_id());

            ps.executeUpdate();
        }catch (SQLException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Override
    public User findById() {
        return null;
    }

    @Override
    public List<User> findAll() {
        /*TODO fix bug where RETURN ALL creates a list of users, and ENCRYPTS encrypted passwords again,
        *  this does not affect program functionality, but adds a a layer of encryption to json return*/
        List<User> users = new ArrayList<User>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User currentUser = new User(
                        rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getBoolean("is_active"),
                        rs.getString("role_id"));
                users.add(currentUser);
            }

            }catch (SQLException | NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        return users;
    }


    /*custom methods*/
    public User getUserByUsernameAndPassword(String username, String password){
        User user = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();


            if(rs.next()) {
                user = new User(rs.getString("user_id"), rs.getString("username"),rs.getString("email"), rs.getString("password"), rs.getString("given_name"), rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
            }
        }catch (SQLException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return user;
    }

    public List<String> findAllUsernames() {
        List<String> usernames = new ArrayList<String>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (username) FROM reimbursementsys.ers_users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentUsername = rs.getString("username");
                usernames.add(currentUsername);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return usernames;
    }

    public List<String> findAllEmails() {
        List<String> emails = new ArrayList<String>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (email) FROM reimbursementsys.ers_users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentEmail = rs.getString("email");
                emails.add(currentEmail);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return emails;
    }

    public List<User> findAllInactiveUsers() {
        List<User> users = new ArrayList<User>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_users WHERE is_active = false");
            ResultSet rs = ps.executeQuery();
            System.out.println(ps);
            while (rs.next()) {
                User currentUser = new User(rs.getString("user_id"),rs.getString("username"),rs.getString("email"), rs.getString("password"), rs.getString("given_name"), rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
                users.add(currentUser);
            }


        }catch (SQLException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return users;
    }
}

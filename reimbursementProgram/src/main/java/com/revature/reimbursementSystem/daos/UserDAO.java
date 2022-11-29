package com.revature.reimbursementSystem.daos;

import com.revature.reimbursementSystem.models.User;
import com.revature.reimbursementSystem.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*Purpose of a DAO is to return data from the database
* DAO = DATA ACCESS OBJECT*/
public class UserDAO implements CrudDAO<User>{
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
    public void update(User obj) {

    }

    @Override
    public User findById() {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
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
                user = new User(rs.getString("user_id"), rs.getString("username"),rs.getString("email"), rs.getString("password"), rs.getString("given_name"), rs.getString("surname"), Boolean.parseBoolean(rs.getString("is_active")), rs.getString("role_id"));
            }
        }catch (SQLException e){
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
}

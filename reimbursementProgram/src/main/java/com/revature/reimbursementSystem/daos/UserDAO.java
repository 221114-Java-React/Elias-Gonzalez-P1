package com.revature.reimbursementSystem.daos;

import com.revature.reimbursementSystem.models.User;
import java.util.List;

/*Purpose of a DAO is to return data from the database
* DAO = DATA ACCESS OBJECT*/
public class UserDAO implements CrudDAO<User>{
    @Override
    public void save(User obj) {

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
}

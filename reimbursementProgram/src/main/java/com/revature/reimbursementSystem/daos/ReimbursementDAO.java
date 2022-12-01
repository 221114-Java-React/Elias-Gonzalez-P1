package com.revature.reimbursementSystem.daos;

import com.revature.reimbursementSystem.models.Reimbursement;

import java.util.List;


//DAO MUST SET TIMESTAMP FOR CREATION OF REIMBURSEMENT


public class ReimbursementDAO implements CrudDAO<Reimbursement>{

    @Override
    public void save(Reimbursement obj) {

    }

    @Override
    public void delete(Reimbursement obj) {

    }

    @Override
    public void update(Reimbursement obj) {

    }

    @Override
    public Reimbursement findById() {
        return null;
    }

    @Override
    public List<Reimbursement> findAll() {
        return null;
    }
}

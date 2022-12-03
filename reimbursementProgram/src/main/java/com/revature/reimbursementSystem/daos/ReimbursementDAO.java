package com.revature.reimbursementSystem.daos;

import com.revature.reimbursementSystem.models.Reimbursement;
import com.revature.reimbursementSystem.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReimbursementDAO implements CrudDAO<Reimbursement>{

    //custom methods
    //financeManagerOnly
    public List<Reimbursement> getAllPendingReimbursements(){
        List<Reimbursement> pendingReimbursements = new ArrayList<Reimbursement>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements WHERE status_id = '0' ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement currentReimbursement = new Reimbursement(
                        rs.getString("reimb_id"),
                        rs.getString("description"),
                        rs.getString("payment_id"),
                        rs.getString("author_id"),
                        rs.getString("resolver_id"),
                        rs.getString("status_id"),
                        rs.getString("type_id"),
                        rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"),
                        rs.getString("receipt"),
                        rs.getDouble("amount"));
                pendingReimbursements.add(currentReimbursement);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pendingReimbursements;
    }



    @Override
    public void save(Reimbursement obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("insert into reimbursementsys.ers_reimbursements (reimb_id, amount, submitted, resolved, description, receipt, payment_id, author_id, resolver_id, status_id, type_id)\n" + "values (?, ?, ?, null, ?, '', '', ?, null, '0', ?)");
            ps.setString(1, obj.getReimb_id());
            ps.setDouble(2, obj.getAmount());
            ps.setTimestamp(3, obj.getSubmitted());
            ps.setString(4, obj.getDescription());
            ps.setString(5, obj.getAuthor_id());
            ps.setString(6, obj.getType_id());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Reimbursement obj) {
    }

    @Override
    public void update(Reimbursement obj) {
        Reimbursement reimbursementRequiringUpdate = findByReimb_id(obj.getReimb_id());
        System.out.println(reimbursementRequiringUpdate.toString());
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE reimbursementsys.ers_reimbursements SET status_id = ?, resolved= ?, resolver_id = ?  WHERE reimb_id = ?");
            ps.setString(1, obj.getStatus_id());
            ps.setTimestamp(2,obj.getResolved());
            ps.setString(3, obj.getResolver_id());
            ps.setString(4, obj.getReimb_id());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        };
    }
    /*
    public void update(UpdateReimbursementRequest obj){
        Reimbursement reimbursementRequiringUpdate = findByReimb_id(obj.getReimb_id());
        System.out.println(reimbursementRequiringUpdate.toString());
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE reimbursementsys.ers_reimbursements SET status_id = ? WHERE reimb_id = ?");
            ps.setString(1, obj.getStatus_id());
            ps.setString(2, obj.getReimb_id());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    */



    @Override
    public List<Reimbursement> findAll() {
        List<Reimbursement> allReimbursements = new ArrayList<Reimbursement>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement currentReimbursement = new Reimbursement(
                        rs.getString("reimb_id"),
                        rs.getString("description"),
                        rs.getString("payment_id"),
                        rs.getString("author_id"),
                        rs.getString("resolver_id"),
                        rs.getString("status_id"),
                        rs.getString("type_id"),
                        rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"),
                        rs.getString("receipt"),
                        rs.getDouble("amount"));
                allReimbursements.add(currentReimbursement);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allReimbursements;
    }

    @Override
    public Reimbursement findById(){
        return null;
    }




    public Reimbursement findByReimb_id(String id) {
        Reimbursement reimbursement = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements WHERE reimb_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();


            if(rs.next()) {
                reimbursement = new Reimbursement(
                        rs.getString("reimb_id"),
                        rs.getString("description"),
                        rs.getString("payment_id"),
                        rs.getString("author_id"),
                        rs.getString("resolver_id"),
                        rs.getString("status_id"),
                        rs.getString("type_id"),
                        rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"),
                        rs.getString("receipt"),
                        rs.getDouble("amount"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reimbursement;
    }

    public List<String> findAllIds() {
        List<String> ticketIds = new ArrayList<String>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (reimb_id) FROM reimbursementsys.ers_reimbursements");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentId = rs.getString("reimb_id");
                ticketIds.add(currentId);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return ticketIds;
    }
}

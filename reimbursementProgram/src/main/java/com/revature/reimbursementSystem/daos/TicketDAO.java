package com.revature.reimbursementSystem.daos;

import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.models.Ticket;
import com.revature.reimbursementSystem.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TicketDAO implements CrudDAO<Ticket>{

    //custom methods

    //financeManagerOnly

    @Override
    public void save(Ticket obj) {
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
    public void delete(Ticket obj) {
    }

    @Override
    public void update(Ticket obj) {
        Ticket ticketRequiringUpdate = findByReimb_id(obj.getReimb_id());
        System.out.println(ticketRequiringUpdate.toString());
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE reimbursementsys.ers_reimbursements SET status_id = ?, amount= ?, description= ?, receipt= ?, payment_id= ?, type_id= ?, resolved= ?, resolver_id = ?  WHERE reimb_id = ?");
            //PreparedStatement ps = con.prepareStatement("UPDATE reimbursementsys.ers_reimbursements SET status_id = ?, resolved= ?, resolver_id = ?  WHERE reimb_id = ?");
            ps.setString(1, obj.getStatus_id());
            ps.setDouble(2, obj.getAmount());
            ps.setString(3, obj.getDescription());
            ps.setBytes(4, obj.getReceipt().getBytes());
            ps.setString(5, obj.getPayment_id());
            ps.setString(6, obj.getType_id());
            ps.setTimestamp(7,obj.getResolved());
            ps.setString(8, obj.getResolver_id());
            ps.setString(9, obj.getReimb_id());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        };
    }
    /*
    public void update(UpdateTicketRequest obj){
        Ticket reimbursementRequiringUpdate = findByReimb_id(obj.getReimb_id());
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
    public List<Ticket> findAll() {
        List<Ticket> allTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket currentTicket = new Ticket(
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
                allTickets.add(currentTicket);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allTickets;
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

    public List<Ticket> getAllPendingTickets(){
        List<Ticket> pendingTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements WHERE status_id = '0' ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket currentTicket = new Ticket(
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
                pendingTickets.add(currentTicket);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pendingTickets;
    }

    public static List<Ticket> getAllTicketsByUserId(Principal principal) {
        List<Ticket> userTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements WHERE author_id = ? ");
            ps.setString(1, principal.getUser_id());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket currentTicket = new Ticket(
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
                userTickets.add(currentTicket);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userTickets;
    }

    public static List<Ticket> getAllPendingTicketsByUserId(Principal principal) {
        List<Ticket> userTickets = new ArrayList<Ticket>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements WHERE author_id = ? AND status_id = '0' ");
            ps.setString(1, principal.getUser_id());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket currentTicket = new Ticket(
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
                userTickets.add(currentTicket);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userTickets;
    }


    @Override
    public Ticket findById(){
        return null;
    }

    public Ticket findByReimb_id(String id) {
        Ticket ticket = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursementsys.ers_reimbursements WHERE reimb_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                ticket = new Ticket(
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
        return ticket;
    }


}

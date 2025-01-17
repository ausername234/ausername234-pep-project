package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message insertMessage(Message m) {
        Connection c = ConnectionUtil.getConnection();

        try {
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)", 
                Statement.RETURN_GENERATED_KEYS
                );
            ps.setInt(1, m.getPosted_by());
            ps.setString(2, m.getMessage_text());
            ps.setLong(3, m.getTime_posted_epoch());

            int ct = ps.executeUpdate();
            if (ct > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    m.setMessage_id(rs.getInt(1));
                    return m;
                }
            }
        
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public ArrayList<Message> getMessages() {
        Connection c = ConnectionUtil.getConnection();
        ArrayList<Message> mlist = new ArrayList<Message>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = c.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mlist.add(new Message(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getLong(4)
                ));
            }
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return mlist;
    }

    public Message getMessageById(int id) {
        Connection c = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT posted_by, message_text, time_posted_epoch FROM message WHERE message_id = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Message(
                    id,
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getLong(3)
                );
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int id) {
        Connection c = ConnectionUtil.getConnection();
        try {
            String sel = "SELECT posted_by, message_text, time_posted_epoch FROM message WHERE message_id = ?";
            PreparedStatement pss = c.prepareStatement(sel);
            pss.setInt(1, id);
            Message m;

            ResultSet rs = pss.executeQuery();
            if (rs.next()) {
                m = new Message(
                    id,
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getLong(3)
                );
            } else return null;

            //If message of id found, now try to delete it
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            int ct = ps.executeUpdate();
            if(ct > 0) return m; //Only return msg data if delete succeeded
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(Message m) {
        Connection c = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, m.getMessage_text());
            ps.setInt(2, m.getMessage_id());

            int ct = ps.executeUpdate();
            if(ct > 0) {
                sql = "SELECT posted_by, time_posted_epoch FROM message WHERE message_id = ?";
                ps = c.prepareStatement(sql);
                ps.setInt(1, m.getMessage_id());
    
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    m.setPosted_by(rs.getInt(1));
                    m.setTime_posted_epoch(rs.getLong(2));
                    return m;
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int id) {
        Connection c = ConnectionUtil.getConnection();
        ArrayList<Message> mlist = new ArrayList<Message>();

        try {
            String sql = "SELECT message_id, message_text, time_posted_epoch FROM message WHERE posted_by = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mlist.add(
                    new Message(
                        rs.getInt(1),
                        id,
                        rs.getString(2),
                        rs.getLong(3)
                    )
                );
            }
            return mlist;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}

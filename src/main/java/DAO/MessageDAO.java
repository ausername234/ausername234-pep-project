package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message insertMessage(Message m) {
        Connection c = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement ps = c.prepareStatement(sql);

            ps.executeQuery();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()) {
                int n = (int) rs.getInt(1);
                m.setMessage_id(n);
                return m;
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
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = c.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mlist.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return mlist;
    }
    
}

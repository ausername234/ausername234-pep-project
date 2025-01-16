package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account insertAccount(Account a) {
        Connection c = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());

            ps.executeQuery();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()) {
                int id = (int)rs.getInt(1);
                a.setAccount_id(id);
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(Account a) {
        Connection c = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id FROM account WHERE username = ? AND password = ?;";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int id = (int)rs.getInt(1);
                a.setAccount_id(id);
                return a;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}

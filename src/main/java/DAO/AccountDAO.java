package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account insertAccount(Account a) {
        Connection c = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                a.setAccount_id(rs.getInt(1));
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
            String sql = "SELECT account_id FROM account WHERE username = ? AND password = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                a.setAccount_id(rs.getInt(1));
                return a;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}

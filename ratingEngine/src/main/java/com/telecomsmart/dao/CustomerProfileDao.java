package com.telecomsmart.dao;

import com.telecomsmart.model.CustomerProfile;
import com.telecomsmart.services.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CustomerProfileDao {

    private Connection con;

    private Connection connect() {
        if (con == null) {
            try {
                con = DatabaseManager.getConnection();
            } catch (SQLException e) {
                System.out.println("Error connecting to the database");
                return null;
            }
        }
        return con;
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error disconnecting from the database");
            }
            con = null;
        }
    }

    public Map<String, CustomerProfile> getCustomerProfiles() {
        Map<String, CustomerProfile> customerProfiles = new HashMap<>();
        String query = """
                SELECT msisdn, credit_limit, balance, rateplan_id, free_units_remaining
                FROM customer_profile
                """;
        Connection conn = connect();
        if (conn == null) {
            System.out.println("Error connecting to the database");
            return customerProfiles;
        }
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CustomerProfile customerProfile = new CustomerProfile();
                customerProfile.setMsisdn(rs.getString("msisdn"));
                customerProfile.setCreditLimit(rs.getInt("credit_limit"));
                customerProfile.setBalance(rs.getBigDecimal("balance"));
                customerProfile.setRatePlanId(rs.getInt("rateplan_id"));
                customerProfile.setFreeUnitsRemaining(rs.getLong("free_units_remaining"));
                customerProfiles.put(customerProfile.getMsisdn(), customerProfile);
            }
        } catch (SQLException e) {
            System.out.println("Error getting customer profiles");
            e.printStackTrace();
        }
        return customerProfiles;
    }

    public boolean createCustomerProfile(CustomerProfile customerProfile) {
        Connection conn = connect();
        if (conn == null) {
            return false;
        }
        String query = """
                INSERT INTO customer_profile (msisdn, credit_limit, balance, rateplan_id, free_units_remaining)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customerProfile.getMsisdn());
            ps.setInt(2, customerProfile.getCreditLimit());
            ps.setBigDecimal(3, customerProfile.getBalance());
            ps.setInt(4, customerProfile.getRatePlanId());
            ps.setLong(5, customerProfile.getFreeUnitsRemaining());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating customer profile");
            e.printStackTrace();
            return false;
        }
    }
}

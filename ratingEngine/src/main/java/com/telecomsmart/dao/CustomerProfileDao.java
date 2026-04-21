package com.telecomsmart.dao;

import com.telecomsmart.model.CustomerProfile;
import com.telecomsmart.services.DataBaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// //columns in database: 
// msisdn VARCHAR(15)
// credit_limit INTEGER
// ror_usage NUMERIC(10, 2)
// rateplan_id INTEGER
// free_data_units  BIGINT
// free_voice_units BIGINT
// free_sms_units  BIGINT

public class CustomerProfileDao {
    public Map<String, CustomerProfile> getCustomerProfiles() {
        Map<String, CustomerProfile> customerProfiles = new HashMap<>();
        String query = """
                SELECT msisdn, credit_limit, ror_usage, rateplan_id, free_data_units, free_voice_units, free_sms_units
                FROM customer_profile
                """;
        Connection conn = DataBaseConnect.connect();
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
                customerProfile.setRorUsage(rs.getBigDecimal("ror_usage"));
                customerProfile.setRatePlanId(rs.getInt("rateplan_id"));
                customerProfile.setFreeDataUnits(rs.getLong("free_data_units"));
                customerProfile.setFreeVoiceUnits(rs.getLong("free_voice_units"));
                customerProfile.setFreeSmsUnits(rs.getLong("free_sms_units"));
                customerProfiles.put(customerProfile.getMsisdn(), customerProfile);
            }
        } catch (SQLException e) {
            System.out.println("Error getting customer profiles");
            e.printStackTrace();
        } finally {
            DataBaseConnect.disconnect(conn);
        }
        return customerProfiles;
    }

    public boolean createCustomerProfile (CustomerProfile customerProfile) {
        Connection conn = DataBaseConnect.connect();
        if (conn == null) {
            return false;
        }
        String query = """
                INSERT INTO customer_profile (msisdn, credit_limit, ror_usage, rateplan_id, free_data_units, free_voice_units, free_sms_units)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customerProfile.getMsisdn());
            ps.setInt(2, customerProfile.getCreditLimit());
            ps.setBigDecimal(3, customerProfile.getRorUsage());
            ps.setInt(4, customerProfile.getRatePlanId());
            ps.setLong(5, customerProfile.getFreeDataUnits());
            ps.setLong(6, customerProfile.getFreeVoiceUnits());
            ps.setLong(7, customerProfile.getFreeSmsUnits());
            ps.executeUpdate();
            return true;    
        } catch (SQLException e) {  
            System.out.println("Error creating customer profile");
            e.printStackTrace();
            return false;
        } finally {
            DataBaseConnect.disconnect(conn);
        }
    }
}

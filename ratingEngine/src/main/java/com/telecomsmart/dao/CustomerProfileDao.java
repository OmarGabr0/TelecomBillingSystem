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
                SELECT msisdn, credit_limit, ror_usage, rateplan_id, data_units, voice_units, sms_units,free_units
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
                customerProfile.setDataUnits(rs.getLong("data_units"));
                customerProfile.setVoiceUnits(rs.getLong("voice_units"));
                customerProfile.setSmsUnits(rs.getLong("sms_units"));
                customerProfile.setFreeUnits(rs.getLong("free_units"));
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
                INSERT INTO customer_profile (msisdn, credit_limit, ror_usage, rateplan_id, data_units, voice_units, sms_units, free_units)
                VALUES (?, ?, ?, ?, ?, ?, ?,?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customerProfile.getMsisdn());
            ps.setInt(2, customerProfile.getCreditLimit());
            ps.setBigDecimal(3, customerProfile.getRorUsage());
            ps.setInt(4, customerProfile.getRatePlanId());
            ps.setLong(5, customerProfile.getDataUnits());
            ps.setLong(6, customerProfile.getVoiceUnits());
            ps.setLong(7, customerProfile.getSmsUnits());
            ps.setLong(8, customerProfile.getFreeUnits());
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

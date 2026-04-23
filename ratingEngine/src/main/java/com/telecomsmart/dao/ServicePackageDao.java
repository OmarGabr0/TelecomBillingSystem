package com.telecomsmart.dao;

import com.telecomsmart.services.DataBaseConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicePackageDao {

    // input: ratePlanId and serviceType (1=voice, 2=sms, 3=data from the CDR)
    // output: the service_id (servicePackageId) that matches
    public Integer getServicePackageId(int ratePlanId, int serviceType) {

        String query = """
                SELECT sp.service_id
                FROM service_package sp
                JOIN service_rateplan sr ON sp.service_id = sr.service_id
                WHERE sr.rateplan_id = ?
                AND sp.service_type = ?
                """;

        Connection conn = DataBaseConnect.connect();
        if (conn == null) {
            System.out.println("Error connecting to the database");
            return null;
        }

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, ratePlanId);
            ps.setInt(2, serviceType);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("service_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting service package id");
            e.printStackTrace();
        } finally {
            DataBaseConnect.disconnect(conn);
        }

        return null; // no match found
    }
}
package com.telecomsmart.dao;

import com.telecomsmart.model.ZonePrice;
import com.telecomsmart.services.DataBaseConnect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ZonePriceDao {

    /**
     * Zone prices for one rate plan and service (key = {@code zone_id}).
     * Joins {@code tariff_zone} for {@code dial_prefix}.
     */
    // retrive zone price for each service id  from DataBase
    //input  service_id and rateplan Id 
    // MAP = (ID for service , object of zonePrice )
    // object have the pricing info for the specified zone_id and rateplan entered 
     public Map<Integer, ZonePrice> getZonePrices(int ratePlanId, int servicePackageID) {
        Map<Integer, ZonePrice> zonePrices = new HashMap<>();

        String query = """
            SELECT z.dial_prefix, z.zone_id, rz.price_per_volume, rz.free_unit_deduction
            FROM tariff_zone z
            INNER JOIN rateplan_service_zone rz ON z.zone_id = rz.zone_id
            WHERE rz.rateplan_id = ? AND rz.service_id = ?
            """;

        Connection conn = DataBaseConnect.connect();
        if (conn == null) {
            System.out.println("Error connecting to the database");
            return zonePrices;
        }

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, ratePlanId);
            ps.setInt(2, servicePackageID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ZonePrice zonePrice = new ZonePrice();
                    zonePrice.setDialPrefix(rs.getString("dial_prefix"));
                    zonePrice.setZoneId(rs.getInt("zone_id"));
                    BigDecimal price = rs.getBigDecimal("price_per_volume");
                    zonePrice.setPricePerVolume(price != null ? price : BigDecimal.ZERO);
                    zonePrice.setFreeUnitDeduction(
                            BigDecimal.valueOf(rs.getLong("free_unit_deduction")));

                    zonePrices.put(zonePrice.getZoneId(), zonePrice);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting zone prices");
            e.printStackTrace();
        } finally {
            DataBaseConnect.disconnect(conn);
        }

        return zonePrices;
    }
/*
    public Map<Integer, ZonePrice> getZonePrices(int ratePlanId, int serviceId) {
        Map<Integer, ZonePrice> zonePrices = new HashMap<>();

        String query = """
            SELECT z.dial_prefix, z.zone_id, rz.price_per_volume, rz.free_unit_deduction
            FROM tariff_zone z
            INNER JOIN rateplan_service_zone rz ON z.zone_id = rz.zone_id
            WHERE rz.rateplan_id = ? AND rz.service_id = ?
            """;

        Connection conn = DataBaseConnect.connect();
        if (conn == null) {
            System.out.println("Error connecting to the database");
            return zonePrices;
        }

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, ratePlanId);
            ps.setInt(2, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ZonePrice zonePrice = new ZonePrice();
                    zonePrice.setDialPrefix(rs.getString("dial_prefix"));
                    zonePrice.setZoneId(rs.getInt("zone_id"));
                    BigDecimal price = rs.getBigDecimal("price_per_volume");
                    zonePrice.setPricePerVolume(price != null ? price : BigDecimal.ZERO);
                    zonePrice.setFreeUnitDeduction(
                            BigDecimal.valueOf(rs.getLong("free_unit_deduction")));
                    zonePrices.put(zonePrice.getZoneId(), zonePrice);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting zone prices");
            e.printStackTrace();
        } finally {
            DataBaseConnect.disconnect(conn);
        }

        return zonePrices;
    }
    */ 
}

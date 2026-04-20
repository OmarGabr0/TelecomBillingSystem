/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aggregationengine;
import java.sql.*;
import java.util.*;

/**
 *
 * @author mohamed
 */
public class DataLoader {
    
    // ===============================
    // 1.Database Connection
    // ===============================
    private Connection con;
    public DataLoader(){
        this.con = DatabaseConnection.getConnection();
    }
    // ===============================
    // 2.Get All Active Contracts
    // ===============================
    public List<Integer> getAllActiveContacts (){
        List<Integer> contracts = new ArrayList<>();
        String sql = "SELECT id FROM contracts WHERE status = 'ACTIVE' ";
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                contracts.add(rs.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return contracts;
    }
    // ================================
    // 3.Get Run On Rate From Profile
    // ================================
    public double getProfileROR(int msisdn){
        String sql = "SELECT total_ror FROM profile WHERE contract_id=?";
        double ror=0;
        try (PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, msisdn);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ror=rs.getDouble("total_ror");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ror;
    }
    // ================================
    // 4.Get Monthly Cost of Rate Plan
    // ================================
    public double getMonthlyCost(int msisdn){
        String sql = """
                     SELECT rp.monthly_cost
                     FROM rate_plans rp 
                     JOIN contracts c ON rp.id=c.rate_plan_id
                     WHERE c.id=?
                     """;
        double cost=0;
        try (PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, msisdn);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                cost=rs.getDouble("monthly_cost");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cost;
    }
    // =============================
    // 5.Get Recurring Fees
    // =============================
    public double getRecurringFees(int msisdn) {
        String sql = """
                SELECT COALESCE(SUM(rfd.amount),0) AS total
                FROM contract_recurring_fees crf
                JOIN recurring_fee_definitions rfd
                ON crf.fee_id = rfd.id
                WHERE crf.contract_id = ?
                AND (crf.end_date IS NULL OR crf.end_date >= CURRENT_DATE)
                """;

        double total = 0;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, msisdn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
    // =============================
    // 6.Get One-Time Fees
    // =============================
    public double getOneTimeFees(int msisdn) {
        String sql = """
                SELECT COALESCE(SUM(otf.amount),0) AS total
                FROM contract_one_time_fees cotf
                JOIN one_time_fee_definitions otf
                ON cotf.fee_id = otf.id
                WHERE cotf.contract_id = ?
                AND cotf.billed = false
                """;

        double total = 0;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, msisdn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}

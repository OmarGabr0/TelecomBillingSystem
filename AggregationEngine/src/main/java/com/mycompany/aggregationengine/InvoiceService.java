/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aggregationengine;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 *
 * @author mohamed
 */
public class InvoiceService {

    public final DataLoader repo;
    private final Connection con;
    private final pdfService pdfService;

    public InvoiceService() {
        this.con = DatabaseConnection.getConnection();
        this.repo = new DataLoader(con);
        this.pdfService = new pdfService();
    }

    public void generateAllInvoices() {

        List<Integer> contracts = repo.getAllActiveContacts();
        for (int contractId : contracts) {
            try {
                con.setAutoCommit(false);
                System.out.println("Processing: " + contractId);

                // Get Billing Cycle
                BillingCycle cycle = repo.getBillingCycle(contractId);
                if (cycle == null) {
                    System.out.println("No cycle → skip");
                    continue;
                }
                // Duplicate Check
                if (invoiceExists(contractId, cycle)) {
                    System.out.println("Invoice already exists → skip");
                    continue;
                }
                // Get Data
                CustomerData customer = repo.getCustomerData(contractId);
                if (customer == null) {
                    System.out.println("No Customer Found → skip");
                    continue;
                }
                
                String planName =repo.getRatePlanName(contractId);
                double monthlyCost = repo.getMonthlyCost(contractId);
                double ror = repo.getProfileROR(contractId);
                double oneTimeFees = repo.getOneTimeFees(contractId);
                double recurringFees = repo.getRecurringFees(contractId);
                // Calculate Total Invoice Cost 
                double subtotal = monthlyCost + ror + recurringFees + oneTimeFees;
                double discount = 0;
                double taxRate = 0.1;

                double tax = (subtotal - discount) * taxRate;
                double total = subtotal - discount + tax;
                // Set Invoice Data (Build Object)
                InvoiceData data = new InvoiceData(contractId,
                        planName,
                        cycle,
                        customer,
                        monthlyCost,
                        recurringFees,
                        oneTimeFees,
                        ror,
                        subtotal,
                        discount,
                        tax,
                        total);
                // Generate PDF
                String pdfPath = pdfService.generate(data);
                // Insert Invoice
                insertInvoice(data, pdfPath);
                // reset + mark Billed
                resetProfile(contractId);
                markOneTimeFeesAsBilled(contractId);
                con.commit();
                System.out.println("Committed ✔ " + contractId);
            } catch (Exception e) {

                try {
                    con.rollback();
                    System.out.println("Rolled back ❌ " + contractId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                e.printStackTrace();
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // =========================
    // Check Duplication
    // =========================
    private boolean invoiceExists(int contractId, BillingCycle cycle) {

        String sql = """
        SELECT 1 FROM invoices
        WHERE contract_id = ?
        AND billing_start = ?
        AND billing_end = ?
        """;

        try (var ps = con.prepareStatement(sql)) {

            ps.setInt(1, contractId);
            ps.setDate(2, (Date) cycle.start);
            ps.setDate(3, (Date) cycle.end);

            var rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // =========================
    // Insert Invoice
    // =========================
    private void insertInvoice(InvoiceData data, String pdfPath) {

        String sql = """
        INSERT INTO invoices (
            contract_id,
            billing_start,
            billing_end,
            subtotal,
            discount,
            tax,
            total,
            pdf_path
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, data.contractId);
            ps.setDate(2, (Date) data.cycle.start);
            ps.setDate(3, (Date) data.cycle.end);
            ps.setDouble(4, data.subtotal);
            ps.setDouble(5, data.discount);
            ps.setDouble(6, data.tax);
            ps.setDouble(7, data.total);
            ps.setString(8, pdfPath);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // Reset Profile
    // =========================
    private void resetProfile(int contractId) {

        String sql = "UPDATE profile SET total_ror = 0 WHERE contract_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, contractId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // Mark One-Time Fees
    // =========================
    private void markOneTimeFeesAsBilled(int contractId) {

        String sql = """
                UPDATE contract_one_time_fees
                SET billed = true
                WHERE contract_id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, contractId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



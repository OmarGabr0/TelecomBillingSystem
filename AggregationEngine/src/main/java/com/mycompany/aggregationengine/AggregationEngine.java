/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.aggregationengine;

import java.sql.Connection;


/**
 *
 * @author mohamed
 */
public class AggregationEngine {

    public static void main(String[] args) {
        DataLoader repo = new DataLoader();

        var contracts = repo.getAllActiveContacts();

        for (int c : contracts) {
            System.out.println("Contract: " + c);
            System.out.println("ROR: " + repo.getProfileROR(c));
            System.out.println("Monthly: " + repo.getMonthlyCost(c));
            System.out.println("Recurring: " + repo.getRecurringFees(c));
            System.out.println("OneTime: " + repo.getOneTimeFees(c));
            System.out.println("----------------------");
        }
    }
}

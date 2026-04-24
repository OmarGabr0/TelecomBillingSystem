/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aggregationengine;

/**
 *
 * @author mohamed
 */
public class InvoiceData {

    public int contractId;
    
    public String  ratePlanName;

    
    public BillingCycle cycle;
    
    public CustomerData customer;
    
    public double monthly;
    public double recurring;
    public double oneTime;
    public double ror;
    
    public double subtotal;
    public double discount;
    public double tax;
    public double total;

   public InvoiceData(int contractId, String ratePlanName, BillingCycle cycle, CustomerData customer, double monthly, double recurring, double oneTime, double ror, double subtotal, double discount, double tax, double total) {
        this.contractId = contractId;
        this.ratePlanName = ratePlanName;
        this.cycle = cycle;
        this.customer = customer;
        this.monthly = monthly;
        this.recurring = recurring;
        this.oneTime = oneTime;
        this.ror = ror;
        this.subtotal = subtotal;
        this.discount = discount;
        this.tax = tax;
        this.total = total;
    }
    
    

}

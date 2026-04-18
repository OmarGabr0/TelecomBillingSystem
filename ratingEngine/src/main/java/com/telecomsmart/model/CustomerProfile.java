package com.telecomsmart.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author omar
 */
public class CustomerProfile {
//    CREATE TABLE IF NOT EXISTS customer_profile (
//    msisdn VARCHAR(15) NOT NULL REFERENCES contract (msisdn) ON DELETE CASCADE,
//    credit_limit INT NOT NULL,
//    balance DECIMAL(10, 2) NOT NULL,
//rateplan_id INT NOT NULL REFERENCES rateplan (rateplan_id) ON DELETE CASCADE,
//free_units_remaining BIGINT NOT NULL
//);
//    
    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getRatedPlan() {
        return ratedPlan;
    }

    public BigInteger getFreeUnitsRemaining() {
        return freeUnitsRemaining;
    }
    private Integer creditLimit;
    private BigDecimal balance;
    private Integer ratedPlan ; 
    private BigInteger freeUnitsRemaining;

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setRatedPlan(Integer ratedPlan) {
        this.ratedPlan = ratedPlan;
    }

    public void setFreeUnitsRemaining(BigInteger freeUnitsRemaining) {
        this.freeUnitsRemaining = freeUnitsRemaining;
    }
    
}

package com.telecomsmart.model;

import java.math.BigDecimal;

/**
 * Maps to {@code customer_profile}.
 *
 * @author omar
 */
public class CustomerProfile {

    // CREATE TABLE IF NOT EXISTS customer_profile (
    //     msisdn VARCHAR(15) NOT NULL REFERENCES contract (msisdn) ON DELETE CASCADE,
    //     credit_limit INT NOT NULL,
    //     balance DECIMAL(10, 2) NOT NULL,
    //     rateplan_id INT NOT NULL REFERENCES rateplan (rateplan_id) ON DELETE CASCADE,
    //     free_units_remaining BIGINT NOT NULL
    // );

    private String msisdn;
    private Integer creditLimit;
    private BigDecimal balance;
    private Integer ratePlanId;
    private long freeUnitsRemaining;

    public CustomerProfile() {
    }

    public CustomerProfile(String msisdn, Integer creditLimit, BigDecimal balance,
            Integer ratePlanId, long freeUnitsRemaining) {
        this.msisdn = msisdn;
        this.creditLimit = creditLimit;
        this.balance = balance;
        this.ratePlanId = ratePlanId;
        this.freeUnitsRemaining = freeUnitsRemaining;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getRatePlanId() {
        return ratePlanId;
    }

    public void setRatePlanId(Integer ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public long getFreeUnitsRemaining() {
        return freeUnitsRemaining;
    }

    public void setFreeUnitsRemaining(long freeUnitsRemaining) {
        this.freeUnitsRemaining = freeUnitsRemaining;
    }

    @Override
    public String toString() {
        return "CustomerProfile{"
                + "msisdn='" + msisdn + '\''
                + ", creditLimit=" + creditLimit
                + ", balance=" + balance
                + ", ratePlanId=" + ratePlanId
                + ", freeUnitsRemaining=" + freeUnitsRemaining
                + '}';
    }
}

package com.telecomsmart.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Maps to {@code customer_profile} (see {@code Billing.sql}).
 *
 * @author omar
 */
public class CustomerProfile {

    // CREATE TABLE IF NOT EXISTS customer_profile (
    //     msisdn VARCHAR(15) NOT NULL REFERENCES contract (msisdn) ON DELETE CASCADE,
    //     credit_limit INT NOT NULL,
    //     ror_usage DECIMAL(10, 2) NOT NULL,
    //     rateplan_id INT NOT NULL REFERENCES rateplan (rateplan_id) ON DELETE CASCADE,
    //     free_data_units BIGINT NOT NULL,
    //     free_voice_units BIGINT NOT NULL,
    //     free_sms_units BIGINT NOT NULL
    // );
    // //columns in database: 



    private String msisdn;
    private Integer creditLimit;
    private BigDecimal rorUsage;
    private Integer ratePlanId;
    private long freeDataUnits;
    private long freeVoiceUnits;
    private long freeSmsUnits;

    public CustomerProfile() {
    }

    public CustomerProfile(String msisdn, Integer creditLimit, BigDecimal rorUsage,
            Integer ratePlanId, long freeDataUnits, long freeVoiceUnits, long freeSmsUnits) {
        this.msisdn = msisdn;
        this.creditLimit = creditLimit;
        this.rorUsage = rorUsage;
        this.ratePlanId = ratePlanId;
        this.freeDataUnits = freeDataUnits;
        this.freeVoiceUnits = freeVoiceUnits;
        this.freeSmsUnits = freeSmsUnits;
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

    public BigDecimal getRorUsage() {
        return rorUsage;
    }

    public void setRorUsage(BigDecimal rorUsage) {
        this.rorUsage = rorUsage;
    }

    public Integer getRatePlanId() {
        return ratePlanId;
    }

    public void setRatePlanId(Integer ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public long getFreeDataUnits() {
        return freeDataUnits;
    }

    public void setFreeDataUnits(long freeDataUnits) {
        this.freeDataUnits = freeDataUnits;
    }

    public long getFreeVoiceUnits() {
        return freeVoiceUnits;
    }

    public void setFreeVoiceUnits(long freeVoiceUnits) {
        this.freeVoiceUnits = freeVoiceUnits;
    }

    public long getFreeSmsUnits() {
        return freeSmsUnits;
    }

    public void setFreeSmsUnits(long freeSmsUnits) {
        this.freeSmsUnits = freeSmsUnits;
    }

    public long getFreeUnitsRemaining() {
        return freeDataUnits + freeVoiceUnits + freeSmsUnits;
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerProfile that = (CustomerProfile) o;
        return Objects.equals(msisdn, that.msisdn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(msisdn);
    }
*/
    @Override
    public String toString() {
        return "CustomerProfile{"
                + "msisdn='" + msisdn + '\''
                + ", creditLimit=" + creditLimit
                + ", rorUsage=" + rorUsage
                + ", ratePlanId=" + ratePlanId
                + ", freeDataUnits=" + freeDataUnits
                + ", freeVoiceUnits=" + freeVoiceUnits
                + ", freeSmsUnits=" + freeSmsUnits
                + ", freeUnitsRemaining=" + getFreeUnitsRemaining()
                + '}';
    }
}

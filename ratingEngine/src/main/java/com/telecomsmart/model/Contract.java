
package com.telecomsmart.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author omar
 */
public class Contract {

    private String msisdn;
    private String contractName;
    private Integer creditLimit;
    private BigDecimal balance;

    private Integer customerId;
    private Integer rateplanId;

    private LocalDateTime createdAt;

    private Customer customer;
    private RatePlan ratePlan;

}
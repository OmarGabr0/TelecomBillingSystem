//RatingEngine.java: The orchestrator. It loops through CDRs, calls the Resolver and Processor, and creates a RatedCdr result.
package com.telecomsmart.ratingengine;

import com.telecomsmart.model.*;
import com.telecomsmart.services.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.telecomsmart.dao.*;

/**
 *
 * @author omar
 */
public class RatingEngine {
    private long bytesToMB(long bytes) {
        return (bytes + (1024 * 1024 - 1)) / (1024 * 1024);
    }

    private long secondsToMinutes(long seconds) {
        return (seconds + 59) / 60;
    }

    public static void main(String[] args) {
        RatingEngine ratingEngine = new RatingEngine();

        while (true) {
            // Caching the customer profiles
            // (msisdn,object of customer profile)
            ServicePackageDao servicePackageDao = new ServicePackageDao();
            ZoneResolver zoneResolver = new ZoneResolver();
            CustomerProfileDao customerProfileDao = new CustomerProfileDao();

            // get all customers profiles in memory
            Map<String, CustomerProfile> Customers = customerProfileDao.getCustomerProfiles();
            //
            // Input for Omar:
            // assume i have the cdr from cdr filter
            List<CdrRecord> cdrs = CdrHandling.getCdrs();

            if (cdrs.isEmpty()) {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 
                continue;
            }

            // looping in the cdrs
            for (CdrRecord cdr : cdrs) {
                String cdrMsisdn = cdr.getMsisdn();
                // git the customer for that CDR
                CustomerProfile customer = Customers.get(cdrMsisdn);

                if (customer != null) {
                    // 1- git service package
                    Integer servicePackageId = servicePackageDao.getServicePackageId(customer.getRatePlanId(),cdr.getServiceId());
                    if (servicePackageId == null) {
                        System.out.println("No service package found for msisdn: " + cdrMsisdn);
                        continue; // skip this CDR
                    }
                    // 2- git zone price
                    // zone resolving based on service and rateplan
                    ZonePrice pricedZone = zoneResolver.resolveZonePrice(customer.getRatePlanId(), servicePackageId,cdr);
                    if (pricedZone == null) {
                        System.out.println("No pricing found for CDR: " + cdr.getCdrId());
                        continue;
                    }

                    // RLH
                    switch (cdr.getServiceId()) {

                        case 1: // VOICE

                            long seconds = cdr.getDurationVolume();
                            long minutes = (long) Math.ceil(seconds / 60.0); // rounding up to the nearest minute

                            if (customer.getFreeUnits() > 0) {

                                long remainingUnits = customer.getFreeUnits() - minutes; // 1 minuts = 1 unit 

                                if (remainingUnits >= 0) {
                                    customer.setFreeUnits(remainingUnits);
                                } else {
                                    long chargeableMinutes = Math.abs(remainingUnits);
                                    BigDecimal charge = pricedZone.getPricePerVolume().multiply(BigDecimal.valueOf(chargeableMinutes));
                                    customer.setFreeUnits(0L);
                                    customer.setRorUsage(customer.getRorUsage().add(charge));
                                }
                            } else {
                                BigDecimal charge = pricedZone.getPricePerVolume().multiply(BigDecimal.valueOf(minutes));
                                customer.setRorUsage(customer.getRorUsage().add(charge));
                            }

                            break;

                        case 2: // SMS
                                long smsCount = cdr.getDurationVolume();

                                long deduction = pricedZone.getUnitDeduction() * smsCount;

                                if (customer.getSmsUnits() > 0) {
                                    customer.setSmsUnits(customer.getSmsUnits() - deduction);
                                } else {
                                    BigDecimal charge = pricedZone.getPricePerVolume().multiply(BigDecimal.valueOf(smsCount));
                                    customer.setRorUsage(customer.getRorUsage().add(charge));
                                }
                            break;

                        case 3: // DATA

                            long usageMB = ratingEngine.bytesToMB(cdr.getDurationVolume());

                            if (customer.getDataUnits() > 0) {

                                long remainingUnits = customer.getDataUnits() - usageMB;

                                if (remainingUnits >= 0) {
                                    customer.setDataUnits(remainingUnits);
                                } else {
                                    // partial consumption
                                    long chargeableMB = Math.abs(remainingUnits);
                                    BigDecimal charge = pricedZone.getPricePerVolume().multiply(BigDecimal.valueOf(chargeableMB));
                                    customer.setDataUnits(0L);
                                    customer.setRorUsage(customer.getRorUsage().add(charge));
                                }
                            } else {
                                BigDecimal charge = pricedZone.getPricePerVolume().multiply(BigDecimal.valueOf(usageMB));
                                customer.setRorUsage(customer.getRorUsage().add(charge));
                            }
                            break;

                        default:
                            System.out.println("Unknown service");
                    }

                    // 1. Add external fees from the CDR to the customer's ROR
                    if (cdr.getExternalFeesAmount() != null && cdr.getExternalFeesAmount().compareTo(BigDecimal.ZERO) > 0) {
                        customer.setRorUsage(customer.getRorUsage().add(cdr.getExternalFeesAmount()));
                    }

                    // 2. Update the Database!
                    boolean isUpdated = customerProfileDao.updateCustomerProfile(customer);
                    if (isUpdated) {
                        System.out.println("Successfully updated DB for MSISDN: " + cdrMsisdn + " | New ROR: " + customer.getRorUsage());
                    } else {
                        System.out.println("Failed to update DB for MSISDN: " + cdrMsisdn);
                    }
                    
                    // --- NEW CODE ENDS HERE ---

                    // credit limit

                } else {
                    System.out.println("No match for: " + cdr.getMsisdn());
                }

            }

        }

    }

}

//RatingEngine.java: The orchestrator. It loops through CDRs, calls the Resolver and Processor, and creates a RatedCdr result.
package com.telecosmart.ratingengine;
import com.telecomsmart.model.*;
import com.telecomsmart.service.*;
import com.telecomsmart.dao.*;
/**
 *
 * @author omar
 */
    public class RatingEngine {
    public static void main(String[] args) {
    
        while(true){
            // Caching the customer profiles 
                 //(msisdn,object of customer profile)
            Map<String, CustomerProfile> Customers =  CustomerProfileDao.getCustomerProfiles();
           //
           // Input for Omar: 
            // assume  i have the cdr from cdr filter 
            List<CdrRecord> cdrs = CdrHandling.getCdrs() ; 
            // looping in the cdrs 
            for(CdrRecord cdr : cdrs){
                String cdrMsisdn= cdr.getMsisdn();
                //git the customer for that CDR 
                CustomerProfile customer = Customers.get(cdrMsisdn);

                if(customer != null ){
                    //zone resolving based on service and rateplan 
                    ZonePrice pricedZone = ZoneResolver.resolveZonePrice(customer.ratePlanId,cdr.serviceId);
                   
                    //RLH
                    switch(cdr.serviceId){
                        case 1: // Voice call 
                            if(customer.freeVoiceUnits > 0) // if customer have free units
                            {
                                customer.setFreeVoiceUnits(customer.getFreeVoiceUnits()-pricedZone.freeUnitDeduction ) ; 

                            } else {
                                customer.setRorUsage += pricedZone.pricePerVolume ; 
                            }
                        case 2: //  SMS 
                            if(customer.freeSmsUnits> 0 ){
                                customer.setFreeSmsUnits(customer.getFreeSmsUnits()-pricedZone.freeUnitDeduction);
                            } else { 
                                customer.setRorUsage += pricedZone.pricePerVolume ; 
                            }
                        case 3: // Data
                            if(customer.freeDataUnits> 0 ){
                                customer.setFreeDataUnits(customer.getFreeDataUnits()-pricedZone.freeUnitDeduction);
                            } else { 
                                customer.setRorUsage += pricedZone.pricePerVolume ; 
                            }
                        default:
                            System.out.println("RatingEngine-ZoneResolving: Unknown Service");
                    }

                    //credit limit 
                    

                } else { 
                    System.out.println("No match for: " + msisdn);
                }

            }

        }
                
    }
    
}

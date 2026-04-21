//ZoneResolver.java: Takes dialB, checks the TreeMap, and returns the zone_id.
package com.telecosmart.ratingengine;
import com.telecomsmart.*;
/**
 *
 * @author omar
 */
public class ZoneResolver {
    // id SERIAL PRIMARY KEY,
    // rateplan_id INT NOT NULL REFERENCES rateplan(rateplan_id) ON DELETE CASCADE,
    // service_id INT NOT NULL REFERENCES service_package(service_id) ON DELETE CASCADE,
    // zone_id INT NOT NULL REFERENCES tariff_zone(zone_id) ON DELETE CASCADE,

    // price_per_volume DECIMAL(10,2) NOT NULL,
    // free_unit_deduction BIGINT DEFAULT 0

    public List<BigDecimal> zoneDeduct (List<CdrRecord> CDRs){
        List<BigDecimal> listdeduction=new List<> ; 

        for(CdrRecord cdr : CDRs ){
            String DaialB= cdr.getDialB() ;
            String dialBPrefix = DaialB.substring(0,3) ; 
            
        }
    }
}

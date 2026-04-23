//ZoneResolver.java: Takes dialB, checks the TreeMap, and returns the zone_id.
package com.telecomsmart.ratingengine;

import com.telecomsmart.dao.TarrifZoneDao;
import com.telecomsmart.dao.ZonePriceDao;
import com.telecomsmart.model.CdrRecord;
import com.telecomsmart.model.CustomerProfile;
import com.telecomsmart.model.TariffZone;
import com.telecomsmart.model.ZonePrice;
import java.math.BigDecimal;
import java.util.Map;

public class ZoneResolver {
    private final TarrifZoneDao tariffZoneDao = new TarrifZoneDao();
    private final ZonePriceDao zonePriceDao = new ZonePriceDao();

    ////////////////////////////////////////////////////////////
    // Function: to git the object (ZonePrice) for a specified user
    // by knowing the user Rate Plan and Service ID (Type)
    // Need: need to add freeUnitS deduction for sms and Data in the table
    //////////////////////////////////////////////////////////// rateplan_service_zone
    /**
     * help in pricing the user based on the dialB zone
     * 
     * returns the objet and the rating engine would pick if he want to
     * rate based on credits or based on the free units
     * 
     * input the cdr you parced , the customer based on this cdr
     */
    /**
     * Resolves the zone price entry for a CDR, based on dialB prefix.
     */
    public ZonePrice resolveZonePrice(Integer ratePlanId, Integer servicePackageId, CdrRecord cdr) {

        if (ratePlanId == null || servicePackageId == null || cdr == null) {
            return null;
        }

        // data case
        if (cdr.getServiceId() == 3) {
            return ZonePrice.forData();
        }

        // VOICE / SMS CASE
        Map<String, TariffZone> zoneMapping = tariffZoneDao.getZoneMaping();

        Integer zoneId = resolveZoneId(cdr.getDialB(), zoneMapping);
        if (zoneId == null)
            return null;

        Map<Integer, ZonePrice> zonePrices = zonePriceDao.getZonePrices(ratePlanId, servicePackageId);

        return zonePrices.get(zoneId);
    }

    ///////////////////////////////////////////////////////////////////
    //////////// Function to decide the credit or the free unit deduction for a use
    /**
     * I assume you got the cdr for wich user and have the customerProfile for it
     * also you know which rateplan and service id for that user
     * project Fisrt Stage: only one service id for each user
     */
    /**
     * Returns charge amount using price_per_volume * usage volume.
     */

    public BigDecimal calculateCharge(Integer ratePlanId, Integer serviceId, CdrRecord cdr,
            CustomerProfile customer) {

        if (cdr == null)
            return BigDecimal.ZERO;

        ZonePrice zonePrice = resolveZonePrice(ratePlanId, serviceId, cdr);

        if (zonePrice == null || zonePrice.getPricePerVolume() == null) {
            return BigDecimal.ZERO;
        }

        return zonePrice.getPricePerVolume()
                .multiply(BigDecimal.valueOf(cdr.getDurationVolume()));
    }

    private Integer resolveZoneId(String dialB, Map<String, TariffZone> zoneMapping) {

        if (dialB == null)
            return null;

        String normalized = dialB.replaceAll("[^0-9]", "");

        for (Map.Entry<String, TariffZone> entry : zoneMapping.entrySet()) {
            if (normalized.startsWith(entry.getKey())) {
                return entry.getValue().getZoneId();
            }
        }
        return null;
    }
}

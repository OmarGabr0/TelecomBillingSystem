//ZoneResolver.java: Takes dialB, checks the TreeMap, and returns the zone_id.
package com.telecosmart.ratingengine;

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
    // by knowing the user Rate Plan and Service ID i will consume from
    // Need: need to add freeUnitS deduction for sms and Data in the table rateplan_service_zone
    /**
        help in pricing the user based on the dialB zone
        returns the objet and the rating engine would pick if he want to
        rate based on credits or based on the free units
        input the cdr you parced , the customer based on this cdr
    */
    /**
     * Resolves the zone price entry for a CDR, based on dialB prefix.
     */
    public ZonePrice resolveZonePrice(Integer ratePlanId, Integer servicePackageId, CdrRecord cdr) {
        if (ratePlanId == null || servicePackageId == null || cdr == null || cdr.getDialB() == null) {
            return null;
        }

        Map<String, TariffZone> zoneMapping = tariffZoneDao.getZoneMaping();
        Integer currentZoneId = resolveZoneId(cdr.getDialB(), zoneMapping);
        if (currentZoneId == null) {
            return null;
        }

        Map<Integer, ZonePrice> zonePricesMap = zonePriceDao.getZonePrices(ratePlanId, servicePackageId);
        return zonePricesMap.get(currentZoneId);
    }

    ///////////////////////////////////////////////////////////////////
    //////////// Function to decide the credit or the free unit deduction for a use
    /**
        I assume you got the cdr for wich user and have the customerProfile for it
        also you know which rateplan and service package id for that user
        project Fisrt Stage: only one rateplan,servicepackageId for each user
    */
    /**
     * Returns charge amount using price_per_volume * usage volume.
     */

      /*
    public BigDecimal calculateCharge(Integer ratePlanId, Integer servicePackageId, CdrRecord cdr, CustomerProfile customer) {
        ZonePrice zonePrice = resolveZonePrice(ratePlanId, servicePackageId, cdr);
        if (zonePrice == null || zonePrice.getPricePerVolume() == null || cdr == null) {
            return BigDecimal.ZERO;
        }

        return zonePrice.getPricePerVolume().multiply(BigDecimal.valueOf(cdr.getDurationVolume()));
    }

    private Integer resolveZoneId(String dialB, Map<String, TariffZone> zoneMapping) {
        String matchedPrefix = null;

        for (String prefix : zoneMapping.keySet()) {
            if (dialB.startsWith(prefix)) {
                if (matchedPrefix == null || prefix.length() > matchedPrefix.length()) {
                    matchedPrefix = prefix;
                }
            }
        }

        if (matchedPrefix == null) {
            return null;
        }

        TariffZone zone = zoneMapping.get(matchedPrefix);
        return zone == null ? null : zone.getZoneId();
    }
      */
}
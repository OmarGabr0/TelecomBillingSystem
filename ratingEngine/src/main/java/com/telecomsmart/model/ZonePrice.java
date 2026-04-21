package com.telecomsmart.model;

import java.math.BigDecimal;

public class ZonePrice { 
    private String dialPrefix ; // can be deleted 
    private Integer zoneId ; 
    private BigDecimal pricePerVolume; 
    private BigDecimal freeUnitDeduction ; 

    // public TariffZone(Integer zoneId, String dialPrefix, String zoneName,
    //     String description, String destinationName) {
    //     this.zoneId = zoneId;
    //     this.dialPrefix = dialPrefix;
    //     this.zoneName = zoneName;
    //     this.description = description;
    //     this.destinationName = destinationName;
    //     }

    public String getDialPrefix() {
        return dialPrefix;
    }

    public void setDialPrefix(String dialPrefix) {
        this.dialPrefix = dialPrefix;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public BigDecimal getPricePerVolume() {
        return pricePerVolume;
    }

    public void setPricePerVolume(BigDecimal pricePerVolume) {
        this.pricePerVolume = pricePerVolume;
    }

    public BigDecimal getFreeUnitDeduction() {
        return freeUnitDeduction;
    }

    public void setFreeUnitDeduction(BigDecimal freeUnitDeduction) {
        this.freeUnitDeduction = freeUnitDeduction;
    }

}
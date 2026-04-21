package com.telecomsmart.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author omar
 */
public class RatePlan {

    private int ratePlanId;
    private String name;
    private Float ror;
    private String description;
    private Float planPrice;
    private Map<Integer, ServicePackage> serviceMap;

    public RatePlan() {
        this.serviceMap = new HashMap<>();
    }

    public int getRatePlanId() {
        return ratePlanId;
    }

    public void setRatePlanId(int ratePlanId) {
        this.ratePlanId = ratePlanId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRor() {
        return ror;
    }

    public void setRor(Float ror) {
        this.ror = ror;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(Float planPrice) {
        this.planPrice = planPrice;
    }

    // the linking between the service packages it have 
    public Map<Integer, ServicePackage> getServiceMap() {
        return serviceMap;
    }
    
    // ill move it to the DAO 
 //  ServicePackage sp = serviceRegistry.getById(serviceId);
    public ServicePackage getById(int serviceId) {
        return serviceMap.get(serviceId);
    }

    // setter for service map 
    public void setServiceMap(Map<Integer, ServicePackage> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    public String toString() {
        return "RatePlan{"
                + "ratePlanId=" + ratePlanId
                + ", name='" + name + '\''
                + ", ror=" + ror
                + ", description='" + description + '\''
                + ", planPrice=" + planPrice
                + ", serviceMap=" + serviceMap
                + '}';
    }

}

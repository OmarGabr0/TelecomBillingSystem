package com.telecomsmart.model;

//import java.util.List;
import java.util.Map;
import java.util.HashMap;
//import com.telecomsmart.ServicePackage;

/**
 *
 * @author omar
 */
public class RatePlan {

    private int rateplan_id;
    private String name;
    private Float ROR;
    private String description;
    private Float plan_price;
    private Map<Integer, ServicePackage> serviceMap;

    public void setRateplan_id(int rateplan_id) {
        this.rateplan_id = rateplan_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setROR(Float ROR) {
        this.ROR = ROR;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlan_price(Float plan_price) {
        this.plan_price = plan_price;
    }

    public int getRateplan_id() {
        return rateplan_id;
    }

    public String getName() {
        return name;
    }

    public Float getROR() {
        return ROR;
    }

    public String getDescription() {
        return description;
    }

    public Float getPlan_price() {
        return plan_price;
    }

    // the linking between the service packages it have 
    public RatePlan() {
        Map<Integer, ServicePackage> serviceMap = new HashMap<Integer, ServicePackage>();
    }
    // to return the private map 
    // will use in retrive the service package by id 
   
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
        return "RatePlan{" + "rateplan_id=" + rateplan_id + ", name=" + name + ", ROR=" + ROR + ", description=" + description + ", plan_price=" + plan_price + ", serviceMap=" + serviceMap + '}';
    }

}

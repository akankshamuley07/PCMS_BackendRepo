package com.pcms.users.Config.Model;
import java.util.Date;
public class UsageResponse {

    private String planName;
    private long units_consumed;
    private double pricePerUnit;
    private double totalCost;
    private long total_units_consumed;
    private double percentage;
    private Date requiredFrom;
    public Date getRequiredFrom() {
        return requiredFrom;    }
    public void setRequiredFrom(Date requiredFrom) {
        this.requiredFrom = requiredFrom;    }
    public String getPlanName() {
        return planName;    }
    public void setPlanName(String planName) {
        this.planName = planName;    }
    public long getUnits_consumed() {
        return units_consumed;    }
    public void setUnits_consumed(long units_consumed) {
        this.units_consumed = units_consumed;
    }
    public double getPricePerUnit() {
        return pricePerUnit;    }
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;    }
    public double getTotalCost() {
        return totalCost;    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;    }
    public double getPercentage() {
        return percentage;    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;    }
    public long getTotal_units_consumed() {
        return total_units_consumed;    }
    public void setTotal_units_consumed(long total_units_consumed) {
        this.total_units_consumed = total_units_consumed;    }
    public UsageResponse(String planName, long units_consumed, double pricePerUnit, double totalCost,
                         long total_units_consumed, double percentage) {
        super();
        this.planName = planName;
        this.units_consumed = units_consumed;
        this.pricePerUnit = pricePerUnit;
        this.totalCost = totalCost;
        this.total_units_consumed = total_units_consumed;
        this.percentage = percentage;    }
    public UsageResponse(String planName, long units_consumed, double pricePerUnit, double totalCost,
                         long total_units_consumed, double percentage, Date requiredFrom) {
        super();
        this.planName = planName;
        this.units_consumed = units_consumed;
        this.pricePerUnit = pricePerUnit;
        this.totalCost = totalCost;
        this.total_units_consumed = total_units_consumed;
        this.percentage = percentage;
        this.requiredFrom = requiredFrom;    }
    public UsageResponse() {
        super();
    }}
package org.acme.dto;

import org.bson.types.ObjectId;

public class IncomeDTO {
    private ObjectId enterpriseId;
    private double amount;
    private String dayDate;
    private String description;

    public IncomeDTO() {}

    public IncomeDTO(ObjectId enterpriseId, double amount, String  dayDate, String description) {
        this.enterpriseId = enterpriseId;
        this.amount = amount;
        this.dayDate = dayDate;
        this.description = description;
    }

    public ObjectId getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(ObjectId enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }
}

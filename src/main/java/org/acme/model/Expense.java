package org.acme.model;

import org.bson.types.ObjectId;

public class Expense {
    private ObjectId id;
    private ObjectId enterpriseId;
    private double amount;
    private String date;
    private String description;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
            this.description = description;
        }

}

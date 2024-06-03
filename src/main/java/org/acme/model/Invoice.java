package org.acme.model;

import org.bson.types.ObjectId;

public class Invoice {
    private ObjectId id;
    private ObjectId enterpriseId;
    private double amount;
    private String  issueDate;
    private String  dueDate;
    private String status;

    public ObjectId getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(ObjectId enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

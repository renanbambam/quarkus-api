package org.acme.dto;

import org.bson.types.ObjectId;

public class InvoiceDTO {
    private ObjectId enterpriseId;

    private double amount;

    private String issueDate;

    private String dueDate;

    private String status;

    private ObjectId paymentId;

    public InvoiceDTO() {}

    public InvoiceDTO(ObjectId enterpriseId, double amount, String issueDate, String dueDate, String status) {
        this.enterpriseId = enterpriseId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
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

    public ObjectId getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(ObjectId paymentId) {
        this.paymentId = paymentId;
    }
}

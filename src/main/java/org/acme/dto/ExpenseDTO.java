package org.acme.dto;

public class ExpenseDTO {
    private String enterpriseId;
    private double amount;
    private String date;
    private String description;

    public ExpenseDTO() {}

    public ExpenseDTO(String enterpriseId, double amount, String  date, String description) {
        this.enterpriseId = enterpriseId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
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

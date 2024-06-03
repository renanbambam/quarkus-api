package org.acme.dto;

public class AccountancyDTO {
    private String enterpriseId;
    private double active;
    private double passive;
    private double netWorth;

    public AccountancyDTO() {}

    public AccountancyDTO(String enterpriseId, double active, double passive, double netWorth) {
        this.enterpriseId = enterpriseId;
        this.active = active;
        this.passive = passive;
        this.netWorth = netWorth;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public double getActive() {
        return active;
    }

    public void setActive(double active) {
        this.active = active;
    }

    public double getPassive() {
        return passive;
    }

    public void setPassive(double passive) {
        this.passive = passive;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }
}

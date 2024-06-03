package org.acme.model;

import org.bson.types.ObjectId;

public class Accountancy {

    private ObjectId id;
    private ObjectId enterpriseId;
    private double active;
    private double passive;
    private double netWorth;

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

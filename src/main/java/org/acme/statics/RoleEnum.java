package org.acme.statics;

public enum RoleEnum {

    ADMIN("Adim".toUpperCase()),
    ENTERPRISE("Enterprise".toUpperCase());

    public final String label;

    RoleEnum(String label) {
        this.label = label;
    }
}

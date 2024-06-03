package org.acme.model;

import jakarta.validation.constraints.Email;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Enterprise {

    @BsonProperty("_id")
    private ObjectId id;
    @Email
    private String email;
    private String name;
    private String password;
    public String activeRefreshTokens;
    public List<String> revokedRefreshTokens = new ArrayList<>();
    private String role;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId enterpriseId) {
        this.id = enterpriseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActiveRefreshTokens() {
        return activeRefreshTokens;
    }

    public void setActiveRefreshTokens(String activeRefreshTokens) {
        this.activeRefreshTokens = activeRefreshTokens;
    }

    public List<String> getRevokedRefreshTokens() {
        return revokedRefreshTokens;
    }

    public void setRevokedRefreshTokens(List<String> revokedRefreshTokens) {
        this.revokedRefreshTokens = revokedRefreshTokens;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

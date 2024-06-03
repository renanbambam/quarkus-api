package org.acme.dto;

import jakarta.validation.constraints.Email;

public class EnterpriseDTO {

    @Email
    private String email;
    private String name;
    private String password;
    private String refreshToken;
    private String role;

    public EnterpriseDTO() {
    }

    public EnterpriseDTO(String name, String email, String password, String refreshToken) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.refreshToken = refreshToken;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


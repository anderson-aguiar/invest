package com.anderson.invest.dtos;

import java.time.LocalDateTime;

public class UserMinDTO {

    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public UserMinDTO() {
    }

    public UserMinDTO(String name, String email, String role, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

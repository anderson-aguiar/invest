package com.anderson.invest.dtos;

public class LoginResponseDTO {

    private String accesstoken;
    private String refreshToken;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String accesstoken, String refreshToken) {
        this.accesstoken = accesstoken;
        this.refreshToken = refreshToken;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

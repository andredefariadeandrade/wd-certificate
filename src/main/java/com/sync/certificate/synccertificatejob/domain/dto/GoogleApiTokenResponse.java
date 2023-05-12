package com.sync.certificate.synccertificatejob.domain.dto;

import com.google.gson.annotations.SerializedName;

public class GoogleApiTokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private Integer expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}

package com.sync.certificate.synccertificatejob.domain.dto;

import com.google.gson.annotations.SerializedName;

public class GoogleApiTokenBody {

    @SerializedName("client_id")
    private final String clientId;

    @SerializedName("client_secret")
    private final String clientSecret;

    @SerializedName("refresh_token")
    private final String refreshToken;

    @SerializedName("grant_type")
    private final String grantType;

    public GoogleApiTokenBody(String clientId, String clientSecret, String refreshToken, String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getGrantType() {
        return grantType;
    }
}

package com.sync.certificate.synccertificatejob.domain.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleApiMessagesResponse {

    @SerializedName("messages")
    private List<GoogleApiMessageResponse> googleApiMessageResponse;

    public List<GoogleApiMessageResponse> getGoogleApiMessageResponse() {
        return googleApiMessageResponse;
    }
}

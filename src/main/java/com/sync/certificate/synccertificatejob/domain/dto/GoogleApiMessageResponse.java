package com.sync.certificate.synccertificatejob.domain.dto;

import com.google.gson.annotations.SerializedName;

public class GoogleApiMessageResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("threadId")
    private String threadId;

    public String getId() {
        return id;
    }

    public String getThreadId() {
        return threadId;
    }
}

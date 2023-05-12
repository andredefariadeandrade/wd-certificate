package com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment;

import com.google.gson.annotations.SerializedName;

public class GoogleApiListMessageWithAttachmentHeaderResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

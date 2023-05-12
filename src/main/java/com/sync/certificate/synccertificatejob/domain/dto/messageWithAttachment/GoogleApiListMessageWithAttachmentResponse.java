package com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment;

import com.google.gson.annotations.SerializedName;

public class GoogleApiListMessageWithAttachmentResponse {

    @SerializedName("id")
    private String messageId;

    @SerializedName("payload")
    private GoogleApiListMessageWithAttachmentPayloadResponse googleApiListMessageWithAttachmentPayload;

    public String getMessageId() {
        return messageId;
    }

    public GoogleApiListMessageWithAttachmentPayloadResponse getGoogleApiListMessageWithAttachmentPayload() {
        return googleApiListMessageWithAttachmentPayload;
    }
}

package com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment;

import com.google.gson.annotations.SerializedName;

public class GoogleApiListMessageWithAttachmentBodyResponse {

    @SerializedName("attachmentId")
    private String attachmentId;

    public String getAttachmentId() {
        return attachmentId;
    }
}

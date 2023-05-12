package com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment;

import com.google.gson.annotations.SerializedName;

public class GoogleApiListMessageWithAttachmentPartResponse {

    @SerializedName("filename")
    private String filename;

    @SerializedName("body")
    private GoogleApiListMessageWithAttachmentBodyResponse body;

    public String getFilename() {
        return filename;
    }

    public GoogleApiListMessageWithAttachmentBodyResponse getBody() {
        return body;
    }
}

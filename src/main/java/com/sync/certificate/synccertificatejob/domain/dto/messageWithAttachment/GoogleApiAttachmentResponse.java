package com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment;

import com.google.gson.annotations.SerializedName;

public class GoogleApiAttachmentResponse {

    @SerializedName("data")
    private String pdfBase64;

    public String getPdfBase64() {
        return pdfBase64;
    }
}

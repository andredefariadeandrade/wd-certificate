package com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleApiListMessageWithAttachmentPayloadResponse {

    @SerializedName("parts")
    private List<GoogleApiListMessageWithAttachmentPartResponse> googleApiListMessageWithAttachmentPartResponses;

    @SerializedName("headers")
    private List<GoogleApiListMessageWithAttachmentHeaderResponse> googleApiListMessageWithAttachmentHeaderResponses;

    public List<GoogleApiListMessageWithAttachmentPartResponse> getGoogleApiListMessageWithAttachmentPartResponses() {
        return googleApiListMessageWithAttachmentPartResponses;
    }

    public List<GoogleApiListMessageWithAttachmentHeaderResponse> getGoogleApiListMessageWithAttachmentHeaderResponses() {
        return googleApiListMessageWithAttachmentHeaderResponses;
    }
}

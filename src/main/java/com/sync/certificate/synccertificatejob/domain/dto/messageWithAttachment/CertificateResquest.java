package com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment;

import com.google.gson.annotations.SerializedName;

public class CertificateResquest {

    @SerializedName("Employee_ID")
    private String employeeId;

    @SerializedName("Certification_ID")
    private String certificationId;

    @SerializedName("success")
    private boolean success;

    public String getEmployeeId() {
        return employeeId;
    }

    public String getCertificationId() {
        return certificationId;
    }

    public boolean isSuccess() {
        return success;
    }
}

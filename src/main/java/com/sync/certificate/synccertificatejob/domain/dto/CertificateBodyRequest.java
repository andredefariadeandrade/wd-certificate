package com.sync.certificate.synccertificatejob.domain.dto;

import com.google.gson.annotations.SerializedName;

public class CertificateBodyRequest {

    @SerializedName("Auto_Complete")
    private final Integer autoComplete;

    @SerializedName("Employee_ID")
    private final String employeeID;

    @SerializedName("Remove_Certification")
    private final Integer removeCertification;

    @SerializedName("Certification_ID")
    private final String certificationId;

    @SerializedName("Issued_Date")
    private final String issuedDate;

    @SerializedName("Expiration_Date")
    private final String expirationdate;

    public CertificateBodyRequest(Integer autoComplete, String employeeID, Integer removeCertification, String certificationId, String issuedDate, String expirationdate) {
        this.autoComplete = autoComplete;
        this.employeeID = employeeID;
        this.removeCertification = removeCertification;
        this.certificationId = certificationId;
        this.issuedDate = issuedDate;
        this.expirationdate = expirationdate;
    }

    public Integer getAutoComplete() {
        return autoComplete;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public Integer getRemoveCertification() {
        return removeCertification;
    }

    public String getCertificationId() {
        return certificationId;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getExpirationdate() {
        return expirationdate;
    }
}

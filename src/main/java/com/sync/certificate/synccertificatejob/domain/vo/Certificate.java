package com.sync.certificate.synccertificatejob.domain.vo;

public class Certificate {
    private final String name;
    private final String email;
    private final String issuedDate;
    private final String expirationDate;
    private final String certificationId;

    public Certificate(String name, String email, String issuedDate, String expirationDate, String certificationId) {
        this.name = name;
        this.email = email;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
        this.certificationId = certificationId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCertificationId() {
        return "MICROSOFT_"+certificationId;
    }
}

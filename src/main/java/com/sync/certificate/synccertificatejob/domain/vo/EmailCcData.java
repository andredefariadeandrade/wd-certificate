package com.sync.certificate.synccertificatejob.domain.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class EmailCcData {

    private String name;
    private String email;

    private Set<Certificate> certificates = new HashSet<>();
    private boolean dataFound;

    public String getName() {
        return name;
    }

    public EmailCcData withName(final String name) {
        this.name = (!StringUtils.isEmpty(name) ? name.trim() : name);
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EmailCcData withEmail(final String email) {
        this.email = (!StringUtils.isEmpty(email) ? email.trim() : email);
        return this;
    }

    public Set<Certificate> getCertificates() {
        return certificates;
    }

    public EmailCcData withCertificate(final Certificate certificate) {
        this.certificates.add(certificate);
        return this;
    }

    public EmailCcData withDataFound() {
        this.dataFound = true;
        return this;
    }

    public EmailCcData withDataNotFound() {
        this.dataFound = false;
        return this;
    }

    public boolean isDataFound() {
        return dataFound;
    }
}

package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import com.sync.certificate.synccertificatejob.utils.MonthsOfYearEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


public class GoogleExtractCertifiedData implements IExtractCertifiedData {


    @Override
    public Set<Certificate> setCertificateData(List<String> certificatePdfString) {
        return null;
    }

    @Override
    public String getCertificateId(String pdfString) {
        return (isNotEmpty(pdfString) ? pdfString.split("https")[0].trim() : EMPTY);
    }

    @Override
    public String getIssuedDate(String pdfString) {
        return pdfString;
    }

    @Override
    public String getExpirationDate(String pdfString) {
        return pdfString;
    }

    @Override
    public String formatDate(String date, MonthsOfYearEnum monthsOfYearEnum) {
        return null;
    }

    @Override
    public String getNameInCertificate(List<String> certificatePdfStringPage) {
        return null;
    }

    @Override
    public String getEmilaInCErtificate(List<String> certificatePdfStringPage) {
        return null;
    }
}

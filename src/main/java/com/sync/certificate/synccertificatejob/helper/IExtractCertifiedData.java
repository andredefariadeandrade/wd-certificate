package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import com.sync.certificate.synccertificatejob.utils.MonthsOfYearEnum;

import java.util.List;
import java.util.Set;

public interface IExtractCertifiedData {
    Set<Certificate> setCertificateData(final List<String> certificatePdfString);
    String getCertificateId(final String rawCertificateId);
    String getIssuedDate(final String pdfString);
    String getExpirationDate(final String pdfString);

    String formatDate(final String date, final MonthsOfYearEnum monthsOfYearEnum);

    String getNameInCertificate(List<String> certificatePdfStringPage);

    String getEmilaInCErtificate(List<String> certificatePdfStringPage);
}

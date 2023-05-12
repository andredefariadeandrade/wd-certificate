package com.sync.certificate.synccertificatejob.application.workday.service;

import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import com.sync.certificate.synccertificatejob.exception.CertificateNotFound;
import com.sync.certificate.synccertificatejob.helper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class PrepareCertifiedDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareCertifiedDataService.class);

    private final ExtractCertifiedHelper extractCertifiedHelper;

    @Autowired
    public PrepareCertifiedDataService(ExtractCertifiedHelper extractCertifiedHelper) {
        this.extractCertifiedHelper = extractCertifiedHelper;
    }

    public Set<Certificate> prepare (final byte[] pdfBase64){
        try {
            this.extractCertifiedHelper.setExtractCertifiedData(pdfBase64);
            IExtractCertifiedData extractCertifiedData;
            if(this.extractCertifiedHelper.isCertifiedAws()){
                extractCertifiedData = new AwsExtractCertifiedData();
            } else if(this.extractCertifiedHelper.isCertifiedGoogle()){
                extractCertifiedData = new GoogleExtractCertifiedData();
            } else if(this.extractCertifiedHelper.isCertifiedMicrosoft()){
                extractCertifiedData = new MicrosoftExtractCertifiedData();
            } else {
                LOGGER.warn("Certificate Type Not Found");
                return null;
            }
            return extractCertifiedData.setCertificateData(this.extractCertifiedHelper.getExtractCertifiedData());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}

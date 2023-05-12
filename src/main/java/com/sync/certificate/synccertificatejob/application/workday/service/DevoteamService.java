package com.sync.certificate.synccertificatejob.application.workday.service;

import com.sync.certificate.synccertificatejob.config.ExternalConfiguration;
import com.sync.certificate.synccertificatejob.domain.dto.CertificateBodyRequest;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.CertificateResquest;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sync.certificate.synccertificatejob.integration.rest.client.ApiClient.createRequestPost;
import static com.sync.certificate.synccertificatejob.integration.rest.client.ApiClient.doNewCall;
import static com.sync.certificate.synccertificatejob.utils.GoogleApiConst.apiCertifications;
import static com.sync.certificate.synccertificatejob.utils.JsonUtils.parseObjetToString;
import static com.sync.certificate.synccertificatejob.utils.JsonUtils.parseStringToObject;

@Service
public class DevoteamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevoteamService.class);

    private final ExternalConfiguration externalConfiguration;

    @Autowired
    public DevoteamService(ExternalConfiguration externalConfiguration) {
        this.externalConfiguration = externalConfiguration;
    }

    public CertificateResquest sendCertificates(final String employeeID, final String certificationId, final String issuedDate, final String expirationdate){
        CertificateBodyRequest certificateBodyRequest = new CertificateBodyRequest(1, employeeID, 0, certificationId, issuedDate, expirationdate);

        Map<String, String> header = new HashMap<>();
        header.put("client_id", this.externalConfiguration.getMulesoftClientId());
        header.put("client_secret", this.externalConfiguration.getMulesoftClientSecret());

        final Request request = createRequestPost(this.externalConfiguration.getApiUatMulesoftDevoteam(), apiCertifications, parseObjetToString(certificateBodyRequest), header);

        try (Response response = doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                return parseStringToObject(CertificateResquest.class, response.body().string());
            } else {
                LOGGER.error("Error when trying to register certificate: "+response.code()+" - "+response.message());
                LOGGER.error("service used".concat(this.externalConfiguration.getApiUatMulesoftDevoteam().concat(apiCertifications)));
            }
        } catch (IOException e) {
            LOGGER.error("Error when trying to send the certificate: "+e.getMessage(), e);
        }
        return null;
    }



}

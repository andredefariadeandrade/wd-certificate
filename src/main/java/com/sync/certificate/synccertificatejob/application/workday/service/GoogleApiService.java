package com.sync.certificate.synccertificatejob.application.workday.service;

import com.sync.certificate.synccertificatejob.config.ExternalConfiguration;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiMessagesResponse;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiTokenBody;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiTokenResponse;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.GoogleApiAttachmentResponse;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.GoogleApiListMessageWithAttachmentResponse;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sync.certificate.synccertificatejob.integration.rest.client.ApiClient.*;
import static com.sync.certificate.synccertificatejob.utils.GoogleApiConst.*;
import static com.sync.certificate.synccertificatejob.utils.JsonUtils.parseObjetToString;
import static com.sync.certificate.synccertificatejob.utils.JsonUtils.parseStringToObject;

@Service
public class GoogleApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleApiService.class);

    private final ExternalConfiguration externalConfiguration;

    @Autowired
    public GoogleApiService(ExternalConfiguration externalConfiguration) {
        this.externalConfiguration = externalConfiguration;
    }

    public GoogleApiTokenResponse getTokenGoogleService(){
        final GoogleApiTokenBody googleApiTokenBody = new GoogleApiTokenBody(this.externalConfiguration.getClientId(), this.externalConfiguration.getClientSecret(),
                this.externalConfiguration.getRefreshToken(), this.externalConfiguration.getGrantType());

        LOGGER.info("Looking for data in: ".concat(this.externalConfiguration.getAccountsGoogle().concat(getTokenUrl)));

        final Request request = createRequestPost(this.externalConfiguration.getAccountsGoogle(), getTokenUrl, parseObjetToString(googleApiTokenBody), null);

        try (Response response = doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                return parseStringToObject(GoogleApiTokenResponse.class, response.body().string());
            } else {
                LOGGER.error("Error when fetching data in: ".concat(this.externalConfiguration.getAccountsGoogle().concat(getTokenUrl)));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public GoogleApiMessagesResponse getMessagensList(final String googleApiToken){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer ".concat(googleApiToken));
        final String emailCertificate =  this.externalConfiguration.getEmailAddress();
        LOGGER.info("Looking for data in: ".concat(this.externalConfiguration.getGoogleApis().concat(getMessagesList.replace("{EMAIL}", emailCertificate))));

        final Request request = createRequestGet(this.externalConfiguration.getGoogleApis(), getMessagesList.replace("{EMAIL}", emailCertificate), header);

        try (Response response = doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                return parseStringToObject(GoogleApiMessagesResponse.class, response.body().string());
            } else {
                LOGGER.error("Error when fetching data in: ".concat(this.externalConfiguration.getGoogleApis().concat(getMessagesList.replace("{EMAIL}", emailCertificate))));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public GoogleApiListMessageWithAttachmentResponse getMessageDetais(final String googleApiToken, final String messageId){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer ".concat(googleApiToken));

        LOGGER.info("Looking for data in: ".concat(this.externalConfiguration.getGmailGoogleApis()).concat(getMessageDetails.concat(messageId)));

        final Request request = createRequestGet(this.externalConfiguration.getGmailGoogleApis(), getMessageDetails.concat(messageId), header);

        try (Response response = doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                return parseStringToObject(GoogleApiListMessageWithAttachmentResponse.class, response.body().string());
            } else {
                LOGGER.error("Error when fetching data in: ".concat(this.externalConfiguration.getGmailGoogleApis()).concat(getMessageDetails.concat(messageId)));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public GoogleApiAttachmentResponse getAttachment(final String googleApiToken, final String messageId, final String attachmentId){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer ".concat(googleApiToken));

        final String partUrl = getAttachment.replace(MESSAGE_ID, messageId).replace(ATTACHMENT_ID, attachmentId);

        LOGGER.info("Looking for data in: ".concat(this.externalConfiguration.getGoogleApis()).concat(partUrl));

        final Request request = createRequestGet(this.externalConfiguration.getGoogleApis(), partUrl, header);

        try (Response response = doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                return parseStringToObject(GoogleApiAttachmentResponse.class, response.body().string());
            } else {
                LOGGER.error("Error when fetching data in: ".concat(this.externalConfiguration.getGoogleApis()).concat(partUrl));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean deleteMessage(final String googleApiToken, final String messageId){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer ".concat(googleApiToken));

        LOGGER.info("Deleting data: ".concat(this.externalConfiguration.getGmailGoogleApis()).concat(getMessageDetails.concat(messageId)));

        final Request request = createRequestDelete(this.externalConfiguration.getGmailGoogleApis(), getMessageDetails.concat(messageId), header);

        try (Response response = doNewCall(request).execute()) {
            LOGGER.info("Message Id: "+messageId+" successfully deleted");
            return response.isSuccessful();
        } catch (IOException e) {
            LOGGER.error("Error deleting message id: "+messageId+" Erro message: "+e.getMessage(), e);
        }
        return false;
    }

}

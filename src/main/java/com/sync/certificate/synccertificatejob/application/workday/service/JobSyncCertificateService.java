package com.sync.certificate.synccertificatejob.application.workday.service;

import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiMessageResponse;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiMessagesResponse;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiTokenResponse;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.*;
import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import com.sync.certificate.synccertificatejob.domain.vo.EmailCcData;
import com.sync.certificate.synccertificatejob.domain.vo.ReportData;
import com.sync.certificate.synccertificatejob.helper.ExtractCertifiedHelper;
import com.sync.certificate.synccertificatejob.helper.ExtractMessageDataHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class JobSyncCertificateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobSyncCertificateService.class);
    private final GoogleApiService googleApiService;
    private final EmailService emailService;

    private final DevoteamService devoteamService;

    @Autowired
    public JobSyncCertificateService(GoogleApiService googleApiService, EmailService emailService, DevoteamService devoteamService) {
        this.googleApiService = googleApiService;
        this.emailService = emailService;
        this.devoteamService = devoteamService;
    }

    public void start(){
        try {
            final String token = this.getToken();
            this.syncCertificate(token);
        } catch (JAXBException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String getToken(){
        GoogleApiTokenResponse googleApiTokenResponse = googleApiService.getTokenGoogleService();
        return googleApiTokenResponse.getAccessToken();
    }

    private List<String> getAttachmentId(final GoogleApiListMessageWithAttachmentResponse googleApiListMessageWithAttachmentResponse){
        List<String> attachmentId = new ArrayList<>();
        for(GoogleApiListMessageWithAttachmentPartResponse value : googleApiListMessageWithAttachmentResponse.getGoogleApiListMessageWithAttachmentPayload().getGoogleApiListMessageWithAttachmentPartResponses()){
            if(StringUtils.isNotEmpty(value.getBody().getAttachmentId())){
                attachmentId.add(value.getBody().getAttachmentId());
            }
        }
        return attachmentId;
    }

    private GoogleApiAttachmentResponse getAttachment(final String googleApiToken, final String messageId, final String attachmentId){
        return this.googleApiService.getAttachment(googleApiToken, messageId, attachmentId);
    }

    private void syncCertificate(final String token) throws JAXBException, IOException {

        List<String> getMessagesId = this.getMessagesId(token);

        for(String messageId : getMessagesId) {
            Set<Certificate> allCertificates = new HashSet<>();

            final GoogleApiListMessageWithAttachmentResponse messageDetails = googleApiService.getMessageDetais(token, messageId);

            ExtractMessageDataHelper extractMessageDataHelper = new ExtractMessageDataHelper();
            Set<EmailCcData> emailCcDatas = extractMessageDataHelper.getNameAndEmailMessage(messageDetails);

            final GoogleApiListMessageWithAttachmentHeaderResponse attachmentHeader = getEmailAttachmentHeader(messageDetails);
            final String emailSender = this.getEmailSender(attachmentHeader);

            if(!existEmailInCc(emailCcDatas, emailSender)){
                googleApiService.deleteMessage(token, messageId);
                continue;
            }

            if (isContainsAttachmentId(messageDetails)) {
                final List<String> attachmentIds = getAttachmentId(messageDetails);
                allCertificates.addAll(this.getAllCertificatesByAttachmentIds(attachmentIds, token, messageDetails.getMessageId(), emailSender));
            } else {
                final String message = "Dear sender, \n" +
                                        "\n" +
                                        "Unfortunately, your haven't attached a certification.. \n" +
                                        "Please be sure to upload your certification attachment to the email before re-sending it. \n" +
                                        "\n" +
                                        "Best regards \n" +
                                        "Your certification team";
                final String subject = "E-mail without attachment";
                LOGGER.warn(message);
                emailService.sendEmailMessage(emailSender, subject, message);
                googleApiService.deleteMessage(token, messageId);
                continue;
            }

            if(!CollectionUtils.isEmpty(allCertificates)){
                this.matchCertificateAndCcData(emailCcDatas, allCertificates);
                this.sendCertificateToPlatform(emailCcDatas, emailSender);
            } else {
                final String message = "Dear sender, \n" +
                                                    "\n" +
                                                    "Unfortunately, your haven't attached a certification.. \n" +
                                                    "Please be sure to upload your certification attachment to the email before re-sending it. \n" +
                                                    "\n" +
                                                    "Best regards \n" +
                                                    "Your certification team";
                final String subject = "E-mail without valid certificate";

                emailService.sendEmailMessage(emailSender, subject, message);
            }
            googleApiService.deleteMessage(token, messageId);
        }

    }

    private void sendCertificateToPlatform(final Set<EmailCcData> emailCcDatas, final String emailSender) {
        for(EmailCcData emailCcData :emailCcDatas){
            try {
                String emailSubject = "";
                String emailMessage = "";
                if(emailCcData.isDataFound()){
                    final ReportData reportData = this.emailService.getWorkerByEmail(emailCcData.getEmail());
                    if(!this.emailService.isEmailValid(reportData)){
                        LOGGER.warn("E-mail "+ emailCcData.getEmail() +" not found on workday");
                        continue;
                    }
                    for(Certificate certificate : emailCcData.getCertificates()){
                        final CertificateResquest certificateResquest = devoteamService.sendCertificates(reportData.getReportEntry().getEmployeeId(),
                                certificate.getCertificationId(),
                                certificate.getIssuedDate(),
                                certificate.getExpirationDate());

                        if(certificateResquest != null && certificateResquest.isSuccess()){
                            emailMessage = "Dear sender,\n" +
                                            "\n" +
                                            "Congratulations! "+(emailCcData.getName() != null  ? emailCcData.getName()+" your" : EMPTY+"Your")+" certification "+certificate.getCertificationId()+" have been successfully uploaded in Workday.\n" +
                                            "\n" +
                                            "Best regards \n" +
                                            "Your certification team";
                            emailSubject = "Congrats | Your Certification is uploaded";
                        } else {
                            emailMessage = "Dear sender, \n" +
                                            "\n" +
                                            "Unfortunately, "+(emailCcData.getName() != null  ? emailCcData.getName() : EMPTY)+" your certification "+certificate.getCertificationId()+" wasn´t registered. \n" +
                                            "To get you certificate uploaded, please create a <a href=\"https://devoteam.service-now.com/itportal?id=sc_cat_item_guide&sys_id=e2a4a5aac3f1a150647b0f359901317f\">Service Now Ticket \"\"[Workday] Manage Certification\"\"</a>\n" +
                                            "We will review your demand and get back to you. \n" +
                                            "\n" +
                                            "Best regards \n" +
                                            "Your certification team";
                            emailSubject = "Referentiel | Missing Certification in Workday ";
                        }
                        LOGGER.info(emailMessage);
                        emailService.sendEmailMessage(emailSender, emailSubject, emailMessage);
                        LOGGER.info("End of sending confirmation email");

                    }
                } else {
                    emailMessage = "Dear sender, \n" +
                            "\n" +
                            "Unfortunately, the upload didn't work properly. \n" +
                            "Please make sure the number of uploaded certifications is the same as the number of professional emails in CC. \n" +
                            "\n" +
                            "Best regards \n" +
                            "Your certification team";
                    emailSubject = "Email in CC didn´t match";
                    LOGGER.info(emailMessage);
                    emailService.sendEmailMessage(emailSender, emailSubject, emailMessage);
                }
            } catch (Exception e) {
                LOGGER.error("Error when trying to register certificate for "+emailCcData.getName()+ "Erro message: "+e.getMessage(), e);
            }
        }
    }

    private boolean existEmailInCc(Set<EmailCcData> emailCcDatas, String emailSender) {

        if(CollectionUtils.isEmpty(emailCcDatas)){
            final String message = "Dear sender, \n" +
                                    "\n" +
                                    "For the upload to Workday to work, we'll need you company email address (@devoteam.com) in the CC. Unfortunately, your email address is currently missing. \n" +
                                    "Please make sure you indicated it in CC before sending.\n" +
                                    "\n" +
                                    "Best regards";
            final String subject = "Error | Missing Professionnal Email";
            LOGGER.warn(message);
            emailService.sendEmailMessage(emailSender, subject, message);
            return false;
        }
        return true;
    }

    private void matchCertificateAndCcData(Set<EmailCcData> emailCcDatas, Set<Certificate> allCertificates) {
        for(EmailCcData emailCcData : emailCcDatas){
            for(Certificate certificate : allCertificates){
                if(emailCcData.getEmail() != null && emailCcData.getEmail().equalsIgnoreCase(certificate.getEmail())){
                    emailCcData.getCertificates().add(certificate);
                    emailCcData.withDataFound();
                    LOGGER.info("Certificate match by e-mail: name("+emailCcData.getName()+") e-mail("+emailCcData.getEmail()+") id Certificate("+certificate.getCertificationId()+")");
                } else if(emailCcData.getName() != null && emailCcData.getName().equalsIgnoreCase(certificate.getName())) {
                    emailCcData.getCertificates().add(certificate);
                    emailCcData.withDataFound();
                    LOGGER.info("Certificate match  by name: name("+emailCcData.getName()+") e-mail("+emailCcData.getEmail()+") id Certificate("+certificate.getCertificationId()+")");
                }
            }
            if(!emailCcData.isDataFound()){
                LOGGER.info("Certificate not match name("+emailCcData.getName()+") e-mail("+emailCcData.getEmail()+")");
            }
        }

    }

    private List<String> getMessagesId(final String token){
        GoogleApiMessagesResponse googleApiMessagesResponse = googleApiService.getMessagensList(token);
        List<String> messagesIds = new ArrayList<>();
        if(!this.validateMessageIds(googleApiMessagesResponse)){
            LOGGER.warn("Messages ids is empty");
            return messagesIds;
        }

        for(GoogleApiMessageResponse googleApiMessageResponse : googleApiMessagesResponse.getGoogleApiMessageResponse()){
            messagesIds.add(googleApiMessageResponse.getId());
        }
        return messagesIds;
    }

    private boolean validateMessageIds(GoogleApiMessagesResponse googleApiMessagesResponse) {
        return (googleApiMessagesResponse != null && googleApiMessagesResponse.getGoogleApiMessageResponse() != null);
    }


    private boolean isContainsAttachmentId(final GoogleApiListMessageWithAttachmentResponse messageDetais){
        final List<GoogleApiListMessageWithAttachmentPartResponse> googleApiListMessageWithAttachmentPartResponses =
                messageDetais.getGoogleApiListMessageWithAttachmentPayload().getGoogleApiListMessageWithAttachmentPartResponses();

        if(googleApiListMessageWithAttachmentPartResponses != null && googleApiListMessageWithAttachmentPartResponses.size() > 0){
            for(GoogleApiListMessageWithAttachmentPartResponse value : googleApiListMessageWithAttachmentPartResponses){
                if(value != null && value.getBody() != null && isNotEmpty(value.getBody().getAttachmentId())){
                    return true;
                }
            }
        }
        return false;
    }

    private Set<Certificate> getAllCertificatesByAttachmentIds(List<String> attachmentIds, final String token, final String messageId, final String emailSender){
        Set<Certificate> allCertificates = new HashSet<>();

        for(String attachmentId: attachmentIds) {
            try {
                PrepareCertifiedDataService prepareCertifiedDataService = new PrepareCertifiedDataService(new ExtractCertifiedHelper());
                final GoogleApiAttachmentResponse googleApiAttachmentResponse = this.getAttachment(token, messageId, attachmentId);

                Set<Certificate> certificates = prepareCertifiedDataService.prepare(Base64.getUrlDecoder().decode(googleApiAttachmentResponse.getPdfBase64()));

                if(!CollectionUtils.isEmpty(certificates)){
                    allCertificates.addAll(certificates);
                }
            } catch (Exception e) {
                LOGGER.info("Error fetching certificates: "+e.getMessage(), e);
            }
        }
        return allCertificates;
    }

    private GoogleApiListMessageWithAttachmentHeaderResponse getEmailAttachmentHeader(final GoogleApiListMessageWithAttachmentResponse messageDetais) {
        final String returnPath = "Return-Path";
        final List<GoogleApiListMessageWithAttachmentHeaderResponse> googleApiListMessageWithAttachmentHeaderResponses =
                messageDetais.getGoogleApiListMessageWithAttachmentPayload().getGoogleApiListMessageWithAttachmentHeaderResponses();

        for(GoogleApiListMessageWithAttachmentHeaderResponse value : googleApiListMessageWithAttachmentHeaderResponses){
            if(value != null && isNotEmpty(value.getName()) && value.getName().equals(returnPath)){
                return value;
            }
        }
        return null;
    }

    private String getEmailSender(final GoogleApiListMessageWithAttachmentHeaderResponse googleApiListMessageWithAttachmentHeaderResponse){
        if(googleApiListMessageWithAttachmentHeaderResponse != null){
           return googleApiListMessageWithAttachmentHeaderResponse.getValue().replace("<", "").replace(">", "");
        }
        return EMPTY;
    }

}

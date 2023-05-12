package com.sync.certificate.synccertificatejob.application.workday.service;

import com.sync.certificate.synccertificatejob.config.ExternalConfiguration;
import com.sync.certificate.synccertificatejob.domain.vo.ReportData;
import com.sync.certificate.synccertificatejob.helper.EmailHelper;
import com.sync.certificate.synccertificatejob.integration.rest.client.WorkdayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static com.sync.certificate.synccertificatejob.utils.WorkDayServiceConst.workerByEmail;

@Service
public class EmailService extends WorkdayClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Value("${service.path-base.workday}")
    private String pathBaseWorkday;

    private final ExternalConfiguration externalConfiguration;

    @Autowired
    public EmailService(ExternalConfiguration externalConfiguration) {
        this.externalConfiguration = externalConfiguration;
    }

    public ReportData getWorkerByEmail(final String email) throws JAXBException, IOException {
        return super.workdayGet(pathBaseWorkday.concat(workerByEmail.concat(email)), ReportData.class);
    }

    public boolean isEmailValid(final ReportData reportData){
        return (reportData != null && reportData.getReportEntry() != null
                && reportData.getReportEntry().getEmployeeId() != null);
    }

    public void sendEmailMessage(final String receiver, final String subject, final String message) {
        try {
            if(!receiver.equalsIgnoreCase(this.externalConfiguration.getEmailAddress())){
                final EmailHelper emailHelper = new EmailHelper(this.externalConfiguration, receiver, subject, message);
                emailHelper.sendEmail();
            } else {
                LOGGER.warn("Same outgoing email");
            }
        } catch (MessagingException e) {
            LOGGER.error("Error when trying to send email, receiver: "+receiver+" subject: "+subject, e);
        }

    }

}

package com.sync.certificate.synccertificatejob.integration.rest.client;

import com.sync.certificate.synccertificatejob.application.workday.service.EmailService;
import com.sync.certificate.synccertificatejob.application.workday.service.PrepareCertifiedDataService;
import com.sync.certificate.synccertificatejob.config.ExternalConfiguration;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiMessagesResponse;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiTokenBody;
import com.sync.certificate.synccertificatejob.domain.dto.GoogleApiTokenResponse;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.GoogleApiAttachmentResponse;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.GoogleApiListMessageWithAttachmentResponse;
import com.sync.certificate.synccertificatejob.domain.vo.ReportData;
import com.sync.certificate.synccertificatejob.utils.GoogleApiConst;
import com.sync.certificate.synccertificatejob.utils.JsonUtils;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class workdayClientTest  extends WorkdayClient {

    @Autowired
    private PrepareCertifiedDataService prepareCertifiedDataService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExternalConfiguration externalConfiguration;

    @Ignore
    @Test
    public void whenGetRequest_thenCorrect() throws IOException, JAXBException, JAXBException {
//        final String urlService = "https://wd3-impl-services1.workday.com/ccx/service/customreport2/devoteam1/ISU_API_Test/Interfaces_-_CR_Worker_by_Email?primaryWorkEmail=ian.jardinel@devoteam.com";
//        final ReportData reportData = super.workdayGet(urlService, ReportData.class);
        ReportData reportData = emailService.getWorkerByEmail("ian.jardinel@devoteam.com");
        System.out.println("ReportData: "+reportData);
    }

    @Ignore
    @Test
    public void testEmail() throws MessagingException {
        this.emailService.sendEmailMessage("andre.faria.andrade.ext@devoteam.com", "Certificate registration.", "test success.");
    }

    @Ignore
    @Test
    public void GoogleApiPostTokenTest() {
        GoogleApiTokenBody googleApiTokenBody = new GoogleApiTokenBody(externalConfiguration.getClientId(), externalConfiguration.getClientSecret(),
                                                                        externalConfiguration.getRefreshToken(), externalConfiguration.getGrantType());

        final Request request = ApiClient.createRequestPost(externalConfiguration.getAccountsGoogle(), GoogleApiConst.getTokenUrl, JsonUtils.parseObjetToString(googleApiTokenBody), null);

        try (Response response = ApiClient.doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                final GoogleApiTokenResponse googleApiTokenResponse = JsonUtils.parseStringToObject(GoogleApiTokenResponse.class, response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Ignore
    @Test
    public void GoogleApiGetMessageTest() throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer ya29.a0AWY7CknbKaoY7pOGcftGO3u3rVfswDC6wujjtoJuokH0oeCyGU6wWaN_4ZmjUUjam3pB7UdNXfvOO8DHGj88oSZ2bYlarcpmd08YJCXkErUGgC4b2JQFDmuKjmh3degrZFFfMGUOFQZMdmUPmN2It-VdDMaIkm-7aCgYKAfsSARASFQG1tDrpxqX-WsnYmlvDiTE2etp7xA0167");

        final Request request = ApiClient.createRequestGet("https://www.googleapis.com/gmail/v1/", "users/me/messages?q=certifications@devoteam.com", header);

        try (Response response = ApiClient.doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                final GoogleApiMessagesResponse googleApiTokenResponse = JsonUtils.parseStringToObject(GoogleApiMessagesResponse.class, response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void GoogleApiGetListMessagesTest() throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer ya29.a0AWY7CkmZBR28jZ4hEYCszeueKGdd8Txv1QlAXa60yj7HNkqcaeURrzfxxLcUZKTb6aw9QFKmLFCru9SfTy_nACEnhaRm0qzxVUD-5LaPcChUcFxJ5V9LOVynD5A53tmLg50ZhtMkQyeVyLrIetBQo4m3tu57kJp-aCgYKAVYSARASFQG1tDrpEtDCvoLdsMO7vI-mhwAskA0167");

        final Request request = ApiClient.createRequestGet("https://gmail.googleapis.com/gmail/v1/", "users/me/messages/187ee6a2feb413cc", header);

        try (Response response = ApiClient.doNewCall(request).execute()) {
            if (response.isSuccessful()) {
//                System.out.println("Attachments:"+response.body().string());
                final GoogleApiListMessageWithAttachmentResponse googleApiTokenResponse = JsonUtils.parseStringToObject(GoogleApiListMessageWithAttachmentResponse.class, response.body().string());
                System.out.println("googleApiTokenResponse: "+googleApiTokenResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Ignore
    @Test
    public void GoogleApiGetAttachmentsTest() throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer ya29.a0AWY7CkmNP8mHfihGzMAqCChHIF9nqgE2kxhZPdDSoqH8qzGk0UTAAVFEp4ej_o16pXIOx56NBq7xJio1AA_gufNBWTmjHxlQrlbvuyUSeaJAM3bOBPf87sAmG9U6uxrKjAYbGDkyiL9cwsUtJhGZWWjm6K5e69F5aCgYKAYUSARASFQG1tDrpF9qF3O3o1cTB-NwwGvA_sw0167");

        final Request request = ApiClient.createRequestGet("https://gmail.googleapis.com/gmail/v1/", "users/me/messages/187bfe425cd73be9/attachments/ANGjdJ8ewezqAFybEKhjqlBp1ArmbW8DjvtEj0FQ1GZ_-aJNDHNRi-FTW3tB5CQp96iPfp6YLgxldmL1fpSW74OL5SkrU3iwYNkYCd8jD_POy588gFSHpv4_098ffYIVQV8SEpGx8uKEe-zSgFjnzbyClZRR2RmJWOAtopkxBEYu9VaTSyzHqZ9jK1tbmnWRZhiMSiEdg0sqClzzotWfwg3QJxh0X9HZRjM_IPQh8ICdcHXJhpsbcaFW1mks4gmGV3JMC10fjbOBQ2hHSrKgHOdRvkPDzY3ddYHx7nL5nMZIGzm4NNrXRB6Hd-BmjBLVzQvx1oMAgLTHhfVViUiIx1CgClwvS0x64Ejc_FVP9xvAhLCr36TyCBxm0vtZgHT5sGXtQP2IuvHh7wTRs-9h", header);

        try (Response response = ApiClient.doNewCall(request).execute()) {
            if (response.isSuccessful()) {
                final GoogleApiAttachmentResponse googleApiAttachmentResponse = JsonUtils.parseStringToObject(GoogleApiAttachmentResponse.class, response.body().string());
                System.out.println("googleApiAttachmentResponse:"+googleApiAttachmentResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

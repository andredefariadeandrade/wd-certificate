package com.sync.certificate.synccertificatejob.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalConfiguration {

    private final String emailAddress;
    private final String emailPassword;
    private final String clientId;
    private final String clientSecret;
    private final String refreshToken;
    private final String grantType;
    private final String accountsGoogle;
    private final String googleApis;
    private final String gmailGoogleApis;

    private final String apiUatMulesoftDevoteam;

    private final String mulesoftClientId;
    private final String mulesoftClientSecret;

    ExternalConfiguration(@Value("${external-configuration.email.address}") final String emailAddress,
                          @Value("${external-configuration.email.password}") final String emailPassword,
                          @Value("${external-configuration.email.client-id}") final String clientId,
                          @Value("${external-configuration.email.client-secret}") final String clientSecret,
                          @Value("${external-configuration.email.refresh-token}") final String refreshToken,
                          @Value("${external-configuration.email.grant-type}") final String grantType,
                          @Value("${service.path-base.accounts-google}") final String accountsGoogle,
                          @Value("${service.path-base.google-apis}") final String googleApis,
                          @Value("${service.path-base.gmail-google-apis}") final String gmailGoogleApis,
                          @Value("${service.path-base.api-uat-mulesoft-devoteam}") final String apiUatMulesoftDevoteam,
                          @Value("${external-configuration.mulesoft.client-id}") final String mulesoftClientId,
                          @Value("${external-configuration.mulesoft.client-secret}") final String mulesoftClientSecret){
            this.emailAddress = emailAddress;
            this.emailPassword = emailPassword;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.refreshToken = refreshToken;
            this.grantType = grantType;
            this.accountsGoogle = accountsGoogle;
            this.googleApis = googleApis;
            this.gmailGoogleApis = gmailGoogleApis;
            this.apiUatMulesoftDevoteam = apiUatMulesoftDevoteam;
            this.mulesoftClientId = mulesoftClientId;
            this.mulesoftClientSecret = mulesoftClientSecret;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getAccountsGoogle() {
        return accountsGoogle;
    }

    public String getGoogleApis() {
        return googleApis;
    }

    public String getGmailGoogleApis() {
        return gmailGoogleApis;
    }

    public String getApiUatMulesoftDevoteam() {
        return apiUatMulesoftDevoteam;
    }

    public String getMulesoftClientId() {
        return mulesoftClientId;
    }

    public String getMulesoftClientSecret() {
        return mulesoftClientSecret;
    }
}

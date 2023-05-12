package com.sync.certificate.synccertificatejob.integration.rest.client;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public abstract class WorkdayClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkdayClient.class);

    @Value("${basic-authentication.username}")
    private String basicauthenticationUserName;

    @Value("${basic-authentication.password}")
    private String basicauthenticationPassword;

    /**
     * Use {@link ApiClient#createRequestGet(String, String, Map)}
     * 
     */
    @Deprecated
    public <T> T workdayGet(final String urlService, Class<T> clazz) throws IOException, JAXBException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        String credential = Credentials.basic(basicauthenticationUserName, basicauthenticationPassword);

        Request request = new Request.Builder()
                .url(urlService)
                .header("Authorization", credential)
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()){
            if(response.isSuccessful()){
                final String responseXml = response.body().string();
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(responseXml.replace("wd:", ""));
                T result = (T) unmarshaller.unmarshal(reader);

                return result;
            }
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return null;

    }

}

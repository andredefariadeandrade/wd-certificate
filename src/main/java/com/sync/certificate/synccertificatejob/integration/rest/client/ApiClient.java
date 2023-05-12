package com.sync.certificate.synccertificatejob.integration.rest.client;

import com.sync.certificate.synccertificatejob.application.workday.service.JobSyncCertificateService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobSyncCertificateService.class);

    private ApiClient() {
    }

    public static Request createRequestPost(final String host, final String partUrl, final String json, final Map<String, String> header){

        LOGGER.info("Sending request: ".concat(host).concat(partUrl));
        LOGGER.info("Body: ".concat(json));

        final RequestBody requestBody = createRequestBodyJson(json);
        Request.Builder builder = new Request.Builder();
        builder.url(host.concat(partUrl));
        if(header != null){
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        return builder.post(requestBody).build();
    }

    public static Request createRequestGet(final String host, final String partUrl, final Map<String, String> header){
        Request.Builder builder =  new Request.Builder();
        builder.url(host.concat(partUrl));
        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        return builder.build();

    }

    public static Request createRequestDelete(final String host, final String partUrl, final Map<String, String> header){
        Request.Builder builder =  new Request.Builder();
        builder.url(host.concat(partUrl));
        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        return builder.delete().build();

    }

    private static RequestBody createRequestBodyJson(final String json) {
        return RequestBody.create(json, okhttp3.MediaType.parse("application/json; charset=utf-8"));
    }

    public static Call doNewCall(Request request) {
        OkHttpClient httpClient = new OkHttpClient()
                                    .newBuilder()
                                    .connectTimeout(60, TimeUnit.SECONDS)
                                    .readTimeout(60, TimeUnit.SECONDS)
                                    .writeTimeout(60, TimeUnit.SECONDS)
                                    .build();

        return httpClient.newCall(request);
    }

}

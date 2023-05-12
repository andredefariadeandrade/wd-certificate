package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.application.workday.service.PrepareCertifiedDataService;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.GoogleApiListMessageWithAttachmentResponse;
import com.sync.certificate.synccertificatejob.domain.vo.EmailCcData;
import com.sync.certificate.synccertificatejob.utils.JsonUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ExtractMessageDataHelperTest {

    private PrepareCertifiedDataService prepareCertifiedDataService;

    @Test
    public void extractEmailAndName(){
        final GoogleApiListMessageWithAttachmentResponse googleApiTokenResponse = JsonUtils.parseStringToObject(GoogleApiListMessageWithAttachmentResponse.class, this.getResponseTwoEmailAndOneAttachment());

        ExtractMessageDataHelper extractMessageDataHelper = new ExtractMessageDataHelper();
        Set<EmailCcData> emailCcDatas = extractMessageDataHelper.getNameAndEmailMessage(googleApiTokenResponse);
        final List<EmailCcData> emailCcDatasList = new ArrayList<>(emailCcDatas);

        assertEquals("daniel.trindade@devoteam.com", emailCcDatasList.get(0).getEmail());
        assertEquals("Daniel Trindade", emailCcDatasList.get(0).getName());
        assertEquals("abderrahmen.mokrani@devoteam.com", emailCcDatasList.get(1).getEmail());
        assertEquals("Abderrahmen MOKRANI", emailCcDatasList.get(1).getName());

    }

    private String getResponseTwoEmailAndOneAttachment(){
        return  "{\n" +
                "    \"id\": \"187f28044462ae03\",\n" +
                "    \"threadId\": \"187f28044462ae03\",\n" +
                "    \"labelIds\": [\n" +
                "        \"UNREAD\",\n" +
                "        \"IMPORTANT\",\n" +
                "        \"CATEGORY_PERSONAL\",\n" +
                "        \"INBOX\"\n" +
                "    ],\n" +
                "    \"snippet\": \"\",\n" +
                "    \"payload\": {\n" +
                "        \"partId\": \"\",\n" +
                "        \"mimeType\": \"multipart/mixed\",\n" +
                "        \"filename\": \"\",\n" +
                "        \"headers\": [\n" +
                "            {\n" +
                "                \"name\": \"Delivered-To\",\n" +
                "                \"value\": \"certifications@devoteam.com\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Received\",\n" +
                "                \"value\": \"by 2002:a17:906:1618:b0:966:8c1:78a with SMTP id m24csp555262ejd;        Sat, 6 May 2023 12:18:25 -0700 (PDT)\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"X-Received\",\n" +
                "                \"value\": \"by 2002:a2e:b6c6:0:b0:2ab:4399:708b with SMTP id m6-20020a2eb6c6000000b002ab4399708bmr1490772ljo.40.1683400705180;        Sat, 06 May 2023 12:18:25 -0700 (PDT)\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"ARC-Seal\",\n" +
                "                \"value\": \"i=1; a=rsa-sha256; t=1683400705; cv=none;        d=google.com; s=arc-20160816;        b=wgy90iONB2cURI8L3QKuTMDbOmkCKlkpsRg8+bN91WjybiBq6Xo5zlMkQKiKgG1G6h         5DS9ctWbFG9mK3pAJIPBCBKGj4EQjcRyWUnRJ78IXx44bXDPhIRkdjuCbivkUVeEuLG/         pgLB28bq87J08SGYytUZrsJzeUH+EgG7Ti1yh8HR0Ni2/4LLGxA9k+Ej3y0tYhkvSZHE         EmcFiOO/O7sTbns77AnQYD5B7UFV9G1cfzldW3j//WRNNOjwn7Uit5axzkIPCQo11lLP         ywgAqU41W1k8w25rA39vZA7O1MzRfFz63j+oFYgBK5eGrvtfI6FUlSsyliD7aAVpJUpe         l5+w==\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"ARC-Message-Signature\",\n" +
                "                \"value\": \"i=1; a=rsa-sha256; c=relaxed/relaxed; d=google.com; s=arc-20160816;        h=cc:to:subject:message-id:date:from:mime-version:dkim-signature;        bh=RvMZB/ytkMpw73VWiAt3V+mYe0VTWu25DeS7UgoZWzY=;        b=dNHTLPi28CBwQucrNM4zojb0RmYmTVb4eM8DNHZQjWsjEQt3+Zyvz+Jxq6cysfMDRv         JZSRfT2G1rOg3Dz470I0qeJbvk9gQCGKabFlKHGrylmM1VnV1sJNZXAxoKyl1zs2qdPW         mF8VocBTXV8ttvD3fa6GsLb5s5qETG62UKT5n6w9CguGwYNJNAPEDN6eQVQNAYToddkV         yEagkL7rTTDWT8v++GzAgB8TpijsXvcr9dUoqLPxZ7wVELaR+9ZTzQxPQzPEw8kkPM9R         iUs9zeIUMl846+CX6c6t9u5r+PF/I5PYIbtYoxonvWzXaGad5+OPPjlaGidBy+0Kmt1K         XylQ==\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"ARC-Authentication-Results\",\n" +
                "                \"value\": \"i=1; mx.google.com;       dkim=pass header.i=@devoteam.com header.s=google header.b=Ib1xZQoA;       spf=pass (google.com: domain of andre.faria.andrade.ext@devoteam.com designates 209.85.220.41 as permitted sender) smtp.mailfrom=andre.faria.andrade.ext@devoteam.com;       dmarc=pass (p=QUARANTINE sp=QUARANTINE dis=NONE) header.from=devoteam.com\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Return-Path\",\n" +
                "                \"value\": \"<andre.faria.andrade.ext@devoteam.com>\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Received\",\n" +
                "                \"value\": \"from mail-sor-f41.google.com (mail-sor-f41.google.com. [209.85.220.41])        by mx.google.com with SMTPS id bg27-20020a05651c0b9b00b002a8bed4d02bsor508956ljb.6.2023.05.06.12.18.24        for <certifications@devoteam.com>        (Google Transport Security);        Sat, 06 May 2023 12:18:25 -0700 (PDT)\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Received-SPF\",\n" +
                "                \"value\": \"pass (google.com: domain of andre.faria.andrade.ext@devoteam.com designates 209.85.220.41 as permitted sender) client-ip=209.85.220.41;\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Authentication-Results\",\n" +
                "                \"value\": \"mx.google.com;       dkim=pass header.i=@devoteam.com header.s=google header.b=Ib1xZQoA;       spf=pass (google.com: domain of andre.faria.andrade.ext@devoteam.com designates 209.85.220.41 as permitted sender) smtp.mailfrom=andre.faria.andrade.ext@devoteam.com;       dmarc=pass (p=QUARANTINE sp=QUARANTINE dis=NONE) header.from=devoteam.com\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"DKIM-Signature\",\n" +
                "                \"value\": \"v=1; a=rsa-sha256; c=relaxed/relaxed;        d=devoteam.com; s=google; t=1683400704; x=1685992704;        h=cc:to:subject:message-id:date:from:mime-version:from:to:cc:subject         :date:message-id:reply-to;        bh=RvMZB/ytkMpw73VWiAt3V+mYe0VTWu25DeS7UgoZWzY=;        b=Ib1xZQoA4WlObRe36crPWfyWwGQCvRVxz3QKmOjcT8/6Wdbo4UEsYsKQLa/9fXoXWF         gOo6tLYFMSTlLx3BRmUUm5NEf7k+P0rgpr+0r/LXW7nvCuPdPrm+WE7PgFkxDcVQDoCF         IMPn7oPEVgWVE4NUjm360+JdT5Gve8PzFN2iLpi5/T/8PPJgvo6AAN/oLsKAk6KHEOnV         EBB4GqhNOIfMcLQMT5OxdOs6IbrIAjN8gW/fRG4B70/vtbxRb8HagviLOZFkE7oGVrFt         f0quuKsH0l1iDw/4jXB3mFcJDXEGfbOlpxDjgbk30XIx4a/5Vhuh0gycfLLAlybEJA0E         B7ig==\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"X-Google-DKIM-Signature\",\n" +
                "                \"value\": \"v=1; a=rsa-sha256; c=relaxed/relaxed;        d=1e100.net; s=20221208; t=1683400704; x=1685992704;        h=cc:to:subject:message-id:date:from:mime-version:x-gm-message-state         :from:to:cc:subject:date:message-id:reply-to;        bh=RvMZB/ytkMpw73VWiAt3V+mYe0VTWu25DeS7UgoZWzY=;        b=eq9rNjTDczo0LtGDYJICj+Qxxb7eXpBrOA0uGCidegaI3MvekcqHNmhbfEL4QGg+R4         Fd+DTWDD4rWNwn8560R1So1FHz1gugNb7XY8F5eSfKkGG8ACI+kkyvPngiurj6J/KqEJ         aKofX64FB/BBQ9FxXkZYWuomHnvJRLSooYzaUlDWzppHynO+l3Em9+NT4mPTJSZuHmYh         6v2RfC8FNkWNEZY61qrKQutN9W0m0lj4sH9KBCm7bQr9ANePuyFu6rfshiGKoRsB7Hdn         jK2T5rG143rZ+75x+6yj/yaSIIxsvvx3vHjyZOvBl8hgpMOohlD+jwGF6BBKd1/0ceBF         51hA==\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"X-Gm-Message-State\",\n" +
                "                \"value\": \"AC+VfDyP82542GTn9RYtr0O99f/jHhLxWsKEQz84utZqJ80UgfiRI/Jx gfg89aQJLk6lPscwOmjFelgUiSYrMJa31T/uJukuGbtVFfNKIbI5bYpuy6GI\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"X-Google-Smtp-Source\",\n" +
                "                \"value\": \"ACHHUZ6TvBMYPMIRjjCuZXvQ/M1jaSgEnLCyZI0MZsW8xiOQwdV82Voh3giwOC657luK9P9sDBTO5Y0qW1Biah0W7Xc=\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"X-Received\",\n" +
                "                \"value\": \"by 2002:a2e:9382:0:b0:2a8:c75c:96cb with SMTP id g2-20020a2e9382000000b002a8c75c96cbmr1334294ljh.1.1683400704102; Sat, 06 May 2023 12:18:24 -0700 (PDT)\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"MIME-Version\",\n" +
                "                \"value\": \"1.0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"From\",\n" +
                "                \"value\": \"\\\"André Faria Andrade\\\" <andre.faria.andrade.ext@devoteam.com>\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Date\",\n" +
                "                \"value\": \"Sat, 6 May 2023 20:18:13 +0100\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Message-ID\",\n" +
                "                \"value\": \"<CAHr6OhWMLyTj_s_nkgC_aWN1hu3+BXBOwWTwi68L5hi+sH9hmA@mail.gmail.com>\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Subject\",\n" +
                "                \"value\": \"test email\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"To\",\n" +
                "                \"value\": \"\\\"WW.GEN Certifications Workday\\\" <certifications@devoteam.com>\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Cc\",\n" +
                "                \"value\": \"Abderrahmen MOKRANI <abderrahmen.mokrani@devoteam.com>, Daniel Trindade <daniel.trindade@devoteam.com>\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Content-Type\",\n" +
                "                \"value\": \"multipart/mixed; boundary=\\\"000000000000fbfbb505fb0b44f4\\\"\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"body\": {\n" +
                "            \"size\": 0\n" +
                "        },\n" +
                "        \"parts\": [\n" +
                "            {\n" +
                "                \"partId\": \"0\",\n" +
                "                \"mimeType\": \"multipart/alternative\",\n" +
                "                \"filename\": \"\",\n" +
                "                \"headers\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Content-Type\",\n" +
                "                        \"value\": \"multipart/alternative; boundary=\\\"000000000000fbfbb305fb0b44f2\\\"\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"body\": {\n" +
                "                    \"size\": 0\n" +
                "                },\n" +
                "                \"parts\": [\n" +
                "                    {\n" +
                "                        \"partId\": \"0.0\",\n" +
                "                        \"mimeType\": \"text/plain\",\n" +
                "                        \"filename\": \"\",\n" +
                "                        \"headers\": [\n" +
                "                            {\n" +
                "                                \"name\": \"Content-Type\",\n" +
                "                                \"value\": \"text/plain; charset=\\\"UTF-8\\\"\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"body\": {\n" +
                "                            \"size\": 2,\n" +
                "                            \"data\": \"DQo=\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"partId\": \"0.1\",\n" +
                "                        \"mimeType\": \"text/html\",\n" +
                "                        \"filename\": \"\",\n" +
                "                        \"headers\": [\n" +
                "                            {\n" +
                "                                \"name\": \"Content-Type\",\n" +
                "                                \"value\": \"text/html; charset=\\\"UTF-8\\\"\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"body\": {\n" +
                "                            \"size\": 2,\n" +
                "                            \"data\": \"DQo=\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"partId\": \"1\",\n" +
                "                \"mimeType\": \"application/pdf\",\n" +
                "                \"filename\": \"pdf-ms.pdf\",\n" +
                "                \"headers\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Content-Type\",\n" +
                "                        \"value\": \"application/pdf; name=\\\"pdf-ms.pdf\\\"\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Content-Disposition\",\n" +
                "                        \"value\": \"attachment; filename=\\\"pdf-ms.pdf\\\"\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Content-Transfer-Encoding\",\n" +
                "                        \"value\": \"base64\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Content-ID\",\n" +
                "                        \"value\": \"<187f2800db6f99ceeb92>\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"X-Attachment-Id\",\n" +
                "                        \"value\": \"187f2800db6f99ceeb92\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"body\": {\n" +
                "                    \"attachmentId\": \"ANGjdJ_1fkXlf6eE_B6jwMd8EXsXnnpjlLPXxCLBbozawCiUh5zSW4xA7eR9jqhYqBDNdRYTim2Yo2H0oKhHO6W56LDFYOeXBHPaUFuPDFmeMeOR_osu6XaMHeEKzOyd-w9LgLmir2GatUoNMb7L53GYoEGcATjYetQG6KhIX0Sm-OMGphb-yHdgbe4f1T70ASf-FZZxP53CztkYlbEc4z0udBLvYiQnqbpZDxJcIfoxaVZRii5vUUc5LtSrgFZkn0JS98_2rhcQ8CZ74lABqnPxqNWlKm6Pbkiljf6NUKppFAC857fT1FVSkiUhFRUMQkUb6thXQ6my8kR8NgphZZHQ0MD31fOPOP0lwuINiJf0zBeSjAD1k5EPGn-a9sEXJOkoJLKlTIsCShVyDE2m\",\n" +
                "                    \"size\": 6819\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"partId\": \"2\",\n" +
                "                \"mimeType\": \"application/pdf\",\n" +
                "                \"filename\": \"pdf-ms2.pdf\",\n" +
                "                \"headers\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Content-Type\",\n" +
                "                        \"value\": \"application/pdf; name=\\\"pdf-ms2.pdf\\\"\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Content-Disposition\",\n" +
                "                        \"value\": \"attachment; filename=\\\"pdf-ms2.pdf\\\"\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Content-Transfer-Encoding\",\n" +
                "                        \"value\": \"base64\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"Content-ID\",\n" +
                "                        \"value\": \"<187f2800db59f888eec1>\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"name\": \"X-Attachment-Id\",\n" +
                "                        \"value\": \"187f2800db59f888eec1\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"body\": {\n" +
                "                    \"attachmentId\": \"ANGjdJ-DlM6tq8UpEDMj5OcrCSfKaIz5yCQq3gFjGfk2qXkYWROfAN8Cry_lYMgwrSTfO-hDtTJUwFLdtZ9l6JPJECBcQ1ANRTWwuMrV5tz_PVRKEvzd1wYIHCYa-3t574af8N_4UcAyXRxyz5HSXRyrcfBPaOHKJgHBusXzKKmPJHtHpp5QJXs9uHGF-ro83NuvyYe8NaCgeTKuRczRb8p2N1ZZZyv_e8CYntJh1vkAfNMaCjini_BAaO79xXhH0qSFQZJ2jKDD6J5ujabu12iHMIAiN8De7zmvOREvHr5M-xLK8ReQSDY-kVxkMghK00A2F_I897M-Ae5tn6QuzDXJpwR_XhuvsNubuILAwUVrxHeo7B5JFnvB1AM8FkP5rZNSJ0jtK2OIkjU0rLwI\",\n" +
                "                    \"size\": 4750\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"sizeEstimate\": 21793,\n" +
                "    \"historyId\": \"43973\",\n" +
                "    \"internalDate\": \"1683400693000\"\n" +
                "}";
    }

}

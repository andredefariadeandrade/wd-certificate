package com.sync.certificate.synccertificatejob.application.workday.service;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobSyncCertificateServiceTest {

    @Autowired
    private JobSyncCertificateService jobSyncCertificateService;

    @Test
    public void jobSyncCertificateTest(){
        jobSyncCertificateService.start();
    }

}

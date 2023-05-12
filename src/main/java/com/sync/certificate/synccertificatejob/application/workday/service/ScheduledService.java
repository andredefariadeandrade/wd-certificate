package com.sync.certificate.synccertificatejob.application.workday.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledService.class);

    private final JobSyncCertificateService jobSyncCertificateService;

    @Autowired
    public ScheduledService(JobSyncCertificateService jobSyncCertificateService) {
        this.jobSyncCertificateService = jobSyncCertificateService;
    }

    @Scheduled(cron = "${scheduled.start-job-sync-certificate}")
    public void jobSyncCertificate(){
        LOGGER.info("Start Job Sync Cetification.");
        this.jobSyncCertificateService.start();
        LOGGER.info("Finish Job Sync Cetification.");
    }

}

package com.sync.certificate.synccertificatejob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SyncCertificateJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncCertificateJobApplication.class, args);
	}

}

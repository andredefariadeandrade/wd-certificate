package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.application.workday.service.PrepareCertifiedDataService;
import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AwsExtractCertifiedDataTest {

    @Autowired
    private PrepareCertifiedDataService prepareCertifiedDataService;

//    @Test
//    public void awsExtractCertifiedDataTest_return_1_certificates(){
//        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/aws_01.pdf");
//        List<Certificate> certificates = this.prepareCertifiedDataService.prepare(path);
//        assertEquals(1, certificates.size());
//    }

//    @Test
//    public void awsExtractCertifiedDataTest_validate_data_certificate(){
//        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/aws_01.pdf");
//        List<Certificate> certificates = this.prepareCertifiedDataService.prepare(path);
//        assertEquals("HQXBQJBC22B41FK7", certificates.get(0).getCertificationId());
//        assertEquals("August 05, 2017", certificates.get(0).getIssuedDate());
//        assertEquals("August 05, 2019", certificates.get(0).getExpirationDate());
//    }

}

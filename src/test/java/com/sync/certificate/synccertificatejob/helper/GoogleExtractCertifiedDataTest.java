package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.application.workday.service.PrepareCertifiedDataService;
import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleExtractCertifiedDataTest {

    @Autowired
    private PrepareCertifiedDataService prepareCertifiedDataService;

//    @Test
//    public void googleExtractCertifiedDataTest_return_1_certificates(){
//        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/google_01.pdf");
//        List<Certificate> certificates = this.prepareCertifiedDataService.prepare(path);
//        assertEquals(1, certificates.size());
//    }

//    @Test
//    public void googleExtractCertifiedDataTest_validate_data_certificate(){
//        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/google_01.pdf");
//        List<Certificate> certificates = this.prepareCertifiedDataService.prepare(path);
//        assertEquals("7DV W83 4H3", certificates.get(0).getCertificationId());
//        assertEquals("24/10/2021", certificates.get(0).getIssuedDate());
//        assertEquals(EMPTY, certificates.get(0).getExpirationDate());
//    }

}

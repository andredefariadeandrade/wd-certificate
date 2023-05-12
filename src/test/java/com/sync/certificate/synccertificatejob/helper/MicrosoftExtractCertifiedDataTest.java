package com.sync.certificate.synccertificatejob.helper;

import com.itextpdf.text.DocumentException;
import com.sync.certificate.synccertificatejob.application.workday.service.PrepareCertifiedDataService;
import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.sync.certificate.synccertificatejob.utils.MonthsOfYearEnum.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicrosoftExtractCertifiedDataTest {

    private PrepareCertifiedDataService prepareCertifiedDataService;

    @Before
    public void before() {
        this.prepareCertifiedDataService = new PrepareCertifiedDataService(new ExtractCertifiedHelper());
    }

    @Test
    public void microsoftExtractCertifiedDataTest_return_1_certificates() throws IOException {
        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/microsoft_02.pdf");
        final byte[] inFileBytes = Files.readAllBytes(Paths.get(path));
        final Set<Certificate> certificates = this.prepareCertifiedDataService.prepare(inFileBytes);
        assertEquals(1, certificates.size());

    }

    @Test
    public void microsoftExtractCertifiedDataTest_validate_data_certificate_1_records() throws IOException {
        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/microsoft_02.pdf");

        final byte[] inFileBytes = Files.readAllBytes(Paths.get(path));
        final Set<Certificate> certificates = this.prepareCertifiedDataService.prepare(inFileBytes);
        final List<Certificate> certificatesList = new ArrayList<>(certificates);

        assertEquals("AZ-900", certificatesList.get(0).getCertificationId());
        assertEquals("2022".concat("-").concat(MARCH.getMonthNumber()).concat("-").concat("10"), certificatesList.get(0).getIssuedDate());
    }

    @Test
    public void microsoftExtractCertifiedDataTest_return_8_certificates() throws IOException, DocumentException {
        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/microsoft_01.pdf");
        final byte[] inFileBytes = Files.readAllBytes(Paths.get(path));
        final Set<Certificate> certificates = this.prepareCertifiedDataService.prepare(inFileBytes);
        assertEquals(15, certificates.size());

    }

    @Test
    public void microsoftExtractCertifiedDataTest_validate_data_certificate_8_records() throws IOException {
        final String path = System.getProperty("user.dir").concat("/src/test/java/com/sync/certificate/synccertificatejob/certificates/microsoft_01.pdf");
        final byte[] inFileBytes = Files.readAllBytes(Paths.get(path));
        final Set<Certificate> certificates = this.prepareCertifiedDataService.prepare(inFileBytes);
        final List<Certificate> certificatesList = new ArrayList<>(certificates);
//        Validate certificate id
        assertEquals("AZ-303", certificatesList.get(0).getCertificationId());
        assertEquals("AZ-304", certificatesList.get(1).getCertificationId());
        assertEquals("AZ-900", certificatesList.get(2).getCertificationId());
        assertEquals("346", certificatesList.get(3).getCertificationId());
        assertEquals("342", certificatesList.get(4).getCertificationId());
        assertEquals("341", certificatesList.get(5).getCertificationId());
        assertEquals("337", certificatesList.get(6).getCertificationId());
        assertEquals("336", certificatesList.get(7).getCertificationId());
        assertEquals("665", certificatesList.get(8).getCertificationId());
        assertEquals("664", certificatesList.get(9).getCertificationId());

        assertEquals("2021".concat("-").concat(JULY.getMonthNumber()).concat("-").concat("26"), certificatesList.get(0).getIssuedDate());
        assertEquals("2021".concat("-").concat(JUNE.getMonthNumber()).concat("-").concat("09"), certificatesList.get(1).getIssuedDate());
        assertEquals("2021".concat("-").concat(APRIL.getMonthNumber()).concat("-").concat("16"), certificatesList.get(2).getIssuedDate());
        assertEquals("2016".concat("-").concat(JUNE.getMonthNumber()).concat("-").concat("19"), certificatesList.get(3).getIssuedDate());
        assertEquals("2015".concat("-").concat(MAY.getMonthNumber()).concat("-").concat("31"), certificatesList.get(4).getIssuedDate());
        assertEquals("2015".concat("-").concat(APRIL.getMonthNumber()).concat("-").concat("15"), certificatesList.get(5).getIssuedDate());
        assertEquals("2015".concat("-").concat(MARCH.getMonthNumber()).concat("-").concat("08"), certificatesList.get(6).getIssuedDate());
        assertEquals("2015".concat("-").concat(FEBRUARY.getMonthNumber()).concat("-").concat("01"), certificatesList.get(7).getIssuedDate());
        assertEquals("2012".concat("-").concat(APRIL.getMonthNumber()).concat("-").concat("19"), certificatesList.get(8).getIssuedDate());
        assertEquals("2012".concat("-").concat(MARCH.getMonthNumber()).concat("-").concat("18"), certificatesList.get(9).getIssuedDate());

        assertEquals("2022".concat("-").concat(JULY.getMonthNumber()).concat("-").concat("26"), certificatesList.get(0).getExpirationDate());
        assertEquals("2022".concat("-").concat(JUNE.getMonthNumber()).concat("-").concat("09"), certificatesList.get(1).getExpirationDate());
        assertEquals("2022".concat("-").concat(APRIL.getMonthNumber()).concat("-").concat("16"), certificatesList.get(2).getExpirationDate());
        assertEquals("2017".concat("-").concat(JUNE.getMonthNumber()).concat("-").concat("19"), certificatesList.get(3).getExpirationDate());
        assertEquals("2016".concat("-").concat(MAY.getMonthNumber()).concat("-").concat("31"), certificatesList.get(4).getExpirationDate());
        assertEquals("2016".concat("-").concat(APRIL.getMonthNumber()).concat("-").concat("15"), certificatesList.get(5).getExpirationDate());
        assertEquals("2016".concat("-").concat(MARCH.getMonthNumber()).concat("-").concat("08"), certificatesList.get(6).getExpirationDate());
        assertEquals("2016".concat("-").concat(FEBRUARY.getMonthNumber()).concat("-").concat("01"), certificatesList.get(7).getExpirationDate());
        assertEquals("2013".concat("-").concat(APRIL.getMonthNumber()).concat("-").concat("19"), certificatesList.get(8).getExpirationDate());
        assertEquals("2013".concat("-").concat(MARCH.getMonthNumber()).concat("-").concat("18"), certificatesList.get(9).getExpirationDate());
    }

}

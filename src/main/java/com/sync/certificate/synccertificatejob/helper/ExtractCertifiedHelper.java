package com.sync.certificate.synccertificatejob.helper;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExtractCertifiedHelper {

    private List<String> extractCertifiedData = new ArrayList<>();
    private int numberOfPages;
    private boolean certifiedAws;
    private boolean certifiedGoogle;
    private boolean certifiedMicrosoft;

    private final String MICROSOFT = "MICROSOFT";
    private final String GOOGLE = "GOOGLE";
    private final String AWS = "AWS ";

    public void setExtractCertifiedData(final byte[] pdfBase64) throws IOException {
        PdfReader reader = new PdfReader(pdfBase64);
        this.numberOfPages = reader.getNumberOfPages();
        for(int i = 1; i <= this.numberOfPages; i++){
            final String strPdf = PdfTextExtractor.getTextFromPage(reader, i);
            this.setCertifiedAws(strPdf);
            this.setCertifiedGoogle(strPdf);
            this.setCertifiedMicrosoft(strPdf);

            this.extractCertifiedData.add(strPdf);
        }
        reader.close();
    }

    public List<String> getExtractCertifiedData() {
        return extractCertifiedData;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public boolean isCertifiedAws() {
        return certifiedAws;
    }

    private void setCertifiedAws(final String certificatPdfString) {
        this.certifiedAws = (StringUtils.isNotEmpty(certificatPdfString) && certificatPdfString.toUpperCase().contains(AWS));
    }

    public boolean isCertifiedGoogle() {
        return certifiedGoogle;
    }

    public void setCertifiedGoogle(final String certificatPdfString) {
        this.certifiedGoogle = (StringUtils.isNotEmpty(certificatPdfString) && certificatPdfString.toUpperCase().contains(GOOGLE));
    }

    public boolean isCertifiedMicrosoft() {
        return certifiedMicrosoft;
    }

    public void setCertifiedMicrosoft(final String certificatPdfString) {
        this.certifiedMicrosoft = (StringUtils.isNotEmpty(certificatPdfString) && certificatPdfString.toUpperCase().contains(MICROSOFT));
    }
}

package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.domain.vo.Certificate;
import com.sync.certificate.synccertificatejob.utils.MonthsOfYearEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.*;

import static com.sync.certificate.synccertificatejob.utils.MonthsOfYearEnum.*;
import static org.apache.commons.lang3.StringUtils.*;

public class MicrosoftExtractCertifiedData implements IExtractCertifiedData {

    public Set<Certificate> setCertificateData(final List<String> certificatePdfStringPage) {
        final String name = this.getNameInCertificate(certificatePdfStringPage);
        final String email = this.getEmilaInCErtificate(certificatePdfStringPage);
        Set<Certificate> certificates = new HashSet<>();
        for(String pdfText : certificatePdfStringPage.get(0).split("Date Completed")[1].split("\n")){

            if(isEmpty(pdfText)){
                continue;
            }
            String issuedDate = this.getIssuedDate(pdfText);
            String expirationDate = this.getExpirationDate(issuedDate);
            String certificationId = this.getCertificateId(pdfText);

            if(isNotEmpty(certificationId)){
                certificates.add(new Certificate(name, email, issuedDate, expirationDate, certificationId));
            }
        }

        if(certificatePdfStringPage.size() > 1){
            for(String pdfText : certificatePdfStringPage.get(1).split("Microsoft Certification Official Transcript")[1].split("\n")){
                if(isEmpty(pdfText)){
                    continue;
                }
                if(pdfText.contains("Certification History Achievement Date")){
                    break;
                }
                String issuedDate = this.getIssuedDate(pdfText);
                String expirationDate = this.getExpirationDate(issuedDate);
                String certificationId = this.getCertificateId(pdfText);

                if(isNotEmpty(issuedDate) && isNotEmpty(expirationDate) && isNotEmpty(certificationId)){
                    certificates.add(new Certificate(name, email, issuedDate, expirationDate, certificationId));
                }
            }
        }
        return certificates;
    }

    @Override
    public String getCertificateId(String pdfText) {
        if(isNotEmpty(pdfText.split(" ")[0])){
            return pdfText.split(" ")[0];
        }
        return EMPTY;
    }

    @Override
    public String getIssuedDate(String pdfText ) {
        for(String date : pdfText.split("\n")){

            if(date.toUpperCase().contains(JANUARY.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(JANUARY.getMonthCapital())), JANUARY);
            }
            else if(date.toUpperCase().contains(FEBRUARY.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(FEBRUARY.getMonthCapital())), FEBRUARY);
            }
            else if(date.toUpperCase().toUpperCase().contains(MARCH.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(MARCH.getMonthCapital())), MARCH);
            }
            else if(date.toUpperCase().contains(APRIL.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(APRIL.getMonthCapital())), APRIL);
            }
            else if(date.toUpperCase().contains(MAY.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(MAY.getMonthCapital())), MAY);
            }
            else if(date.toUpperCase().contains(JUNE.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(JUNE.getMonthCapital())), JUNE);
            }
            else if(date.toUpperCase().contains(JULY.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(JULY.getMonthCapital())), JULY);
            }
            else if(date.toUpperCase().contains(AUGUST.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(AUGUST.getMonthCapital())), AUGUST);
            }
            else if(date.toUpperCase().contains(SEPTEMBER.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(SEPTEMBER.getMonthCapital())), SEPTEMBER);
            }
            else if(date.toUpperCase().contains(OCTOBER.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(OCTOBER.getMonthCapital())), OCTOBER);
            }
            else if(date.toUpperCase().contains(NOVEMBER.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(NOVEMBER.getMonthCapital())), NOVEMBER);
            }
            else if(date.toUpperCase().contains(DECEMBER.getMonthCapital())){
                return formatDate(date.toUpperCase().substring(date.toUpperCase().indexOf(DECEMBER.getMonthCapital())), DECEMBER);
            }
        }
        return EMPTY;
    }

    @Override
    public String getExpirationDate(final String issuedDate) {
        if(isNotEmpty(issuedDate)){
            final String[] date = issuedDate.split("-");
            return (Integer.parseInt(date[0])+1) + "-" + date[1] + "-" + date[2];
        }
        return EMPTY;
    }

    @Override
    public String formatDate(final String date, final MonthsOfYearEnum monthsOfYearEnum) {
        final String day = StringUtils.right("0"+date.replace(",", "").split(" ")[1], 2) ;
        final String year = date.replace(",", "").split(" ")[2];
        return year.concat("-").concat(monthsOfYearEnum.getMonthNumber()).concat("-").concat(day);
    }

    @Override
    public String getNameInCertificate(List<String> certificatePdfStringPage) {
        if(!CollectionUtils.isEmpty(certificatePdfStringPage) && certificatePdfStringPage.get(0).split("\n").length >= 3){
            return certificatePdfStringPage.get(0).split("\n")[2];
        }
        return EMPTY;
    }

    @Override
    public String getEmilaInCErtificate(List<String> certificatePdfStringPage) {
        if(!CollectionUtils.isEmpty(certificatePdfStringPage) && certificatePdfStringPage.get(0).split("\n").length >= 6){
            return certificatePdfStringPage.get(0).split("\n")[5];
        }
        return EMPTY;
    }
}

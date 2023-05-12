package com.sync.certificate.synccertificatejob.domain.vo;


import javax.xml.bind.annotation.*;


@XmlRootElement(name = "Report_Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportData {



    @XmlElement(name="Report_Entry")
    private ReportEntry reportEntry;

    public ReportData() {
    }

    public ReportEntry getReportEntry() {
        return reportEntry;
    }
}

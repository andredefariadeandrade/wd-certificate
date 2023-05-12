package com.sync.certificate.synccertificatejob.domain.vo;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Report_Entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportEntry {

    @XmlElement(name="Employee_ID")
    private String employeeId;

    @XmlElement(name="primaryWorkEmail")
    private String primaryWorkEmail;

    @XmlElement(name="firstName")
    private String firstName;

    @XmlElement(name="lastName")
    private String lastName;

    public ReportEntry() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPrimaryWorkEmail() {
        return primaryWorkEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

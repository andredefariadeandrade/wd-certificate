package com.sync.certificate.synccertificatejob.utils;

public class GoogleApiConst {

    public static String MESSAGE_ID = "MESSAGE_ID";
    public static String ATTACHMENT_ID = "ATTACHMENT_ID";
    public static String getTokenUrl = "o/oauth2/token";
    public static String getMessagesList = "users/{EMAIL}/messages/?q=in:inbox";
    public static String getMessageDetails = "users/me/messages/";

    public static String getAttachment = getMessageDetails.concat(MESSAGE_ID).concat("/attachments/").concat(ATTACHMENT_ID);

    public static String apiCertifications = "sys-devoteam-workday/api/certifications";

    private GoogleApiConst() {
    }

}

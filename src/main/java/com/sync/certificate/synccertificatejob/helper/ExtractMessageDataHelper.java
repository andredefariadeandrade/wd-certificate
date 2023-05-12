package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.GoogleApiListMessageWithAttachmentHeaderResponse;
import com.sync.certificate.synccertificatejob.domain.dto.messageWithAttachment.GoogleApiListMessageWithAttachmentResponse;
import com.sync.certificate.synccertificatejob.domain.vo.EmailCcData;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class ExtractMessageDataHelper {

    public ExtractMessageDataHelper() {
    }

    public Set<EmailCcData> getNameAndEmailMessage(final GoogleApiListMessageWithAttachmentResponse messageDetails){
        Set<EmailCcData> emailCcDatas =  new HashSet<>();
        if(this.validateMessageEmail(messageDetails)){
            return Collections.EMPTY_SET;
        }
        for(GoogleApiListMessageWithAttachmentHeaderResponse value : messageDetails.getGoogleApiListMessageWithAttachmentPayload().getGoogleApiListMessageWithAttachmentHeaderResponses()){
            if(this.isEmailCc(value)){
                for(String cc : this.getListCcData(value)){
                    String[] emailAndName = cc.replace("\"","").split("<");
                    if(emailAndName.length > 0){
                        EmailCcData emailAndNomeMap =  new EmailCcData();
                        if(this.existName(emailAndName)){
                            emailAndNomeMap.withName(emailAndName[0].trim());
                        }
                        if(this.existName(emailAndName) && this.existEmail(emailAndName)){
                            emailAndNomeMap.withEmail(emailAndName[1].replace(">",""));
                        } else {
                            emailAndNomeMap.withEmail(emailAndName[0].replace(">",""));
                        }
                        emailCcDatas.add(emailAndNomeMap);
                    }
                }
            }
        }
        return emailCcDatas;
    }

    private boolean validateMessageEmail(GoogleApiListMessageWithAttachmentResponse value){
        return (value == null ||
                value.getGoogleApiListMessageWithAttachmentPayload() == null ||
                CollectionUtils.isEmpty(value.getGoogleApiListMessageWithAttachmentPayload().getGoogleApiListMessageWithAttachmentHeaderResponses()));
    }

    private boolean existName(final String[] emailAndName){
        return (emailAndName.length > 1 && emailAndName[0] != null);
    }

    private boolean existEmail(final String[] emailAndName){
        if(this.existName(emailAndName)){
            return (emailAndName.length > 1 && emailAndName[1] != null);
        }
        return (emailAndName.length > 0 && emailAndName[0] != null);
    }

    private boolean isEmailCc(final GoogleApiListMessageWithAttachmentHeaderResponse googleApiListMessageWithAttachmentHeaderResponse){
        return (googleApiListMessageWithAttachmentHeaderResponse != null && googleApiListMessageWithAttachmentHeaderResponse.getName().equalsIgnoreCase("CC"));
    }

    private String[] getListCcData(final GoogleApiListMessageWithAttachmentHeaderResponse value){
        return value.getValue().split(",");
    }

}

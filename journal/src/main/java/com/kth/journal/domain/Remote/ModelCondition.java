package com.kth.journal.domain.Remote;

import org.hl7.fhir.r4.model.Condition;

public class ModelCondition {
    private String id;

    private String clinicalStatus;
    private String verificationStatus;

    private String code;

    private String subject;

    private String onsetDateTime;
    private String recordedDate;

    public ModelCondition(Condition condition) {
        this.id = condition.getId();
        this.clinicalStatus = condition.getClinicalStatus().getCoding().get(0).getDisplay();
        this.verificationStatus = condition.getVerificationStatus().getCoding().get(0).getDisplay();
        this.code = condition.getCode().getCoding().get(0).getDisplay();
        this.subject = condition.getSubject().getReference();
        this.onsetDateTime = condition.getOnsetDateTimeType().getValueAsString();
        this.recordedDate = condition.getRecordedDate().toString();
    }

    public String getId() {
        return id;
    }

    public String getClinicalStatus() {
        return clinicalStatus;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public String getCode() {
        return code;
    }

    public String getSubject() {
        return subject;
    }

    public String getOnsetDateTime() {
        return onsetDateTime;
    }

    public String getRecordedDate() {
        return recordedDate;
    }
}

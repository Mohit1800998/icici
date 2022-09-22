package com.rapipay.qrsicici.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("jnVpaMaster")
public class JnVpaMaster {

    @Id
    private String jnId;
    private String vpaId;
    private String updatedDate;
    private String bankResponse;
    private String acquiringBank;
    private String vpaStatus;
    private String createdDate;
    private String registeredVpa;
    private String vpaRefNumber;
    private String updatedBy;
    private String updatedFrom;

    public JnVpaMaster(){

    }

    public JnVpaMaster(String jnId, String vpaId, String updatedDate, String bankResponse, String acquiringBank, String vpaStatus, String createdDate, String registeredVpa, String vpaRefNumber, String updatedBy, String updatedFrom) {
        this.jnId = jnId;
        this.vpaId = vpaId;
        this.updatedDate = updatedDate;
        this.bankResponse = bankResponse;
        this.acquiringBank = acquiringBank;
        this.vpaStatus = vpaStatus;
        this.createdDate = createdDate;
        this.registeredVpa = registeredVpa;
        this.vpaRefNumber = vpaRefNumber;
        this.updatedBy = updatedBy;
        this.updatedFrom = updatedFrom;
    }

    public String getJnId() {
        return jnId;
    }

    public void setJnId(String jnId) {
        this.jnId = jnId;
    }

    public String getVpaId() {
        return vpaId;
    }

    public void setVpaId(String vpaId) {
        this.vpaId = vpaId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getBankResponse() {
        return bankResponse;
    }

    public void setBankResponse(String bankResponse) {
        this.bankResponse = bankResponse;
    }

    public String getAcquiringBank() {
        return acquiringBank;
    }

    public void setAcquiringBank(String acquiringBank) {
        this.acquiringBank = acquiringBank;
    }

    public String getVpaStatus() {
        return vpaStatus;
    }

    public void setVpaStatus(String vpaStatus) {
        this.vpaStatus = vpaStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRegisteredVpa() {
        return registeredVpa;
    }

    public void setRegisteredVpa(String registeredVpa) {
        this.registeredVpa = registeredVpa;
    }

    public String getVpaRefNumber() {
        return vpaRefNumber;
    }

    public void setVpaRefNumber(String vpaRefNumber) {
        this.vpaRefNumber = vpaRefNumber;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedFrom() {
        return updatedFrom;
    }

    public void setUpdatedFrom(String updatedFrom) {
        this.updatedFrom = updatedFrom;
    }

    @Override
    public String toString() {
        return "JnVpaMaster{" +
                "jnId='" + jnId + '\'' +
                ", vpaId='" + vpaId + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", bankResponse='" + bankResponse + '\'' +
                ", acquiringBank='" + acquiringBank + '\'' +
                ", vpaStatus='" + vpaStatus + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", registeredVpa='" + registeredVpa + '\'' +
                ", vpaRefNumber='" + vpaRefNumber + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedFrom='" + updatedFrom + '\'' +
                '}';
    }
}

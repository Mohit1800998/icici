package com.rapipay.qrsicici.entity;

import com.rapipay.qrsicici.utils.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Document("vpaMaster")
public class VPAMaster {

    @Id
    private String vpaId;
    private String requestId;
    private String vpaRefNumber;
    private String registeredVpa;
    private String createdDate = new SimpleDateFormat(Constants.RESPONSEDATEFORMAT).format(new Date());
    private String vpaStatus;
    private String acquiringBank;
    private String bankResponse;
    private String updatedDate = new SimpleDateFormat(Constants.RESPONSEDATEFORMAT).format(new Date());

    public VPAMaster() {
    }

    public VPAMaster(String vpaId, String requestId, String vpaRefNumber, String registeredVpa, String createdDate, String vpaStatus, String acquiringBank, String bankResponse, String updatedDate) {
        this.vpaId = vpaId;
        this.requestId = requestId;
        this.vpaRefNumber = vpaRefNumber;
        this.registeredVpa = registeredVpa;
        this.createdDate = createdDate;
        this.vpaStatus = vpaStatus;
        this.acquiringBank = acquiringBank;
        this.bankResponse = bankResponse;
        this.updatedDate = updatedDate;
    }

    public String getVpaId() {
        return vpaId;
    }

    public void setVpaId(String vpaId) {
        this.vpaId = vpaId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getVpaRefNumber() {
        return vpaRefNumber;
    }

    public void setVpaRefNumber(String vpaRefNumber) {
        this.vpaRefNumber = vpaRefNumber;
    }

    public String getRegisteredVpa() {
        return registeredVpa;
    }

    public void setRegisteredVpa(String registeredVpa) {
        this.registeredVpa = registeredVpa;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getVpaStatus() {
        return vpaStatus;
    }

    public void setVpaStatus(String vpaStatus) {
        this.vpaStatus = vpaStatus;
    }

    public String getAcquiringBank() {
        return acquiringBank;
    }

    public void setAcquiringBank(String acquiringBank) {
        this.acquiringBank = acquiringBank;
    }

    public String getBankResponse() {
        return bankResponse;
    }

    public void setBankResponse(String bankResponse) {
        this.bankResponse = bankResponse;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "VPAMaster{" +
                "vpaId='" + vpaId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", vpaRefNumber='" + vpaRefNumber + '\'' +
                ", registeredVpa='" + registeredVpa + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", vpaStatus='" + vpaStatus + '\'' +
                ", acquiringBank='" + acquiringBank + '\'' +
                ", bankResponse='" + bankResponse + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }
}

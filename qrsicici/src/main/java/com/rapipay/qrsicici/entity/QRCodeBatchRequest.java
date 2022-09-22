package com.rapipay.qrsicici.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("qrCodeBatchRequest")
public class QRCodeBatchRequest {

    @Id
    private String requestId;
    private String requestUserId;
    private String requestUserName;
    private String requestUserMobile;
    private String numberOfQrCode;
    private String generatedQrCode;
    private String requestStatus;
    private String acquiringBank;
    private String requestDate;

    public QRCodeBatchRequest() {
    }

    public QRCodeBatchRequest(String requestId, String requestUserId, String requestUserName, String requestUserMobile, String numberOfQrcode, String generatedQrCode, String requestStatus, String acquiringBank, String requestDate) {
        this.requestId = requestId;
        this.requestUserId = requestUserId;
        this.requestUserName = requestUserName;
        this.requestUserMobile = requestUserMobile;
        this.numberOfQrCode = numberOfQrcode;
        this.generatedQrCode = generatedQrCode;
        this.requestStatus = requestStatus;
        this.acquiringBank = acquiringBank;
        this.requestDate = requestDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    public String getRequestUserName() {
        return requestUserName;
    }

    public void setRequestUserName(String requestUserName) {
        this.requestUserName = requestUserName;
    }

    public String getRequestUserMobile() {
        return requestUserMobile;
    }

    public void setRequestUserMobile(String requestUserMobile) {
        this.requestUserMobile = requestUserMobile;
    }

    public String getNumberOfQrCode() {
        return numberOfQrCode;
    }

    public void setNumberOfQrCode(String numberOfQrCode) {
        this.numberOfQrCode = numberOfQrCode;
    }

    public String getGeneratedQrCode() {
        return generatedQrCode;
    }

    public void setGeneratedQrCode(String generatedQrCode) {
        this.generatedQrCode = generatedQrCode;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getAcquiringBank() {
        return acquiringBank;
    }

    public void setAcquiringBank(String acquiringBank) {
        this.acquiringBank = acquiringBank;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }


    @Override
    public String toString() {
        return "QRCodeBatchRequest{" +
                "requestId='" + requestId + '\'' +
                ", requestUserId='" + requestUserId + '\'' +
                ", requestUserName='" + requestUserName + '\'' +
                ", requestUserMobile='" + requestUserMobile + '\'' +
                ", numberOfQrCode='" + numberOfQrCode + '\'' +
                ", generatedQrCode='" + generatedQrCode + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                ", acquiringBank='" + acquiringBank + '\'' +
                ", requestDate='" + requestDate + '\'' +
                '}';
    }
}

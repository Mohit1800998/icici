package com.rapipay.qrsicici.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("vpaIssuanceFlatDetails")
public class VpaIssuanceFlatDetails {

    @Id
    private String issuanceId;
    private String vpaId;
    private String cicoSmid;
    private String bankResponse;
    private String acquiringBank;
    private String registeredVpa;
    private String vpaRefNumber;
    private String issuanceDate;
    private String qrImage;
    private String merchantName;
    private String employeeId;
    private String panCard;
    private String ip;
    private String latitude;
    private String longitude;
    private String mobileNo;
    private String vpaStatus;


    public VpaIssuanceFlatDetails() {
    }

    public VpaIssuanceFlatDetails(String issuanceId, String vpaId, String cicoSmid, String bankResponse, String acquiringBank, String registeredVpa, String vpaRefNumber, String issuanceDate, String qrImage, String merchantName, String employeeId, String panCard, String ip, String latitude, String longitude, String mobileNo, String vpaStatus) {
        this.issuanceId = issuanceId;
        this.vpaId = vpaId;
        this.cicoSmid = cicoSmid;
        this.bankResponse = bankResponse;
        this.acquiringBank = acquiringBank;
        this.registeredVpa = registeredVpa;
        this.vpaRefNumber = vpaRefNumber;
        this.issuanceDate = issuanceDate;
        this.qrImage = qrImage;
        this.merchantName = merchantName;
        this.employeeId = employeeId;
        this.panCard = panCard;
        this.ip = ip;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mobileNo = mobileNo;
        this.vpaStatus = vpaStatus;
    }

    public String getIssuanceId() {
        return issuanceId;
    }

    public void setIssuanceId(String issuanceId) {
        this.issuanceId = issuanceId;
    }

    public String getVpaId() {
        return vpaId;
    }

    public void setVpaId(String vpaId) {
        this.vpaId = vpaId;
    }

    public String getCicoSmid() {
        return cicoSmid;
    }

    public void setCicoSmid(String cicoSmid) {
        this.cicoSmid = cicoSmid;
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

    public String getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(String issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public String getQrImage() {
        return qrImage;
    }

    public void setQrImage(String qrImage) {
        this.qrImage = qrImage;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getVpaStatus() {
        return vpaStatus;
    }

    public void setVpaStatus(String vpaStatus) {
        this.vpaStatus = vpaStatus;
    }

    @Override
    public String toString() {
        return "VpaIssuanceFlatDetails{" +
                "issuanceId='" + issuanceId + '\'' +
                ", vpaId='" + vpaId + '\'' +
                ", cicoSmid='" + cicoSmid + '\'' +
                ", bankResponse='" + bankResponse + '\'' +
                ", acquiringBank='" + acquiringBank + '\'' +
                ", registeredVpa='" + registeredVpa + '\'' +
                ", vpaRefNumber='" + vpaRefNumber + '\'' +
                ", issuanceDate='" + issuanceDate + '\'' +
                ", qrImage='" + qrImage + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", panCard='" + panCard + '\'' +
                ", ip='" + ip + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", vpaStatus='" + vpaStatus + '\'' +
                '}';
    }
}

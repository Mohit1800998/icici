package com.rapipay.qrsicici.responsemodel;

public class QRResponseDto {

    private String apiResponseCode;
    private String apiResponseMessage;
    private ApiResponseData apiResponseData;
    private String apiResponseFrom;
    private String apiResponseDateTime;

    public QRResponseDto() {
    }

    public QRResponseDto(String apiResponseCode, String apiResponseMessage, ApiResponseData apiResponseData, String apiResponseFrom, String apiResponseDateTime) {
        this.apiResponseCode = apiResponseCode;
        this.apiResponseMessage = apiResponseMessage;
        this.apiResponseData = apiResponseData;
        this.apiResponseFrom = apiResponseFrom;
        this.apiResponseDateTime = apiResponseDateTime;
    }

    public String getApiResponseCode() {
        return apiResponseCode;
    }

    public void setApiResponseCode(String apiResponseCode) {
        this.apiResponseCode = apiResponseCode;
    }

    public String getApiResponseMessage() {
        return apiResponseMessage;
    }

    public void setApiResponseMessage(String apiResponseMessage) {
        this.apiResponseMessage = apiResponseMessage;
    }

    public ApiResponseData getApiResponseData() {
        return apiResponseData;
    }

    public void setApiResponseData(ApiResponseData apiResponseData) {
        this.apiResponseData = apiResponseData;
    }

    public String getApiResponseFrom() {
        return apiResponseFrom;
    }

    public void setApiResponseFrom(String apiResponseFrom) {
        this.apiResponseFrom = apiResponseFrom;
    }

    public String getApiResponseDateTime() {
        return apiResponseDateTime;
    }

    public void setApiResponseDateTime(String apiResponseDateTime) {
        this.apiResponseDateTime = apiResponseDateTime;
    }

    @Override
    public String toString() {
        return "QRResponseDto{" +
                "apiResponseCode='" + apiResponseCode + '\'' +
                ", apiResponseMessage='" + apiResponseMessage + '\'' +
                ", apiResponseData=" + apiResponseData +
                ", apiResponseFrom='" + apiResponseFrom + '\'' +
                ", apiResponseDateTime='" + apiResponseDateTime + '\'' +
                '}';
    }
}

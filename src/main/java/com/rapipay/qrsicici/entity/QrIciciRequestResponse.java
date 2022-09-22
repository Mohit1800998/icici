package com.rapipay.qrsicici.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("qrIciciRequestResponse")
public class QrIciciRequestResponse {

    private String request;
    private String response;

    public QrIciciRequestResponse(String request, String response) {
        this.request = request;
        this.response = response;
    }

    public QrIciciRequestResponse() {
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "QrIciciRequestResponse{" +
                "request='" + request + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}

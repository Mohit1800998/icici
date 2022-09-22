package com.rapipay.qrsicici.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlRootElement;


@JacksonXmlRootElement(localName = "XML")
@XmlRootElement
public class ICICIRequest {

    private String source;
    private String subscriberId;
    private String txnId;
    private String merchantKey;

    public ICICIRequest() {
    }

    public ICICIRequest(String source, String subscriberId, String txnId, String merchantKey) {
        this.source = source;
        this.subscriberId = subscriberId;
        this.txnId = txnId;
        this.merchantKey = merchantKey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    @Override
    public String toString() {
        return "ICICIRequest{" +
                "source='" + source + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", txnId='" + txnId + '\'' +
                ", merchantKey='" + merchantKey + '\'' +
                '}';
    }
}


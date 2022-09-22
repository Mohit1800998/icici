package com.rapipay.qrsicici.utils;

public enum CollectionName {
    QRCODEBATCHREQUEST("qrCodeBatchRequest"),
    VPAISUNACEFLATDETAILS("vpaIssuanceFlatDetails"),
    VPAMERCHANTFLATDETAILS("vpaMerchantFlatDetails"),
    VPAMASTER("vpaMaster"),
    JNVPAMASTER("jnVpaMaster"),
    SEQUENSEDATA("sequenceData");

    public final String collectionName;

    private CollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}



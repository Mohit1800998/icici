package com.rapipay.qrsicici.service;

public interface QrIciciService {

    /**
     *
     * @param requestData Icici bank xml request data
     * @return
     */
    String userMain(String requestData);

    /**
     *
     * @param requestData Icici bank request data
     * @return
     */
    String fetchDataFromDb(String requestData);

    /**
     *
     * @param customerName Name of the valid customer
     * @param txnId TxnId of a specific transaction
     * @return
     */
    String successResponse(String customerName, String txnId);

}

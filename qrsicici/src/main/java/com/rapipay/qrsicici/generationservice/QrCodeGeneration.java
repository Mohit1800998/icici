package com.rapipay.qrsicici.generationservice;

public interface QrCodeGeneration {
    /**
     * Main method to generate QR Code
     */
    void main();

    /**
     *  Fetches All QR Code Generation Batch Requests from MongoDB, execute then One by One
     *  and update generatedQrCode and requestStatus in qrCodeBatchRequest Collection of MongoDB.
     */
    void fetchqrsiciciRequest();

    /**
     *  Update generatedQrCode and requestStatus for particular requestId in qrCodeBatchRequest Collection of MongoDB.
     *
     * @param requestId - Request Id
     * @param qrCodeGenerated - Count of QR Code Generated Successfully
     */
    void updateRequestStatus(String requestId, long qrCodeGenerated);

}

package com.rapipay.qrsicici.sequencegeneration;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface FlatSequenceService {

    /**
     * Checks whether Collection(sequenceData) in present in MongoDB.
     * If present then call updateCounter() to update the counter for sequence(QRCounter)
     *
     * @param requestId      - requestId for which to update counter in document(QRCounter) of Collection(sequenceData)
     * @param collectionName - Name of Collection(sequenceData)
     * @param sequenceName   -  Name of Sequence(QRCounter)
     * @return - returns a JSONObject of registeredVpa and vpaRefNumber
     */
    JSONObject main(String requestId, String collectionName, String sequenceName, String flag);


    /**
     * Finds the sequence(QRCounter) in Collection(sequenceData), increment it by 1
     * and update the counter of sequence(QRCounter) in MongoDB
     *
     * @param requestId      - requestId for which to update counter in document(QRCounter) of Collection(sequenceData)
     * @param collectionName - Name of Collection(sequenceData)
     * @param sequenceName   -  Name of Sequence(QRCounter)
     * @return - returns JSONObject of counter and updatedCounter
     */
    JSONObject updateCounterInMongo(String requestId, String collectionName, String sequenceName, String flag);

    /**
     * Generate vpaId and vpaRefNo for particular QR Code
     *
     * @param requestId     - requestId for which to update counter in document(QRCounter) of Collection(sequenceData)
     * @param counterRecord - Contains currentCounter and updatedCounter value.
     * @return - returns a JSONObject of registeredVpa and vpaRefNumber
     */
    JSONObject generateVpa(String requestId, JSONObject counterRecord);

}

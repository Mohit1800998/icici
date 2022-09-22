package com.rapipay.qrsicici.sequencegeneration.impl;

import com.rapipay.conf.appprop.ReadProperties;
import com.rapipay.qrsicici.sequencegeneration.FlatSequenceService;
import com.rapipay.qrsicici.sequencegeneration.Sequence;
import com.rapipay.qrsicici.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Scope("prototype")
public class FlatSequenceServiceImpl implements FlatSequenceService {

    public static final Logger log = LogManager.getLogger(FlatSequenceServiceImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    public JSONObject main(String requestId, String collectionName, String sequenceName, String flag) {
        try {
            log.info("[Request_Id_{}] Generating Flat Sequence ", requestId);
            if (!mongoTemplate.collectionExists(collectionName)) {
                log.error("[Request_Id_{}] Collection [{}] not found", requestId, collectionName);
            } else {
                JSONObject counterRecord = updateCounterInMongo(requestId, collectionName, sequenceName, flag);
                    if ((counterRecord.toString()).equals("{}")) {
                    log.error("[Request_Id_{}] Unable to update Counter of sequenceName [{}] in Collection [{}] ", requestId, sequenceName, collectionName);
                    return new JSONObject();
                }
                if (flag.equals(Constants.INCREMENT_COUNTER))
                    return generateVpa(requestId, counterRecord);
                else
                    return new JSONObject();
            }
        } catch (NullPointerException e) {
            log.error("[Request_Id_{}] Null Pointer Exception while Flat Sequence Generation  =======>: {} {}", requestId, e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Request_Id_{}] Exception while Flat Sequence Generation  =======>: {} ", requestId, e.getMessage());
        }
        return new JSONObject();
    }

    public JSONObject updateCounterInMongo(String requestId, String collectionName, String sequenceName, String flag) {
        try {

            log.info("[Request_Id_{}] Updating Counter of sequenceName [{}] in Collection [{}] ", requestId, sequenceName, collectionName);
            Query select = new Query();
            select.addCriteria(Criteria.where("sequenceName").is(sequenceName));
            Sequence sequence = mongoTemplate.findOne(select, Sequence.class, collectionName);
            if (sequence == null) {
                log.error("[Request_Id_{}] SequenceName {} not found in Collection {}  =======>: {} ", requestId, sequenceName, collectionName);
                return new JSONObject();
            }
            Update update = new Update();
            String updatedCounter = updateCounter(requestId, sequence.getIcicicounter(),flag);
            if (updatedCounter.equals("")) {
                return new JSONObject();
            }
            update.set("icicicounter", updatedCounter);
            Sequence updatedSequence = mongoTemplate.findAndModify(select, update, Sequence.class, collectionName);
            Sequence newSequence =mongoTemplate.findOne(select, Sequence.class, collectionName);
            if(newSequence.getIcicicounter().equals(sequence.getIcicicounter())){
                return new JSONObject();
            }
            JSONObject counterRecord = new JSONObject();
            counterRecord.put("icicicounter", sequence.getIcicicounter());
            counterRecord.put("updatedCounter", updatedCounter);
            log.info("[Request_Id_{}] Counter of sequenceName [{}] Updated Successfully in Collection [{}] ===> {}", requestId, sequenceName, collectionName, counterRecord.toString());

            return counterRecord;

        } catch (NullPointerException e) {
            log.error("[Request_Id_{}] Null Pointer Exception while Updating Counter of SequenceName {} in Collection {} =======>: {} {}", requestId, sequenceName, collectionName, e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Request_Id_{}] Exception while Updating Counter of SequenceName {} in Collection {}  =======>: {} ", requestId, sequenceName, collectionName, e.getMessage());
        }
        return new JSONObject();
    }


    public JSONObject generateVpa(String requestId, JSONObject counterRecord) {
        try {
            log.info("[Request_Id_{}] Generating vpaId and vpaRefNo ", requestId);

            String baseString = "00000000";

            String counter = counterRecord.optString("icicicounter");
            String numeric = baseString.substring(counterRecord.optString("updatedCounter").length(), baseString.length()) + counter;
            String vpaId = ReadProperties.getPropertyData("vpaPrefix") + numeric + ReadProperties.getPropertyData("vpaSuffix");

            String vpaRefNo = new SimpleDateFormat("yyDDD").format(new Date()) + numeric;
            JSONObject vpaDetails = new JSONObject();
            vpaDetails.put("registeredVpa", vpaId);
            vpaDetails.put("vpaRefNumber", vpaRefNo);

            log.info("[Request_Id_{}] vpaId and vpaRefNo generated successfully ===> {}", requestId, vpaDetails.toString());

            return vpaDetails;
        } catch (NullPointerException e) {
            log.error("[Request_Id_{}] Null Pointer Exception while Generating vpaId and vpaRefNo  =======>: {} {}", requestId, e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Request_Id_{}] Exception while Generating vpaId and vpaRefNo  =======>: {} ", requestId, e.getMessage());
        }
        return new JSONObject();
    }

    public String updateCounter(String requestId, String counter, String flag) {
        try {
            log.info("[Request_Id_{}] Updating Counter ", requestId);
            String updatedCounter = "";
            updatedCounter = String.valueOf(Integer.parseInt(counter, 16) + 1);
            String hexUpdatedCounter = Integer.toHexString(Integer.parseInt(updatedCounter));
            return hexUpdatedCounter;
        } catch (NullPointerException e) {
            log.error("[Request_Id_{}] Null Pointer Exception while Updating Counter  =======>: {} {}", requestId, e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Request_Id_{}] Exception while Updating Counter  =======>: {} ", requestId, e.getMessage());
        }
        return "";
    }

}

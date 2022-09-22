package com.rapipay.qrsicici.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rapipay.qrsicici.entity.QrIciciRequestResponse;
import com.rapipay.qrsicici.entity.VpaIssuanceFlatDetails;
import com.rapipay.qrsicici.service.QrIciciService;
import com.rapipay.qrsicici.utils.Constants;
import com.rapipay.qrsicici.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class QrIciciServiceImpl implements QrIciciService {

    public static final Logger log = LogManager.getLogger(QrIciciServiceImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public String userMain(String requestData) {

        log.info("Calling the method to fetch the merchant details from the database");
        return fetchDataFromDb(requestData);
    }


    @Override
    public String fetchDataFromDb(String requestData) {
        String xmlResponse;

        try {

            JSONObject jsonData = new JSONObject(convertXmlToJson2(requestData));
            log.info("Converting the xml request to json : {}", jsonData);

            Query query = new Query();
            query.addCriteria(Criteria.where("registeredVpa").is("Rapi" + jsonData.optString("subscriberId") + "@ICICIBank"));
            query.addCriteria(Criteria.where("vpaStatus").is(Constants.VPASTATUS_ACTIVATED));

            log.info("[Txn_Id_{}] Checking if the Collection vpaIssuanceFlatDetails exist or not",jsonData.optString("txnId"));
            if (mongoTemplate.exists(query, "vpaIssuanceFlatDetails")) {
                log.info("[Txn_Id_{}] Fetching data from the collection vpaIssuanceFlatDetails",jsonData.optString("txnId"));
                VpaIssuanceFlatDetails vpaIssuanceFlatDetails = mongoTemplate.find(query, VpaIssuanceFlatDetails.class, "vpaIssuanceFlatDetails").get(0);
                if (vpaIssuanceFlatDetails.equals(null) || vpaIssuanceFlatDetails.equals("")) {
                    log.info("[Txn_Id_{}] Setting up failure response for the callback api",jsonData.optString("txnId"));
                    return Util.failureResponse();
                } else {
                    String customerName = vpaIssuanceFlatDetails.getMerchantName();
                    xmlResponse = successResponse(customerName, jsonData.optString("txnId"));
                    log.info("[Txn_Id_{}] Setting up success response for the callback api : {}",jsonData.optString("txnId"),xmlResponse);
                    saveInDb(requestData,xmlResponse);
                    return xmlResponse;
                    }
            } else {
                xmlResponse = Util.failureResponse();
                log.info("[Txn_Id_{}] Setting up failure response for the callback api",jsonData.optString("txnId"),xmlResponse);

                saveInDb(requestData,xmlResponse);
                log.info("[Txn_Id_{}] Saving the request and response data in the iciciRequestResponse Collecton",jsonData.optString("txnId"));

                return xmlResponse;
            }
        }catch (NullPointerException e){
            log.error("Null Pointer Exception while Fetching mid  =======>: {} {}", e.getMessage(), e);

            xmlResponse = Util.failureResponse();
            saveInDb(requestData,xmlResponse);
            return xmlResponse;
        }catch (Exception e){
            log.error("Exception while Fetching mid  =======>: {} {}", e.getMessage(), e);

            xmlResponse = Util.failureResponse();
            saveInDb(requestData,xmlResponse);
            return xmlResponse;
        }

    }

    public String convertXmlToJson2(String xml) {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode;
        try {
            jsonNode = xmlMapper.readTree(xml.getBytes());
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(jsonNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Override
    public String successResponse(String customerName, String txnId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("CustName", customerName);
            jsonObject.put("ActCode", 0);
            jsonObject.put("Message", "VALID");
            jsonObject.put("TxnId", txnId);
            String rootElement = "XML";
            return "<" + rootElement + ">" + XML.toString(jsonObject) + "</" + rootElement + ">";
        }catch (NullPointerException e) {
            log.error("Null Pointer Exception while Fetching mid  =======>: {} {}",  e.getMessage(), e);

        } catch (Exception e) {
            log.error("Exception while Fetching mid  =======>: {} ", e.getMessage());

        }
        return "";

    }

    public void saveInDb(String request, String response) {
        try {
            QrIciciRequestResponse qrIciciRequestResponseToBeSaved =new QrIciciRequestResponse();
            qrIciciRequestResponseToBeSaved.setRequest(request);
            qrIciciRequestResponseToBeSaved.setResponse(response);
            mongoTemplate.save(qrIciciRequestResponseToBeSaved,"qrIciciRequestResponse");
        }
        catch (NullPointerException e) {
            log.error("Null Pointer Exception while Fetching mid  =======>: {} {}", e.getMessage(), e);

        } catch (Exception e) {
            log.error("Exception while Fetching mid  =======>: {} ", e.getMessage());

        }
    }



}

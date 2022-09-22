package com.rapipay.qrsicici.activationservice.impl;


import com.rapipay.qrsicici.activationservice.QrCodeActivation;
import com.rapipay.qrsicici.entity.JnVpaMaster;
import com.rapipay.qrsicici.entity.VPAMaster;
import com.rapipay.qrsicici.entity.VpaIssuanceFlatDetails;
import com.rapipay.qrsicici.responsemodel.QRResponseDto;
import com.rapipay.qrsicici.utils.CollectionName;
import com.rapipay.qrsicici.utils.Constants;
import com.rapipay.qrsicici.utils.ValidateAuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
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
public class QrCodeActivationImpl implements QrCodeActivation {

    public static final Logger log = LogManager.getLogger(QrCodeActivationImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    ValidateAuth validateAuth;

    String bankResponse;

    public void main(QRResponseDto qrResponseDto, String mobileNo, String registeredVpa, String auth) {
        validateAuth = GetValidateAuthObject();
        JSONObject responseStatus = new JSONObject();
        responseStatus.put("registeredVpa",registeredVpa);
        try {
            validateAuth.validateAuth(mobileNo, auth, qrResponseDto);

            if(qrResponseDto.getApiResponseData().getResponseCode().equals(Constants.FAIL_RESPONSE_CODE)){
                return ;
            }
            fetchRequest(mobileNo,qrResponseDto,registeredVpa);
        } catch (NullPointerException e) {
            log.error("Null Pointer Exception in Main function  =======>: {} {}", e.getMessage(), e);
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());


        } catch (Exception e) {
            log.error("Exception in Main Function  =======>: {} ", e.getMessage());
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());


        }
    }

    @Override
    public void fetchRequest(String mobileNo,QRResponseDto qrResponseDto,String registeredVpa) {
        JSONObject responseStatus = new JSONObject();
        responseStatus.put("registeredVpa",registeredVpa);
        try {
            log.info("[Mobile_No_{}] Fetching Document from Collection {} in MongoDB",mobileNo, CollectionName.VPAISUNACEFLATDETAILS.collectionName);

            Query query = new Query();
            query.addCriteria(Criteria.where("mobileNo").is(mobileNo));
            query.addCriteria(Criteria.where("registeredVpa").is(registeredVpa));
            VpaIssuanceFlatDetails data = mongoTemplate.find(query, VpaIssuanceFlatDetails.class, CollectionName.VPAISUNACEFLATDETAILS.collectionName).get(0);

            if (data.equals(null) || data.equals("")) {
                log.info("[Mobile_No_{}] No Data present ",mobileNo);
                qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
                qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
                qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

            } else {
                if (data.getVpaStatus().equals(Constants.VPAPRINTING)) {
                    updateDetails(Constants.VPASTATUS_ACTIVATED, data, qrResponseDto);
                    qrResponseDto.getApiResponseData().setResponseCode(Constants.SUCCESS_RESPONSECODE);
                    qrResponseDto.getApiResponseData().setResponseMessage(Constants.QR_ACTIVATED_SUCCESSFULLY);
                    qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_SUCCESS).toMap());

                }
                else{
                    qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
                    qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
                    qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());
                    return ;
                }
            }

        } catch (NullPointerException e) {
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

            log.error("[Mobile_No_{}] Null Pointer Exception while fetching QR Code activation Requests  =======>: {} {}",mobileNo, e.getMessage(), e);
        } catch (Exception e) {
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

            log.error("[Mobile_No_{}] Exception while fetching QR Code activation Requests  =======>: {} ",mobileNo, e.getMessage());
        }
    }

    public void updateDetails(String vpaStatus, VpaIssuanceFlatDetails details,QRResponseDto qrResponseDto){
        JSONObject responseStatus = new JSONObject();
        responseStatus.put("registeredVpa",details.getRegisteredVpa());
        try {
            updateJnVpaMaster(vpaStatus,details,qrResponseDto);
            updateVpaIssuanceFlatDetails(vpaStatus,details,qrResponseDto);
            updateVPAMaster(vpaStatus,details,qrResponseDto);
        } catch (NullPointerException e) {
            log.error("[Vpa_Id_{}] Null Pointer Exception while Updating collection  =======>: {} {}", details.getVpaId(), e.getMessage(), e);
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        } catch (Exception e) {
            log.error("[Vpa_Id_{}] Exception while Updating Collection  =======>: {} ", details.getVpaId(), e.getMessage());
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        }
    }

    @Override
    public void updateJnVpaMaster(String vpaStatus, VpaIssuanceFlatDetails details,QRResponseDto qrResponseDto) {
        JnVpaMaster jnVpaMaster = new JnVpaMaster();
        JSONObject responseStatus = new JSONObject();
        responseStatus.put("registeredVpa",details.getRegisteredVpa());
        try {
            log.info("[Vpa_Id_{}] Updating jnVpaMaster collection.",details.getVpaId());
            jnVpaMaster.setVpaStatus(vpaStatus);
            Query query = Query.query(Criteria.where("vpaId").is(details.getVpaId()));
            Update update = new Update();
            update.set("vpaStatus",vpaStatus);
            update.set("updatedDate",new SimpleDateFormat(Constants.TIMEFORMATFORFETCHDATA).format(new Date()));
            update.set("bankResponse",bankResponse);
            update.set("updatedBy",Constants.ACTIVATIONPROCESS);
            update.set("updatedFrom",Constants.ACTIVATIONPROCESS);
            mongoTemplate.updateFirst(query, update, JnVpaMaster.class);
        } catch (NullPointerException e) {
            log.error("[Vpa_Id_{}] Null Pointer Exception while Updating jnVpaMaster collection  =======>: {} {}", details.getVpaId(), e.getMessage(), e);
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        } catch (Exception e) {
            log.error("[Vpa_Id_{}] Exception while Updating jnVpaMaster collection  =======>: {} ", details.getVpaId(), e.getMessage());
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        }


    }

    @Override
    public void updateVpaIssuanceFlatDetails(String vpaStatus, VpaIssuanceFlatDetails details , QRResponseDto qrResponseDto) {
        VpaIssuanceFlatDetails VpaIssuanceFlatDetails = new VpaIssuanceFlatDetails();
        JSONObject responseStatus = new JSONObject();
        responseStatus.put("registeredVpa",details.getRegisteredVpa());
        try {
            log.info("[Vpa_Id_{}] Updating VpaIssuanceFlatDetails collection.",details.getVpaId());

            details.setVpaStatus(vpaStatus);
            details.setBankResponse(bankResponse);
            mongoTemplate.save(details,CollectionName.VPAISUNACEFLATDETAILS.collectionName);
        } catch (NullPointerException e) {
            log.error("[Vpa_ID_{}] Null Pointer Exception while Updating VpaIssuanceFlatDetails  =======>: {} {}", VpaIssuanceFlatDetails.getVpaId(), e.getMessage(), e);
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        } catch (Exception e) {
            log.error("[Vpa_ID_{}] Exception while Updating VpaIssuanceFlatDetails  =======>: {} ", VpaIssuanceFlatDetails.getVpaId(), e.getMessage());
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        }


    }

    @Override
    public void updateVPAMaster(String vpaStatus, VpaIssuanceFlatDetails details ,QRResponseDto qrResponseDto) {
        VPAMaster vpaMaster = new VPAMaster();
        JSONObject responseStatus = new JSONObject();
        responseStatus.put("registeredVpa",details.getRegisteredVpa());
        try {
            log.info("[Vpa_Id_{}] Updating vpaMaster collection.",details.getVpaId());
            vpaMaster.setVpaStatus(vpaStatus);
            Query query = Query.query(Criteria.where("vpaId").is(details.getVpaId()));
            Update update = new Update();
            update.set("vpaStatus",vpaStatus);
            update.set("updatedDate",new SimpleDateFormat(Constants.TIMEFORMATFORFETCHDATA).format(new Date()));
            mongoTemplate.updateFirst(query, update, VPAMaster.class);
        } catch (NullPointerException e) {
            log.error("[Vpa_ID_{}] Null Pointer Exception while Updating VPA Master  =======>: {} {}", vpaMaster.getVpaId(), e.getMessage(), e);
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        } catch (Exception e) {
            log.error("[Vpa_ID_{}] Exception while Updating VPA Master  =======>: {} ", vpaMaster.getVpaId(), e.getMessage());
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.SOMETHING_WENT_WRONG);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_FAILURE).toMap());

        }


    }





    @Lookup
    public ValidateAuth GetValidateAuthObject() {
        return null;
    }
}


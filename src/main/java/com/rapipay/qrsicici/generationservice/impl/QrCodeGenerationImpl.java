package com.rapipay.qrsicici.generationservice.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.rapipay.qrsicici.entity.JnVpaMaster;
import com.rapipay.qrsicici.entity.QRCodeBatchRequest;
import com.rapipay.qrsicici.entity.VPAMaster;
import com.rapipay.qrsicici.generationservice.QrCodeGeneration;
import com.rapipay.qrsicici.sequencegeneration.FlatSequenceService;
import com.rapipay.qrsicici.utils.CollectionName;
import com.rapipay.qrsicici.utils.Constants;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
public class QrCodeGenerationImpl implements QrCodeGeneration {

    public static final Logger log = LogManager.getLogger(QrCodeGenerationImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    FlatSequenceService flatSequenceService;

    public void main() {
        try {
            flatSequenceService = getFlatSequenceServiceObject();
            fetchqrsiciciRequest();
        } catch (NullPointerException e) {
            log.error("Null Pointer Exception in Main function  =======>: {} {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Exception in Main Function  =======>: {} ", e.getMessage());
        }
    }

    public void fetchqrsiciciRequest() {
        try {
            log.info("Fetching QRCode Generation Requests from Collection {} in MongoDB", CollectionName.QRCODEBATCHREQUEST.collectionName);

            Query query = new Query();
            query.addCriteria(Criteria.where("requestStatus").is(Constants.REQUESTSTATUS_PENDING));
            query.addCriteria(Criteria.where("acquiringBank").is("ICICI Bank"));

            List<QRCodeBatchRequest> batchRequests = mongoTemplate.find(query, QRCodeBatchRequest.class, CollectionName.QRCODEBATCHREQUEST.collectionName);

            if (batchRequests.isEmpty() == true) {
                log.info("No QRCode Generation Request");
            } else {
                log.info("{} QRCode Generation Requests fetched from Collection {} in MongoDB", batchRequests.size(), CollectionName.QRCODEBATCHREQUEST.collectionName);
                for (QRCodeBatchRequest qrCodeRequest : batchRequests) {
                        long qrCodeGenerated = generateQr(qrCodeRequest);
                        updateRequestStatus(qrCodeRequest.getRequestId(), qrCodeGenerated);
                }
            }

        } catch (NullPointerException e) {
            log.error("Null Pointer Exception while fetching QR Code generation Requests  =======>: {} {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Exception while fetching QR Code generation Requests  =======>: {} ", e.getMessage());
        }
    }

    public long generateQr(QRCodeBatchRequest qrCodeRequest) {

        long qrCodeGenerated = 0;
        try {
            log.info("[Request_Id_{}] Generating QRCode for requestId {}", qrCodeRequest.getRequestId(), qrCodeRequest.getRequestId());

            for (long i = 1; i <= Long.parseLong(qrCodeRequest.getNumberOfQrCode()); i++) {
                generateQRApiCall(qrCodeRequest);
                qrCodeGenerated++;
            }
        } catch (NullPointerException e) {
            log.error("[Request_Id_{}] Null Pointer Exception while Generating QR Code  =======>: {} {}", qrCodeRequest.getRequestId(), e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Request_Id_{}] Exception while Generating QR Code  =======>: {} ", qrCodeRequest.getRequestId(), e.getMessage());
        }

        log.info("[Request_Id_{}] {} QR Code has been generated for requestId {}", qrCodeRequest.getRequestId(), qrCodeGenerated, qrCodeRequest.getRequestId());
        return qrCodeGenerated;
    }

    public String generateQRApiCall(QRCodeBatchRequest qrCodeRequest) {

        try {
            log.info("[Request_Id_{}] API Calling to Generate QR Code", qrCodeRequest.getRequestId());

            JSONObject vpaDetails = flatSequenceService.main(qrCodeRequest.getRequestId(), CollectionName.SEQUENSEDATA.collectionName, Constants.SEQUENCENAME, Constants.INCREMENT_COUNTER);
            if ((vpaDetails.toString()).equals("{}")) {
                log.error("[Request_Id_{}] Unable to generate vpaId and vpaRefNo ", qrCodeRequest.getRequestId());
                return "";
            }


            String uri = UriComponentsBuilder.fromUriString("upi://pay?")
                    .queryParam("pa", vpaDetails.optString("registeredVpa"))
                    .queryParam("pn", "ICICI BANK")
                    .queryParam("tn=", "Pay for in-app purchase")
                    .queryParam("cu", "INR").build().toString();
            QRCodeWriter barcodeWriter = new QRCodeWriter();

            Map<EncodeHintType, Object> crunchifyHintType = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            crunchifyHintType.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            crunchifyHintType.put(EncodeHintType.MARGIN, 1);
            Object put = crunchifyHintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix =
                    barcodeWriter.encode(uri, BarcodeFormat.QR_CODE, 200, 200, crunchifyHintType);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);


            String qrBase64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            if(!qrBase64.equals("")) {
                VPAMaster vpaMaster = createVpaMaster(qrCodeRequest, vpaDetails, qrBase64);
                createJnVpaMaster(vpaMaster, vpaDetails, qrBase64);
            }

            return qrBase64;

        } catch (NullPointerException e) {
            log.error("[Request_Id_{}] Null Pointer Exception in Calling API =======>: {} {}", qrCodeRequest.getRequestId(), e.getMessage(), e);
            return "";
        } catch (Exception e) {
            log.error("[Request_Id_{}] Exception in Calling API =======>: {} ", qrCodeRequest.getRequestId(), e.getMessage());
            return "";
        }
    }


    public void updateRequestStatus(String requestId, long qrCodeGenerated) {
        try {
            log.info("[Request_Id_{}] Updating Request Status in Collection [{}] of Mongo DB", requestId, CollectionName.QRCODEBATCHREQUEST.collectionName);
            log.info("[Request_Id_{}] GeneratedQRCode : {}", requestId,String.valueOf(qrCodeGenerated));
            Query select = Query.query(Criteria.where("_id").is(requestId));
            Update update = new Update();
            update.set("generatedQrCode", String.valueOf(qrCodeGenerated));
            update.set("requestStatus", Constants.REQUESTSTATUS_SUCCESS);
            QRCodeBatchRequest updatedQrCodeRequest = mongoTemplate.findAndModify(select, update, QRCodeBatchRequest.class, CollectionName.QRCODEBATCHREQUEST.collectionName);
            QRCodeBatchRequest find =mongoTemplate.findOne(select, QRCodeBatchRequest.class, CollectionName.QRCODEBATCHREQUEST.collectionName);
            if(find.getGeneratedQrCode().equals("") || find.getRequestStatus().equals(Constants.REQUESTSTATUS_PENDING)){
                log.error("[Request_Id_{}] Request Status has not been updated in Collection {} in Mongo DB ", requestId, CollectionName.QRCODEBATCHREQUEST.collectionName);
                return ;
            }
            if (!(updatedQrCodeRequest == null)) {
                log.info("[Request_Id_{}] Request Status has been updated in Collection {} in Mongo DB", requestId, CollectionName.QRCODEBATCHREQUEST.collectionName);
            } else {
                log.error("[Request_Id_{}] Request Status has not been updated in Collection {} in Mongo DB", requestId, CollectionName.QRCODEBATCHREQUEST.collectionName);
            }

        } catch (NullPointerException e) {
            log.error("[Request_Id_{}] Null Pointer Exception while Updating Request Status in {} Collection  =======>: {} {}", requestId, CollectionName.QRCODEBATCHREQUEST.collectionName, e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Request_Id_{}] Exception while Updating Request Status in {} Collection  =======>: {} ", requestId, CollectionName.QRCODEBATCHREQUEST.collectionName, e.getMessage());
        }
    }


    public VPAMaster createVpaMaster(QRCodeBatchRequest qrCodeRequest, JSONObject vpaDetails, String base64String) {
        VPAMaster vpaMaster = new VPAMaster();
        try {
            log.info("[Request_Id_{}] Inserting VPA in Collection [{}] of Mongo DB", qrCodeRequest.getRequestId(), CollectionName.VPAMASTER.collectionName);
            vpaMaster.setRequestId(qrCodeRequest.getRequestId());
            vpaMaster.setVpaRefNumber(vpaDetails.optString("vpaRefNumber"));
            vpaMaster.setRegisteredVpa(vpaDetails.optString("registeredVpa"));
            vpaMaster.setVpaStatus(Constants.GENERATED_CODE);
            vpaMaster.setAcquiringBank(qrCodeRequest.getAcquiringBank());
            vpaMaster.setBankResponse(base64String);

            mongoTemplate.save(vpaMaster, CollectionName.VPAMASTER.collectionName);

        } catch (NullPointerException e) {
            log.error("[Vpa_Id_{}] Null Pointer Exception while inserting VPA in {} Collection  =======>: {} {}", vpaMaster.getVpaId(), CollectionName.VPAMASTER.collectionName, e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Vpa_Id_{}] Exception while inserting VPA in {} Collection  =======>: {} ", vpaMaster.getVpaId(), CollectionName.VPAMASTER.collectionName, e.getMessage());
        }
        return vpaMaster;
    }

    public void createJnVpaMaster(VPAMaster vpaMaster, JSONObject vpaDetails, String response){
        try {
            log.info("[Vpa_Id_{}] Inserting in Collection [{}] of Mongo DB", vpaMaster.getRequestId(), CollectionName.JNVPAMASTER.collectionName);
            JnVpaMaster jnVpaMaster = new JnVpaMaster();
            jnVpaMaster.setVpaId(vpaMaster.getVpaId());
            jnVpaMaster.setUpdatedDate(new SimpleDateFormat(Constants.RESPONSEDATEFORMAT).format(new Date()));
            jnVpaMaster.setBankResponse(response);
            jnVpaMaster.setAcquiringBank(vpaMaster.getAcquiringBank());
            jnVpaMaster.setVpaStatus(Constants.GENERATED_CODE);
            jnVpaMaster.setCreatedDate(new SimpleDateFormat(Constants.RESPONSEDATEFORMAT).format(new Date()));
            jnVpaMaster.setRegisteredVpa(vpaDetails.optString("registeredVpa"));
            jnVpaMaster.setVpaRefNumber(vpaDetails.optString("vpaRefNumber"));
            jnVpaMaster.setUpdatedBy(Constants.SCHEDULER);
            jnVpaMaster.setUpdatedFrom(Constants.SCHEDULER);

            mongoTemplate.save(jnVpaMaster, CollectionName.JNVPAMASTER.collectionName);

        } catch (NullPointerException e) {
            log.error("[Vpa_Id_{}] Null Pointer Exception while inserting in {} Collection  =======>: {} {}", vpaMaster.getRequestId(), CollectionName.JNVPAMASTER.collectionName, e.getMessage(), e);
        } catch (Exception e) {
            log.error("[Vpa_Id_{}] Exception while inserting in {} Collection  =======>: {} ", vpaMaster.getRequestId(), CollectionName.JNVPAMASTER.collectionName, e.getMessage());
        }
    }




    @Lookup
    public FlatSequenceService getFlatSequenceServiceObject() {
        return null;
    }

}

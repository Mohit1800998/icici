package com.rapipay.qrsicici.controller;

import com.rapipay.qrsicici.activationservice.QrCodeActivation;
import com.rapipay.qrsicici.responsemodel.ApiResponseData;
import com.rapipay.qrsicici.responsemodel.QRResponseDto;
import com.rapipay.qrsicici.service.QrIciciService;
import com.rapipay.qrsicici.utils.Constants;
import com.rapipay.qrsicici.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class Controller {

    public static final Logger log = LogManager.getLogger(Controller.class);

    QrIciciService qrIciciService;

    QrCodeActivation qrCodeActivation;

    @PostMapping(path = "/qrvalidation", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String getQrDetailFromDb(@RequestBody String requestData) {

        try{

            log.info("Inside the controller for the Icici callback api : {}", XML.toString(XML.toJSONObject(requestData)));

            qrIciciService = getQrIciciServiceObject();
            return  qrIciciService.userMain(requestData);

        }
        catch (Exception e){
            return Util.failureResponse();
        }

    }

    @GetMapping("/qrActivation")
    public QRResponseDto activateQr(@RequestHeader("auth") String auth, @RequestHeader String mobileNo, @RequestHeader String registeredVpa){
        QRResponseDto qrResponseDto = new QRResponseDto();
        qrResponseDto.setApiResponseData(new ApiResponseData());
        JSONObject responseStatus = new JSONObject();
        responseStatus.put("registeredVpa",registeredVpa);
        log.info("[Mobile_No_{}] Inside the Activate Qr code method {} with registered Vpa {}",mobileNo,registeredVpa);
        try {

            qrResponseDto.setApiResponseCode(Constants.SUCCESS_RESPONSECODE);
            qrResponseDto.setApiResponseMessage(Constants.SUCCESS_RESPONSE_MESSAGE);

            qrCodeActivation = getQrCodeActivationObject();

            qrCodeActivation.main(qrResponseDto,mobileNo,registeredVpa,auth);

        }catch (NullPointerException e) {
            log.error("[Mobile_No_{}] Null Pointer Exception in Activation Bank Api : {}",mobileNo, e.getMessage(), e);
            qrResponseDto.getApiResponseData().setResponseCode(Constants.PENDING_RESPONSECODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.RESPONSETIMEOUTMESSAGE);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_PENDING).toMap());

        } catch (Exception e) {
            log.error("[Mobile_No_{}] Exception in Activation Bank Api : {}",mobileNo, e.getMessage());
            qrResponseDto.getApiResponseData().setResponseCode(Constants.PENDING_RESPONSECODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.RESPONSETIMEOUTMESSAGE);
            qrResponseDto.getApiResponseData().setResponseData(responseStatus.put("vpaStatus",Constants.REQUESTSTATUS_PENDING).toMap());

        }

        qrResponseDto.setApiResponseFrom("QR_ACTIVATION");
        qrResponseDto.setApiResponseDateTime((new SimpleDateFormat(Constants.RESPONSEDATEFORMAT)).format(new Date()));
        log.info("[Mobile_No_{}] Response sent to qr activation api calling : {}", mobileNo, qrResponseDto.toString());

        return qrResponseDto;

    }


    @Lookup
    public QrIciciService getQrIciciServiceObject() {
        return null;
    }


    @Lookup
    public QrCodeActivation getQrCodeActivationObject() {
        return null;
    }
}

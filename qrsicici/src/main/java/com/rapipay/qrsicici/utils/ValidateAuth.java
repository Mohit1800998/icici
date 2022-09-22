package com.rapipay.qrsicici.utils;

import com.rapipay.conf.appprop.ReadProperties;
import com.rapipay.qrsicici.responsemodel.QRResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ValidateAuth {
    public static final Logger log = LogManager.getLogger(ValidateAuth.class);

    public void validateAuth(String mobileNo, String auth, QRResponseDto qrResponseDto) {
        try {
            log.info("[Mobile_No_{}] Calling method to verify base auth ", mobileNo);
            String authToken = "Basic " + Base64.getEncoder().encodeToString((ReadProperties.getPropertyData("AUTH_CREDENTIAL") + ":" + ReadProperties.getPropertyData("AUTH_PASSWORD")).getBytes());
            if (!authToken.equals(auth)) {
                qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
                qrResponseDto.getApiResponseData().setResponseMessage(Constants.AUTHORIZATIONFAILURE);
                qrResponseDto.getApiResponseData().setResponseData("{}");
            }
            else{
                qrResponseDto.getApiResponseData().setResponseCode("");
                qrResponseDto.getApiResponseData().setResponseMessage("");
                qrResponseDto.getApiResponseData().setResponseData("{}");
            }
        } catch (NullPointerException e) {
            log.error("[Mobile_No_{}] Exception at verifying basic auth ", mobileNo, e.getMessage(), e);
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.AUTHORIZATIONFAILURE);
            qrResponseDto.getApiResponseData().setResponseData("{}");
        } catch (Exception e) {
            log.error("[Mobile_No_{}] Exception at verifying auth ", mobileNo, e.getMessage());
            qrResponseDto.getApiResponseData().setResponseCode(Constants.FAIL_RESPONSE_CODE);
            qrResponseDto.getApiResponseData().setResponseMessage(Constants.AUTHORIZATIONFAILURE);
            qrResponseDto.getApiResponseData().setResponseData("{}");
        }

    }
}

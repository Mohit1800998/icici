package com.rapipay.qrsicici.activationservice;

import com.rapipay.qrsicici.entity.VpaIssuanceFlatDetails;
import com.rapipay.qrsicici.responsemodel.QRResponseDto;

public interface QrCodeActivation {

    /**
     * Main method for Activating qr code to a corresponding mobile Number
     */
    void main(QRResponseDto qrResponseDto, String mobileNo, String registeredVpa, String auth);


    /**
     *  Fetch the request from database using mobile number.
     * @param mobileNo - RMN of the User.
     * @param qrResponseDto Response Model to send success or failure response
     */
     void fetchRequest(String mobileNo,QRResponseDto qrResponseDto,String registeredVpa);
    /**
     *
     * @param vpaStatus Vpa Status for activation
     * @param details VpaIssuanceFlatDetails to update bankResponse and vpaStatus in jnVpaMaster collection
     * @param qrResponseDto Response Model to send success or failure response
     */
     void updateJnVpaMaster(String vpaStatus,VpaIssuanceFlatDetails details,QRResponseDto qrResponseDto);

    /**
     *
     * @param vpaStatus Vpa Status for activation
     * @param details VpaIssuanceFlatDetails to update bankResponse and vpaStatus in VpaIssuanceFlatDetails collection
     * @param qrResponseDto Response Model to send success or failure response
     */
     void updateVpaIssuanceFlatDetails(String vpaStatus, VpaIssuanceFlatDetails details ,QRResponseDto qrResponseDto);

    /**
     *
     * @param vpaStatus Vpa Status for activation
     * @param details VpaIssuanceFlatDetails to update bankResponse and vpaStatus in VpaIssuanceFlatDetails collection
     * @param qrResponseDto Response Model to send success or failure response
     */
    void updateVPAMaster(String vpaStatus, VpaIssuanceFlatDetails details ,QRResponseDto qrResponseDto);

}


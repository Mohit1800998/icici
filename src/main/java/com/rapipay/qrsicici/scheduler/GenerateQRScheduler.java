package com.rapipay.qrsicici.scheduler;

import com.rapipay.qrsicici.generationservice.QrCodeGeneration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class GenerateQRScheduler {

    public static final Logger log = LogManager.getLogger(GenerateQRScheduler.class);

    QrCodeGeneration qrCodeGeneration;

    @Scheduled(cron = "${GENERATE_QR_CODE_CRON}")
    public void generateQrCode() {
        try {
            log.info("Inside qricici Scheduler");
            qrCodeGeneration = getCodeGenerationObject();
            qrCodeGeneration.main();
        } catch (NullPointerException e) {
            log.error("Null Pointer Exception in QR Code Generation Scheduler  =======>: {} {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Exception in QR Code Generation Scheduler  =======>: {} ", e.getMessage());
        }
    }

    @Lookup
    public QrCodeGeneration getCodeGenerationObject() {
        return null;
    }

}
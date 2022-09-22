package com.rapipay.qrsicici;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QrsiciciApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrsiciciApplication.class, args);
	}

}

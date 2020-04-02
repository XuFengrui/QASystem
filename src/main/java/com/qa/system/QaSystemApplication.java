package com.qa.system;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.qa.system.utils.AliyunSmsUtils.sendSms;

@SpringBootApplication
public class QaSystemApplication {

	public static void main(String[] args) throws ClientException {
		SpringApplication.run(QaSystemApplication.class, args);
	}

}

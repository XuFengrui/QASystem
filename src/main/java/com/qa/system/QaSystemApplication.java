package com.qa.system;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.qa.system.utils.AliyunSmsUtils.sendSms;
import static com.qa.system.utils.AliyunSmsUtils.sendSuccess;

@SpringBootApplication
public class QaSystemApplication {

//	private static int newcode;
//	public static int getNewcode()
//	{        return newcode;
//	}
//	public static void setNewcode(){
//		newcode = (int)(Math.random()*9999)+100;  //每次调用生成一位四位数的随机数
//	}

	public static void main(String[] args) throws ClientException {
		SpringApplication.run(QaSystemApplication.class, args);
//		setNewcode();
//		String code = Integer.toString(getNewcode());
//		SendSmsResponse response =sendSuccess("18063061870","徐冯睿");
//		sendSms("18063061870",code);
	}

}

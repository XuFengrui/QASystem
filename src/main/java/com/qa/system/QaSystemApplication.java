package com.qa.system;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.qa.system.utils.SendMailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.qa.system.utils.AliyunSmsUtils.sendSms;
import static com.qa.system.utils.AliyunSmsUtils.sendSuccess;

@SpringBootApplication
public class QaSystemApplication {

//	@Autowired
//	SendMailUtils sendMailUtils;
//	private static int newcode;
//	public static int getNewcode()
//	{        return newcode;
//	}
//	public static void setNewcode(){
//		newcode = (int)(Math.random()*9999)+100;  //每次调用生成一位四位数的随机数
//	}

	public static void main(String[] args) throws ClientException {
//	public void main(String[] args) throws ClientException {
		SpringApplication.run(QaSystemApplication.class, args);
//		setNewcode();
//		String code = Integer.toString(getNewcode());
//		SendSmsResponse response =sendSuccess("18063061870","徐冯睿");
//		sendSms("18063061870",code);
//		sendMailUtils.sendSuccessRegisterMail("2609231098@qq.com","睿睿");
	}

}

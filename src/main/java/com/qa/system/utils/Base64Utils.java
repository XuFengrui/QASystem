package com.qa.system.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;

/**
 * @ClassName Base64Utils
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/4/22
 * @Version 1.0
 **/
@Component
public class Base64Utils {

    /**
    * @Author XuFengrui
    * @Description 图片转化为base64字符串
    * @Date 10:27 2020/4/22
    * @Param [image]
    * @return java.lang.String
    **/
    public static String getImageStr(String imgPath) {
        byte[] data = null;
        //读取图片的字节数组
        try {
            InputStream inputStream = new FileInputStream(imgPath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组进行Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(Objects.requireNonNull(data));
    }

    /**
    * @Author XuFengrui
    * @Description base64字符串转化为图片
    * @Date 11:12 2020/4/22
    * @Param [imgStr, image]
    * @return boolean
    **/
    public static boolean generateImage(String imgStr,String imgPath) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream outputStream = new FileOutputStream(imgPath);
            outputStream.write(b);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}

package com.qa.system.utils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName ReadTxt
 * @Description TODO
 * @Author XuFengrui
 * @Date 2020/5/7
 * @Version 1.0
 **/
@Component
public class ReadTxt {

    public static Set<String> readFile(String filePath) throws Exception{
        //存放文件内容的set集合
        Set<String> set = null;
        //读取文件
        File file = new File(filePath);
        //建立读取流
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filePath),"GBK");
        try {
            //判断文件是否存在
            if (file.isFile() && file.exists()) {
                //初始化set集合
                set = new HashSet<String>();
                //缓冲区读取流
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //循环读取文件中内容，每次读取一行内容
                String txt = null;
                while ((txt = bufferedReader.readLine()) != null) {
                    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else {
                //不存在，抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        }finally {
            inputStreamReader.close();
        }
        return set;
    }
}




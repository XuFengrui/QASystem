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

    public static Object readFile(String pathName)
    {
        Object temp=null;
        File file =new File(pathName);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(fileInputStream);
            temp=objIn.readObject();//从文件当中读取对象
            Set<Object> objects = new HashSet<Object>();
            objects.add(temp);//添加对象到set集合里面
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}




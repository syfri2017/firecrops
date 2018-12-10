package com.syfri.digitalplan.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import sun.misc.BASE64Encoder;

public class Pic {

    /**
    * 获取图片流
    * @param path
    * @return
    */
    public static String getImageStr(String path) {
        String imgFile = path;
        InputStream in = null;
        File t=new File(path);
        if(!t.exists()){
            return null;
        }
        byte[] data = null;
        try {
        in = new FileInputStream(imgFile);
        data = new byte[in.available()];
        in.read(data);
        in.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    
    public static void main(String[] args) {
        String basePath="D:\\%s.jpg";
        String path=String.format(basePath,1);

        System.out.println(getImageStr(path));
    }
}

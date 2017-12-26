package com.spring.boot.demo;

import java.io.File;
import java.net.URL;

/**
 * 生成二维码
 *
 * @author BoSongsh
 * @create 2017-12-26 10:05
 **/
public class QRCodeTest {


    public static void main(String[] args) {
        QRCodeTest poem = new QRCodeTest();
        poem.getFile("static/img/logo.gif");
    }

    public void getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileName);
        System.out.println(url.getFile());
        File file = new File(url.getFile());
        System.out.println(file.exists());
    }


}

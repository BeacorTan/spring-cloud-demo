package com.spring.boot.demo.controller;

import com.swetake.util.Qrcode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * @author BoSongsh
 * @create 2017-12-13 18:20
 **/
@RestController
public class HelloController {

    private final Logger logger = Logger.getLogger(HelloController.class);


    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("/hello")
    public String hello() {

        discoveryClient.getServices();
        discoveryClient.getInstances("111");
        return "Hello World";
    }

    @RequestMapping("/index")
    public ModelMap index(ModelMap modelMap) {
        modelMap.addAttribute("path", "0000000000000000");
        return modelMap;
    }


    /**
     * 生成带logo的二维码
     * @Params:[request, response]
     * @Return:void
     * @Author BoSongsh
     * @Date: 2017/12/26 16:21
     */
    @RequestMapping("/qrcodeProduct")
    public void qrcodeProduct(HttpServletRequest request, HttpServletResponse response) {


        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeErrorCorrect('M');
        qrcode.setQrcodeEncodeMode('B');
        qrcode.setQrcodeVersion(7);

        String content = request.getParameter("msg");
        if (content == null || !content.equals("")) {
            content = "http://172.26.182.189:8081/index.html";
        }
        byte[] bstr = new byte[0];
        try {
            bstr = content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        int qrcodeWidth = 142;
        int qrcodeHeight = 142;
        ///测试 使用
        //二维码 所依附的图片
        BufferedImage buffImage = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);
        //创建绘图
        Graphics2D graphics = buffImage.createGraphics();
        graphics.setBackground(Color.YELLOW);//设置二维码图片的背景色
        graphics.clearRect(1, 1, 139, 139);// x, y 轴; 二维码的图像的宽 高
        graphics.setColor(Color.BLACK); //画二维码的颜色

        if (bstr.length > 0 && bstr.length < 123) {
            boolean[][] b = qrcode.calQrcode(bstr);
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    if (b[j][i]) {
                        graphics.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                    }
                }

            }
        }
        //得到logo的绝对路径
        //String path=request.getRealPath("/static/img/logo.gif");

        //String path = "static/img/logo.gif";
        String path = "static/img/logo.gif";
        Image image = null;//二维码中间的logo
        try {
            // 获取classpath路径
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(path);
            System.out.println(url.getFile());
            image = ImageIO.read(new File(url.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        graphics.drawImage(image, 50, 50, null);//绘制到画布中
        // 画布坐标，在二维码中的开始坐标x:50，y:50
        int imgX = 0;
        int imgY = 0;
        if (qrcodeWidth == qrcodeHeight) {
            int imgWidth = image.getWidth(new ImageObserver() {
                public boolean imageUpdate(Image image, int i, int i1, int i2, int i3, int i4) {
                    return false;
                }
            });
            imgX = (qrcodeWidth - imgWidth) / 2;
            imgY = imgX;
        } else {
            int imgWidth = image.getWidth(new ImageObserver() {
                public boolean imageUpdate(Image image, int i, int i1, int i2, int i3, int i4) {
                    return false;
                }
            });
            int imgHeight = image.getHeight(new ImageObserver() {
                public boolean imageUpdate(Image image, int i, int i1, int i2, int i3, int i4) {
                    return false;
                }
            });
            imgX = (qrcodeWidth - imgWidth) / 2;
            imgY = (qrcodeHeight - imgHeight) / 2;
        }

        graphics.drawImage(image, imgX, imgY, null);//绘制到画布中

        graphics.dispose();
        buffImage.flush();

        response.setContentType("image/png");
        try {
            ImageIO.write(buffImage, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("generateFilletPicture")
    public void generateFilletPicture(HttpServletResponse response) {
        try {

//            String path = "static/img/logo.gif";
//            // 获取classpath路径
//            ClassLoader classLoader = getClass().getClassLoader();
//            URL url = classLoader.getResource(path);
//            File srcImageFile = new File(url.getFile());
//            BufferedImage bi1 = ImageIO.read(srcImageFile);
            int width=200;
            int height=300;


            // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);

            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, width, height);

            Graphics2D g2 = image.createGraphics();
            image = g2.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2 = image.createGraphics();
            g2.setComposite(AlphaComposite.Clear);
            g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
            g2.setClip(shape);
            // 使用 setRenderingHint 设置抗锯齿
            g2 = image.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int cornerRadius = 10;
            g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
            g2.setComposite(AlphaComposite.SrcIn);
            //g2.drawImage(bi1, 0, 0, width, height, null);
            g2.dispose();
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

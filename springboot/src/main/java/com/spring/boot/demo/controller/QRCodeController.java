package com.spring.boot.demo.controller;

import com.swetake.util.Qrcode;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
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
 * 二维码控制器
 *
 * @author BoSongsh
 * @create 2017-12-27 11:05
 **/
@RestController
public class QRCodeController {


    private final static Logger LOGGER = Logger.getLogger(QRCodeController.class);

    /**
     * 生成带二维码【含logo、文字描述】的圆角矩形
     *
     * @Author BoSongsh
     * @Date: 2017/12/27 11:04
     */
    @RequestMapping("generateFilletPicture")
    public void generateFilletPicture(HttpServletResponse response) throws IOException {

        // 图片宽度
        int width = 200;
        // 图片高度
        int height = 300;

        // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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
        // 画布背景颜色
        g2.setBackground(Color.WHITE);

        // 文字颜色
        g2.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 16);

        g2.setFont(font);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g2.getFontMetrics(font);
        String headText = "刮开涂层 验证真伪";
        int textWidth = fm.stringWidth(headText);
        int textHeight = fm.getHeight();
        g2.drawString(headText, (width - textWidth) / 2, textHeight << 1);

        String text = "00001 00002";
        textWidth = fm.stringWidth(text);
        g2.drawString(text, (width - textWidth) / 2, height - (textHeight << 1));


        // ******************************生成二维码并绘制在生成的图片中间  begin***********************************//
        String qrCodeContent = "http://172.26.182.189:8081/index.html";
        byte[] bstr = new byte[0];
        try {
            bstr = qrCodeContent.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("QRCodeController.generateFilletPicture 异常：", e);
            response.getWriter().write("服务开小差了！请稍等！！！");
            return;
        }
        int version = 7;
        //根据版本计算尺寸
        int imgSize = 67 + 12 * (version - 1);
        BufferedImage qrCodeImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
        Qrcode qrcode = new Qrcode();
        //设置二维码排错率，可选L(7%) M(15%) Q(25%) H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
        qrcode.setQrcodeErrorCorrect('Q');
        //N代表数字,A代表字符a-Z,B代表其他字符
        qrcode.setQrcodeEncodeMode('B');
        //版本1为21*21矩阵，版本每增1，二维码的两个边长都增4；所以版本7为45*45的矩阵；最高版本为是40，是177*177的矩
        qrcode.setQrcodeVersion(version);
        Graphics2D qrCodeG2 = qrCodeImage.createGraphics();
        qrCodeG2.setBackground(Color.WHITE);
        // 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        qrCodeG2.clearRect(0, 0, imgSize, imgSize);
        qrCodeG2.setColor(Color.ORANGE);
        // 设置偏移量 不设置可能导致解析出错
        int pixoff = 1;
        if (bstr.length > 0 && bstr.length < 123) {
            boolean[][] b = qrcode.calQrcode(bstr);
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    if (b[j][i]) {
                        qrCodeG2.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        }
        // 绘制到画布中
        g2.drawImage(qrCodeImage, (width - imgSize) / 2, (height - imgSize) / 2, null);
        // 释放资源
        qrCodeG2.dispose();
        // ******************************生成二维码并绘制在生成的图片中间  end***********************************//


        // ******************************设置二维码的logo  begin***********************************//
        // logo图片位置
        String path = "static/img/logo.gif";
        Image imageLogo;
        try {
            // 获取classpath路径
            ClassLoader classLoader = getClass().getClassLoader();
            // 获取图片绝对路径
            URL url = classLoader.getResource(path);
            imageLogo = ImageIO.read(new File(url.getFile()));
        } catch (IOException e) {
            LOGGER.error("QRCodeController.generateFilletPicture 异常：", e);
            response.getWriter().write("服务开小差了！请稍等！！！");
            return;
        }

        // 画布坐标，在二维码中的开始坐标x:50，y:50
        int imgWidth = imageLogo.getWidth(new ImageObserver() {
            public boolean imageUpdate(Image image, int i, int i1, int i2, int i3, int i4) {
                return false;
            }
        });
        int imgY = imageLogo.getHeight(new ImageObserver() {
            public boolean imageUpdate(Image image, int i, int i1, int i2, int i3, int i4) {
                return false;
            }
        });
        int imgX = (width - imgWidth) / 2;
        // logo填充在二维码上，绘制到画布中
        g2.drawImage(imageLogo, imgX, (height - imgY) / 2, null);
        // ******************************设置二维码的logo  end***********************************//

        // 释放此图形的上下文并释放它所使用的所有系统资源。
        g2.dispose();

        // 生成图片返回给调用方
        ImageIO.write(image, "png", response.getOutputStream());
    }
}

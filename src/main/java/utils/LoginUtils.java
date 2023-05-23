package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 工具类
 */
public class LoginUtils {
    //默认的保存验证码的路径
    public final static String PATH = "D:\\AAA\\code\\";
    public static String CODE;

    //静态代码块,初始化程序时,创建必要的目录
    static {
        File file = new File(PATH);
        if (!file.exists()){
            file.mkdirs();
        }
    }
    /**
     * 生成随机数
     * @return 返回随机数
     */
    public static String makeNum(){
        Random random = new Random();
        String num = random.nextInt(9999)+"";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4-num.length(); i++) {//用0填充,保证它是4位数
            sb.append("0");
        }
        num = sb.toString()+num;
        return num;
    }

    /**
     * 生成验证码图片
     */
    public static String createImage(){
        //绘制验证码
        //在内存中创建图片
        BufferedImage image = new BufferedImage(80,20,BufferedImage.TYPE_INT_RGB);
        //得到图片
        Graphics2D pen = (Graphics2D) image.getGraphics(); //画笔
        //设置图片的背景颜色
        pen.setColor(Color.WHITE);
        pen.fillRect(0,0,80,30);//填充背景,从(0,0)到(80,20)
        //给图片写数据
        pen.setColor(Color.BLUE);
        pen.setFont(new Font(null,Font.BOLD,20));
        //pen.drawString(makeNum(),0,20);//数字验证码
        CODE = createCode(5);//字母验证码
        pen.drawString(CODE,0,20);
        try {
            ImageIO.write(image, "jpg", new FileOutputStream(PATH+"code.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CODE;
    }

    /**
     * 返回英文验证码(包含大小写)
     * @param len 期望的验证码长度
     * @return 返回验证码
     */
    public static String createCode(int len){
        StringBuilder result=new StringBuilder();
        char[] words=new char[len];
        for (int i = 0; i < len; i++) {
            int code=0;
            //大小写字母都有出现的概率
            if((int)(Math.random()*i%2)==0)//得到大写字母
                code=(int)(Math.random()*26+65);
            else//得到小写字母
                code=(int)(Math.random()*26+65);
            words[i]=(char)code;
        }
        for(char word:words){
            result.append(word);
        }
        return String.valueOf(result);
    }

    /**
     * 打乱验证码内容
     * @param words 参数是字符数组，存放验证码中的内容
     */
    public static void shuffle(char[] words){
        for (int i = 0; i < words.length; i++) {
            int rand=(int)(Math.random()*words.length);
            swap(i,rand,words);
        }
    }
    //交换数组
    public static void swap(int i,int j,char[] words){
        char temp=words[i];
        words[i]=words[j];
        words[j]=temp;
    }


}

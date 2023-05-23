import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.ImageUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WebcamCaptureExample {
    //保存图片缓存的路径
    public final static String PATH = "D:\\AAA\\img_cache\\";

    static {
        File file = new File(PATH);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static void main(String[] args) {
        // 获取默认的摄像头
        Webcam webcam = Webcam.getDefault();
        // 设置摄像头分辨率
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        // 打开摄像头
        webcam.open();
        // 创建一个定时器，每隔5秒钟截图一次
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // 获取图像
                File file = new File(PATH+"a.png");
                try {
                    ImageIO.write(webcam.getImage(), "png", file);
                    System.out.println("缓存保存到了" + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5000);
    }
}
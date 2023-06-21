package utils;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class WebCamUtils {
    public static Webcam webcam = Webcam.getDefault();
    public static JPanel getCamera() {

        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);

        panel.setFPSDisplayed(true);

        panel.setDisplayDebugInfo(true);

        panel.setImageSizeDisplayed(true);

        panel.setMirrored(true);

        JPanel jPanel = new JPanel();
        jPanel.add(panel);

        return jPanel;
    }


    public static void closeCamera(){
        webcam.close();
    }

    /**
     * 下载图片
     * @param path 下载路径
     * @throws IOException 抛出
     */
    public static void getImage(String path) throws IOException {
        File file = new File(path);
        ImageIO.write(webcam.getImage(),"PNG",file);
        webcam.getImage();
    }

}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test3 {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "这是一个弹出框");
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.getRootFrame().dispose(); // 关闭弹出框
            }
        });
        timer.setRepeats(false); // 设置定时器不重复执行
        timer.start(); // 启动定时器
    }
}

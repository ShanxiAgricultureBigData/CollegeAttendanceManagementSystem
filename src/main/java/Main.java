import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.LoginPane;



public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        //设置托盘图标
//        SystemTray systemTray = SystemTray.getSystemTray();
//        java.awt.Image image = Toolkit.getDefaultToolkit().getImage("/icon.png");
//        String notifyStr = "山西农业大学学生考勤管理系统";
//        PopupMenu menu = new PopupMenu();
//        MenuItem item = new MenuItem("exit");
//        item.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                       System.exit(0);
//                    }
//                });
//            }
//        });
//        menu.add(item);
//        TrayIcon trayIcon = new TrayIcon(image,notifyStr,menu);
//        try {
//            systemTray.add(trayIcon);
//        } catch (AWTException e) {
//        }

        Scene loginPane = new Scene(new LoginPane(),1042,700);
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setTitle("山西农业大学考勤管理系统");
        primaryStage.setScene(loginPane);
        primaryStage.show();
    }
}

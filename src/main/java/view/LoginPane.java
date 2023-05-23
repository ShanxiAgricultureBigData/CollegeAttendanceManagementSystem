package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.DBUtils;
import utils.LoginUtils;
import utils.WebCamUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.Locale;

/**
 * 首界面
 */
public class LoginPane extends GridPane {


    private static String user_name;//用户输入的账号
    private static String user_pwd;//用户输入的密码
    private static boolean ifSuccess = false;//是否验证成功

    private static String CODE;//验证码
    private static Text label;
    private static Button exit;
    private static Button sign;
    private static Button register;
    private static Button log;
    private static TextField userTextField;
    private static PasswordField passwordField;
    private static TextField idCode;

    public String getUsername(){
        return userTextField.getText();
    }
    public String getPassword(){
        return passwordField.getText();
    }
    public String getImgCode(){
        return idCode.getText();
    }
    //获取按钮对象,作为切换窗体的桥梁
    public Button getSign(){
        return sign;
    }

    public LoginPane() throws FileNotFoundException, InterruptedException {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);

        label = new Text("Welcome");
        label.setId("welcome-text");
        this.add(label,0,0,2,1);

        Label userName = new Label("用户名:");
        this.add(userName, 0, 1);

        userTextField = new TextField();
        this.add(userTextField, 1, 1);

        Label pw = new Label("密码:");
        this.add(pw, 0, 2);

        passwordField = new PasswordField();
//        passwordField.setTooltip(new Tooltip("密码不得超过10位"));
//        passwordField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                Alert alert=new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("密码长度不得超过10位");
//                ImageView alert_view = new ImageView(new Image(getClass().getResourceAsStream("/icon/info.png")));
//                alert.setGraphic(alert_view);
//                if(newValue.length()>10){
//                    alert.show();
//                    passwordField.setText("");
//                }
//            }
//        });
        this.add(passwordField, 1, 2);

        Label code = new Label("验证码");
        this.add(code,0,3);

        idCode = new TextField();
        idCode.setPromptText("不区分大小写");
        idCode.setTooltip(new Tooltip("不区分大小写"));//提示
        //限制输入字个数
        idCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>5){
                    idCode.setText(oldValue);
                }
            }
        });
        this.add(idCode,1,3);

        //添加按钮和文本
//        Image sign_icon = new Image(getClass().getResourceAsStream("/icon/warning.png"));
        sign = new Button("登录");
        sign.setTooltip(new Tooltip("仅限管理员登陆"));//提示内容
        sign.setOnAction(event->{
            //用户输入的账号和密码
            user_name = getUsername();
            user_pwd = getPassword();
            ResultSet rs = null;
            try {
                rs = DBUtils.executeQuery("select * from user");
                while (rs.next()){
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    if (username.equals(user_name) && password.equals(user_pwd)){
                        System.out.println("账号密码正确");
                        if (LoginUtils.CODE.toUpperCase(Locale.ROOT).equals(getImgCode().toUpperCase(Locale.ROOT))){
                            ifSuccess = true;
                        }
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                DBUtils.close();//千万记得关闭资源
            }
            if (ifSuccess){
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("登陆成功,欢迎你"+user_name);
                success.setWidth(300);
                success.setHeight(200);
                ImageView alert_view = new ImageView(new Image(getClass().getResourceAsStream("/icon/info.png")));
                alert_view.setFitWidth(100);
                alert_view.setFitHeight(80);
                success.setGraphic(alert_view);
                success.setContentText("验证成功,欢迎你-管理员");
                success.show();
                Stage stage = (Stage) getSign().getScene().getWindow();
                Scene scene = new Scene(new WorkPane());
                stage.setScene(scene);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("信息输入错误");
                ImageView alert_view = new ImageView(new Image(getClass().getResourceAsStream("/icon/error.png")));
                alert_view.setFitHeight(80);
                alert_view.setFitWidth(100);
                alert.setGraphic(alert_view);
                alert.setTitle("错误");
                alert.show();
            }
        });
        register = new Button("注册");
        register.setOnAction(event->{
            Stage registerStage = new Stage();
            registerStage.getIcons().add(new Image("faceIcon.png"));
            registerStage.setTitle("学生信息注册");
            Scene scene = new Scene(new RegisterPane());
            registerStage.setScene(scene);
            registerStage.setWidth(1042);
            registerStage.setHeight(700);
            registerStage.show();
        });
        HBox hbBtn = new HBox(10);

        log = new Button("签到");
        log.setOnAction(event->{
            Stage logStage = new Stage();
//            logStage.getIcons().add(new Image("sign.png"));
            logStage.setTitle("签到");
            logStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    WebCamUtils.closeCamera();
                }
            });
            Scene scene = null;
            try {
                scene = new Scene(new LogPane());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logStage.setScene(scene);
            logStage.setHeight(800);
            logStage.setWidth(1060);
            logStage.show();
        });

        //hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        //设置按钮控件和上下左右的间距
        HBox.setMargin(sign, new Insets(20, 20, 20, 20));
        HBox.setMargin(register, new Insets(20, 20, 20, 20));
        HBox.setMargin(log, new Insets(20, 20, 20, 20));
        hbBtn.getChildren().addAll(sign,register,log);
        this.add(hbBtn, 1, 5);

        Label codeImag = new Label();
        CODE= LoginUtils.createImage();
        System.out.println("正确验证码为 "+CODE);
        Thread.sleep(200);
        FileInputStream inStream = new FileInputStream(LoginUtils.PATH+"code.jpg");//节点流读取数据
        Image capImg= new Image(inStream);
        codeImag.setGraphic(new ImageView(capImg));
        this.add(codeImag,1,4);

        exit = new Button("退出登录");
        exit.setTranslateX(800);
        exit.setTranslateY(600);

        this.getChildren().add(exit);
        this.getStylesheets().add("login.css");

    }

}

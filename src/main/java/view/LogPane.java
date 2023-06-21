package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import pojo.Log;
import utils.ArcFaceUtils;
import utils.DBUtils;
import utils.RegexUtils;
import utils.WebCamUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;


/**
 * 签到面板
 */
public class LogPane extends BorderPane {
    private static boolean ifSuccess = false;//是否匹配成功
    private static boolean repeat = false;//是否重复匹配

    private static Map<String,Integer> map = new HashMap<>();//防止重复签到

    private String id;
    private String name;
    private String sex;
    private String college;
    private String major;
    private String grade;

    private String log_time;

    //保存图片缓存的路径
    public final static String PATH = "D:\\AAA\\img_cache\\";

    static {
        File file = new File(PATH);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    private Label name_label = new Label("姓名");
    private TextField name_text = new TextField();
    private Label college_label = new Label("学院");
    private TextField college_text = new TextField();
    private Label major_label = new Label("专业");
    private TextField major_text = new TextField();
    private Label grade_label = new Label("班级");
    private TextField grade_text = new TextField();
    private Label stu_id_label = new Label("学号");
    private TextField stu_id_text = new TextField();
    private Label similar_label = new Label("相似度");
    private TextField similar_text = new TextField();

    public LogPane() throws InterruptedException {

        SwingNode swingNode = new SwingNode();
        swingNode.setContent(WebCamUtils.getCamera());
        this.setTop(swingNode);

        //等待摄像头完全打开再截取图片
        Thread.sleep(1000);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repeat = false;
                File file  = new File(PATH+"cache.png");
                try {
                    WebCamUtils.getImage(PATH+"cache.png");
                    System.out.println("缓存保存到了" + file.getAbsolutePath());
                    byte[] newValue = ArcFaceUtils.getFeatureValue(file.getAbsolutePath());

                    ResultSet rs = DBUtils.executeQuery("SELECT * FROM all_student");
                    while (rs.next()) {
                            id = rs.getString("student_id");
                            name = rs.getString("name");
                            sex = rs.getString("sex");
                            college = rs.getString("college");
                            major = rs.getString("major");
                            grade = rs.getString("grade");


                                System.out.println(name);
                                System.out.println("正在对比");
                                byte[] oldFeatures = rs.getBytes("face_feature");//特征值
                                double similarValue = ArcFaceUtils.similarValueFromDate(newValue, oldFeatures);
                                if (similarValue > 0.75) {
                                    DBUtils.close();
                                    ifSuccess = true;
                                    name_text.setText(name);
                                    college_text.setText(college);
                                    major_text.setText(major);
                                    grade_text.setText(grade);
                                    stu_id_text.setText(id);
                                    similar_text.setText(similarValue + "");

                                    if (!map.containsKey(id)){
                                        map.put(id,1);
                                    }else {
                                        int count = map.get(id);
                                        map.put(id,++count);
                                    }

                                    if (map.get(id)>1){
                                            ifSuccess = false;
                                            repeat = true;
                                            break;
                                    }
                                    System.out.println("匹配成功");
                                    break;
                                } else {
                                    System.out.println("匹配失败");
                                }
                    }

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }finally {
                    DBUtils.close();//在这里关闭资源
                }

                System.out.println("匹配状态 "+ifSuccess);
                System.out.println("是否重复 "+repeat);
                if (repeat){
                    Platform.runLater(new Runnable() {//更新UI线程需要放到Platform.runLater里面
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            ImageView alert_view = new ImageView(new Image(getClass().getResourceAsStream("/icon/repeat.png")));
                            alert.setHeight(80);
                            alert.setWidth(100);
                            alert_view.setFitWidth(100);
                            alert_view.setFitHeight(80);
                            alert.setGraphic(alert_view);
                            alert.setTitle("重复签到");
                            alert.setContentText("请勿重复签到哦~");
                            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                                alert.setResult(ButtonType.CANCEL); // 关闭对话框
                            }));
                            timeline.setCycleCount(1); // 设置时间线只执行一次
                            timeline.play(); // 启动时间线
                            alert.show();
                        }
                    });
                }
                if (!repeat && ifSuccess){
                    // 获取当前时间
                    LocalDateTime now = LocalDateTime.now();
                    // 格式化时间
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    log_time = now.format(formatter);
//                            Log log = new Log(id,name,sex,college,major,grade,log_time);
                    String[] params = {id,name,sex,college,major,grade,log_time,"正常"};

                    int count = DBUtils.executeUpdate("insert into log(student_id, name, sex, college, major, grade, log_time,status) values(?,?,?,?,?,?,?,?)",params);
                    if (count!=0){
                        Platform.runLater(new Runnable() {//更新UI线程需要放到Platform.runLater里面
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                ImageView alert_view = new ImageView(new Image(getClass().getResourceAsStream("/icon/smile.png")));
                                alert.setGraphic(alert_view);
                                alert_view.setFitHeight(80);
                                alert_view.setFitWidth(100);
                                alert.setContentText("签到成功");
                                alert.setTitle("已签到");
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                                    alert.setResult(ButtonType.CANCEL); // 关闭对话框
                                }));
                                timeline.setCycleCount(1); // 设置时间线只执行一次
                                timeline.play(); // 启动时间线
                                alert.show();
                            }
                        });
                    }
                    repeat = false;
                }
                System.out.println("===========================================");
            }

        }, 0, 5000);


        GridPane gridPane = new GridPane();
        gridPane.add(name_label,1,1);
        gridPane.add(college_label,1,2);
        gridPane.add(major_label,1,3);
        gridPane.add(grade_label,1,4);
        gridPane.add(stu_id_label,1,5);
        gridPane.add(similar_label,1,6);

        gridPane.add(name_text,2,1);
        gridPane.add(college_text,2,2);
        gridPane.add(major_text,2,3);
        gridPane.add(grade_text,2,4);
        gridPane.add(stu_id_text,2,5);
        gridPane.add(similar_text,2,6);
        this.setCenter(gridPane);

    }
}


//数据库查询语句

// SQLite连接串，指向本地的test.db文件
//                    String url = "jdbc:sqlite:test.db";
//                    Connection conn = null;
//                    Statement stmt = null;
//                    ResultSet rs = null;
//                    try {
//                        // 加载SQLite驱动
//                        Class.forName("org.sqlite.JDBC");
//                        // 建立连接
//                        conn = DriverManager.getConnection(url);
//                        // 创建Statement对象
//                        stmt = conn.createStatement();
//                        // 执行SELECT语句
//                        rs = stmt.executeQuery("SELECT * FROM all_student");
//                        // 处理查询结果
//                        while (rs.next()) {
//                            id = rs.getString("student_id");
//                            name = rs.getString("name");
//                            sex = rs.getString("sex");
//                            college = rs.getString("college");
//                            major = rs.getString("major");
//                            grade = rs.getString("grade");
//
//                            System.out.println(name);
//                            System.out.println("正在对比");
//                        byte[] oldFeatures = rs.getBytes("face_feature");//特征值
//                        double similarValue = ArcFaceUtils.similarValueFromDate(newValue,oldFeatures);
//                        if (similarValue > 0.75){
//                            DBUtils.close();
//                            ifSuccess = true;
//                            name_text.setText(name);
//                            System.out.println("匹配成功");
//                            break;
//                        }else {
//                            System.out.println("匹配失败");
//                        }
//
//                        }
//                    } catch (ClassNotFoundException | SQLException e) {
//                        e.printStackTrace();
//                    } finally {
//                        // 关闭ResultSet、Statement和Connection对象
//                        if (rs != null) {
//                            try {
//                                rs.close();
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (stmt != null) {
//                            try {
//                                stmt.close();
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (conn != null) {
//                            try {
//                                conn.close();
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
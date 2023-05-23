package view;

import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.sqlite.core.DB;
import pojo.Student;
import utils.ArcFaceUtils;
import utils.DBUtils;
import utils.WebCamUtils;

import java.io.File;
import java.io.IOException;


/**
 * 注册面板
 */
public class RegisterPane extends AnchorPane {
    //保存图片的路径
    public final static String PATH = "D:\\AAA\\face_db\\";

    static {
        File file = new File(PATH);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    //属性值
    private static String stu_id;
    private static String name;
    private static String sex;
    private static String college="";
    private static String major="";
    private static String grade="";
    private static byte[] face_feature;

    //后期可以导入
    private String[] colleges= {"软件学院","农学院","园艺学院"};//山西农业大学学院
    private String[] soft_college= {"数据科学与大数据技术","数字媒体艺术","智能科学与技术","软件工程"};//软件学院专业
    private String[] soft_bigdata= {"大数据2101班","大数据2102班","大数据2103班","大数据2104班"};//软件学院专业



    //UI组件
    private static Label student_id_label;
    private static TextField student_id;
    private static Label student_name_label;
    private static TextField student_name;
    private static Label student_sex_label;
    private static ComboBox<String> student_sex = new ComboBox();
    private static Label student_college_label;
    private static ComboBox<String> student_college = new ComboBox<>();
    private static Label student_major_label;
    private static ComboBox<String> student_major=new ComboBox<>();
    private static Label student_class_label;
    private static ComboBox<String> student_grade = new ComboBox<>();
    private static Label ImgSign;
    public static Button openCam=new Button("打开摄像头");
    public static Button closeCam=new Button("关闭摄像头");
    public static Button capture=new Button("关闭摄像头");
    public static Button sign=new Button("录入人脸");

    public static Button save=new Button("保存数据");



    public RegisterPane(){
        //添加Form面板
        Form form = new Form();
        this.getChildren().add(form);
        AnchorPane.setTopAnchor(form,20.0);
        AnchorPane.setLeftAnchor(form,20.0);

        ImgSign = new Label("人脸录入");
        Image image = new Image("cam.jpeg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        sign = new Button("录入");
        capture = new Button("拍照");
        AnchorPane.setLeftAnchor(ImgSign,450.0);
        AnchorPane.setTopAnchor(ImgSign,5.0);
        AnchorPane.setTopAnchor(sign,550.0);
        AnchorPane.setLeftAnchor(sign,450.0);
        AnchorPane.setTopAnchor(openCam,550.0);
        AnchorPane.setLeftAnchor(openCam,500.0);
        AnchorPane.setTopAnchor(capture,550.0);
        AnchorPane.setLeftAnchor(capture,600.0);
        AnchorPane.setTopAnchor(closeCam,550.0);
        AnchorPane.setLeftAnchor(closeCam,650.0);
        AnchorPane.setLeftAnchor(imageView,450.0);
        AnchorPane.setTopAnchor(imageView,50.0);

        openCam.setOnAction(event->{
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(WebCamUtils.getCamera());
            this.getChildren().add(swingNode);
            AnchorPane.setTopAnchor(swingNode,50.0);
            AnchorPane.setLeftAnchor(swingNode,450.0);
        });
        closeCam.setOnAction(event->{
            WebCamUtils.closeCamera();
        });

        this.getChildren().addAll(ImgSign,imageView,openCam,capture,closeCam,sign);

        capture.setOnAction(event->{
            stu_id = student_id.getText();
            name = student_name.getText();
            if (!check()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("请先将信息填写完整");
                alert.show();
            }else{
            try {
                //下载图片到本地
                WebCamUtils.getImage(PATH+stu_id+".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(PATH+stu_id+".png");
            if (file.exists()){
                Image pic = new Image("file:/"+PATH+stu_id+".png");
                ImageView capRes = new ImageView(pic);
                capRes.setFitHeight(pic.getHeight()/2);
                capRes.setFitWidth(pic.getWidth()/2);
                AnchorPane.setTopAnchor(capRes,600.0);
                AnchorPane.setLeftAnchor(capRes,600.0);
                this.getChildren().add(capRes);
                System.out.println("成功下载到本地 ="+file.getAbsolutePath());
            }
            }
        });
        sign.setOnAction(event->{
            File file = new File(PATH+stu_id+".png");
            if (!file.exists()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("请拍照");
                alert.setContentText("请先拍照再录入");
                alert.show();
            }

            name = student_name.getText();
            face_feature = ArcFaceUtils.getFeatureValue(PATH+stu_id+".png");

            Student student = new Student(stu_id, name, sex, college, major, grade, face_feature);
            System.out.println(student.toString());

            //判断是否有空
            if (!check()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("信息不完整");
                alert.setContentText("所填信息不完整,请检查后重新提交");
                alert.show();
            }else {
                String sql = "insert into all_student(student_id, name, sex, college, major, grade, face_feature) values(?,?,?,?,?,?,?)";
                String[] params = {student.getId(),student.getName(),student.getSex(),student.getCollege(),student.getMajor(),student.getGrade()};
                //录入到全校学生数据库表中
                DBUtils.executeUpdate(sql,params,student.getFace_feature());
                //录入到各个学院学生数据库表
                if(college.equals("软件学院")){
                    String sql2 = "insert into soft_college(student_id, name, sex, college, major, grade, face_feature) values(?,?,?,?,?,?,?)";
                    //录入到软件学院学生数据库
                    DBUtils.executeUpdate(sql2,params,student.getFace_feature());
                }else if(college.equals("农学院")){
                    String sql2 = "insert into agriculture_college(student_id, name, sex, college, major, grade, face_feature) values(?,?,?,?,?,?,?)";
                    //录入到软件学院学生数据库
                    DBUtils.executeUpdate(sql2,params,student.getFace_feature());
                }else if(college.equals("园艺学院")){
                    String sql2 = "insert into gardening_college(student_id, name, sex, college, major, grade, face_feature) values(?,?,?,?,?,?,?)";
                    //录入到软件学院学生数据库
                    DBUtils.executeUpdate(sql2,params,student.getFace_feature());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("录入成功");
                System.out.println(student.toString());
                alert.setContentText("已成功录入系统");
                alert.show();
            }
        });
    }

    /**
     * 检查用于填的表单是否完整
     * @return 完整-true
     */
    public boolean check(){
        if (stu_id ==null || name==null || sex==null || college==null || major==null || grade==null)
            return false;
        else
            return true;
    }

    class Form extends GridPane{
        public Form(){
            this.setAlignment(Pos.CENTER);
            this.setHgap(10);
            this.setVgap(10);

            student_id_label = new Label("学号");
            student_name_label = new Label("姓名");
            student_sex_label = new Label("性别");
            student_college_label = new Label("学院");
            student_major_label = new Label("专业");
            student_class_label = new Label("班级");
            //循环减少代码量
            Label[] labels = {student_id_label,student_name_label,student_sex_label,student_college_label
                    ,student_major_label,student_class_label};
            for (int i = 1; i < labels.length+1; i++) {
                this.add(labels[i-1],0,i);
            }

            student_id = new TextField();
            student_name = new TextField();
            student_sex.getItems().addAll("男","女");
            student_sex.setOnAction(event->{
                sex = student_sex.getSelectionModel().getSelectedItem();
            });
            student_college.getItems().addAll(colleges);//导入学院表
            student_college.setOnAction(event->{
                //获得用户选项-学院
                college = student_college.getSelectionModel().getSelectedItem();
                System.out.println("用户选择了"+college);
                if (college.equals("软件学院")){
                    student_major.getItems().clear();
                    student_major.getItems().addAll(soft_college);
                }else {
                    student_major.getItems().clear();
                }
            });
            student_major.setOnAction(event->{
                //获得用户选项-专业
                major = student_major.getSelectionModel().getSelectedItem();
                if (major.equals("数据科学与大数据技术")){
                    student_grade.getItems().clear();
                    student_grade.getItems().addAll(soft_bigdata);
                    System.out.println("用户选择了"+major);
                }else {
                    student_grade.getItems().clear();
                }
            });

            student_grade.setOnAction(event->{
                //获得用户选项-班级
                grade = student_grade.getSelectionModel().getSelectedItem();
                System.out.println("用户选择了"+grade);
            });

            Parent[] parents = {student_id,student_name,student_sex,student_college,student_major,student_grade};
            for (int i = 1; i < parents.length+1; i++) {
                this.add(parents[i-1],1,i);
            }


        }
    }
}


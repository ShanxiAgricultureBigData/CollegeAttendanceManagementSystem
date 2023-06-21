package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ArcFaceUtils;
import utils.DBUtils;
import utils.JFreeUtils;
import utils.Poi_03_Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class WorkPane extends BorderPane{

    private static TreeItem<String> treeRoot = new TreeItem<>("系统管理");
    private static TreeItem<String> treeItem1 = new TreeItem<>("班级管理");
    private static TreeItem<String> treeItem2 = new TreeItem<>("课表管理");
    private static TreeItem<String> treeItem3 = new TreeItem<>("考勤记录");
    private static TreeItem<String> treeItem4 = new TreeItem<>("考勤分析");
    private static TreeItem<String> treeItem5 = new TreeItem<>("上传到服务器");
    private static TreeItem<String> treeItem6 = new TreeItem<>("配置设置");
    private static TreeItem<String> treeItem7 = new TreeItem<>("退出登录");
    private static MultipleSelectionModel<TreeItem<String>> tvSelModel;

    public static TreeItem<String> getTreeRoot() {
        return treeRoot;
    }

    public void setSysPage(){
        InputStream inputStream = getClass().getResourceAsStream("/sxau.jpg");
        Image bg = new Image(inputStream);
        ImageView view = new ImageView(bg);
        this.setCenter(view);
    }
    //考勤记录面板
    public void setLog() throws Exception {
        this.setCenter(new LogTablePane());
    }
    //上传到服务器面板
    public void setUpDate(){
        this.setCenter(new UpDateToServerPane());
    }
    public void setGrade() throws Exception {
        this.setCenter(new ClassManagerPane());
    }
    //课表管理
    public void setLessonTable(){
        this.setCenter(new LessonManagerPane());
    }
    //配置设置
    public void setConfig(){
        this.setCenter(new ConfigPane());
    }
    //考勤分析
    public void setChart() throws SQLException {
        this.setCenter(new ChartPane());
    }

    public WorkPane(){

        InputStream inputStream = getClass().getResourceAsStream("/au.png");
        ImageView bg = new ImageView(new Image(inputStream));
        this.setCenter(bg);
        this.getStylesheets().add("workpane.css");

        treeRoot.getChildren().addAll(treeItem1,treeItem2,treeItem3,treeItem4,treeItem5,treeItem6,treeItem7);
        TreeView<String> treeView = new TreeView<>(treeRoot);
        treeView.getStyleClass().add("my-tree-view");
        //设置图标
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/icon/sys.png");
//        ImageView icon = new ImageView(new Image(inputStream));
//        icon.setFitHeight(12);
//        icon.setFitWidth(12);
//        treeRoot.setGraphic(icon);

        InputStream ins = getClass().getResourceAsStream("/icon/sys.png");
        Image image = new Image(ins);
        ImageView icon = new ImageView(image);
        icon.setFitWidth(13);
        icon.setFitHeight(13);
        treeRoot.setGraphic(icon);

        InputStream ins1 = getClass().getResourceAsStream("/icon/myclass.png");
        Image image1 = new Image(ins1);
        ImageView icon1 = new ImageView(image1);
        icon1.setFitWidth(13);
        icon1.setFitHeight(13);
        treeItem1.setGraphic(icon1);

        InputStream ins2 = getClass().getResourceAsStream("/icon/lesson.png");
        ImageView icon2 = new ImageView(new Image(ins2));
        icon2.setFitWidth(13);
        icon2.setFitHeight(13);
        treeItem2.setGraphic(icon2);

        InputStream ins3 = getClass().getResourceAsStream("/icon/log.png");
        ImageView icon3 = new ImageView(new Image(ins3));
        icon3.setFitWidth(13);
        icon3.setFitHeight(13);
        treeItem3.setGraphic(icon3);

        InputStream ins4 = getClass().getResourceAsStream("/icon/fenxi.png");
        ImageView icon4 = new ImageView(new Image(ins4));
        icon4.setFitWidth(13);
        icon4.setFitHeight(13);
        treeItem4.setGraphic(icon4);

        InputStream ins5 = getClass().getResourceAsStream("/icon/up.png");
        ImageView icon5 = new ImageView(new Image(ins5));
        icon5.setFitWidth(13);
        icon5.setFitHeight(13);
        treeItem5.setGraphic(icon5);

        InputStream ins6 = getClass().getResourceAsStream("/icon/config.png");
        ImageView icon6 = new ImageView(new Image(ins6));
        icon6.setFitWidth(13);
        icon6.setFitHeight(13);
        treeItem6.setGraphic(icon6);

        InputStream ins7 = getClass().getResourceAsStream("/icon/exit.png");
        ImageView icon7 = new ImageView(new Image(ins7));
        icon7.setFitHeight(13);
        icon7.setFitWidth(13);
        treeItem7.setGraphic(icon7);


        //设置TreeView的选择模式
        tvSelModel = treeView.getSelectionModel();


        tvSelModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> oldValue, TreeItem<String> newValue) {
                //显示用户的选择以及子树路径
                if(newValue != null){
                    //构造入口路径和选择的条目
                    String path = newValue.getValue();
                    TreeItem<String> tmp = newValue.getParent();
                    if (newValue.getValue().equals("班级管理")){
                        try {
                            setGrade();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if (newValue.getValue().equals("考勤记录")){
                        try {
                            setLog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if (newValue.getValue().equals("课表管理")){
                        setLessonTable();
                    }
                    else if (newValue.getValue().equals("上传到服务器")){
                        setUpDate();
                    }else if (newValue.getValue().equals("配置设置")){
                        setConfig();
                    }else if (newValue.getValue().equals("考勤分析")){
                        try {
                            setChart();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (newValue.getValue().equals("退出登录")){
                        Scene scene = treeView.getScene();
                        Stage stage = (Stage) scene.getWindow();
                        double height = scene.getWindow().getHeight();
                        double width = scene.getWindow().getWidth();
                        stage.setHeight(height);
                        stage.setWidth(width);
                        try {
                            Scene scene1 = new Scene(new LoginPane());
                            stage.setScene(scene1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    while (tmp != null){
                        path = tmp.getValue() + " -> "+path;
                        tmp =  tmp.getParent();
                    }
                    //显示用户选择的条目以及路径
                    System.out.println("Selection is "+ newValue.getValue() + "\nComplete path is " + path);
                }

            }


        });

        //设置图标
//        treeItem1.setGraphic(new ImageView("index.png"));

        this.setLeft(treeView);
        this.setPrefSize(1042,700);
    }

    public static MultipleSelectionModel<TreeItem<String>> getTvSelModel() {
        return tvSelModel;
    }

    public static void setTvSelModel(MultipleSelectionModel<TreeItem<String>> tvSelModel) {
        WorkPane.tvSelModel = tvSelModel;
    }
}

/**
 * 签到记录面板
 */
class LogTablePane extends BorderPane{
    private JScrollPane jScrollPane;
    private JTable table;
    private Button save,delete,add,flush;
    //默认表格样式
    private DefaultTableModel model;

    public LogTablePane() throws Exception {
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

        model = new DefaultTableModel();
        table = new JTable(model);

        //将表格放入滚动面板
        jScrollPane = new JScrollPane(table);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(jScrollPane);
        this.setCenter(swingNode);

        HBox hBox = new HBox();
        save = new Button("保存");
        add = new Button("添加");
        delete = new Button("删除");
        flush = new Button("刷新");
        hBox.getChildren().addAll(save,add,delete,flush);
        hBox.setPadding(new Insets(10,20,10,10));
        hBox.setSpacing(20);

        save.setVisible(false);
        this.setBottom(hBox);

        //显示数据
        showData();

        add.setOnAction(event->{
            addData();
        });

        delete.setOnAction(event -> {
            deleteData();
        });

        save.setOnAction(event->{
            saveDate();
        });

    }

    public void showData() throws SQLException {
        ResultSet rs = null;
        try {
            String sql = "select * from log";
             rs = DBUtils.executeQuery(sql);//用完记得释放资源
            //获取列名
            ResultSetMetaData rsmt = rs.getMetaData();
            //获取列数
            int cols = 0;
            cols = rsmt.getColumnCount();
            //存放列名
            Vector<String> titles = new Vector<>();
            //存放列名
            for(int i=1;i<=cols;i++){
                titles.add(rsmt.getColumnLabel(i));
            }
            //存放行数据
            //存放集合（行数据）的集合
            Vector<Vector<String>> data = new Vector<Vector<String>>();
            //记录行数
            int rowCount=0;
            while (rs.next()){
                rowCount++;
                //每一行存到一个集合中
                Vector<String> rowData = new Vector<>();
                for (int i = 0; i < cols; i++) {
                    rowData.add(rs.getString(i+1));
                }
                data.add(rowData);
            }
            if (rowCount==0){
                //如果行数为0-即没有数据,只将title放到表格当中
                model.setDataVector(null,titles);
            }else {
                //否则有数据的话,将数据展示到JTable中
                model.setDataVector(data,titles);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close();
            rs.close();
        }

    }

    private void addData() {
        // TODO Auto-generated method stub
        int rowCount = model.getRowCount();
        //最好使用Object[]
        model.insertRow(rowCount, new String[]{"0","0","0","0","0","0","0","0","0"});
        add.setVisible(false);
        save.setVisible(true);
    }


    private void saveDate() {
        // TODO Auto-generated method stub
        int rowCount = table.getRowCount()-1;
        //获取自己填写的数据
        String stu_id = table.getValueAt(rowCount, 1).toString();
        String name = table.getValueAt(rowCount, 2).toString();
        String sex = table.getValueAt(rowCount, 3).toString();
        String college = table.getValueAt(rowCount, 4).toString();
        String major = table.getValueAt(rowCount, 5).toString();
        String grade = table.getValueAt(rowCount, 6).toString();
        String log_time = table.getValueAt(rowCount, 7).toString();
        String status = table.getValueAt(rowCount, 8).toString();

        String[] params = {stu_id, name,sex ,college, major, grade,log_time,status};
        for (String param : params) {
            System.out.println(param);
        }
        try{
            int count = DBUtils.executeUpdate("insert into log(student_id, name, sex, college, major, grade, log_time,status) values(?,?,?,?,?,?,?,?)",params);
            showData();
            add.setVisible(true);
            save.setVisible(false);

            if(count == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("保存数据");
                alert.setContentText("修改成功");
                alert.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("保存失败");
                alert.setContentText("保存失败");
                alert.show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.close();
        }

    }

    private void deleteData(){
            int index[] = table.getSelectedRows();

            if(index==null){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("未选择数据");
                alert1.setContentText("请选择要删除的行!");
                alert1.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/icon/warning.png")));
                alert.setGraphic(imageView);
                alert.setTitle("确认删除");
                alert.setContentText("请确认是否删除 ");
                ButtonType buttonTypeYes = new ButtonType("确认删除");
                ButtonType buttonTypeNo = new ButtonType("取消操作");
                //将按钮添加到对话框中
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                //获取按钮并添加监听器
                Button yesButton = (Button) alert.getDialogPane().lookupButton(buttonTypeYes);
                yesButton.setOnAction(event -> {
                    try {
                        String sql = "delete from log where id=?";
                        String name = table.getValueAt(index[0], 0).toString();
                        System.out.println("学生"+name+"即将被删除");
                        int count = DBUtils.executeUpdate(sql, new String[]{name});
                        showData();
                        if (count == 1) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("删除成功");
                            alert1.setContentText("删除成功!");
                            alert1.show();
                        } else {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("删除失败");
                            alert1.setContentText("删除失败!");
                            alert1.show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        DBUtils.close();
                    }
                });
                Button noButton = (Button) alert.getDialogPane().lookupButton(buttonTypeNo);
                noButton.setOnAction(event -> {
                    alert.close();
                });
                alert.show();
            }
    }
}


/**
 * 班级管理
 */
class ClassManagerPane extends BorderPane{
    private static String college="";
    private static String major="";
    private static String year="";
    private static String grade="";

    private Label college_label = new Label("学院");
    private ComboBox<String> college_list = new ComboBox<>();
    private static Label major_label = new Label("专业");
    private static ComboBox<String> student_major=new ComboBox<>();
    private static Label year_label = new Label("年级");
    private static ComboBox<String> student_year=new ComboBox<>();
    private static Label grade_label = new Label("班级");
    private static ComboBox<String> student_grade = new ComboBox<>();

    //后期可以导入
    private String[] colleges= {"软件学院"};//山西农业大学学院
    private String[] soft_college= {"数据科学与大数据技术","数字媒体艺术","智能科学与技术","软件工程"};//软件学院专业
    private String[] year_school = {"2021级"};
    private String[] soft_bigdata= {"大数据2101班","大数据2102班","大数据2103班","大数据2104班"};//软件学院专业
    private String[] soft_soft= {"软件2101Z班","软件2102Z班","软件2103Z班","软件2104Z班","软件2105Z班","软件2106Z班","软件2107Z班","软件2108Z班","软件2109Z班","软件2110Z班","软件2111Z班","软件2112Z班","软件2113班"};//软件学院专业
    private String[] zhi_soft = {"智能2101班","智能2102班"};
    private String[] shu_soft = {"数媒2101班"};

    private static Button search = new Button("查询该班级学生注册名单");

    //默认表格样式
    private DefaultTableModel model;
    private JScrollPane jScrollPane;
    private JTable table;

    public ClassManagerPane() throws Exception {

        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

        model = new DefaultTableModel();
        table = new JTable(model);

        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(college_label,college_list,year_label,student_year,major_label,student_major,grade_label,student_grade,search);
        this.setTop(gridPane);

        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(5,20,5,20));
        GridPane.setConstraints(college_label,1,1);
        GridPane.setConstraints(college_list,2,1);
        GridPane.setConstraints(year_label,1,2);
        GridPane.setConstraints(student_year,2,2);
        GridPane.setConstraints(major_label,1,3);
        GridPane.setConstraints(student_major,2,3);
        GridPane.setConstraints(grade_label,1,4);
        GridPane.setConstraints(student_grade,2,4);
        /**
         * 子节点的列索引（从 0 开始计数），表示子节点位于网格布局的第几列。
         * 子节点的行索引（从 0 开始计数），表示子节点位于网格布局的第几行。
         * 子节点所占的列数，表示子节点在水平方向上占据了几个网格列。
         * 子节点所占的行数，表示子节点在垂直方向上占据了几个网格行。
         */
        GridPane.setConstraints(search,1,5,2,1);

        search.setOnAction(event->{
            if (!grade.equals("") && college.equals("软件学院")){
                String sql = "select id,student_id, name, sex,college,major,grade from soft_college where grade is '"+grade+"';";
                //将表格放入滚动面板
                jScrollPane = new JScrollPane(table);
                SwingNode swingNode = new SwingNode();
                swingNode.setContent(jScrollPane);
                this.setCenter(swingNode);

                try {
                    showData(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (!grade.equals("大数据2102班")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("该班级未录入");
                    alert.setContentText("该班级未录入,请联系管理员添加班级信息\n已录入班级:大数据2102班");
                    InputStream ins = getClass().getResourceAsStream("/icon/warning.png");
                    ImageView alert_view = new ImageView(new Image(ins));
                    alert.setGraphic(alert_view);
                    alert.show();
                }
            }
        });




        college_list.getItems().addAll(colleges);//导入学院表
        college_list.setOnAction(event->{
            //获得用户选项-学院
            college = college_list.getSelectionModel().getSelectedItem();
            System.out.println("用户选择了"+college);
            if (college.equals("软件学院")){
//                student_major.getItems().clear();
                student_year.getItems().clear();
                student_year.getItems().addAll(year_school);
//                student_major.getItems().addAll(soft_college);
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("请联系管理员添加");
                alert.setTitle("提示");
                alert.show();
            }
        });
        student_year.setOnAction(event->{
            year = student_year.getSelectionModel().getSelectedItem();
            if(year.equals("2021级")){
                student_major.getItems().clear();
                student_major.getItems().addAll(soft_college);
            }
        });
        student_major.setOnAction(event->{
            //获得用户选项-专业
            major = student_major.getSelectionModel().getSelectedItem();
            if (major.equals("数据科学与大数据技术")){
                student_grade.getItems().clear();
                student_grade.getItems().addAll(soft_bigdata);
                System.out.println("用户选择了"+major);
            }else if (major.equals("软件工程")){
              student_grade.getItems().clear();
              student_grade.getItems().addAll(soft_soft);
                System.out.println("用户选择了"+major);
            }else if (major.equals("数字媒体艺术")){
                student_grade.getItems().clear();
                student_grade.getItems().addAll(shu_soft);
                System.out.println("用户选择了"+major);
            }else if (major.equals("智能科学与技术")){
                student_grade.getItems().clear();
                student_grade.getItems().addAll(zhi_soft);
                System.out.println("用户选择了"+major);
            }
            else {
                student_grade.getItems().clear();
            }
        });

        student_grade.setOnAction(event->{
            //获得用户选项-班级
            grade = student_grade.getSelectionModel().getSelectedItem();
            System.out.println("用户选择了"+grade);
        });
    }

    public void showData(String sql) throws SQLException {
        ResultSet rs = null;
        try {
            rs = DBUtils.executeQuery(sql);//用完记得释放资源
            //获取列名
            ResultSetMetaData rsmt = rs.getMetaData();
            //获取列数
            int cols = 0;
            cols = rsmt.getColumnCount();
            //存放列名
            Vector<String> titles = new Vector<>();
            //存放列名
            for(int i=1;i<=cols;i++){
                titles.add(rsmt.getColumnLabel(i));
            }
            //存放行数据
            //存放集合（行数据）的集合
            Vector<Vector<String>> data = new Vector<Vector<String>>();
            //记录行数
            int rowCount=0;
            while (rs.next()){
                rowCount++;
                //每一行存到一个集合中
                Vector<String> rowData = new Vector<>();
                for (int i = 0; i < cols; i++) {
                    rowData.add(rs.getString(i+1));
                }
                data.add(rowData);
            }
            if (rowCount==0){
                //如果行数为0-即没有数据,只将title放到表格当中
                model.setDataVector(null,titles);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("未注册");
                ImageView alert_view = new ImageView(new Image(getClass().getResourceAsStream("/icon/repeat.png")));
                alert.setWidth(100);
                alert.setHeight(80);
                alert.setGraphic(alert_view);
                alert.setContentText("该班级未注册,请联系管理员注册班级学生信息");
                alert.show();
            }else {
                //否则有数据的话,将数据展示到JTable中
                model.setDataVector(data,titles);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close();
            rs.close();
        }

    }
}

/**
 * 课表管理
 */
class LessonManagerPane extends BorderPane {
    private Label stu_id_label = new Label("教师工号");
    private TextField teacher_id = new TextField();
    private Label choice_table = new Label("导入课表");
    private Button choose = new Button("从外部导入课表(仅支持xls格式)");

    private Label search_label = new Label("查询课表");
    private Button search = new Button("通过工号查询课表");

    private Button update = new Button("保存到数据库");;

    //默认表格样式
    private DefaultTableModel model;
    private JScrollPane jScrollPane;
    private JTable table;

//    static {
//        try {
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private final FileChooser fileChooser = new FileChooser();

    private String[][] dat;


    public LessonManagerPane(){
        model = new DefaultTableModel();
        table = new JTable(model);

        //将表格放入滚动面板
        jScrollPane = new JScrollPane(table);

        //北面
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5,10,5,20));//外边距
        gridPane.setHgap(10);//内边距
        gridPane.setVgap(15);
        this.setTop(gridPane);
        gridPane.getChildren().addAll(stu_id_label,teacher_id,choice_table,choose,search_label,search);
        GridPane.setConstraints(stu_id_label,1,2);
        GridPane.setConstraints(teacher_id,2,2);
        GridPane.setConstraints(choice_table,1,3);
        GridPane.setConstraints(choose,2,3);
        GridPane.setConstraints(search_label,1,4);
        GridPane.setConstraints(search,2,4);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS","*.xls"),
                new FileChooser.ExtensionFilter("XLSX","*.xlsx")
        );
        choose.setOnAction(event -> {
            update.setVisible(true);

            Stage stage = (Stage) getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);//在当前窗口打开文件选择器
            if (file != null){
                try {
//                    FileInputStream inputStream = new FileInputStream(file);
//                    BufferedInputStream stream = new BufferedInputStream(inputStream);
//                    String fileName = file.getName();
                    String filePath = file.getAbsolutePath();
                    System.out.println("文件路径 = "+filePath);
                    Poi_03_Utils.testCellType(filePath);
                    String[] titles = Poi_03_Utils.getTitles();
                    String[][] data = Poi_03_Utils.getData();
                    dat = new String[6][8];//data.length是行数
                    for (int i = 3; i < 9; i++) {
                        for (int j = 0; j < 8; j++) {
                            dat[i-3][j] = data[i][j];
                        }
                    }
                    System.out.println("行数 = "+dat.length);
                    System.out.println("列数 = "+dat[0].length);

                    SwingNode swingNode = new SwingNode();
                    JTable jTable = new JTable(dat,titles);
                    jTable.setRowHeight(1,50);
                    for (int i = 0; i < 6; i++) {
                        jTable.setRowHeight(i,100);
                    }
                    JScrollPane jScrollPane = new JScrollPane(jTable);
                    swingNode.setContent(jScrollPane);
                    this.setCenter(swingNode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        search.setOnAction(event->{
            update.setVisible(false);
            if (!teacher_id.getText().equals("")) {
                String table_name = "lesson"+teacher_id.getText();
                try {
                    String sql = "select * from "+table_name+"";
                    System.out.println("SQL = "+sql);
                    //将表格放入滚动面板
                    jScrollPane = new JScrollPane(table);
                    SwingNode swingNode = new SwingNode();
                    swingNode.setContent(jScrollPane);
                    this.setCenter(swingNode);

                    showData(sql);

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    DBUtils.close();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("请将表单填写完整");
                alert.setTitle("数据不完整");
                alert.show();
            }
        });
        //南面
        this.setBottom(update);
        update.setOnAction(event->{
            if (!teacher_id.getText().equals("")) {
                String table_name = "lesson"+teacher_id.getText();
                try {
                    if (ifTableExists(table_name)) {//如果不存在
                        //创建该表
                        DBUtils.createTeacherLesson(teacher_id.getText());

                        String[][] params = getLesson(dat);
                        System.out.println("参数长度=" + params.length);
                        for (int i = 0; i < 6; i++) {
                            String sql = "insert into " + table_name + "(monday,tuesday,wednesday,thursday,friday ,saturday,sunday) values(?,?,?,?,?,?,?)";
                            int count = DBUtils.executeUpdate(sql, params[i]);
                            if (count != 0) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("录入成功");
                                alert.setContentText("成功保存到系统数据库");
                                alert.show();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("录入失败");
                                alert.setTitle("录入失败");
                                alert.show();
                            }
                        }
                }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("已存在");
                        alert.setContentText("该课表已存在,是否覆盖原课表?");
                        ButtonType buttonTypeYes = new ButtonType("确认覆盖");
                        ButtonType buttonTypeNo = new ButtonType("取消覆盖");
                        //将按钮添加到对话框中
                        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                        //获取按钮并添加监听器
                        Button yesButton = (Button) alert.getDialogPane().lookupButton(buttonTypeYes);
                        yesButton.setOnAction(event1 -> {
                            //删除原数据
                            String del_sql = "drop table "+table_name;
                            int c = DBUtils.executeUpdate(del_sql);
                            if (c!=0){
                                System.out.println("删除成功");
                            }
                            try {
                                //创建该表
                                DBUtils.createTeacherLesson(teacher_id.getText());

                                String[][] params = getLesson(dat);
                                System.out.println("参数长度=" + params.length);
                                for (int i = 0; i < 6; i++) {
                                    String sql = "insert into " + table_name + "(monday,tuesday,wednesday,thursday,friday ,saturday,sunday) values(?,?,?,?,?,?,?)";
                                    int count = DBUtils.executeUpdate(sql, params[i]);
                                    if (count != 0) {
                                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert1.setTitle("录入成功");
                                        alert1.setContentText("成功保存到系统数据库");
                                        alert1.show();
                                    } else {
                                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                                        alert1.setContentText("录入失败");
                                        alert1.setTitle("录入失败");
                                        alert1.show();
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                DBUtils.close();
                            }
                        });
                        Button noButton = (Button) alert.getDialogPane().lookupButton(buttonTypeNo);
                        noButton.setOnAction(event1 -> {
                            alert.close();
                        });
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("请将表单填写完整");
                alert.setTitle("数据不完整");
                alert.show();
            }
        });
    }

    /**
     * 判断数据库是否已经存在该表
     * @param table_name 表名
     * @return true-已存在 -false-不存在
     */
    public boolean ifTableExists(String table_name){
        Connection conn = null;
        boolean res = false;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            ResultSet rs = conn.getMetaData().getTables(null, null, table_name, null);
            if (!rs.next()) {
                System.out.println("该表不存在！");
            } else {
                res = true;
                System.out.println("该表已经存在！");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 获得每一行的单元格参数插入到sql语句，提交到数据库中
     * @param data 二维数组
     * @return 返回课表数组参数
     */
    public String[][] getLesson(String[][] data){
        String[][] params = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < 8; j++) {
                params[i][j-1] = data[i][j];
            }
        }
        return params;
    }

    public void showData(String sql) throws SQLException {
        ResultSet rs = null;
        try {
            rs = DBUtils.executeQuery(sql);//用完记得释放资源

            //获取列名
            ResultSetMetaData rsmt = rs.getMetaData();
            //获取列数
            int cols = 0;
            cols = rsmt.getColumnCount();
            if (cols==0){//如果列数为0,说明没有这张表
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setContentText("未找到该课表,请先确认已导入该课表");
                alert.show();
            }
            //存放列名
            Vector<String> titles = new Vector<>();
            //存放列名
            for(int i=1;i<=cols;i++){
                titles.add(rsmt.getColumnLabel(i));
            }
            //存放行数据
            //存放集合（行数据）的集合
            Vector<Vector<String>> data = new Vector<Vector<String>>();
            //记录行数
            int rowCount=0;
            while (rs.next()){
                rowCount++;
                //每一行存到一个集合中
                Vector<String> rowData = new Vector<>();
                for (int i = 0; i < cols; i++) {
                    rowData.add(rs.getString(i+1));
                }
                data.add(rowData);
            }
            if (rowCount==0){
                //如果行数为0-即没有数据,只将title放到表格当中
                model.setDataVector(null,titles);
            }else {
                //否则有数据的话,将数据展示到JTable中
                table.setRowHeight(100);
                model.setDataVector(data,titles);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close();
            rs.close();
        }

    }
}

/**
 * 上传到服务器面板
 */
class UpDateToServerPane extends Group {
    public UpDateToServerPane(){
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("coding.png");
//        ImageView icon = new ImageView(new Image(inputStream));
        // 读取资源文件
        InputStream inputStream = getClass().getResourceAsStream("/image/coding.png");
        Image image = new Image(inputStream);
        // 创建ImageView并显示图片
        ImageView imageView = new ImageView(image);
//        ImageView imageView = new ImageView(new Image("file:/D:\\IdeaProjects\\CollegeAttendanceManagementSystem\\src\\main\\resources\\coding.png"));
        imageView.setFitHeight(600);
        imageView.setFitWidth(800);
        this.getChildren().add(imageView);
    }
}

/**
 * 配置管理
 */
class ConfigPane extends GridPane{
    private Label appIdLabel = new Label("appID: ");
    private TextField appId_text = new TextField("2gaQVTSVHimFCFvJSDegHv7RwSUoSQjehBrmgpLjw7mA");
    private Label sdkKeyLabel = new Label("sdkKey: ");
    private TextField sdkKey_text = new TextField("DrbZdaswjLyHJKCGUPZeoDJwdy5ToRKzw5U7JrEP172H");

    private Button update = new Button("更新密钥");

    public ConfigPane(){

            appId_text.setFont(Font.font(12));
            appId_text.setPrefHeight(100);
            appId_text.setPrefWidth(300);

            sdkKey_text.setFont(Font.font(12));
            sdkKey_text.setPrefWidth(300);
            sdkKey_text.setPrefHeight(100);



            this.getChildren().addAll(appIdLabel,appId_text,sdkKeyLabel,sdkKey_text,update);
            GridPane.setConstraints(appIdLabel,10,20);
            GridPane.setConstraints(appId_text,20,20);
            GridPane.setConstraints(sdkKeyLabel,10,30);
            GridPane.setConstraints(sdkKey_text,20,30);
            GridPane.setConstraints(update,20,50);



        update.setOnAction(event1->{
            String appId = appId_text.getText();
            String sdkKey = sdkKey_text.getText();
            if (appId.equals("") || sdkKey.equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("警告");
                alert.setContentText("请先设置appId和adkKey的内容");
                alert.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/icon/warning.png")));
                alert.setGraphic(imageView);
                alert.setTitle("确认修改");
                alert.setContentText("请确认是管理员操作: ");
                ButtonType buttonTypeYes = new ButtonType("确认");
                ButtonType buttonTypeNo = new ButtonType("取消操作");
                //将按钮添加到对话框中
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                //获取按钮并添加监听器
                Button yesButton = (Button) alert.getDialogPane().lookupButton(buttonTypeYes);
                yesButton.setOnAction(event -> {
                    try {
                        ArcFaceUtils.setAppId(appId, sdkKey);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                Button noButton = (Button) alert.getDialogPane().lookupButton(buttonTypeNo);
                noButton.setOnAction(event -> {
                    alert.close();
                });
                alert.show();
            }
        });
    }
}



/**
 * 考勤分析
 */
class ChartPane extends BorderPane{
    private Button xyChart_btn = new Button("考勤人数统计");
    private Button pieChart_btn = new Button("考勤状态统计");
    private Button lineChart_btn = new Button("考勤人数波动");
    private WebView webView = new WebView();

    public ChartPane() throws SQLException {
        Map<String, Integer> map = sumOfLog();
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5,20,5,20));
        hBox.getChildren().addAll(xyChart_btn,pieChart_btn,lineChart_btn);
        this.setTop(hBox);

        lineChart_btn.setOnAction(event -> {

            WebEngine webEngine = webView.getEngine();
            // 启用 JavaScript 支持
            webEngine.setJavaScriptEnabled(true);

//            URL url = getClass().getResource("/PieChart.html");
            URL url = getClass().getResource("/lineChart.html");
            webEngine.load(url.toExternalForm());

            this.setCenter(webView);

            //List<List<>> 签到人数 学生 日期
//            List<List<String>> lists = new ArrayList<>();
//
//            for (String date:map.keySet()){
//                List<String> list = new ArrayList<>();
//                list.add(date);
//                list.add(map.get(date)+"");
//                lists.add(list);
//            }
//
//            Collections.sort(lists, new Comparator<List<String>>() {
//                @Override
//                public int compare(List<String> o1, List<String> o2) {
//                    return o1.get(0).compareTo(o2.get(0));
//                }
//            });
//            Stage stage = new Stage();
//            Scene scene = JFreeUtils.drawLineOfLog(lists);
//            stage.setScene(scene);
//            stage.show();
        });

        Map<String,Integer> statusMap = statusOfLog();
        pieChart_btn.setOnAction(event -> {
            WebEngine webEngine = webView.getEngine();
            // 启用 JavaScript 支持
            webEngine.setJavaScriptEnabled(true);

//            URL url = getClass().getResource("/PieChart.html");
            URL url = getClass().getResource("/PieChart.html");
            webEngine.load(url.toExternalForm());

            this.setCenter(webView);


//            Stage stage = new Stage();
//            Scene scene = JFreeUtils.drawPieChart(statusMap);
//            stage.setScene(scene);
//            stage.show();
        });
        xyChart_btn.setOnAction(event -> {

            WebEngine webEngine = webView.getEngine();
            // 启用 JavaScript 支持
            webEngine.setJavaScriptEnabled(true);

//            URL url = getClass().getResource("/PieChart.html");
            URL url = getClass().getResource("/xyChart.html");
            webEngine.load(url.toExternalForm());

            this.setCenter(webView);


            //List<List<>> 签到人数 学生 日期
            List<List<String>> lists = new ArrayList<>();

            for (String date:map.keySet()){
                List<String> list = new ArrayList<>();
                list.add(date);
                list.add(map.get(date)+"");
                lists.add(list);
            }
            Collections.sort(lists, new Comparator<List<String>>() {
                @Override
                public int compare(List<String> o1, List<String> o2) {
                    return o1.get(0).compareTo(o2.get(0));
                }
            });
            Stage stage = new Stage();
            if (lists.size()!=0){
            Scene scene = JFreeUtils.drawXYByNums(lists);
            stage.setScene(scene);
            stage.show();
            }
        });
    }

    /**
     * 签到状态统计
     */
    public Map<String,Integer> statusOfLog(){

        Map<String,Integer> map = new HashMap<>();
        try {

            String sql = "select * from log";
            ResultSet rs = DBUtils.executeQuery(sql);
            while (rs.next()) {
                String status = rs.getString("status");//签到状态
                if (!map.containsKey(status)) {
                    map.put(status, 1);
                } else {
                    int count = map.get(status);
                    map.replace(status, ++count);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.close();
        }
        return map;
    }

    /**
     * 签到人数计算
     */
    public Map<String,Integer> sumOfLog() {
        Map<String,Integer> map = new HashMap<>();

        try {

            String sql = "select * from log";
            ResultSet rs = DBUtils.executeQuery(sql);
            while (rs.next()) {
                String log_time = rs.getString("log_time").substring(6, 11);//签到日期
                if (!map.containsKey(log_time)) {
                    map.put(log_time, 1);
                } else {
                    int count = map.get(log_time);
                    map.replace(log_time, ++count);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.close();
        }

        return map;
    }
}
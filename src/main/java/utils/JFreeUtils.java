package utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.GradientPaintTransformType;
import org.jfree.chart.ui.GradientPaintTransformer;
import org.jfree.chart.ui.StandardGradientPaintTransformer;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.chart.util.ShadowGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 数据可视化展示
 */
public class JFreeUtils {
    public static void main(String[] args) {

    }

    /**
     * 考勤折线图
     */
    public static Scene drawLineOfLog(List<List<String>> lists){
        //创建数据
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (List<String> list : lists) {
            dataset.addValue(Double.parseDouble(list.get(1)),"人数",list.get(0));
        }

        // 创建JFreeChart对象
        JFreeChart chart = ChartFactory.createLineChart(
                "考勤统计", // 图标题
                "考勤人数",
                "考勤日期",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,18));
//        Plot plot = chart.getPlot();
//        // 设置背景透明度
//        plot.setBackgroundAlpha(0.1f);

        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
// 设置折线颜色
        renderer.setSeriesPaint(0, Color.RED); // 第一条折线的颜色为红色
// 设置折线标记（数据点）的形状和颜色
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2.5, -2.5, 5, 5)); // 第一条折线的数据点形状为圆形
        renderer.setSeriesShapesFilled(0, true); // 第一条折线的数据点填充为实心
        renderer.setSeriesShapesVisible(0, true); // 第一条折线的数据点可见
        renderer.setSeriesItemLabelFont(0, new Font("宋体", Font.PLAIN, 12)); // 设置折线标记的字体为宋体，12号
        renderer.setSeriesItemLabelPaint(0, Color.RED); // 设置折线标记的颜色为红色

        // 设置折线标记的字体和颜色
        renderer.setSeriesItemLabelFont(0, new Font("宋体", Font.PLAIN, 12)); // 设置折线标记的字体为宋体，12号
        renderer.setSeriesItemLabelPaint(0, Color.RED); // 设置折线标记的颜色为红色
// 设置折线标记的显示内容
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0"));
        renderer.setSeriesItemLabelGenerator(0, generator); // 设置第一条折线的标记显示相应的值
        renderer.setDefaultItemLabelsVisible(true); // 显示所有折线标记
        plot.setBackgroundAlpha(0.1f);
        // 利用fx进行显示
        ChartViewer viewer = new ChartViewer(chart);

        return new Scene(viewer);
    }

    /**
     *出勤人数统计-近7天-柱状图
     */
    public static Scene drawXYByNums(List<List<String>> lists){

        //创建数据
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (List<String> list : lists) {
            dataset.addValue(Double.parseDouble(list.get(1)),"人数",list.get(0));
        }

        // 创建JFreeChart对象
        JFreeChart chart = ChartFactory.createBarChart(
                "考勤统计", // 图标题
                "考勤人数",
                "考勤日期",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,18));

        // 获取柱状图的渲染器
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
// 设置柱子颜色
        renderer.setSeriesPaint(0, Color.PINK); // 第一组柱子的颜色为蓝色
        renderer.setSeriesPaint(1, Color.ORANGE); // 第二组柱子的颜色为橙色
// 设置柱子边框颜色和粗细
        renderer.setDrawBarOutline(true); // 绘制柱子边框
        renderer.setSeriesOutlinePaint(0, Color.BLACK); // 第一组柱子边框颜色为黑色
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f)); // 第一组柱子边框粗细为1.5像素
// 设置柱子的渐变颜色
        GradientPaintTransformer transformer = new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL);
        renderer.setGradientPaintTransformer(transformer); // 设置柱子的渐变颜色


        //设置边框
        chart.setBorderPaint(Color.BLACK);
        chart.setBorderVisible(true);
        // 利用fx进行显示
        ChartViewer viewer = new ChartViewer(chart);

        return new Scene(viewer);
    }

    /**
     * 出勤分析
     */
    public static Scene drawPieChart(Map<String,Integer> map){
        //创建数据
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String status : map.keySet()){
            dataset.setValue(status,map.get(status));
        }
        // 创建JFreeChart对象
        JFreeChart chart = ChartFactory.createPieChart(
                "考勤统计", // 图标题
                dataset, // 数据集
                false, true, true);
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,18));

//        Plot plot = chart.getPlot();
//        //设置plot的前景色透明度
//        plot.setForegroundAlpha(0.7f);
//        //设置plot的背景色透明度
//        plot.setBackgroundAlpha(0.0f);
        PiePlot plot = (PiePlot) chart.getPlot();
// 设置饼图的颜色
        plot.setSectionPaint("到勤", Color.GREEN);
        plot.setSectionPaint("迟到", Color.YELLOW);
        plot.setSectionPaint("早退", Color.ORANGE);
        plot.setSectionPaint("旷工", Color.RED);
// 设置饼图的标签字体和颜色
        plot.setLabelFont(new Font("宋体", Font.PLAIN, 12));
        plot.setLabelPaint(Color.BLACK);
// 设置饼图的标签显示格式
        String format = "{0}: ({1}人)";
        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(format);
        plot.setLabelGenerator(generator);

        // 利用fx进行显示
        ChartViewer viewer = new ChartViewer(chart);

        return new Scene(viewer);
    }
}
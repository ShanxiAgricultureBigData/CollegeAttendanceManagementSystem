package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class Poi_03_Utils {

    private static int columns;
    private static int rows;

    private static String[] titles;
    private static String[][] data;

    public void testRead03(String filePath) throws IOException {

        //获取文件流
        FileInputStream inputStream = new FileInputStream(new File(filePath));

        //1.创建一个工作簿,使用excel能操作的，它都可以操作
        Workbook workbook = new HSSFWorkbook(inputStream);
        //2.获取表
        Sheet sheet0 = workbook.getSheetAt(0);
        //3.得到行
        Row row = sheet0.getRow(1);//获取第1行
        //4.得到列
        Cell cell = row.getCell(0);//获取第一行第一列
        //以Number形式输出-可以选择格式
        //必须对应格式-number类型不可以转为String类型！！
        System.out.println(cell.getNumericCellValue());
        //关闭流资源
        inputStream.close();

    }

    public static String[] getTitles() {
        return titles;
    }

    public static String[][] getData() {
        return data;
    }

    public static void testCellType(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(filePath));

        Workbook workbook = new HSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        //读取标题内容
        Row title = sheet.getRow(2);//根据学校官网的格式，选择第三行作为表头
        if (title != null){
            columns = title.getPhysicalNumberOfCells();//列数
            titles = new String[columns];
            for (int cellNum = 0; cellNum < columns; cellNum++) {
                Cell cell = title.getCell(cellNum);
                if (cell != null){
                    titles[cellNum] = cell.getStringCellValue();
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue+" | ");
                }
            }
            System.out.println();
        }

        //读取表中的内容
        rows = sheet.getPhysicalNumberOfRows();//行数
        //表格
        data = new String[rows][columns];

        for (int rowNum = 1; rowNum < rows; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null){
                //读取行中的列
                int columns = title.getPhysicalNumberOfCells();
                for (int col = 0; col < columns; col++) {
                    System.out.print("["+(rowNum+1)+"-"+(col+1)+"]");
                    Cell cell = row.getCell(col);
                    //匹配列的数据类型
                    if (cell != null){
                        int cellType = cell.getCellType();
                        String cellValue = "";
                        switch (cellType){
                            case HSSFCell.CELL_TYPE_STRING://字符串
                                cellValue = cell.getStringCellValue();
                                data[rowNum][col] = cellValue;
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC://数字(日期、数字)
                                if (HSSFDateUtil.isCellDateFormatted(cell)){//日期
                                    System.out.print("[DATE]");
                                    Date date = cell.getDateCellValue();
                                    cellValue = new DateTime(date).toString("yyyy-MM-dd");
                                }else{
                                    System.out.print("[NUMBER]");
                                    //防止数字过长,转为String
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cellValue = cell.toString();
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK://空
                                System.out.print("[NULL]");
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN://布尔
                                System.out.print("[BOOLEAN]");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                            case HSSFCell.CELL_TYPE_ERROR:
                                System.out.print("[ERROR]");
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }
        inputStream.close();
    }
}

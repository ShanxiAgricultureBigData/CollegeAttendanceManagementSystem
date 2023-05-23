package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Poi_07_Utils {
    @Test
    public void testRead07(String filePath) throws IOException {

        //获取文件流
        FileInputStream inputStream = new FileInputStream(new File(filePath));

        //1.创建一个工作簿,使用excel能操作的，它都可以操作
        Workbook workbook = new XSSFWorkbook(inputStream);
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
}

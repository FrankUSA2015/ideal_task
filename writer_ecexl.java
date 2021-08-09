import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class writer_ecexl {

     private static String getType(Object a) {
        return a.getClass().toString();

    }

    /*
    * 判断得到的cell是否为空。
    * */
    private static String get_cell(Cell cell){
         if(cell!=null){
             return cell.toString();
         }else{
             return "";
         }
    }
    /*
    * 读取告警字段.xlsx文件。
    * */
     public List<Map<String,String>> readExcel(String sourceFilePath,int sheet_num) throws IOException {
        Workbook workbook = null;
        FileInputStream is = new FileInputStream(sourceFilePath);

        try {
            List<Map<String,String>> contents = new ArrayList();
             workbook =new XSSFWorkbook(is);
            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(sheet_num);
            System.out.println(sheet.getLastRowNum());
            //第0行是表名，忽略，从第二行开始读取
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);//得到每一行的信息
                Map<String,String> class_one = new HashMap<>();
                class_one.put("fileName",row.getCell(0).toString());
                class_one.put("propertiesValue",row.getCell(1).toString());
                class_one.put("type",row.getCell(2).toString());
                class_one.put("columnShow",get_cell(row.getCell(3)));
                class_one.put("searchShow",get_cell(row.getCell(4)));
                class_one.put("dict",get_cell(row.getCell(5)));
                class_one.put("dictKey",get_cell(row.getCell(6)));
                class_one.put("widget",get_cell(row.getCell(7)));
                class_one.put("weight",get_cell(row.getCell(8)));
                contents.add(class_one);
            }
            return contents;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }


}

/**
 * @program: Create_database
 * @description: 读取和处理合并单元格的excel表格
 * @author: GeXin
 * @create: 2021-08-05 13:48
 **/


import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class write_read_excel {

    /*
    * 判断输入的表格的文件版本类型，是xlsx,或者是xls。
    * */
    public Workbook getWorkbook(String filedPath){
        Workbook wb  = null;
        boolean isE2007 = false;
        if(filedPath.endsWith("xlsx")){
            isE2007 = true;
        }
        try {
            InputStream input = new FileInputStream(filedPath);
            if(isE2007){
               wb = new XSSFWorkbook(input);
             }
            else{
                wb = new HSSFWorkbook(input);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
       return wb;
    }

    /*
    * 读取excel文件，并且按照两个级别进行存储。
    * 表的格式是
    * |   | b |
    * | a | c |
    * |   | d |
    *    a
    *   /|\
    *  b c d
    * */
    public List<Map<String,String>> readExcel(String filedPath,int sheetIndex){
        List<Map<String,String>> list = new ArrayList<>();
        Workbook wb = getWorkbook(filedPath);
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
        String flagid ="";
        for(int i=1;i<=sheet.getLastRowNum();i++){
             row = sheet.getRow(i);
            if(row.getCell(1).toString().length()>=1) {
                Map<String,String> class_one = new HashMap<>();
                String id =UUID_generator.get16UUID();
                flagid = id;
                class_one.put("id",id);
                class_one.put("parentId","0");
                class_one.put("name",row.getCell(1).toString());
                class_one.put("value",row.getCell(2).toString());
                list.add(class_one);
                Map<String,String> class_two = new HashMap<>();
                id =UUID_generator.get16UUID();
                class_two.put("id",id);
                class_two.put("parentId",flagid);
                class_two.put("name",row.getCell(3).toString());
                class_two.put("value",row.getCell(4).toString());
                list.add(class_two);
            }else{
                Map<String,String> class_two = new HashMap<>();
                String id =UUID_generator.get16UUID();
                class_two.put("id",id);
                class_two.put("parentId",flagid);
                class_two.put("name",row.getCell(3).toString());
                class_two.put("value",row.getCell(4).toString());
                list.add(class_two);
            }

    }
        return list;
    }


}

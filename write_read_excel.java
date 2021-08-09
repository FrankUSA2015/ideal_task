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

    /**
     * 判断合并了行
     * @param sheet
     * @param row
     * @param column
     * @return
     */
//    private boolean  isMergedRow(Sheet sheet,int row ,int column) {
//        int sheetMergeCount = sheet.getNumMergedRegions();
//        for (int i = 0; i < sheetMergeCount; i++) {
//            CellRangeAddress range = sheet.getMergedRegion(i);
//            int firstColumn = range.getFirstColumn();
//            int lastColumn = range.getLastColumn();
//            int firstRow = range.getFirstRow();
//            int lastRow = range.getLastRow();
//            if(row == firstRow && row == lastRow){
//                if(column >= firstColumn && column <= lastColumn){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

/**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return 1：合并单元的第一格；2：代表在合并单元里；3，代表不在合并单元里
     */
//    private  int isMergedRegion(Sheet sheet,int row ,int column) {
//        int sheetMergeCount = sheet.getNumMergedRegions();
//        System.out.println(sheetMergeCount);
//        for (int i = 0; i < sheetMergeCount; i++) {
//            //获得某一个合并单元格
//            CellRangeAddress range = sheet.getMergedRegion(i);
//            int firstColumn = range.getFirstColumn();//获得合并单元格的开始的列
//            System.out.println("开始的列"+firstColumn);
//            int lastColumn = range.getLastColumn();//获得合并单元格的结束的列
//            System.out.println("结束的列"+lastColumn);
//            int firstRow = range.getFirstRow();//获得合并单元格的开始的行
//            System.out.println("开始的行"+firstRow);
//            int lastRow = range.getLastRow();//获取合并单元格的结束的行
//            System.out.println("结束的行"+lastRow);
//            System.out.println("---------------------");
//            /*
//            * 判断给出的单元格是否在这个合并的单元格的区间内，
//            * 是就返回true,不是就返回flase
//            *
//            * */
//            if(row == firstRow ){
//                if(column == firstColumn ){
//                    return 1;
//                }
//            }
//        }
//        return 2;
//    }

/**
     * 获取单元格的值
     * @param cell
     * @return
     */
//    public String getCellValue(Cell cell){
//
//        if(cell == null) return "";
//
//        if(cell.getCellTypeEnum() == CellType.STRING){
//
//            return cell.getStringCellValue();
//
//        }else if(cell.getCellTypeEnum() == CellType.BOOLEAN){
//
//            return String.valueOf(cell.getBooleanCellValue());
//
//        }else if(cell.getCellTypeEnum() == CellType.FORMULA){
//
//            return cell.getCellFormula() ;
//
//        }else if(cell.getCellTypeEnum()==CellType.NUMERIC){
//
//            return String.valueOf(cell.getNumericCellValue());
//
//        }
//        return "";
//    }



     /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
//    public  String getMergedRegionValue(Sheet sheet ,int row , int column){
//        int sheetMergeCount = sheet.getNumMergedRegions();
//        for(int i = 0 ; i < sheetMergeCount ; i++){
//            CellRangeAddress ca = sheet.getMergedRegion(i);
//            int firstColumn = ca.getFirstColumn();
//            int lastColumn = ca.getLastColumn();
//            int firstRow = ca.getFirstRow();
//            int lastRow = ca.getLastRow();
//            if(row >= firstRow && row <= lastRow){
//                if(column >= firstColumn && column <= lastColumn){
//                    Row fRow = sheet.getRow(firstRow);//得到合并单元格的第一行
//                    Cell fCell = fRow.getCell(firstColumn);//根据上面的内容最终确定单元格。
//                    return getCellValue(fCell) ;
//                }
//            }
//        }
//        return null ;
//    }


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

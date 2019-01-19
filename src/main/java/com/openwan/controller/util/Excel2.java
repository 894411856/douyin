package com.openwan.controller.util;

import java.io.File;
import java.util.*;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel2 {

    static class Tests{
        private String uuid;
        private String key;
        private String val;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Tests{" +
                    "uuid='" + uuid + '\'' +
                    ", key='" + key + '\'' +
                    ", val='" + val + '\'' +
                    '}';
        }
    }
    //常亮，用作第一种模板类型，如下图
    private static final int HEADER_VALUE_TYPE_Z=1;
    //第二种模板类型，如下图
    private static final int HEADER_VALUE_TYPE_S=2;


    public static void main(String[] args) {
        File dir = new File("C:\\a.xlsx");

        //dir文件，0代表是第一行为保存到数据库或者实体类的表头，一般为英文的字符串，2代表是第二种模板，
        List<Map<String, Object>> list = readExcle(dir, 0, 2);

        List<List<Tests>> listList = new ArrayList<>();
        List<Tests> tests = new ArrayList<>();
        for(Map<String,Object> map :list){

            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            System.out.println(uuid);
            for(String key:map.keySet()){
                Tests tests1 = new Tests();
                tests1.setUuid(uuid);
                tests1.setKey(key);
                tests1.setVal(map.get(key)+"");
                tests.add(tests1);
            }



        }


        System.out.println(tests );

    }



    /**
     * 文件过滤
     * @Title: fileNameFileter
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param:
     * @author LiYonghui
     * @date 2017年1月6日 下午4:45:42
     * @return: void
     * @throws
     */
    private  static boolean  fileNameFileter(File file){
        boolean endsWith = false;
        if(file != null){
            String fileName = file.getName();
            endsWith = fileName.endsWith(".xls") || fileName.endsWith(".xlsx");
        }
        return endsWith;
    }

    /**
     * 获取表头行
     * @Title: getHeaderRow
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param sheet
     * @param: @param index
     * @param: @return
     * @author LiYonghui
     * @date 2017年1月6日 下午5:05:24
     * @return: Row
     * @throws
     */
    private static Row getHeaderRow(Sheet sheet, int index){
        Row headerRow = null;
        if(sheet!=null){
            headerRow = sheet.getRow(index);
        }
        return headerRow;
    }

    /**
     * 获取表格中单元格的value
     * @Title: getCellValue
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param row
     * @param: @param cellIndex
     * @param: @param formula
     * @param: @return
     * @author LiYonghui
     * @date 2017年1月6日 下午5:40:28
     * @return: Object
     * @throws
     */
    private static Object getCellValue(Row row,int cellIndex,FormulaEvaluator formula){
        Cell cell = row.getCell(cellIndex);
        if(cell != null){
            switch (cell.getCellType()) {
                //String类型
                case Cell.CELL_TYPE_STRING:
                    return cell.getRichStringCellValue().getString();

                //number类型
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().getTime();
                    } else {
                        return cell.getNumericCellValue();
                    }
                    //boolean类型
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue();
                //公式
                case Cell.CELL_TYPE_FORMULA:
                    return formula.evaluate(cell).getNumberValue();
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * 获取表头value
     * @Title: getHeaderCellValue
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param headerRow
     * @param: @param cellIndex 英文表头所在的行，从0开始计算哦
     * @param: @param type  表头的类型第一种 姓名（name）英文于实体类或者数据库中的变量一致
     * @param: @return
     * @author LiYonghui
     * @date 2017年1月6日 下午6:12:21
     * @return: String
     * @throws
     */
    private static String getHeaderCellValue(Row headerRow,int cellIndex,int type){
        Cell cell = headerRow.getCell(cellIndex);
        String headerValue = null;
        if(cell != null){
            //第一种模板类型
            if(type == HEADER_VALUE_TYPE_Z){
                headerValue = cell.getRichStringCellValue().getString();
                int l_bracket = headerValue.indexOf("（");
                int r_bracket = headerValue.indexOf("）");
                if(l_bracket == -1){
                    l_bracket = headerValue.indexOf("(");
                }
                if(r_bracket == -1){
                    r_bracket = headerValue.indexOf(")");
                }
                headerValue = headerValue.substring(l_bracket+1, r_bracket);
            }else if(type == HEADER_VALUE_TYPE_S){
                //第二种模板类型
                headerValue = cell.getRichStringCellValue().getString();
            }
        }
        return headerValue;
    }

    /**
     * 读取excel表格
     * @Title: readExcle
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param file
     * @param: @param headerIndex
     * @param: @param headType  表头的类型第一种 姓名（name）英文于实体类或者数据库中的变量一致
     * @author LiYonghui
     * @date 2017年1月6日 下午6:13:27
     * @return: void
     * @throws
     */
    public static List<Map<String, Object>> readExcle(File file,int headerIndex,int headType){
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        if(!fileNameFileter(file)){
            return null;
        }else{
            try {
                //加载excel表格
                WorkbookFactory wbFactory = new WorkbookFactory();
                Workbook wb = wbFactory.create(file);
                //读取第一个sheet页
                Sheet sheet = wb.getSheetAt(0);
                //读取表头行
                Row headerRow = getHeaderRow(sheet, headerIndex);
                //读取数据
                FormulaEvaluator formula = wb.getCreationHelper().createFormulaEvaluator();
                for(int r = headerIndex+1; r<= sheet.getLastRowNum();r++){
                    Row dataRow = sheet.getRow(r);
                    Map<String, Object> map = new HashMap<String, Object>();
                    for(int h = 0; h<dataRow.getLastCellNum();h++){
                        //表头为key
                        String key = getHeaderCellValue(headerRow,h,headType);
                        //数据为value
                        Object value = getCellValue(dataRow, h, formula);
                        if(!key.equals("") && !key.equals("null") && key != null ){
                            map.put(key, value);
                        }
                    }
                    lists.add(map);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

}

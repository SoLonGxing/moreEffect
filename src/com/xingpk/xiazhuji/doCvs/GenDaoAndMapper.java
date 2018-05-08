package com.xingpk.xiazhuji.doCvs;

import com.util.CommonUtil;
import com.xingpk.xiazhuji.Column;
import com.xingpk.xiazhuji.Table;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

public class GenDaoAndMapper {
    private String filePathWithName;//带文件名的path
    private String mapperName;
    private String beanName;
    private String packagePath;
    private String tableName;
    private Table table;
    private List<Column> columnList = new ArrayList<>();
    private List<String> keyValue = new ArrayList<>();

    public GenDaoAndMapper(String filePathWithName, String packagePath, String beanName) {
        this.filePathWithName = filePathWithName;
        this.packagePath = packagePath;
        this.tableName = filePathWithName.substring(filePathWithName.lastIndexOf("/")+1,filePathWithName.lastIndexOf("."));
        this.mapperName = CommonUtil.upperCaseFirstCharacter(beanName) + "Mapper";
        this.beanName = beanName;
    }

    public void genDBFile(){
        getDataFromXls();
        table.genMapperFile();
        table.genDaoFile();
    }

    private Table getDataFromXls(){
        //从excel里读取数据
        try
        {
            FileInputStream is =  new FileInputStream(filePathWithName);
            HSSFWorkbook excel=new HSSFWorkbook(is);
            //获取sheet1
            //初始化表字段各List
            HSSFSheet sheet1 = null;
            sheet1 = excel.getSheetAt(0);//字段sheet

            int rowNumber1 = sheet1.getPhysicalNumberOfRows();
            for (int i =1;i<rowNumber1;i++)
            {
                HSSFRow row1 = sheet1.getRow(i);
                if (!row1.getCell(0).getRichStringCellValue().toString().trim().equals("")) {
                    /**
                     * 0-字段英文名
                     * 1-字段中文名
                     * 2-说明
                     * 3-类型+长度*/
                    String typeAndLength = row1.getCell(3).getRichStringCellValue().toString().trim();

                    Column column = new Column(row1.getCell(0).getRichStringCellValue().toString().trim(),
                            typeAndLength.substring(0, typeAndLength.indexOf("(") == -1 ? typeAndLength.length() : typeAndLength.indexOf("(")),
                            row1.getCell(1).getRichStringCellValue().toString().trim(),
                            row1.getCell(2) == null ? "" : row1.getCell(2).getRichStringCellValue().toString().trim());
                    columnList.add(column);

//                    this.columnName.add(row1.getCell(0).getRichStringCellValue().toString().trim());
//                    this.columnSummary.add(row1.getCell(1).getRichStringCellValue().toString().trim());
//                    this.columnType.add(typeAndLength.substring(0, typeAndLength.indexOf("(") == -1 ? typeAndLength.length() : typeAndLength.indexOf("(")));
                }
            }

            //初始化主键List
            HSSFSheet sheet2 = excel.getSheetAt(1);//主键sheet
            int rowNumber2 = sheet2.getPhysicalNumberOfRows();
            HSSFRow row2 = sheet2.getRow(0);
            for (int i =0;i<rowNumber2;i++)
            {
                HSSFCell cell3 = row2.getCell(3);
                if (cell3.getRichStringCellValue().toString().indexOf("主键") != -1){
                    String[] as =  row2.getCell(1).getRichStringCellValue().toString().split("\\+");
                    this.keyValue = CommonUtil.string2List(as);
                }
            }

            table = new Table(this.tableName,this.beanName, packagePath, columnList,keyValue);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        return table;
    }
}

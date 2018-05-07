package com.xingpk.xiazhuji.doCvs;

import com.util.CommonUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenIOJavaFile {
    private static String subPath = "IO/";
    private File file;
    private String filePathWithName;//带文件名的path
    private String className;
    private String packagePath ;
    private List<String> columnName = new ArrayList<>();
    private List<String> columnType = new ArrayList<>();
    private List<String> columnSummary = new ArrayList<>();

    public GenIOJavaFile(String filePathWithName, String packagePath) {
        this.filePathWithName = filePathWithName;
        this.packagePath = packagePath + ".io;\n\n";
        this.className = CommonUtil.upperCaseFirstCharacter(filePathWithName.substring(filePathWithName.lastIndexOf("/")+1,filePathWithName.lastIndexOf(".")));
    }

    public GenIOJavaFile(File file) {
        this.file = file;
    }

    public void genfile(String ioType) {
        String IOJavaFileString = "";


        IOJavaFileString += "package " + this.packagePath;
        IOJavaFileString += "import java.math.BigDecimal;\n";
        IOJavaFileString += "import com.icbc.fova.io.OpeartionCommExt;\n\n";
        IOJavaFileString += "public class " + this.className + ioType + " extends OperationCommExt {\n";

        //从excel里读取数据
        try
        {
            FileInputStream is =  new FileInputStream(filePathWithName);
            HSSFWorkbook excel=new HSSFWorkbook(is);
            //获取sheet
            HSSFSheet sheet = null;
            if (ioType.equals("Input")) {
                sheet = excel.getSheetAt(0);
            }else{
                sheet = excel.getSheetAt(1);
            }
            int rowNumber = sheet.getPhysicalNumberOfRows();
            for (int i =1;i<rowNumber;i++)
            {
                HSSFRow row = sheet.getRow(i);
                if (!row.getCell(0).getRichStringCellValue().toString().trim().equals("")) {
                    /**
                     * 0-变量名称
                     * 1-数据类型
                     * 2-数据长度
                     * 3-中文说明
                     * 4-输入描述
                     * 5-输入限制（数据字典）
                     * 6-取值范围（暂时不用）*/
                    this.columnName.add(row.getCell(0).getRichStringCellValue().toString().trim());
                    this.columnType.add(CommonUtil.getColumnTypeCorrect(row.getCell(1).getRichStringCellValue().toString().trim()));
                    this.columnSummary.add(row.getCell(2).getRichStringCellValue().toString().trim());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        //从List生成字段声明
        for(int i = 0; i< this.columnName.size(); i++){
            IOJavaFileString += "   private " + this.columnType.get(i) + " " + this.columnName.get(i) + ";//" + this.columnSummary.get(i) + "\n";

        }

        IOJavaFileString += "\n\n";

        //从List生成getter和setter
        for(int i = 0; i< this.columnName.size(); i++){
            //getter
            String name = this.columnName.get(i);
            String type = this.columnType.get(i);
            IOJavaFileString += "   public " + type + " get" + CommonUtil.upperCaseFirstCharacter(name) + "() {\n";
            IOJavaFileString += "       return " + this.columnName.get(i) + ";\n";
            IOJavaFileString += "   }\n";

            //setter
            IOJavaFileString += "   public void set" + CommonUtil.upperCaseFirstCharacter(name) + "(" + type + " " + name + ") {\n";
            if (type.equals("String")) {
                IOJavaFileString += "       this." + name + " = " + name + " == null ? null : " + name + ".trim();\n";
            } else {
                IOJavaFileString += "       this." + name + " = " + name + ";\n";
            }
            IOJavaFileString += "   }\n\n";


        }
        IOJavaFileString += "}\n";

        System.out.println(IOJavaFileString);
        CommonUtil.genFile(IOJavaFileString, subPath, className);
    }


}

package com.xingpk.xiazhuji.doCvs;

import com.util.CommonUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenIOJavaFile {
    private static String subPath = "IO/";
    private File file;
    private String filePathWithName;//带文件名的path
    private String className;
    private String packagePath ;
    private List<String> columnName = new ArrayList<>();//名称数组
    private List<String> columnType = new ArrayList<>();//类型数组
    private List<String> columnSummary = new ArrayList<>();//summary数组
    private List<String> columnMInputName = new ArrayList<>();//必输字段名称数组
    private List<String> columnMInputType = new ArrayList<>();//必输字段类型数组

    public GenIOJavaFile(String filePathWithName, String packagePath) {
        this.filePathWithName = filePathWithName;
        this.packagePath = packagePath + ".io;\n\n";
        this.className = CommonUtil.upperCaseFirstCharacter(filePathWithName.substring(filePathWithName.lastIndexOf("\\")+1,filePathWithName.lastIndexOf(".")));
    }

    public GenIOJavaFile(File file) {
        this.file = file;
    }

    public void genfile(String ioType) {
        String IOJavaFileString = "";


        IOJavaFileString += "package " + this.packagePath;
        IOJavaFileString += "import java.math.BigDecimal;\n";
        IOJavaFileString += "import com.icbc.fova.io.OperationCommExt;\n\n";
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
            if(rowNumber >1) {
                for (int i = 1; i < rowNumber; i++) {
                    HSSFRow row = sheet.getRow(i);
                    if (!row.getCell(0).getRichStringCellValue().toString().trim().equals("")) {
                        /**
                         * 0-变量名称
                         * 1-数据类型
                         * 2-中文说明
                         * 3-输入描述（必输/选输）
                         * 4-输入限制（数据字典）
                         * 5-取值范围（暂时不用）*/
                        this.columnName.add(row.getCell(0).getRichStringCellValue().toString().replace("\t","").trim());
                        this.columnType.add(CommonUtil.getColumnTypeCorrect(row.getCell(1).getRichStringCellValue().toString().replace("\t","").trim()));
                        this.columnSummary.add(row.getCell(2).getRichStringCellValue().toString().replace("\t","").trim());
                        String MInput = row.getCell(3).getRichStringCellValue().toString().replace("\t","").trim();

                        if("必输".equals(MInput)){
                            columnMInputName.add(MInput);
                            columnMInputType.add(CommonUtil.getColumnTypeCorrect(row.getCell(1).getRichStringCellValue().toString().replace("\t","").trim()));
                        }


                    }
                }
            }else{
                return;
            }

            //从List生成字段声明
            for(int j = 0; j< this.columnName.size(); j++){
                IOJavaFileString += "   private " + this.columnType.get(j) + " " + this.columnName.get(j) + ";//" + this.columnSummary.get(j) + "\n";

            }

            IOJavaFileString += "\n\n";

            //从List生成getter和setter
            for(int k = 0; k< this.columnName.size(); k++){
                //getter
                String name = this.columnName.get(k);
                String type = this.columnType.get(k);
                IOJavaFileString += "   public " + type + " get" + CommonUtil.upperCaseFirstCharacter(name) + "() {\n";
                IOJavaFileString += "       return " + this.columnName.get(k) + ";\n";
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
            CommonUtil.genFile(IOJavaFileString, subPath, className + ioType + ".java");
            CommonUtil.genFile(genMustInputCheckString(), subPath, className + "checkInput" + ".java");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            IOJavaFileString = e.toString();

        }


    }


    public String genMustInputCheckString(){
        String mustINputCheckString = "";
        if(columnMInputName.size()>0){
            mustINputCheckString += "   private ActionError checkInput(" + className + "Input data){\n";
            mustINputCheckString += "       ActionError actionError = null;\n";
            for (int i =0;i<columnMInputName.size();i++){
                String name = columnMInputName.get(i);
                String type = columnMInputType.get(i);
                switch (CommonUtil.getColumnTypeCorrect(type)){
                    case "int":
                        mustINputCheckString += "      if(data.get" + CommonUtil.upperCaseFirstCharacter(name) + "() == 0){\n";
                        mustINputCheckString += "           actionError = new ActionError(\"2961\", \"" + name + "\", this.getClass());\n";
                        mustINputCheckString += "           return actionError;\n";
                        mustINputCheckString += "       }\n";
                    case "long":
                        mustINputCheckString += "      if(data.get" + CommonUtil.upperCaseFirstCharacter(name) + "() == 0){\n";
                        mustINputCheckString += "           actionError = new ActionError(\"2961\", \"" + name + "\", this.getClass());\n";
                        mustINputCheckString += "           return actionError;\n";
                        mustINputCheckString += "       }\n";
                    case "String":
                        mustINputCheckString += "      if(data.get" + CommonUtil.upperCaseFirstCharacter(name) + "() == null || \"\".equals(data.get" + CommonUtil.upperCaseFirstCharacter(name) + ")){\n";
                        mustINputCheckString += "           actionError = new ActionError(\"2961\", \"" + name + "\", this.getClass());\n";
                        mustINputCheckString += "           return actionError;\n";
                        mustINputCheckString += "       }\n";
                }

            }
                    mustINputCheckString += "   }";
        }

        return mustINputCheckString;
    }
}

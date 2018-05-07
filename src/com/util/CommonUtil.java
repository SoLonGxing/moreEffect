package com.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CommonUtil {
    private static String filePath4MAC = "~/Documents/";
    private static String filePath4Win = "D:/moreEffect/下主机/";

    //生成文件
    public static void genFile(String fileString, String subPath, String fileName){
        //name是带后缀的，比如A.class
        File file = null;
        if (System.getProperties().getProperty("os.name").indexOf("Mac") == -1){
            file = new File(filePath4Win + subPath + fileName);
        }else {
            file = new File(filePath4MAC + subPath + fileName);
        }
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write
        try{

            OutputStream out=new FileOutputStream(file);
            BufferedWriter   rd   =   new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
            rd.write(fileString);
            rd.close();
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    //首字母小写
    public static String lowerCaseFirstCharacter(String input){
        return input.substring(0,1).toLowerCase() + input.substring(1,input.length());
    }

    //首字母大写
    public static String upperCaseFirstCharacter(String input){
        return input.substring(0,1).toUpperCase() + input.substring(1,input.length());
    }

    public static String getColumnTypeCorrect(String columnTypeInput){
        switch (columnTypeInput.toLowerCase()){
            case "string": return "String";
            case "int": return "int";
            case "long": return "long";
            case "bigdecimal": return "BigDecimal";
            case "date": return "Date";
            default: return columnTypeInput;
        }
    }

    public static List string2List(String[] input){
        List<String> rtList = new ArrayList<>();
        for(String a: input){
            rtList.add(a);
        }

        return rtList;

    }
}

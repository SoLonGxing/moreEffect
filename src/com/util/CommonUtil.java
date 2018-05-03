package com.util;

import java.io.*;

public class CommonUtil {
    private String filePath4MAC = "~/Documents";
    private String filePath4Win = "D:/moreEffect/下主机/";

    public void genFile(String fileString, String fileName){
        //name是带后缀的，比如A.class
        File file = null;
        if (System.getProperties().getProperty("os.name").indexOf("Mac") == -1){
            file = new File(filePath4Win + fileName);
        }else {
            file = new File(filePath4MAC + fileName);
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
}

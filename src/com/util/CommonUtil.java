package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommonUtil {
    private String filePath4MAC = "~/Documents";
    private String filePath4Win = "D:/moreEffect/下主机/";

    public void genFile(String filePath4MAC, String fileString, String fileName){
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
        try (FileWriter fw = new FileWriter(file, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileString);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Write failed" );
        }

    }
}

package com.xingpk.xiazhuji.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class callRelationship {
    //查询调用关系
    private String scanType;//搜索类型
    private String scanString;//待搜索服务
    private String scanPath;//待搜索路径
    private List<String> fatherList = new ArrayList<>();

    public callRelationship(String scanType, String scanString, String scanPath) {
        this.scanType = scanType;
        this.scanString = scanString;
        this.scanPath = scanPath;
    }

    public String getReuslt(){


        return fatherList.toString();
    }


    public void search(File a, String x) throws IOException {//在文件a中的每行中查找x
        Scanner scan1 = new Scanner(a,"utf-8");
        int k1 = 0;
        while(true){
            if(scan1.hasNext()==false) break;
            String s1 = scan1.nextLine();
            k1++;
            String tmpString = "";
            if(s1.contains("public class ")){
                tmpString = s1;
            }

            if(s1.contains(x)){
                if (!s1.contains("public class")) {
                    String ss1 = "文件:" + a.getPath() + " 第" + k1 + "行 \n  内容：" + s1;

                    fatherList.add(s1.substring(s1.indexOf("public class ") + 13, s1.indexOf(" extends")));

                    System.out.println(ss1);
                }
            }
        }
    }

    public void find(File a,String s)throws IOException{//在a下所有文件中查找含有s的行

        File[] ff = a.listFiles();
        if(ff==null) return;
        for(File it : ff){
            if(it.isFile()){//若a是文件，直接查找
                search(it,s);
            }
            if(it.isDirectory()){//若a是目录，则对其目录下的目录或文件继续查找
                find(it,s);
            }
        }
    }

}

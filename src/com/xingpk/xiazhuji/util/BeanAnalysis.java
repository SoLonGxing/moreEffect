package com.xingpk.xiazhuji.util;

import com.util.CommonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BeanAnalysis {


    public static List getNames(File file){
        List<String> list = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file, "utf-8");

            while (true) {
                if (scan.hasNext() == false) break;
                String s1 = scan.nextLine();
                String tmpString = "";
                if (s1.contains("public class ")) {
                    tmpString = s1;
                }

                if (s1.contains("private ") && s1.contains(";")) {
                    if (!s1.contains("(")) {
                        String name = s1.substring(s1.lastIndexOf(" ") + 1, s1.indexOf(";"));
                        list.add(name);
                    }
                }
            }

            return list;
        }catch (Exception e){
            list.add("error" + CommonUtil.getStackTrace(e));
            return list;
        }
    }
}

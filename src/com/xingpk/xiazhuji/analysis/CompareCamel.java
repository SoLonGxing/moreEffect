package com.xingpk.xiazhuji.analysis;

import com.xingpk.xiazhuji.util.BeanAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompareCamel {
    private String fileAPath;
    private String fileBPath;
    private File fileA;
    private File fileB;


    public CompareCamel(String fileAPath, String fileBPath) {
        this.fileAPath = fileAPath;
        this.fileBPath = fileBPath;
    }


    public String getResult(){
        StringBuffer sb4NotSame = new StringBuffer();
        List<String> listA = new ArrayList<>();
        List<String> listB = new ArrayList<>();


        fileA = new File(fileAPath);
        fileB = new File(fileBPath);


        listA = BeanAnalysis.getNames(fileA);
        listB = BeanAnalysis.getNames(fileB);

        if (listA.size() > 0 && listB.size() > 0) {

            if (listA.get(0).indexOf("error") != -1) {
                return "Analyze Bean A error\n" + listA.get(0);
            }

            if (listB.get(0).indexOf("error") != -1) {
                return "Analyze Bean B error\n" + listB.get(0);
            }

            for (String sa : listA) {
                for (String sb : listB) {
                    if (!sa.equals(sb) && sa.equalsIgnoreCase(sb)) {
                        sb4NotSame.append(sa + " NOT EQUALS " + sb + "\n");
                    }
                }

            }
        }

        if(sb4NotSame.length()>0){
            return sb4NotSame.toString();
        }else{
            return "All same! Congratulations!";
        }

    }
}

package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.BOS;
import com.xingpk.xiazhuji.intr.Pbrb2ServiceClass;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenACS {
    private JTextField textAtsName;
    private JTextArea textArea1;
    private JTextField txtPackage;
    private String mainName;
    private String[] subName;
    private String packagePath;
    private java.util.List<String> ls;
    private int legalFlag4AcsName = 0;


    public GenACS(JTextField textAtsName, JTextArea textArea1, JTextField txtPackage) {
        this.textAtsName = textAtsName;
        this.textArea1 = textArea1;
        this.txtPackage = txtPackage;

    }

    public void letsDoIt(){
        //acs的名字
        this.mainName = textAtsName.getText().replace("\t","").replace(" ", "");//调用方名称
        //bos的名字们
        this.subName = textArea1.getText().replace("\t","").replace(" ", "").split("\\n");//被调用方名称
        this.ls = Arrays.asList(subName);

        if (checkInput()) {
            ACSimpl acs = new ACSimpl(mainName, packagePath);
            List<Pbrb2ServiceClass> bosList = new ArrayList<Pbrb2ServiceClass>();
            for (String sub : ls) {
                System.out.println(sub);
                //类名不能是空串或数字开头
                if (!sub.equals("")) {
                    bosList.add(new BOSimpl(sub.trim(), packagePath));
                }
            }
            acs.setBosList(bosList);
            acs.printAcsClass();
        }
    }

    private boolean checkInput(){

        return true;
    }
}

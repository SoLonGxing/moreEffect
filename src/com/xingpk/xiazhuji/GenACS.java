package com.xingpk.xiazhuji;

import com.util.CommonUtil;
import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.BOS;
import com.xingpk.xiazhuji.intr.Pbrb2ServiceClass;
import com.xingpk.xiazhuji.util.DataCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public boolean letsDoIt(){
        DataCheck dc = new DataCheck();
        //acs的名字
        this.mainName = textAtsName.getText().replace("\t","").replace(" ", "");//调用方名称
        if (!mainName.endsWith("ACS") || !mainName.endsWith("Acs") || !mainName.endsWith("acs")){
            mainName = mainName + "ACS";
        }
        //bos的名字们
        this.subName = textArea1.getText().replace("\t","").replace(" ", "").split("\\n");//被调用方名称
        this.ls = Arrays.asList(subName);
        this.packagePath = txtPackage.getText().replace("\t","").replace(" ", "");

        if (dc.checkInput(textAtsName, textArea1, txtPackage, ls)) {
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
            System.out.println(acs.printAcsClass());
            CommonUtil.genFile(acs.printAcsClass(), "", acs.getClassName() + ".java");

            return true;
//
        }

        return false;
    }




}

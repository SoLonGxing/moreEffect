package com.xingpk.xiazhuji;

import com.util.CommonUtil;
import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.Pbrb2ServiceClass;
import com.xingpk.xiazhuji.util.DataCheck;
import org.jcp.xml.dsig.internal.MacOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenATS {
    private JTextField textAtsName;
    private JTextArea textArea1;
    private JTextField txtPackage;
    private String mainName;
    private String[] subName;
    private String packagePath;
    private java.util.List<String> ls;
    private int legalFlag4AcsName = 0;


    public GenATS(JTextField textAtsName, JTextArea textArea1, JTextField txtPackage) {
        this.textAtsName = textAtsName;
        this.textArea1 = textArea1;
        this.txtPackage = txtPackage;

    }

    public void letsDoIt(){
        this.mainName = textAtsName.getText().replace("\t","").replace(" ", "");//调用方名称
        this.subName = textArea1.getText().replace("\t","").replace(" ", "").split("\\n");//被调用方名称
        this.ls = Arrays.asList(subName);
        this.packagePath = txtPackage.getText().replace("\t","").replace(" ", "");

        DataCheck dc = new DataCheck();
        if (dc.checkInput(textAtsName, textArea1, txtPackage, ls)) {
            ATSimpl ats = new ATSimpl(mainName, packagePath);
            List<Pbrb2ServiceClass> acsList = new ArrayList<Pbrb2ServiceClass>();
            for (String sub : ls) {
                System.out.println(sub);
                //类名不能是空串或数字开头
                if (!sub.equals("")) {
                    acsList.add(new ACSimpl(sub.trim(), packagePath));
                }
            }

            ats.setAcsList(acsList);
            CommonUtil cu = new CommonUtil();
            cu.genFile(ats.printAtsClass(), ats.getClassName() + ".java");
            cu.genFile(ats.printIAtsClass(), "I" + ats.getClassName() + ".java");

        }
    }


}

package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import org.jcp.xml.dsig.internal.MacOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenPBRB2 {
    private JTextField textAtsName;
    private JTextArea textArea1;
    private JTextField txtPackage;
    private String mainName;
    private String[] subName;
    private String packagePath;
    private java.util.List<String> ls;
    private int legalFlag4AcsName = 0;


    public GenPBRB2(JTextField textAtsName, JTextArea textArea1, JTextField txtPackage) {
        this.textAtsName = textAtsName;
        this.textArea1 = textArea1;
        this.txtPackage = txtPackage;

    }


    public void letsDoIt(){
        this.mainName = textAtsName.getText().replace("\t","").replace(" ", "");//调用方名称
        this.subName = textArea1.getText().replace("\t","").replace(" ", "").split("\\n");//被调用方名称
        this.ls = Arrays.asList(subName);

        if (checkInput()) {


            ATSimpl ats = new ATSimpl(mainName, packagePath);
            List<ACS> acsList = new ArrayList<ACS>();
            for (String sub : ls) {
                System.out.println(sub);
                //类名不能是空串或数字开头
                if (!sub.equals("")) {
                    acsList.add(new ACSimpl(sub.trim(), packagePath));
                }
            }

            ats.setAcsList(acsList);
            xiazhujiRoot xzjr = new xiazhujiRoot(ats);
            xzjr.testPrintFile();
        }
    }

    private boolean checkInput(){
        //设置如果是判断必输之后点击的则背景变白色
        textAtsName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addMoustListener(textAtsName);
                super.mouseClicked(e);
            }
        });

        txtPackage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addMoustListener(txtPackage);
                super.mouseClicked(e);
            }
        });

        textArea1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textArea1.getBackground().equals(Color.red)){
                    textArea1.setText(textArea1.getText().replace("--illegal",""));
                    textArea1.setBackground(Color.white);
                }
                super.mouseClicked(e);
            }
        });

        if (mainName.equals("")){
            textAtsName.setText("必输");
            textAtsName.setBackground(Color.red);
            return false;
        }

        if (this.mainName.substring(0, 1).matches("[0-9]*")){
            textAtsName.setText(mainName + "--illegal");
            textAtsName.setBackground(Color.red);
            return false;
        }

        packagePath = txtPackage.getText().replace("\t","").replace(" ", "");
        if (this.packagePath.substring(0, 1).matches("[0-9]*")){
            txtPackage.setText(packagePath + "--illegal");
            txtPackage.setBackground(Color.red);
            return false;
        }

        if (packagePath.equals("")){
            txtPackage.setText("必输");
            txtPackage.setBackground(Color.red);
            return false;
        }

        String fullAcsNames = "";
        for (String sub : this.ls) {
            if (sub.substring(0, 1).matches("[0-9]*")){
                sub += "--illegal";
                legalFlag4AcsName = 1;
            }
            if (sub.equals(mainName)){
                sub += "--illegal";
                legalFlag4AcsName = 1;
            }

            fullAcsNames += sub + "\n";
        }
        if (legalFlag4AcsName == 1){
            textArea1.setText(fullAcsNames);
            textArea1.setBackground(Color.red);
            return false;
        }
        return true;
    }

    private void addMoustListener(JTextField txtField){
        if (txtField.getBackground().equals(Color.red)){
            txtField.setText(txtField.getText().replace("--illegal",""));
            txtField.setBackground(Color.white);
        }
    }
}

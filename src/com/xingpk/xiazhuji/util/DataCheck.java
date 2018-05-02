package com.xingpk.xiazhuji.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataCheck {

    public boolean checkInput(JTextField textAtsName, JTextArea textArea1, JTextField txtPackage, java.util.List<String> ls){
        //设置如果是判断必输之后点击的则背景变白色
        int legalFlag4AcsName = 0;
        String mainName = textAtsName.getText();
        String packagePath = txtPackage.getText().replace("\t","").replace(" ", "");
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
                if (txtPackage.getBackground().equals(Color.red)){
                    txtPackage.setText("");
                    txtPackage.setBackground(Color.white);
                }
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

        if (mainName.substring(0, 1).matches("[0-9]*")){
            textAtsName.setText(mainName + "--illegal");
            textAtsName.setBackground(Color.red);
            return false;
        }

        if (packagePath.equals("")){
            txtPackage.setText("必输");
            txtPackage.setBackground(Color.red);
            return false;
        }

        if (packagePath.substring(0, 1).matches("[0-9]*")){
            txtPackage.setText(packagePath + "--illegal");
            txtPackage.setBackground(Color.red);
            return false;
        }

        String fullBosNames = "";
        for (String sub : ls) {
            if (sub.substring(0, 1).matches("[0-9]*")){
                sub += "--illegal";
                legalFlag4AcsName = 1;
            }
            if (sub.equals(mainName)){
                sub += "--illegal";
                legalFlag4AcsName = 1;
            }

            fullBosNames += sub + "\n";
        }
        if (legalFlag4AcsName == 1){
            textArea1.setText(fullBosNames);
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

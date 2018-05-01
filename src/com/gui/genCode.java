package com.gui;




import com.sun.tools.javac.util.StringUtils;
import com.xingpk.xiazhuji.ACSimpl;
import com.xingpk.xiazhuji.ATSimpl;


import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.xiazhujiRoot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class genCode {
    private JPanel panel1;
    private JButton button1;
    private JTextField textAtsName;
    private JTextArea textArea1;
    private JPanel jp1;
    private JPanel jp2;
    private JLabel AtsName;
    private JPanel jp3;
    private JPanel jp4;
    private JLabel AcsName;
    private JPanel jpbutton;
    private JComboBox comboBox1;
    private JTextField txtPackage;
    private JButton ACSButton;


    public genCode() {
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String mainName = textAtsName.getText();//调用方名称
                if (mainName.equals("")){
                    textAtsName.setText("必输");
                    textAtsName.setBackground(Color.red);
                    return;

                }
                String[] subName = textArea1.getText().replace("\t","").replace(" ", "").split("\\n");//被调用方名称
                String packagePath = txtPackage.getText();
                List<String> ls = Arrays.asList(subName);

                ATSimpl ats = new ATSimpl(mainName, packagePath);
                List<ACS> acsList = new ArrayList<ACS>();
                for(String sub: ls){
                    System.out.println(sub);
                    //类名不能是空串或数字开头
                    if (!sub.equals("") && !sub.substring(0,1).matches("[0-9]*")) {
                        acsList.add(new ACSimpl(sub.trim(), packagePath));
                    }
                }

                ats.setAcsList(acsList);
                xiazhujiRoot xzjr = new xiazhujiRoot(ats);
                xzjr.testPrintFile();

                super.mouseClicked(e);
            }
        });

        //设置如果main框输入了值则背景变白色
        textAtsName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                textAtsName.setBackground(Color.white);
                super.keyPressed(e);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("genCode");
        genCode gC = new genCode();
        frame.setContentPane(gC.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //TODO combobox1 先隐藏，因为目前没有其他功能
        gC.comboBox1.addItem("aa");
        gC.comboBox1.hide();


        gC.textArea1.setText("");
        frame.setVisible(true);
    }

    public void init(){

    }
}

package com.gui;




import com.xingpk.xiazhuji.ACSimpl;
import com.xingpk.xiazhuji.ATSimpl;


import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.xiazhujiRoot;

import javax.swing.*;
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


    public genCode() {
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String mainName = textAtsName.getText();//调用方名称
                String[] subName = textArea1.getText().replace("\t","").replace(" ", "").split("\\n");//被调用方名称
                String packagePath = txtPackage.getText();
                List<String> ls = Arrays.asList(subName);

                ATSimpl ats = new ATSimpl(mainName, packagePath);
                List<ACS> acsList = new ArrayList<ACS>();
                for(String sub: ls){
                    System.out.println(sub);
                    //TODO 加数字开头的校验
                    if (!sub.equals("")) {
                        acsList.add(new ACSimpl(sub.trim(), packagePath));
                    }
                }

                ats.setAcsList(acsList);
                xiazhujiRoot xzjr = new xiazhujiRoot(ats);
                xzjr.testPrintFile();

                super.mouseClicked(e);
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

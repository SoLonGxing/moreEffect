package com.gui;




import com.xingpk.xiazhuji.ACSimpl;
import com.xingpk.xiazhuji.ATSimpl;


import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.xiazhujiRoot;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                String[] subName = textArea1.getText().split("/n");//被调用方名称

                ATSimpl ats = new ATSimpl(mainName, txtPackage.getText());
                List<ACS> acsList = null;
                for(String sub: subName){
                    System.out.println(sub);
                     acsList.add(new ACSimpl(sub));
                }

                ats.setAcsList(acsList);
                xiazhujiRoot xzjr = new xiazhujiRoot();
                xzjr.setAts(ats);

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

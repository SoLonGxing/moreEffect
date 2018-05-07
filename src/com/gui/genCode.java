package com.gui;

import com.util.CommonUtil;
import com.xingpk.xiazhuji.GenACS;
import com.xingpk.xiazhuji.GenATS;
import com.xingpk.xiazhuji.doCvs.GenIOJavaFile;
import com.xingpk.xiazhuji.doxml.MakeJavaFromXml;
import jdk.internal.util.xml.impl.Input;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


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
    private JButton POSTButton;
    private JButton IOButton;
    private JButton fileButton;
    private JLabel result;


    public genCode() {
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GenATS genATS = new GenATS(textAtsName, textArea1, txtPackage);
                genATS.letsDoIt();
                result.setText("DONE!");
                super.mouseClicked(e);
            }
        });

        ACSButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GenACS genACS = new GenACS(textAtsName, textArea1, txtPackage);
                genACS.letsDoIt();
                result.setText("DONE!");
                super.mouseClicked(e);
            }
        });
        POSTButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MakeJavaFromXml mjfx = new MakeJavaFromXml(textArea1.getText());
                textArea1.setText(mjfx.makePostJava());
                super.mouseClicked(e);
            }
        });
        fileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jfc.showDialog(new JLabel(), "选择xls文件");
                File file=jfc.getSelectedFile();
                if(file.isDirectory()){
                    textArea1.setText("请选择xls文件！");
                }else if(file.isFile()){
                    if (jfc.getSelectedFile().getName().indexOf(".xls") == -1){
                        textArea1.setText("不是xls文件！");
                    }
                    textArea1.setText(file.getAbsolutePath());
                }

                GenIOJavaFile gf = new GenIOJavaFile(file.getAbsolutePath(), txtPackage.getText());
                gf.genfile("Input");
                gf.genfile("Output");
                System.out.println(jfc.getSelectedFile().getName());

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
        gC.textArea1.setBorder(new LineBorder(new java.awt.Color(127,157,185), 1, false));
        frame.setVisible(true);
        System.getProperties().getProperty("os.name").indexOf("abc");
    }
}

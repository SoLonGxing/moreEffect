package com.gui;

import com.util.CommonUtil;
import com.xingpk.xiazhuji.GenACS;
import com.xingpk.xiazhuji.GenATS;
import com.xingpk.xiazhuji.doCvs.GenDaoAndMapper;
import com.xingpk.xiazhuji.doCvs.GenIOJavaFile;
import com.xingpk.xiazhuji.doxml.MakeJavaFromXml;
import jdk.internal.util.xml.impl.Input;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
    private JButton DBButton;
    JFileChooser jfc=new JFileChooser();
    File file=null;
    public genCode() {
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                result.setText("");
                GenATS genATS = new GenATS(textAtsName, textArea1, txtPackage);
                if (genATS.letsDoIt()) {
                    result.setText("DONE!");
                }else{
                    result.setText("GENERATE ERROR;");
                }
                super.mouseClicked(e);
            }
        });

        ACSButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                result.setText("");
                GenACS genACS = new GenACS(textAtsName, textArea1, txtPackage);
                if (genACS.letsDoIt()){
                    result.setText("DONE!");
                }else{
                    result.setText("GENERATE ERROR;");
                }
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

        //生成DAO和MAPPER
        DBButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtPackage.getText().trim().equals("")){
                    txtPackage.setText("IO文件包路径必输");
                    txtPackage.setBackground(Color.red);
                }

                if (textAtsName.getText().trim().equalsIgnoreCase("")){
                    textAtsName.setText("bo文件名必输");
                    textAtsName.setBackground(Color.red);
                }

                GenDaoAndMapper gdam = new GenDaoAndMapper(file.getAbsolutePath(),txtPackage.getText().trim(),textAtsName.getText().trim());
                gdam.genDBFile();

                super.mouseClicked(e);
            }
        });

        //生成IO文件
        IOButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String packagePath = txtPackage.getText().trim();

                if (packagePath.equals("")){
                    txtPackage.setText("IO文件包路径必输");
                    txtPackage.setBackground(Color.red);
                }

                if (null != file) {
                    GenIOJavaFile gf = new GenIOJavaFile(file.getAbsolutePath(), txtPackage.getText());
                    gf.genfile("Input");
                    gf.genfile("Output");
                    System.out.println(jfc.getSelectedFile().getName());
                }
                super.mouseClicked(e);
            }
        });

        //文件按钮
        fileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jfc.showDialog(new JLabel(), "选择xls文件");
                file=jfc.getSelectedFile();
                if(file.isDirectory()){
                    textArea1.setText("请选择xls文件！");
                }else if(file.isFile()){
                    if (jfc.getSelectedFile().getName().indexOf(".xls") == -1){
                        textArea1.setText("不是xls文件！");
                    }
                    textArea1.setText(file.getAbsolutePath());
                }

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

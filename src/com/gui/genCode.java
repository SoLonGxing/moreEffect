package com.gui;


import com.util.CommonUtil;
import com.xingpk.octspbco.workflow.generate.SqlGenerator;
import com.xingpk.xiazhuji.GenACS;
import com.xingpk.xiazhuji.GenATS;
import com.util.StatusReport;
import com.xingpk.xiazhuji.XzjVar;
import com.xingpk.xiazhuji.analysis.CompareCamel;
import com.xingpk.xiazhuji.analysis.ruleCheck.RuleChecker;
import com.xingpk.xiazhuji.doCvs.GenDaoAndMapper;
import com.xingpk.xiazhuji.doCvs.GenIOJavaFile;
import com.xingpk.xiazhuji.doxml.MakeJavaFromXml;
import com.xingpk.xiazhuji.dsfTest.DsfTester;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class genCode {
    private JPanel panel1;
    private JButton atsButton1;
    private JTextField textAtsName;
    private JTextArea textArea1;
    private JPanel jp1;
    private JPanel jp2;
    private JLabel MainLabel;
    private JPanel jp3;
    private JPanel jp4;
    private JLabel SubLabel;
    private JPanel jpbutton;
    private JComboBox comboBox1;
    private JTextField txtPackage;
    private JButton ACSButton;
    private JButton POSTButton;
    private JButton IOButton;
    private JButton fileButton;
    private JLabel result;
    private JButton DBButton;
    private JButton GOButton;
    private JLabel lableFilePath;
    private JButton compareButton;
    private JButton checkButton;
    private JPanel jpRight;
    private JButton pubSqlButton;
    private JButton PLATFORMButton;
    private JTable table1;
    static JFrame frame = new JFrame("genCode");
    File file=null;

    public genCode() {
        atsButton1.addMouseListener(new MouseAdapter() {
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
                textArea1.setText(mjfx.makePostJava(1));
                super.mouseClicked(e);
            }
        });

        PLATFORMButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MakeJavaFromXml mjfx = new MakeJavaFromXml(textArea1.getText());
                textArea1.setText(mjfx.makePostJava(2));
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
                    return;
                }

                if (textAtsName.getText().trim().equalsIgnoreCase("")){
                    textAtsName.setText("bo文件名必输");
                    textAtsName.setBackground(Color.red);
                    return;
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
                    return;
                }

                if (null != file) {
                    GenIOJavaFile gf = new GenIOJavaFile(file.getAbsolutePath(), txtPackage.getText());
                    StatusReport sr = gf.genfile("Input");
                    if (sr.getIsOK() == 1){
                        textArea1.setText(textArea1.getText() + sr.getMessage() + "\n" + sr.getClassName() + "\n");
                        return;
                    }
                    StatusReport sr1 = gf.genfile("Output");
                    if (sr.getIsOK() == 1){
                        textArea1.setText(textArea1.getText() + sr1.getMessage() + "\n" + sr1.getClassName() + "\n");
                        return;
                    }
                }
                super.mouseClicked(e);
            }
        });

        //文件按钮
        fileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );
                jfc.showDialog(new JLabel(), "选择xls文件或java文件");
                file=jfc.getSelectedFile();
                if(file.isDirectory()){
                    lableFilePath.setText("请选择xls或java文件！");
                    return;
                }else if(file.isFile()){
                    if (jfc.getSelectedFile().getName().indexOf(XzjVar.JAVAFILE) == -1 && jfc.getSelectedFile().getName().indexOf(XzjVar.XLSFILE) == -1){
                        lableFilePath.setText("不是xls或java文件！");
                        return;
                    }
                    lableFilePath.setText(file.getAbsolutePath());
                }

                super.mouseClicked(e);
            }
        });


        GOButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String group = textAtsName.getText().toUpperCase();
                if (group.equals("")){
                    JOptionPane.showMessageDialog(null, "main请输入组别", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String filePaht = file.getAbsolutePath();

                if (filePaht.equals("")){
                    JOptionPane.showMessageDialog(null, "请选择输入文件", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String address = txtPackage.getText();
                if (address.equals("")){
                    JOptionPane.showMessageDialog(null, "请在包路径输入服务器IP和端口", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String interfaceName = filePaht.substring(filePaht.lastIndexOf(CommonUtil.getSpilter())+1,filePaht.lastIndexOf("."));

                DsfTester dsfTester = new DsfTester(address,group,interfaceName,filePaht);

                textArea1.setText(dsfTester.getResult());

//                JOptionPane.showMessageDialog(null, dsfTester.getResult(), "结果", JOptionPane.INFORMATION_MESSAGE);

                super.mouseClicked(e);
            }
        });

        compareButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String filePath = lableFilePath.getText();
                if (filePath.equals("")){
                    JOptionPane.showMessageDialog(null, "请点击下方file按钮选择主文件", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );
                jfc.showDialog(new JLabel(), "选择要对比的Bean文件");
                file=jfc.getSelectedFile();
                if(file.isDirectory()){
                    lableFilePath.setText("请选择java文件！");
                    return;
                }else if(file.isFile()){
                    if (jfc.getSelectedFile().getName().indexOf(XzjVar.JAVAFILE) == -1){
                        lableFilePath.setText("不是java文件！");
                        return;
                    }
                    textArea1.setText(file.getAbsolutePath());
                }

                textArea1.setText(textArea1.getText() + "\n" + new CompareCamel(filePath, file.getAbsolutePath()).getResult());

                super.mouseClicked(e);
            }
        });

        //代码规范检查
        checkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String filePath = lableFilePath.getText();
                if (filePath.equals("")){
                    JOptionPane.showMessageDialog(null, "请点击下方file按钮选择需要检查的文件", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (filePath.indexOf(XzjVar.JAVAFILE) == -1 && filePath.indexOf(XzjVar.XLSFILE) == -1){
                    lableFilePath.setText("不是java或xml文件！");
                    return;
                }

                Object[] obj2 ={ "ATS", "ACS", "BOS", "INPUT", "OUTPUT", "DAO", "BO" };
                String fileType = (String)JOptionPane.showInputDialog(frame,"a","a",JOptionPane.PLAIN_MESSAGE,new ImageIcon(),obj2,"");


                RuleChecker ruleChecker = new RuleChecker(filePath, fileType);
                textArea1.setText(ruleChecker.checkIt());
                super.mouseClicked(e);
            }
        });


        jpRight.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 if (e.isMetaDown()){
                     JOptionPane.showMessageDialog(null, "请点击下方file按钮选择需要检查的文件", "错误", JOptionPane.ERROR_MESSAGE);
                 }
                 super.mouseClicked(e);
             }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem().equals("DSF")){
                    pubSqlButton.hide();

                    atsButton1.show();
                    ACSButton.show();
                    POSTButton.show();
                    IOButton.show();
                    DBButton.show();
                    GOButton.show();
                    compareButton.show();
                    checkButton.show();
                }else{
                    atsButton1.hide();
                    ACSButton.hide();
                    POSTButton.hide();
                    IOButton.hide();
                    DBButton.hide();
                    GOButton.hide();
                    compareButton.hide();
                    checkButton.hide();

                    pubSqlButton.show();
                }
            }
        });
        pubSqlButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                SqlGenerator sg = new SqlGenerator(file,"","");

                sg.genIt();

            }
        });
    }

    public static void main(String[] args) {

        genCode gC = new genCode();
        frame.setContentPane(gC.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //TODO combobox1 先隐藏，因为目前没有其他功能
        gC.comboBox1.addItem("DSF");
        gC.comboBox1.addItem("前后台");
//        gC.comboBox1.hide();

        gC.table1.hide();
        //gC.textArea1.hide();
        gC.textArea1.setText("");
        gC.textArea1.setBorder(new LineBorder(new java.awt.Color(127,157,185), 1, false));
        frame.setVisible(true);

    }

}

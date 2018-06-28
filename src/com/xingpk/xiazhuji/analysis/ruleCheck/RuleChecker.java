package com.xingpk.xiazhuji.analysis.ruleCheck;

import com.util.CommonUtil;
import com.xingpk.xiazhuji.XzjVar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RuleChecker {
    private String filePath;
    private File file;
    private String filename;
    private String fileType = "";

    public RuleChecker(String filePath,String fileType) {
        this.filePath = filePath;
        this.file = new File(filePath);
        this.filename = filePath.substring(filePath.lastIndexOf(CommonUtil.getSpilter()) + 1,filePath.length());
        this.fileType = fileType;
//        if (filename.indexOf("ATS") != -1){
//            this.fileType = XzjVar.ATS;
//        }
//
//        if (filename.indexOf("ACS") != -1){
//            this.fileType = XzjVar.ACS;
//        }
//
//        if (filename.indexOf("Bos") != -1){
//            this.fileType = XzjVar.BOS;
//        }
//
//        if (filename.contains("Input")){
//            this.fileType = XzjVar.INPUT;
//        }
//
//        if (filename.indexOf("Output") != -1){
//            this.fileType = XzjVar.OUTPUT;
//        }
//
//        if (filename.indexOf(".xml") != -1 && filename.indexOf("Mapper") != -1){
//            this.fileType = XzjVar.MAPPER;
//        }
//        if (filePath.indexOf(CommonUtil.getSpilter() + "dao" + CommonUtil.getSpilter()) != -1){
//            this.fileType = XzjVar.DAO;
//        }
//
//        if (filePath.indexOf(CommonUtil.getSpilter() + "bo" + CommonUtil.getSpilter()) != -1){
//            this.fileType = XzjVar.BO;
//        }
    }

    public String checkIt(){
        try {
            switch (this.fileType){
                case XzjVar.ATS :
                    return CheckATS.Check(this.filePath,this.file,this.filename);
                case XzjVar.ACS :
                    return CheckACS.check(this.filePath,this.file,this.filename);
                case XzjVar.BOS :
                    return CheckBOS.check(this.filePath,this.file,this.filename);
                case XzjVar.INPUT :
                    return CheckIO.check(this.filePath,this.file,this.filename);
                case XzjVar.OUTPUT :
                    return CheckIO.check(this.filePath,this.file,this.filename);
                case XzjVar.MAPPER :
                    return "Can't check Mapper file;";
                case XzjVar.DAO :
                    return CheckDAO.check(this.filePath,this.file,this.filename);
                case XzjVar.BO :
                    return CheckBO.check(this.filePath,this.file,this.filename);
                default :
                    return "Can't check this type of file;";

            }
        } catch (FileNotFoundException e) {
            return CommonUtil.getStackTrace(e);
        }
    }


}

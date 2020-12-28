package com.xingpk.octspbco.workflow.generate;

import com.xingpk.octspbco.workflow.tables.pubtable.Pbco_intens_organ;
import com.xingpk.octspbco.workflow.tables.pubtable.Pbco_intens_zone;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SqlGenerator {

    //TODO 生成各种rel之前先检查是否基础数据里已经有了

    private File file;
    private String savePath;
    private String sqlFileName;

    public SqlGenerator(File file, String savePath, String sqlFileName) {
        this.file = file;
        this.savePath = savePath;
        this.sqlFileName = sqlFileName;
    }

    public void genIt(){
        StringBuffer sb = new StringBuffer();

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberOfSheets = wb.getNumberOfSheets();

        //基本参数页
        XSSFSheet zoneSheet = wb.getSheetAt(0);
        Pbco_intens_zone zoneTable = new Pbco_intens_zone(zoneSheet);

        sb.append(zoneTable.genSql());


        //机构参数页
        XSSFSheet organSheet = wb.getSheetAt(1);
        Pbco_intens_organ organTable = new Pbco_intens_organ(organSheet);

        sb.append(organTable.genSql());




    }
}

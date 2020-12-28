package com.xingpk.octspbco.workflow.tables.pubtable;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Pbco_intens_zone {
    /**集约化地区属性表
     * 用途：配置地区级开关、参数
     * 4646交易查询维护
     * 表结构：
     * 英文名	        中文名	    类型	        长度	    备注
       ZONENO	        地区号	    NUMBER	    5
       ATTRIBUTE	    属性	        VARCHAR2	20	    SWITCHON-总开关（开） 
                                                        SWITCHOFF-总开关（关）
                                                        PULL-消息模式（抓取）
                                                        PUSH-消息模式（推送）
                                                        DUPLICHKON-防重检查（开）
                                                        DUPLICHKOFF-防重检查（关）
                                                        FILEUPON-附件上传开关（开）
                                                        FILEUPOFF-附件上传开关（关）
                                                        RETRYBONUS-退回重提积分上浮（百分比）
                                                        VCHRNOIDON-OCR识别（开）
                                                        VCHRNOIDOFF-OCR识别（关）
       ACTIVE_DATE	    启用日期	    VARCHAR2	10	    暂不使用
       DEADLINE_DATE	种植日期	    VARCHAR2	10	    暂不使用
       MOD_TIME	        最后修改时间	VARCHAR2	8
       MOD_DATE	        最后修改日期	VARCHAR2	10
       MOD_TELLERNO	    最后修改柜员	NUMBER	    5

     例：insert into PBCO.PBCO_INTENS_ZONE (ZONENO, ATTRIBUTE, ACTIVE_DATE, DEADLINE_DATE, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     values (119,'RETRYBONUS=50','1900-01-01','9999-12-31','00:00:00','1900-01-01',0);
     * **/
    private XSSFSheet sheet = null;
    private String zoneno = "";
    private boolean switchFlag = true;
    private int massageFlag = 0;//1-PULL 2-PUSH
    private boolean duplichkFlag = false;
    private boolean fileupFlag = false;
    private String retrybonus = "";
    private boolean vchrnoidFlag = false;
    private static String names = "insert into PBCO.PBCO_INTENS_ZONE (ZONENO, ATTRIBUTE, ACTIVE_DATE, DEADLINE_DATE, MOD_TIME, MOD_DATE, MOD_TELLERNO)";
    private static String pubString = "'1900-01-01','9999-12-31','00:00:00','1900-01-01',0);\n";

    public Pbco_intens_zone(XSSFSheet sheetIn) {
        this.sheet = sheetIn;
        XSSFRow row = sheet.getRow(2);
        String zoneno = row.getCell(0).toString();
        if (zoneno.indexOf(".") > 0)
            zoneno = zoneno.substring(0,zoneno.indexOf("."));
        this.zoneno = zoneno;

        String msgFlag = row.getCell(3).toString();
        if (msgFlag.equals("推送"))
            this.massageFlag = 2;
        else//拉取
            this.massageFlag = 1;

        String dupFlag = row.getCell(4).toString();
        if (dupFlag.equals("是"))
            this.duplichkFlag = true;
        if (dupFlag.equals("否"))
            this.duplichkFlag = false;


        String fileup = row.getCell(5).toString();
        if (fileup.equals("是"))
            this.fileupFlag = true;
        if (fileup.equals("否"))
            this.fileupFlag = false;


        String bonus = row.getCell(6).toString();
        if (bonus.indexOf(".") > 0)
            bonus = bonus.substring(0,bonus.indexOf("."));

        this.retrybonus = bonus;

        String vchrnoid = row.getCell(7).toString();
        if (vchrnoid.equals("是"))
            this.vchrnoidFlag = true;
        if (vchrnoid.equals("否"))
            this.vchrnoidFlag = false;

    }


    public StringBuffer genSql(){
        StringBuffer sb = null;
        sb.append("-- 集约化地区属性表\n");
        //前后台模式开关
        sb.append("-- 前后台模式开关\n");
        sb.append(names);
        sb.append(" values (" + zoneno + ", ");
        if (switchFlag)
        sb.append("'SWITCHON', ");
        sb.append(pubString);

        //消息模式
        sb.append("-- 消息模式\n");
        sb.append(names);
        sb.append(" values (" + zoneno + ", ");
        if (massageFlag == 2)
            sb.append("'PUSH', ");
        else
            sb.append("'PULL', ");
        sb.append(pubString);

        //防重检查
        sb.append("-- 防重检查\n");
        sb.append(names);
        sb.append(" values (" + zoneno + ", ");
        if (duplichkFlag)
            sb.append("'DUPLICHKON', ");
        else
            sb.append("'DUPLICHKOFF', ");
        sb.append(pubString);

        //附件上传开关
        sb.append("-- 附件上传开关\n");
        sb.append(names);
        sb.append(" values (" + zoneno + ", ");
        if (fileupFlag)
            sb.append("'FILEUPON', ");
        else
            sb.append("'FILEUPOFF', ");
        sb.append(pubString);

        //退回重提积分上浮（百分比）
        sb.append("-- 退回重提积分上浮（百分比）\n");
        sb.append(names);
        sb.append(" values (" + zoneno + ", ");
        sb.append("'RETRYBONUS=" +retrybonus + ", " );
        sb.append(pubString);

        //OCR识别
        sb.append("-- OCR识别\n");
        sb.append(names);
        sb.append(" values (" + zoneno + ", ");
        if (vchrnoidFlag)
            sb.append("'VCHRNOIDON', ");
        else
            sb.append("'VCHRNOIDOFF', ");
        sb.append(pubString);

        return sb;
    }
}

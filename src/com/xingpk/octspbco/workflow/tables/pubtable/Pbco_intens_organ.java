package com.xingpk.octspbco.workflow.tables.pubtable;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.HashMap;

public class Pbco_intens_organ {
    /**集约化机构定义表
     * 用途：配置网点属性
     * 4647交易查询维护
     * 表结构：
     * 英文名	    中文名	        类型	        长度	    备注
     * ZONENO	    地区号	        NUMBER	    5
       BRNO	        网点号	        NUMBER	    5
       TYPE	        机构类型	        NUMBER	    1	    1-受理网点 2-处理网点
       PRIORITY	    优先级（积分）	NUMBER	    3	    受理网点使用
       MOD_TIME	    最后修改时间	    VARCHAR2	8
       MOD_DATE	    最后修改日期	    VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	    NUMBER	    5
     例：
     insert into PBCO.PBCO_INTENS_ORGAN (ZONENO,BRNO,TYPE,PRIORITY,MOD_TIME,MOD_DATE,MOD_TELLERNO)
     values (119,103,1,100,'00:00:00','1900-01-01',0);**/


    private String zoneno = "";
    private ArrayList<HashMap> organ = new ArrayList<>();
    //hashmap结构↓
    //"brno": 100
    //"type": 1
    //"priority": 100

    private static String names = "insert into PBCO.PBCO_INTENS_ORGAN (ZONENO,BRNO,TYPE,PRIORITY,MOD_TIME,MOD_DATE,MOD_TELLERNO)";
    private static String pubString = "'00:00:00','1900-01-01',0);\n";

    public Pbco_intens_organ(String zoneno) {
        this.zoneno = zoneno;
    }

    public Pbco_intens_organ(XSSFSheet sheet){
        int rowNumber = 2;
        while (true) {
            XSSFRow  row = sheet.getRow(rowNumber);
            if (!row.getCell(0).getRichStringCellValue().toString().trim().equals("")) {
                HashMap organMap = new HashMap();
                organMap.put("brno", row.getCell(0).toString());
                //TODO  不获取小数点
                if (row.getCell(1).equals("受理网点")){
                    organMap.put("type", "1");
                }
                if (row.getCell(1).equals("处理网点")){
                    organMap.put("type", "2");
                }
                organMap.put("priority", row.getCell(2).toString());
                //TODO  不获取小数点
                this.pushData(organMap);
            }else{
                break;
            }
            rowNumber++;
        }
    }

    public void pushData(HashMap data){
        if (data.size() == 3)
            organ.add(data);
    }

    public StringBuffer genSql(){
        StringBuffer sb = null;
        sb.append("-- 集约化机构定义表\n");
        int i = 0;
        while (i < organ.size()){
            sb.append(names);
            sb.append(" values (" + zoneno + ", " );
            sb.append(organ.get(i).get("brno") + ", ");
            sb.append(organ.get(i).get("type") + ", ");
            sb.append(organ.get(i).get("priority") + ", ");
            sb.append(pubString);
            i++;
        }

        return sb;
    }

}

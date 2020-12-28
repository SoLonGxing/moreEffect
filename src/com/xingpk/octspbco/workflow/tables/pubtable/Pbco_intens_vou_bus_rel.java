package com.xingpk.octspbco.workflow.tables.pubtable;

import java.util.ArrayList;
import java.util.HashMap;

public class Pbco_intens_vou_bus_rel {
    /**集约化凭证种类与业务种类关系表
     * 用途：配置凭证与业务种类的关系
     * 4649交易查询维护
     * 表结构：
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5
       VOUTH	    凭证种类	    VARCHAR2	20	    四位数字
       BUSINESS	    业务种类	    VARCHAR2	10	    业务大类、业务小类的代码
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     例：
     insert into PBCO.PBCO_INTENS_VOU_BUS_REL (ZONENO, VOUTH, BUSINESS, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     values (119,'1001','10001','00:00:00','1900-01-01',0);**/

    private String zoneno = "";
    private ArrayList<HashMap> vouthBusiness = new ArrayList<>();
    //hashmap结构↓
    //"vouth": "1001"
    //"business": "10001"

    private static String names = "insert into PBCO.PBCO_INTENS_VOU_BUS_REL (ZONENO, VOUTH, BUSINESS, MOD_TIME, MOD_DATE, MOD_TELLERNO)";
    private static String pubString = "'00:00:00','1900-01-01',0);\n";

    public Pbco_intens_vou_bus_rel(String zoneno) {
        this.zoneno = zoneno;
    }

    public void pushData(HashMap data){
        if (data.size() == 2)
            vouthBusiness.add(data);
    }

    public StringBuffer genSql(){
        StringBuffer sb = null;
        sb.append("-- 集约化凭证种类与业务种类关系表\n");
        int i = 0;
        while (i < vouthBusiness.size()){
            sb.append(names);
            sb.append(" values (" + zoneno + ", '" );
            sb.append(vouthBusiness.get(i).get("vouth") + "', '");
            sb.append(vouthBusiness.get(i).get("business") + "', ");
            sb.append(pubString);
            i++;
        }

        return sb;
    }

}

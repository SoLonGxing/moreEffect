package com.xingpk.octspbco.workflow.tables.pubtable;

import java.util.ArrayList;
import java.util.HashMap;

public class Pbco_intens_vouth {
    /**集约化凭证种类定义表
     * 用途：配置凭证信息
     * 4649交易查询维护
     * 表结构：
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5
       VOUTH	    凭证种类	    VARCHAR2	20	    四位数字
       NAME	        凭证名称	    VARCHAR2	60
       DESCRIPTION	描述      	VARCHAR2	300	    暂不使用
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     例：
     insert into PBCO.PBCO_INTENS_VOUTH (ZONENO, VOUTH, NAME, DESCRIPTION, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     values (119,'1001','汇款申请单','','00:00:00','1900-01-01',0);**/

    private String zoneno = "";
    private ArrayList<HashMap> vouthMessage = new ArrayList<>();
    //hashmap结构↓
    //"vouth": "1001"
    //"name": "汇款申请单"
    //"description": ""
    private static String names = "insert into PBCO.PBCO_INTENS_VOUTH (ZONENO, VOUTH, NAME, DESCRIPTION, MOD_TIME, MOD_DATE, MOD_TELLERNO)";
    private static String pubString = "'00:00:00','1900-01-01',0);\n";


    public Pbco_intens_vouth(String zoneno) {
        this.zoneno = zoneno;
    }

    public void pushData(HashMap data){
        if (data.size() == 3)
        vouthMessage.add(data);
    }

    public StringBuffer genSql(){
        StringBuffer sb = null;
        sb.append("-- 集约化凭证种类定义表\n");
        int i = 0;
        while (i < vouthMessage.size()){
            sb.append(names);
            sb.append(" values (" + zoneno + ", '" );
            sb.append(vouthMessage.get(i).get("vouth") + "', '");
            sb.append(vouthMessage.get(i).get("name") + "', '");
            sb.append(vouthMessage.get(i).get("description") + "', ");
            sb.append(pubString);
            i++;
        }

        return sb;
    }
}

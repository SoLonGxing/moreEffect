package com.xingpk.octspbco.workflow.tables.pubtable;

import java.util.ArrayList;
import java.util.HashMap;

public class Pbco_intens_organ_rel {
    /**集约化机构关系表
     * 用途：配置受理网点与处理网点的关系
     * 4647交易查询维护
     * 表结构
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5
       BRNO	        网点号	    NUMBER	    5
       ZONENO_ACCP	受理地区号	NUMBER	    5
       BRNO_ACCP	受理网点号	NUMBER	    5
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     例：
     insert into PBCO.PBCO_INTENS_ORGAN_REL (ZONENO, BRNO, ZONENO_ACCP, BRNO_ACCP, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     values (119,100,119,101,'00:00:00','1900-01-01',0);**/

    private String zoneno = "";
    private ArrayList<HashMap> organ_rel = new ArrayList<>();
    //hashmap结构↓
    //"brno": 100
    //"brno_accp": 101

    private static String names = "insert into PBCO.PBCO_INTENS_ORGAN_REL (ZONENO, BRNO, ZONENO_ACCP, BRNO_ACCP, MOD_TIME, MOD_DATE, MOD_TELLERNO)";
    private static String pubString = "'00:00:00','1900-01-01',0);\n";

    public Pbco_intens_organ_rel(String zoneno) {
        this.zoneno = zoneno;
    }

    public void pushData(HashMap data){
        if (data.size() == 2)
            organ_rel.add(data);
    }

    public StringBuffer genSql(){
        StringBuffer sb = null;
        sb.append("-- 集约化机构关系表\n");
        int i = 0;
        while (i < organ_rel.size()){
            sb.append(names);
            sb.append(" values (" + zoneno + ", " );
            sb.append(organ_rel.get(i).get("brno") + ", ");
            sb.append(" values (" + zoneno + ", " );
            sb.append(organ_rel.get(i).get("brno_accp") + ", ");
            sb.append(pubString);
            i++;
        }

        return sb;
    }
}

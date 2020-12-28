package com.xingpk.octspbco.workflow.tables.pubtable;

public class Pbco_intens_group {
    /**集约化群组定义表
     * 用途：配置群组属性
     * 4648交易查询维护
     * 表结构：
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5
       GROUP_ID	    群组	        VARCHAR2	10
       EMAIL	    电邮地址	    VARCHAR2	50	    暂不使用
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     例：
     insert into PBCO.PBCO_INTENS_GROUP (ZONENO, GROUP_ID, EMAIL, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     values （119，'G00001','','00:00:00',1900-01-01',0);**/
}

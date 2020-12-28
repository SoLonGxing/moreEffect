package com.xingpk.octspbco.workflow.tables.pubtable;

public class Pbco_intens_group_member {
    /**集约化群组成员表
     * 用途：配置群组内包含的成员信息
     * 4648交易查询维护
     * 表结构：
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5
       GROUP_ID	    群组	        VARCHAR2	10
       TELLERNO	    柜员号	    NUMBER	    5
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     例：
     insert into PBCO.PBCO_INTENS_GROUP_MEMBER (ZONENO, GROUP_ID, TELLERNO, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     values (119,'G00001',97023,;00:00:00','1900-01-01',0);**/


    //TODO 暂时没有生成的好办法
}

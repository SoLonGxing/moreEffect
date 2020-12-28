package com.xingpk.octspbco.workflow.tables.prvttable;

public class Pbco_intens_bus_org_rel {
    /**集约化业务种类与机构关系表
     * 用途：配置有那个地区的哪个网点可以处理由某个地区受理的某个业务种类
     * 如需新增业务小类则需登记此表
     * 表结构：
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5	    处理地区号
       BRNO	        网点号	    NUMBER	    5	    处理网点号
       ZONENO_ACCP	受理地区号	NUMBER	    5	    受理地区号
       BUSINESS	    业务种类	    VARCHAR2	10	    业务小类代码
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     例：1000网点可以处理该业务小类
     Insert into PBCO.PBCO_INTENS_BUS_ORG_REL
     (ZONENO, BRNO, ZONENO_ACCP, BUSINESS, MOD_TIME, MOD_DAT, MOD_TELLERNO)
     values (119,1000,119,'10301','00:00:00','1900-01-01',0);**/
}

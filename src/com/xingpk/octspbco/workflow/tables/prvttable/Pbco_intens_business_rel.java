package com.xingpk.octspbco.workflow.tables.prvttable;

public class Pbco_intens_business_rel {
    /**集约化业务种类关系表
     *用途：定义业务大类与业务小类的归属关系
     * 如需新增业务大类或业务小类则需登记此表
     * 表结构：
     * 英文名	        中文名	    类型	        长度	    备注
       ZONENO	        地区号	    NUMBER	    5
       BUSINESS	        业务种类	    VARCHAR2	10	    业务大类、业务小类的代码
       PARENT_BUSINESS	上级业务种类	VARCHAR2	10	    该业务小类归属的业务大类，如果是业务大类则此字段登记为null
       MOD_TIME	        最后修改时间	VARCHAR2	8
       MOD_DATE	        最后修改日期	VARCHAR2	10
       MOD_TELLERNO	    最后修改柜员	NUMBER	    5
     *
     * 例：
     * Insert into PBCO.PBCO_INTENS_BUSINESS_REL
     * (ZONENO, BUSINESS, PARENT_BUSINESS, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     * values (119,'103',null,'00:00:00','1900-01-01',0);**/
}

package com.xingpk.octspbco.workflow.tables.prvttable;

public class Pbco_intens_business {
    /**集约化业务种类定义表
     * 用途：定义所有业务大小类
     * 如需新增业务大类或业务小类则需登记此表
     * 表结构：
     * 英文名	    中文名	        类型	        长度	    备注
       ZONENO	    地区号	        NUMBER	    5
       BUSINESS	    业务种类	        VARCHAR2	10	    业务大类、业务小类的代码
       PRIORITY	    优先级（积分）	NUMBER	    3
       APPNO	    应用	            VARCHAR2	10	    规则如下：
                                                        1-2位：应用号  00通用、01存款、02贷款、03汇款、04会计
                                                        3位：是否强流程控制 0否1是
                                                        4位：反交易标志 0否1是
                                                        5-10位：配置校验及获取交易信息方法名
       MOD_TIME	    最后修改时间	    VARCHAR2	8
       MOD_DATE	    最后修改日期	    VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	    NUMBER	    5

     例：
     Insert into PBCO.PBCO_INTENS_BUSINESS
     (ZONENO, BUSINESS, PRIORITY, APPNO, MOD_TIME, MOD_DATE, MOD_TELLERNO)
     values (119,'103',0,null,'00:00:00','1900-01-01',0);
     * **/
}

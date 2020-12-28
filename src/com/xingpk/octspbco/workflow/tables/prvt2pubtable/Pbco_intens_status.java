package com.xingpk.octspbco.workflow.tables.prvt2pubtable;

public class Pbco_intens_status {
    /**集约化状态定义表
     * 用途：定义状态
     * 如需新增流转状态则需登记此表
     * 表结构：
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5	    地区号
       STATUS	    状态	        VARCHAR2	10	    状态ID，规则为：S+5位数字，如S90001
                                                    数字左数第二位：为1的为由某人锁定的状态，为2的为挂起中的状态
                                                    数字左起第三位：为0的为流程中的状态，为1的为已成功的状态，为2的为作废状态，为3的为XXXXXX
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     例：
     Insert into PBCO.PBCO_INTENS_STATUS (ZONENO,STATUS,MOD_TIME,MOD_DATE,MOD_TELLERNO)
     values (119,'S90001','00:00:00','1900-01-01',0);**/

    private static final String TABLENAME = "PBCO_INTENS_STATUS";
}

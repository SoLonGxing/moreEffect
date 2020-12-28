package com.xingpk.octspbco.workflow.tables.prvttable;

public class Pbco_intens_status_ctrl {
    /**集约化状态权限控制表
     * 用途：配置某个业务小类的流程中，流转操作间的控制关系
     * 如需新增业务小类则需登记此表
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5	    地区号
       BUSINESS	    业务种类	    VARCHAR2	10	    业务小类代码
       STATUS	    状态	        VARCHAR2	10	    当前状态
       ACTION	    动作	        VARCHAR2	10	    当前执行动作
       LAST_STATUS	上一状态	    VARCHAR2	10	    上一次执行的状态
       LAST_ACTION	上一动作	    VARCHAR2	10	    上一次在该状态下执行的动作
       CONTROL	    权限控制	    NUMBER	    3	    他们之间的关系
       MOD_TIME	    最后修改时间	VARCHAR2	8	    1、必须为相同柜员；2、必须为不同柜员；3、必须为转交关系
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5

     例：Insert into PBCO.PBCO_INTENS_STATUS_CTRL
        (ZONENO,BUSINESS,STATUS,ACTION,LAST_STATUS,LAST_ACTION,CONTROL,MOD_TIME,MOD_DATE,MOD_TELLERNO)
        values (119,'10301','S90003','A90002','S90001','A90001',1,'00:00:00','1900-01-01',0);
     */
}

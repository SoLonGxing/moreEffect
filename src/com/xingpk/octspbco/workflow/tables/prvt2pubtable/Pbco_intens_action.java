package com.xingpk.octspbco.workflow.tables.prvt2pubtable;

public class Pbco_intens_action {
    /**集约化动作定义表
     * 用途：定义动作
     * 如需新增流转动作择婿登记此表
     * 表结构
     * 英文名	中文名	类型	长度	备注
       ZONENO	地区号	NUMBER	5	地区号
       ACTION	动作	VARCHAR2	10	动作ID，规则为：A+5位数字，如A90001
       TYPE1	动作类型1	NUMBER	1	0-受理；1-处理；2复核；9-超级管理员
       TYPE2	动作类型2	NUMBER	1	0-其他；1-领用；2-提交；3-后台间退回；4-后台退回前台；5-挂起；6-激活
       TYPE3	动作类型3	NUMBER	1	备用
       TYPE4	动作类型4	NUMBER	1	备用
       TYPE5	动作类型5	NUMBER	1	备用
       TYPE6	动作类型6	NUMBER	1	备用
       TYPE7	动作类型7	NUMBER	1	备用
       TYPE8	动作类型8	NUMBER	1	备用
       MOD_TIME	最后修改时间	VARCHAR2	8
       MOD_DATE	最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	5

     例：
     Insert into PBCO.PBCO_INTENS_ACTION
     (ZONENO,ACTION,TYPE1,TYPE2,TYPE3,TYPE4,TYPE5,TYPE6,TYPE7,TYPE8,MOD_TIME,MOD_DAT,MOD_TELLERNO)
     values (119,'A90001',0,0,0,0,0,0,0,0,'00:00:00','1900-01-01',0);**/
}

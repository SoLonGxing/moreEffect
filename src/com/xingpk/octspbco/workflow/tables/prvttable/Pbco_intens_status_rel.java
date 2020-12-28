package com.xingpk.octspbco.workflow.tables.prvttable;

public class Pbco_intens_status_rel {
    /**集约化状态关系表
     * 用途：配置某个业务小类的流程参数
     * 如需新增业务小类则需登记此表
     * 表结构：
     * 英文名	    中文名	        类型	        长度	    备注
       ZONENO	    地区号	        NUMBER	    5	    地区号
       BUSINESS	    业务种类	        VARCHAR2	10	    业务小类代码
       STATUS	    状态	            VARCHAR2	10	    状态id
       ACTION	    动作	            VARCHAR2	10	    该状态下可执行的动作ID
       ACTION_TYPE	动作类型	        NUMBER	    3	    该动作类型：1-领用；2-提交；3-转交
       ENFORCE	    直接跳转标志	    NUMBER	    3	    默认为1
       GROUP_ID	    群组      	    VARCHAR2	10	    该状态下可以执行这个动作的群组
       SEQ	        序号	            NUMBER	    5	    默认为0
       LIMIT	    超时时间（小时）	NUMBER	    5	    备用
       NEXT_STATUS	下一状态	        VARCHAR2	10	    执行后申请单流转的状态ID
       MESSAGE	    是否邮件通知	    NUMBER	    1	    执行该操作后进行邮件通知的类型 1-通知后续处理人；2-通知前台网点（本票）；3-通知受理柜员
       MOD_TIME	    最后修改时间	    VARCHAR2	8
       MOD_DATE	    最后修改日期	    VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	    NUMBER	    5

     例：
     Insert into PBCO.[BCO_INTENS_STATUS_REL
     (ZONENO,BUSINESS,STATUS,ACTION,ACTION_TYPE,ENFORCE,GROUP_ID,SEQ,LIMIT,NEXT_STATUS,MESSAGE,MOD_TIME,MOD_DATE,MOD_TELLER)
     values (119,'10301','S90001','A90001',2,1,'G00003',1,'00:00:00','1900-01-01',0);**/
}

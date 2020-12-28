package com.xingpk.octspbco.workflow.tables.prvttable;

public class Pbco_intens_stat_trx_rel {
    /**集约化状态与交易代码关系表
     * 用途：配置某个业务小类的流程中，在某个流转状态下需要做的FOVA交易代码
     * 如需新增业务小类择婿登记此表
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5	    地区号
       BUSINESS	    业务种类	    VARCHAR2	10	    交易小类代码
       STATUS	    状态	        VARCHAR2	5	    状态ID
       SEQ	        序号	        NUMBER	    5	    区分多个交易、从0开始
       TRXCODE	    交易代码	    NUMBER	    5	    当前状态下可以使用的交易
       LAYER	    共享组别	    NUMBER	    3	    默认为1
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5
     *
     * 例：
     * Insert into PBCO.PBCO_INTENS_STAT_TRX_REL
     * (ZONENO,BUSINESS,STATUS,SEQ,TRXCODE,LAYER,MOD_TIME,MOD_DATE,MOD_TELLERNO)
     * values (119,'10301','S91001',0,3440,1,'00:00:00','1900-01-01',0);**/
}

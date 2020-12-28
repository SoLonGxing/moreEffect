package com.xingpk.octspbco.workflow.tables.pubtable;

public class Pbco_intens_group_ctrl {
    /**暂不使用
     * 集约化群组控制表
     * 用途：配置群组的互斥关系，如某个柜员不能同属于某两个组
     * 暂不使用
     * 表结构：
     * 英文名	    中文名	    类型	        长度	    备注
       ZONENO	    地区号	    NUMBER	    5
       SEQ	        序号  	    NUMBER	    5
       GROUP_ID	    群组  	    VARCHAR2	10
       TYPE	        控制类型	    NUMBER	    3	    1-非 2-与
       MOD_TIME	    最后修改时间	VARCHAR2	8
       MOD_DATE	    最后修改日期	VARCHAR2	10
       MOD_TELLERNO	最后修改柜员	NUMBER	    5	**/
}

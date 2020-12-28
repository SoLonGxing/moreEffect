package com.xingpk.octspbco.workflow.tables.prvt2pubtable;

public class Pbco_intens_nls {
    /**
     * 集约化数据字典国际化表
     * 用途：配置多语言信息
     * 注意：如需新增群组、业务大类、业务小类、流转状态、流转动作、常用意见需登记此表
     *      即：GROUP、BUSINESS、STATUS、ACTION、DESC
     * 表结构：
     * 英文名      中文名        类型           长度     说明
     * ZONENO     地区号        NUMBER         5       地区号
     * ID         ID           VARCHAR2       10      GROUP群组；BUSINESS业务种类；STATUS状态；ACTION动作；DESC常用意见
     * CODE       字典          VARCHAR2       10      代码值
     * LOCALE     语言种类      VARCHAR2       20       zh_CN；en_US
     * NAME       名称         VARCHAR2        150     含义
     * DESCRIPTION 描述         VARCHAR2      600      备用
     *
     * 例：
     * Insert into PBCO.PBCO_INTENS_NLS(ZONENO,ID,CODE,LOCALE,NAME,DESCRIPTION) values
     * (119,'BUSINESS','103','en_US','Agreement',null);
     *      */

}

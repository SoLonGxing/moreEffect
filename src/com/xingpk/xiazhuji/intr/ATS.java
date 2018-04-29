package com.xingpk.xiazhuji.intr;

public interface ATS {

    String printAtsClass();

    //生成Ats的reponse那一段
    String makeResponseString();

    //生成调用Acs的那一行
    String makeCallString(ACS Acs, String intent);

    //生成调用失败的那一段
    String makeFailString(ACS Acs);
}

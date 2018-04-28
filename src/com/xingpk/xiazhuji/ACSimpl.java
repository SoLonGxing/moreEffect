package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;

public class ACSimpl implements ACS{
    private String ACSName;
    private IO input;
    private IO output;
    private String servicName;

    public void setServiceName(String serviceName) {
        this.servicName = serviceName;
    }


    public ACSimpl(String name) {
        this.ACSName = name;
        setServiceName(name.substring(0,1).toLowerCase() + name.substring(1,name.length()));

        //建立输入输出
        //TODO 是否要加"BOS"在结尾
        this.input = new AtsIO(name + "Input");
        this.output = new AtsIO(name + "Output");
    }


    @Override
    public String printAtsClass() {
        String classStream =null;


        return classStream;
    }
}

package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;

import java.util.List;


public class ATSimpl implements com.xingpk.xiazhuji.intr.ATS{
    private String name;
    private IO input;
    private IO output;
    private List<ACS> acsList;
    private String filePath;
    private String serviceName;



    public ATSimpl(String name){
        this.name = name;
        setServiceName(name.substring(0,1).toLowerCase() + name.substring(1,name.length()));

        //建立输入输出
        //TODO 是否要加"ATS"在结尾
        AtsIO input = new AtsIO(name + "Input");
        AtsIO output = new AtsIO(name + "Output");


    }
    public void setFilePath(String path){
        this.filePath = path;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {

        this.serviceName = serviceName;
    }

    @Override
    public void printAtsClass() {

    }
}

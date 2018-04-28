package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;

import java.util.List;


public class ATSimpl implements com.xingpk.xiazhuji.intr.ATS{
    private String name;
    private IO input;
    private IO output;
    private List<ACS> acsList;
    private String serviceName;
    private String packagePath;
    public List<ACS> getAcsList() {
        return acsList;
    }

    public void setAcsList(List<ACS> acsList) {

        this.acsList = acsList;
    }

    public ATSimpl(String name, String packagePath){
        this.name = name;
        this.packagePath = packagePath;
        setServiceName(name.substring(0,1).toLowerCase() + name.substring(1,name.length()));

        //建立输入输出
        //TODO 是否要加"ATS"在结尾
        this.input = new AtsIO(name + "Input");
        this.output = new AtsIO(name + "Output");


    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {

        this.serviceName = serviceName;
    }

    @Override
    public String printAtsClass() {
        String classStream =null;


        return classStream;
    }
}

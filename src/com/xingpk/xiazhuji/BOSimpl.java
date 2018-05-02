package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.BOS;
import com.xingpk.xiazhuji.intr.IO;
import com.xingpk.xiazhuji.intr.Pbrb2ServiceClass;

import java.util.List;

public class BOSimpl implements BOS{
    private static String importString = "import java.math.BigDecimal;" +
            "import java.text.ParseException;" +
            "import org.springframework.beans.factory.annotation.Autowired;" +
            "import com.icbc.fova.common.ActionError;" +
            "import com.icbc.fova.common.ActionResult;" +
            "import com.icbc.fova.service.AbstractBussObjService;";
    private String ioImportString;
    private String bosClassName;
    private IO input;
    private IO output;
    private String serviceName;


    public BOSimpl(String name, String packagePath){
        this.bosClassName = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        this.packagePath = packagePath + ".bos;";
        this.serviceName = name.substring(0,1).toLowerCase() + name.substring(1,name.length());

        //TODO 是否要加"BOS"在结尾
        this.input = new BosIO(name + "Input");
        this.output = new BosIO(name + "Output");
    }

    @Override
    public String makeVarImportString(List<Pbrb2ServiceClass> list) {
        return null;
    }

    @Override
    public String makeAnnotationString() {
        return null;
    }

    @Override
    public String makeBegainString() {
        return null;
    }

    @Override
    public String makeResponseString() {
        return null;
    }

    @Override
    public String makeCallString(Pbrb2ServiceClass serviceClass, String indent) {
        return null;
    }

    @Override
    public String makeFailString(Pbrb2ServiceClass serviceClass) {
        return null;
    }

    @Override
    public String makeEndString() {
        return null;
    }


    public void setBosClassName(String bosClassName) {
        this.bosClassName = bosClassName;
    }

    public void setInput(IO input) {
        this.input = input;
    }

    public void setOutput(IO output) {
        this.output = output;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public void setIoPath(String ioPath) {
        this.ioPath = ioPath;
    }

    public String getBosClassName() {
        return bosClassName;
    }


    public IO getInput() {
        return input;
    }

    public IO getOutput() {
        return output;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getIoPath() {
        return ioPath;
    }

    private String packagePath;
    private String ioPath;

    @Override
    public String printAtsClass() {
        return null;
    }
}

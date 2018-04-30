package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.IO;

public class AtsIO implements IO{
    private String className;
    private String varName;
    private String outputString;


    public AtsIO(String name) {
        this.className = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        this.varName = name.substring(0,1).toLowerCase() + name.substring(1,name.length());
    }

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {

        this.outputString = outputString;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    @Override
    public void printOutClass() {

    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public String getVarName() {
        return this.varName;
    }
}

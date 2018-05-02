package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.IO;

public class BosIO implements IO{

    private String className;
    private String varName;
    private String outputString;

    public BosIO(String name) {
        this.className = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        this.varName = name.substring(0,1).toLowerCase() + name.substring(1,name.length());
    }

    @Override
    public void printOutClass() {

    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public String getOutputString() {

        return outputString;
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public String getVarName() {
        return null;
    }
}

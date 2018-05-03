package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.IO;

public class AcsIO implements IO{
    public AcsIO(String name) {
        this.className = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        this.varName = name.substring(0,1).toLowerCase() + name.substring(1,name.length());
    }

    private String className;
    private String varName;
    private String outputString;
    

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }


    public void setClassName(String className) {
        this.className = className;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getClassName() {

        return className;
    }

    public String getVarName() {
        return varName;
    }

    public String getOutputString() {
        return outputString;
    }

    @Override
    public void printOutClass() {

    }
}

package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.IO;

public class AcsIO implements IO{
    private String name;
    private String outputString;

    public void setName(String name) {
        this.name = name;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public String getName() {

        return name;
    }

    public String getOutputString() {
        return outputString;
    }

    @Override
    public void printOutClass() {

    }
}

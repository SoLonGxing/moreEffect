package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.IO;

public class AtsIO implements IO{
    private String name;
    private String outputString;


    public AtsIO(String name) {
        this.name = name;
    }

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {

        this.outputString = outputString;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public void printOutClass() {

    }
}

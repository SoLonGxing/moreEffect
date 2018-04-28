package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ATS;

public class xiazhujiRoot {
    private ATS ats;
    private String filePath = "C:/";

    public xiazhujiRoot() {

    }

    public xiazhujiRoot(ATS ats) {
        this.ats = ats;
    }

    public ATS getAts() {

        return ats;
    }

    public void setAts(ATS ats) {

        this.ats = ats;
    }



}

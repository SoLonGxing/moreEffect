package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.ATS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class xiazhujiRoot {
    private ATS ats;
    private ACS acs;

    private String filePath4MAC = "~/Documents";

    public xiazhujiRoot(ATS ats, ACS acs) {
        this.ats = ats;
        this.acs = acs;
    }

    public xiazhujiRoot(ATS ats) {
        this.ats = ats;
    }

    public void printFile(){
        File file = new File(filePath4MAC);
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write
        try (FileWriter fw = new FileWriter(file, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.ats.printAtsClass());
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Write failed" );
        }

    }

    public void testPrintFile(){
        System.out.println(this.ats.printAtsClass());
    }

    public ATS getAts() {

        return ats;
    }

    public void setAts(ATS ats) {

        this.ats = ats;
    }

    public ACS getAcs() {
        return acs;
    }

    public void setAcs(ACS acs) {

        this.acs = acs;
    }

}

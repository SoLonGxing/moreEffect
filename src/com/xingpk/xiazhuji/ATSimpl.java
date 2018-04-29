package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;

import java.util.List;


public class ATSimpl implements com.xingpk.xiazhuji.intr.ATS{
    private static String a="a",b="b";
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
        this.packagePath = packagePath + ";";
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

    @Override
    public String makeResponseString() {
        String responseString = "//生成输出/n" +
                "OperationResponse<" + this.output + "> response = new OperationResponse<" + this.output + ">(OperationStage.TRY, OperationResult.FAIL);" +
                "response.setData(new "+ this.output + "());";
        return responseString;
    }

    @Override
    public String makeCallString(ACS Acs, String indent) {
        String callString = "if (" + Acs.getOutput() + ".getResult().compareTo(OperationResult.FAIL) == 0){/n" +
                "   response.setResult(" + Acs.getOutput() + ".getResult());/n" +
                "   response.getData().setTransOk(1);/n" +
                "   response.getData().setErrcode(Long.valueOf(" + Acs.getOutput() + ".getErrorCode()));" +
                "   response.getData().setTableName(" + Acs.getOutput() + ".getMessage());/n" +
                "   return response;/n" +
                "}";
        return callString;
    }

    @Override
    public String makeFailString(ACS Acs) {
        String failString = "if (" + Acs.getOutput() + ".getResult().compareTo(OperationResult.FAIL) == 0){/n" +
                "   response.setResult(" + Acs.getOutput() + ".getResult());/n" +
                "   response.getData().setTransOk(1);/n" +
                "   response.getData().setErrcode(Long.valueOf(" + Acs.getOutput() + ".getErrorCode()));" +
                "   response.getData().setTableName(" + Acs.getOutput() + ".getMessage());/n" +
                "   return response;/n" +
                "}";
        return failString;
    }
}

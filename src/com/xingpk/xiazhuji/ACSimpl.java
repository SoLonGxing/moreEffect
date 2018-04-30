package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;

public class ACSimpl implements ACS{
    private static String importString = "import com.fova.common.ActionResult;\n" +
            "import com.icbc.fova.common.CommonUtils;\n" +
            "import com.icbc.fova.common.ServiceExcutor;" +
            "import com.icbc.fova.tx.FovaTx;\n" +
            "import com.icbc.stars.transaction.common.OperationResponse;\n" +
            "import com.icbc.stars.transaction.common.OperationResult;\n" +
            "import com.icbc.stars.transaction.common.OperationStage;\n" +
            "import com.icbc.stars.transaction.service.AbstractCommonService;\n\n";
    private String annotation = "@AppComponentService(RouteKey=\"\" , Servicename=\"\";\n";
    private String acsClaccName;
    private IO input;
    private IO output;
    private String serviceName;
    private String packagePath;
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public ACSimpl(String name, String packagePath) {
        this.acsClaccName = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        this.packagePath = packagePath + ".acs;";
        setServiceName(name.substring(0,1).toLowerCase() + name.substring(1,name.length()));

        //建立输入输出
        //TODO 是否要加"BOS"在结尾
        this.input = new AcsIO(name + "Input");
        this.output = new AcsIO(name + "Output");
    }

    public String makeAnnotationString(){
        return "@AppComponentService(RouteKey=\"\" , Servicename=\"" + this.serviceName + "\";\n";
    }

    public String makeBegainString(){
        return "public class " + this.acsClaccName + " extends AbstractCommonService<" + this.input.getClassName() + ", " + this.output.getClassName() + ">{\n\n";
    }

    public String makeResponseString() {
        String responseString = "//生成输出\n" +
                "OperationResponse<" + this.output.getClassName() + "> Response = new OperationResponse<" + this.output.getClassName() + ">(OperationStage.TRY, OperationResult.FAIL);\n";

        return responseString;
    }

    //call BOS TODO
    public String makeCallString(String calledBosServiceName) {

        return null;
    }

    //call BOS fail TODO
    public String makeFailString(){
        return null;
    }

    public String makeEndString(){
        return  "response.setResult(OperationResult.SUCCESS);\n" +
                "return response;" +
                "    }\n" +
                "}";
    }

    @Override
    public String printAtsClass() {
        String classStream =null;


        return classStream;
    }

    public void setAcsClaccName(String acsClaccName) {

        this.acsClaccName = acsClaccName;
    }

    public void setInput(IO input) {

        this.input = input;
    }

    public void setOutput(IO output) {

        this.output = output;
    }

    public void setServicName(String servicName) {

        this.serviceName = servicName;
    }

    public String getAcsClaccName() {

        return acsClaccName;
    }

    public IO getInput() {
        return input;
    }

    public IO getOutput() {
        return output;
    }

    public String getServicName() {
        return serviceName;
    }



}

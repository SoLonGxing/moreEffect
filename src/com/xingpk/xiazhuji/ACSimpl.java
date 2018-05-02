package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;
import com.xingpk.xiazhuji.intr.Pbrb2ServiceClass;

import java.util.List;

public class ACSimpl implements ACS{
    private static String importString = "import com.fova.common.ActionResult;\n" +
            "import com.icbc.fova.common.CommonUtils;\n" +
            "import com.icbc.fova.common.ServiceExcutor;" +
            "import com.icbc.fova.tx.FovaTx;\n" +
            "import com.icbc.stars.transaction.common.OperationResponse;\n" +
            "import com.icbc.stars.transaction.common.OperationResult;\n" +
            "import com.icbc.stars.transaction.common.OperationStage;\n" +
            "import com.icbc.stars.transaction.service.AbstractCommonService;\n\n";
    private String ioImportString;
    private String acsClassName;
    private IO input;
    private IO output;
    private String serviceName;
    private String packagePath;
    private List<Pbrb2ServiceClass> bosList;
    private String ioPath;
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public ACSimpl(String name, String packagePath) {
        this.acsClassName = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        this.packagePath = packagePath + ".acs;";
        this.ioPath = packagePath + ".io";
        setServiceName(name.substring(0,1).toLowerCase() + name.substring(1,name.length()));

        //建立输入输出
        //TODO 是否要加"ACS"在结尾
        this.input = new AcsIO(name + "Input");
        this.output = new AcsIO(name + "Output");
    }

    @Override
    public String makeVarImportString(List list) {

        String varImportString ="";
        for(Pbrb2ServiceClass bos : bosList){
//            ACSimpl ai = (ACSimpl)acs;
            varImportString += "import " + this.ioPath + "." + bos.getInput().getClassName() + ";\n";
            varImportString += "import " + this.ioPath + "." + bos.getOutput().getClassName() + ";\n";
        }
        varImportString += "\n";
        return varImportString;
    }

    public String makeAnnotationString(){
        return "@AppComponentService(RouteKey=\"\" , Servicename=\"" + this.serviceName + "\");\n";
    }

    public String makeBegainString(){
        return "public class " + this.acsClassName + " extends AbstractCommonService<" + this.input.getClassName() + ", " + this.output.getClassName() + ">{\n" +
                "   @Override\n" +
                "   protected OperationResponse<" + this.output.getClassName() + ">" + " response = new OperationResponse<" + this.input.getClassName() + " data) {\n" +
                "\n";
    }

    public String makeResponseString() {
        String responseString = "       //生成输出\n" +
                "       OperationResponse<" + this.output.getClassName() + "> Response = new OperationResponse<" + this.output.getClassName() + ">(OperationStage.TRY, OperationResult.FAIL);\n" +
                "       " + this.output.getClassName() + " " + this.output.getVarName() + " = new " + this.output.getClassName() + "();\n" +
                "       response.setData(" + this.output.getVarName() + ");\n\n";
        return responseString;
    }

    @Override
    public String makeCallString(Pbrb2ServiceClass bos, String indent) {
        String callString = "       //调用" + bos.getInput().getClassName() + "\n" +
                "       LogFactory.getDebugLog().debug(\"" + this.acsClassName + " calling " + bos.getClassName() + "\");\n" +
                "       " + bos.getInput().getClassName() + " " + bos.getInput().getVarName() + " = new " + bos.getInput().getVarName() + "();\n" +
                "       CommonUtils.copyFromBean(data, " + bos.getInput().getVarName() + ");\n" +
                "       ActionResult<" + bos.getOutput().getClassName() + "> " + bos.getOutput().getVarName() + " = ServiceExcutor.executeService(\"" + bos.getServicName() + "\",\"\", " + bos.getInput().getVarName() + ");\n";

        return callString;
    }

    @Override
    public String makeFailString(Pbrb2ServiceClass bos) {
        String failString = "       if(!" + bos.getOutput().getVarName() + ".isOk()){\n" +
                "           FovaTx.setTxFail(response, " + bos.getOutput().getVarName() + ".getErrCode(), " + bos.getOutput().getVarName() + ".getCondition(), " + bos.getOutput().getVarName() + ".getFailProg());\n" +
                "           return response;\n" +
                "       }\n\n";

        return failString;
    }



    public String makeEndString(){
        return  "       response.setResult(OperationResult.SUCCESS);\n" +
                "       return response;\n" +
                "    }\n" +
                "}";
    }

    @Override
    public String printAcsClass() {
        String classStream ="";
        classStream += this.packagePath;
        classStream += this.importString;
        classStream += makeVarImportString(this.bosList);
        classStream += makeAnnotationString();
        classStream += makeBegainString();
        classStream += makeResponseString();
        for (Pbrb2ServiceClass bos : bosList){
            classStream += makeCallString(bos, "");
            classStream += makeFailString(bos);
        }
        classStream += makeEndString();
        return classStream;
    }

    public List<Pbrb2ServiceClass> getBosList() {
        return bosList;
    }

    public void setBosList(List<Pbrb2ServiceClass> bosList) {
        this.bosList = bosList;
    }

    public void setAcsClassName(String acsClassName) {

        this.acsClassName = acsClassName;
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

    public String getAcsClassName() {

        return acsClassName;
    }

    public IO getInput() {
        return input;
    }

    public IO getOutput() {
        return output;
    }

    @Override
    public String getClassName() {
        return this.acsClassName;
    }

    @Override
    public String getServicName() {
        return serviceName;
    }



}

package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;

import java.util.List;


public class ATSimpl implements com.xingpk.xiazhuji.intr.ATS{
    private static String importString = "import com.icbc.fova.annotation.AppTransactionService;\n" +
            "import com.icbc.fova.common.ActionError;\n" +
            "import com.icbc.fova.common.CommonUtils;\n" +
            "import com.icbc.fova.service.AbstractExposeService;\n" +
            "import com.icbc.stars.transaction.common.OperationResponse;\n" +
            "import com.icbc.stars.transaction.common.OperationResult;\n" +
            "import com.icbc.stars.transaction.common.OperationStage;\n";
    private String ioImportString;

    private String atsClassName;
    private IO input;
    private IO output;
    private List<ACS> acsList;
    private String serviceName;

    private String packagePath;
    private String ioPath;


    public ATSimpl(String atsClassName, String packagePath){
        this.atsClassName = atsClassName.substring(0,1).toUpperCase() + atsClassName.substring(1, atsClassName.length());
        this.packagePath = packagePath + ".ats;\n";
        this.ioPath = packagePath + ".io";
        setServiceName(atsClassName.substring(0,1).toLowerCase() + atsClassName.substring(1, atsClassName.length()));

        //建立输入输出
        //TODO 是否要加"ATS"在结尾
        this.input = new AtsIO(atsClassName + "Input");
        this.output = new AtsIO(atsClassName + "Output");

    }

    @Override
    public String printAtsClass() {
        String classStream ="";
        classStream += this.packagePath;
        classStream += this.importString;
        classStream += makeVarImportString(this.acsList);
        classStream += makeAnnotationString();
        classStream += makeBegainString();
        classStream += makeResponseString();
        for (ACS acs : acsList){
            classStream += makeCallString(acs, "");
            classStream += makeFailString(acs);
        }
        classStream += makeEndString();
        return classStream;
    }

    public String makeAnnotationString(){
        return "@AppTransactionService(inputClz = " + this.input.getClassName() + ".class, outputClz = " + this.output.getClassName() + ".class);\n";
    }

    public String makeVarImportString(List<ACS> ascList){
        String varImportString ="";
        for(ACS acs : acsList){
            ACSimpl ai = (ACSimpl)acs;
            varImportString += "import " + this.ioPath + "." + ai.getInput().getClassName() + ";\n";
            varImportString += "import " + this.ioPath + "." + ai.getOutput().getClassName() + ";\n";
        }
        varImportString += "\n";
        return varImportString;
    }
    public String makeBegainString(){
        return  "public class " + this.atsClassName + " extends AbstractExposeService<" + this.input.getClassName() + ", " + this.output.getClassName() + "> implements I" + this.atsClassName + "{\n\n" +
                "   @Override\n" +
                "   protected OperationResponse<" + this.output.getClassName() + "> doExecute(" + this.input.getClassName() + " data){\n";
    }


    public String makeResponseString() {
        String responseString = "   //生成输出\n" +
                "   OperationResponse<" + this.output.getClassName() + "> response = new OperationResponse<" + this.output.getClassName() + ">(OperationStage.TRY, OperationResult.FAIL);\n" +
                "   response.setData(new "+ this.output.getClassName() + "());\n\n" +
                "   response.getData().setTransOk(1);\n" +
                "   response.getData().setErrcode(9999);\n";
        return responseString;
    }


    public String makeCallString(ACS Acs, String indent) {
        ACSimpl ai = (ACSimpl)Acs;
        String callString = "   //调用" + ai.getAcsClaccName() + "\n" +
                "   " + ai.getAcsClaccName() + " " + ai.getInput().getVarName() + " = new " + ai.getAcsClaccName() + "();\n" +
                "   CommonUtils.copyFromBean(data, " + ai.getInput().getVarName() + ");\n" +
                "   OperationResponse<" + ai.getOutput().getClassName() + "> " + ai.getOutput().getVarName() + " = this.invokeService(\"" + ai.getServicName() + "\",\"\", " + ai.getInput().getVarName() + ");\n";

        return callString;
    }


    public String makeFailString(ACS Acs) {
        ACSimpl ai = (ACSimpl)Acs;
        String failString = "   if (" + ai.getOutput().getVarName() + ".getResult().compareTo(OperationResult.FAIL) == 0){\n" +
                "       response.setResult(" + ai.getOutput().getVarName() + ".getResult());\n" +
                "       response.getData().setTransOk(1);\n" +
                "       response.getData().setErrcode(Long.valueOf(" + ai.getOutput().getVarName() + ".getErrorCode()));\n" +
                "       response.getData().setTableName(" + ai.getOutput().getVarName() + ".getMessage());\n" +
                "       return response;\n" +
                "   }\n\n";
        return failString;
    }

    public String makeEndString(){
        return "    response.setResult(OperationResult.SUCCESS);\n" +
                "   response.getData().setTransOk(0);\n" +
                "   return response;\n" +
                "    }\n" +
                "}";
    }

    public List<ACS> getAcsList() {
        return acsList;
    }

    public void setAcsList(List<ACS> acsList) {

        this.acsList = acsList;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {

        this.serviceName = serviceName;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getPackagePath() {
        return packagePath;
    }
}

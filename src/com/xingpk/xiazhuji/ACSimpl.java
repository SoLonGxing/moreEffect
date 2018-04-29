package com.xingpk.xiazhuji;

import com.xingpk.xiazhuji.intr.ACS;
import com.xingpk.xiazhuji.intr.IO;

public class ACSimpl implements ACS{
    private static String importString = "import com.fova.common.ActionResult;/n" +
            "import com.icbc.fova.common.CommonUtils;/n" +
            "import com.icbc.fova.common.ServiceExcutor;" +
            "import com.icbc.fova.tx.FovaTx;/n" +
            "import com.icbc.stars.transaction.common.OperationResponse;/n" +
            "import com.icbc.stars.transaction.common.OperationResult;/n" +
            "import com.icbc.stars.transaction.common.OperationStage;/n" +
            "import com.icbc.stars.transaction.service.AbstractCommonService;",
                    annotation = "@AppComponentService(RouteKey=\"\" , Servicename=\"\";";
    private String ACSName;
    private IO input;
    private IO output;
    private String servicName;
    private String packagePath;
    public void setServiceName(String serviceName) {
        this.servicName = serviceName;
    }


    public ACSimpl(String name, String packagePath) {
        this.ACSName = name;
        this.packagePath = packagePath + ";";
        setServiceName(name.substring(0,1).toLowerCase() + name.substring(1,name.length()));

        //建立输入输出
        //TODO 是否要加"BOS"在结尾
        this.input = new AtsIO(name + "Input");
        this.output = new AtsIO(name + "Output");
    }


    @Override
    public String printAtsClass() {
        String classStream =null;


        return classStream;
    }

    public void setACSName(String ACSName) {
        this.ACSName = ACSName;
    }

    public void setInput(IO input) {
        this.input = input;
    }

    public void setOutput(IO output) {
        this.output = output;
    }

    public void setServicName(String servicName) {
        this.servicName = servicName;
    }

    public String getACSName() {

        return ACSName;
    }

    public IO getInput() {
        return input;
    }

    public IO getOutput() {
        return output;
    }

    public String getServicName() {
        return servicName;
    }

    @Override
    public String makeResponseString() {
        String responseString = "//生成输出/n" +
                "OperationResponse<" + output + "> Response = new OperationResponse<" + output + ">(OperationStage.TRY, OperationResult.FAIL);";
        return responseString;
    }

    @Override
    public String makeCallString(String calledBosServiceName) {

        return null;
    }
}

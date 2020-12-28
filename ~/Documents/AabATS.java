package com.icbc.a.b.ats.ats;

import com.icbc.fova.annotation.AppTransactionService;
import com.icbc.fova.common.ActionError;
import com.icbc.fova.common.CommonUtils;
import com.icbc.cte.logging.LogFactory;
import com.icbc.fova.service.AbstractExposeService;
import com.icbc.stars.transaction.common.OperationResponse;
import com.icbc.stars.transaction.common.OperationResult;
import com.icbc.stars.transaction.common.OperationStage;
import com.icbc.a.b.ats.io.AacInput;
import com.icbc.a.b.ats.io.AacOutput;
import com.icbc.a.b.ats.io.AadInput;
import com.icbc.a.b.ats.io.AadOutput;
import com.icbc.a.b.ats.io.AaeInput;
import com.icbc.a.b.ats.io.AaeOutput;

@AppTransactionService(inputClz = AabATSInput.class, outputClz = AabATSOutput.class)
public class AabATS extends AbstractExposeService<AabATSInput, AabATSOutput> implements IAabATS{

   @Override
   protected OperationResponse<AabATSOutput> doExecute(AabATSInput data){
       //生成输出
       OperationResponse<AabATSOutput> response = new OperationResponse<AabATSOutput>(OperationStage.TRY, OperationResult.FAIL);
       response.setData(new AabATSOutput());
       response.getData().setTransOk(1);
       response.getData().setErrcode(9999);

       //调用AacInput
       LogFactory.getDebugLog().debug("AabATS calling Aac");
       AacInput aacInput = new aacInput();
       CommonUtils.copyFromBean(data, aacInput);
       OperationResponse<AacOutput> aacOutput = this.invokeService("aac","", aacInput);
       if (aacOutput.getResult().compareTo(OperationResult.FAIL) == 0){
           response.setResult(aacOutput.getResult());
           response.getData().setTransOk(1);
           response.getData().setErrcode(Long.valueOf(aacOutput.getErrorCode()));
           response.getData().setTableName(aacOutput.getMessage());
           return response;
       }

       //调用AadInput
       LogFactory.getDebugLog().debug("AabATS calling Aad");
       AadInput aadInput = new aadInput();
       CommonUtils.copyFromBean(data, aadInput);
       OperationResponse<AadOutput> aadOutput = this.invokeService("aad","", aadInput);
       if (aadOutput.getResult().compareTo(OperationResult.FAIL) == 0){
           response.setResult(aadOutput.getResult());
           response.getData().setTransOk(1);
           response.getData().setErrcode(Long.valueOf(aadOutput.getErrorCode()));
           response.getData().setTableName(aadOutput.getMessage());
           return response;
       }

       //调用AaeInput
       LogFactory.getDebugLog().debug("AabATS calling Aae");
       AaeInput aaeInput = new aaeInput();
       CommonUtils.copyFromBean(data, aaeInput);
       OperationResponse<AaeOutput> aaeOutput = this.invokeService("aae","", aaeInput);
       if (aaeOutput.getResult().compareTo(OperationResult.FAIL) == 0){
           response.setResult(aaeOutput.getResult());
           response.getData().setTransOk(1);
           response.getData().setErrcode(Long.valueOf(aaeOutput.getErrorCode()));
           response.getData().setTableName(aaeOutput.getMessage());
           return response;
       }

       response.setResult(OperationResult.SUCCESS);
       response.getData().setTransOk(0);
       return response;
    }
}
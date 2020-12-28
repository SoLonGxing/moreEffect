package com.icbc.a.b.acs.acs;
import com.icbc.fova.common.ActionResult;
import com.icbc.fova.annotation.AppComponentService;
import com.icbc.cte.logging.LogFactory;
import com.icbc.fova.common.CommonUtils;
import com.icbc.fova.common.ServiceExcutor;
import com.icbc.fova.tx.FovaTx;
import com.icbc.stars.transaction.common.OperationResponse;
import com.icbc.stars.transaction.common.OperationResult;
import com.icbc.stars.transaction.common.OperationStage;
import com.icbc.stars.transaction.service.AbstractCommonService;

import com.icbc.a.b.acs.io.BbInput;
import com.icbc.a.b.acs.io.BbOutput;
import com.icbc.a.b.acs.io.CcInput;
import com.icbc.a.b.acs.io.CcOutput;

@AppComponentService(RouteKey="" , ServiceName="aa")
public class Aa extends AbstractCommonService<AaInput, AaOutput>{
   @Override
   protected OperationResponse<AaOutput> doExecute(AaInput data){
       this.setUseLocalTransaction(false);//不启用本地事务，直接使用ATS创建的事务

       //生成输出
       OperationResponse<AaOutput> response = new OperationResponse<AaOutput>(OperationStage.TRY, OperationResult.FAIL);
       AaOutput aaOutput = new AaOutput();
       response.setData(aaOutput);

       //调用bb
       LogFactory.getDebugLog().debug("Aa calling Bb");
       BbInput bbInput = new bbInput();
       CommonUtils.copyFromBean(data, bbInput);
       ActionResult<BbOutput> bbOutput = ServiceExcutor.executeService("bb","", bbInput);
       if(!bbOutput.isOk()){
           FovaTx.setTxFail(response, bbOutput.getErrCode(), bbOutput.getCondition(), bbOutput.getFailProg());
           return response;
       }

       //调用cc
       LogFactory.getDebugLog().debug("Aa calling Cc");
       CcInput ccInput = new ccInput();
       CommonUtils.copyFromBean(data, ccInput);
       ActionResult<CcOutput> ccOutput = ServiceExcutor.executeService("cc","", ccInput);
       if(!ccOutput.isOk()){
           FovaTx.setTxFail(response, ccOutput.getErrCode(), ccOutput.getCondition(), ccOutput.getFailProg());
           return response;
       }

       response.setResult(OperationResult.SUCCESS);
       return response;
    }
}
package com.xingpk.xiazhuji.analysis.ruleCheck;


import com.util.CommonUtil;
import com.xingpk.xiazhuji.intr.ATS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**规范
 * 1、程序包需要在service项目中创建，package参考com.icbc.应用名.业务对象.ats
 * 2、服务定义
 *  （1）在service项目中创建，服务命名应为动词+业务对象的英文名+ATS    【目前无法检测动词加业务对象的英文名】
 *  （2）ATS服务必须继承基类AbstractExposeService，必须实现doExecute方法；同时实现该服务的服务接口
 *  （3）ATS服务必须定义@AppTransactionService标注，标注中填写输入通讯区类inputClz和输出通讯区类outputClz
 * 3、服务接口定义
 *  （1）、在stub项目中创建，每个ats定义一个interface，命名在服务类名前+ I   【目前无法检查】
 *  （2）、ATS服务接口必须继承IExposeService接口                          【目前无法检查】
 * 4、服务IO
 *  （1）、在service项目中创建，包名使用com.icbc.应用名.业务对象.io，类名参考服务名+Input、服务名+Output     【放在IO里检查】
 *  （2）、服务IO类必须继承OperationCommExt基类                                                        【放在IO里检查】
 *  （3）、服务IO类中只有简单的get、set方法、不应填写实际的业务逻辑                                        【放在IO里检查】
 * 5、服务开发
 *  （1）、ATS主要写调用ACS的条件、场景等业务规则的检查和准入                                                【目前无法检查】
 *  （2）、doExecute函数以ATS输入类作为输入，以OperationResponse<ATS输出类>模板类作为输出
 *  （3）、ACS调用时，使用this.invokeService("ACS服务名","差异路由条件",acs输入通讯区);方法，返回OperationResponse<ACS输出类>
 *  （4）、调用acs后，需要进行状态判断（每个ats必须调用checkFovaChannelACS渠道检查服务）
 *        compareTo
 *  （5）、ats所有处理均成功，则返回结果
 *         output定义
 *         setResponse等
 *
 *
 * */


public class CheckATS {


    public static String Check(String filePath, File file, String fileName) throws FileNotFoundException {
        //所有flag，字典0-未检查，1-检查正常、2-检查不正常
        int packageFlag = 0;//包名是否检查是否正确flag
        int nameFlag = 0;//名称是否检查是否包含ATS三个字的FLAG
        int extendsFlag = 0;//检查是否继承了基类flag
        int implementsFlag = 0;//检查是否实现了服务接口
        int havedoExecuteFlag = 0;//检查是否实现了doExecute方法
        int annotationFlag = 0;//检查是否定义了标注
        int doExecuteFlag = 0;//检查doExecute方法的输入输出
        int callACSFlag = 0;//调用acs的检查
        int afterCallACSFlag = 0;//调用acs之后的flag检查
        int callCheckFovaChannelACS = 0;//是否调用了渠道检查flag
        int ATSEndflag = 0;//ATS结束检查
        String serviceName = "";
        String ATSName = "";

        ATSName = fileName.substring(0, fileName.indexOf("."));

        serviceName = CommonUtil.lowerCaseFirstCharacter(ATSName);

        StringBuffer checkResult = new StringBuffer();
        int errCount = 0;
        //（1）在service项目中创建，服务命名应为动词+业务对象的英文名+ATS    【目前无法检测动词加业务对象的英文名】
        if (nameFlag == 0) {
            if (fileName.contains("ATS.java") && filePath.contains("service\\src")) {
                nameFlag = 1;
            } else {
                nameFlag = 2;
                checkResult.append(String.valueOf(errCount) + "、错误项目：在service项目中创建，服务命名应为动词+业务对象的英文名+ATS\n");
                checkResult.append("  文件名称：" + fileName + "\n\n");
                errCount++;
            }
        }

        Scanner scan1 = new Scanner(file, "utf-8");
        int line = 0;
        while (true) {
            if (scan1.hasNext() == false) break;
            String s1 = scan1.nextLine();//获取当前需要检查的行

            line++;//当前行数


            //程序包需要在service项目中创建，package参考com.icbc.应用名.业务对象.ats
            if (packageFlag == 0) {
                if (s1.contains("package ")) {
                    if (s1.contains(".ats;")) {
                        packageFlag = 1;
                    } else {
                        packageFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：程序包需要在service项目中创建，package参考com.icbc.应用名.业务对象.ats\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  当前包名：" + s1 + "\n\n");

                        errCount++;
                    }
                    continue;
                }
            }


            if (extendsFlag == 0 || implementsFlag == 0) {
                if (s1.contains(" extends ")) {
                    if (s1.contains("extends AbstractExposeService")) {
                        extendsFlag = 1;
                    } else {
                        extendsFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：ATS服务必须继承基类AbstractExposeService\n");
                        checkResult.append("  行号：" + line + "\n");
                        errCount++;
                    }

                    if (s1.contains("implements I" + ATSName)) {
                        implementsFlag = 1;
                    } else {
                        implementsFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：ATS服务必须同时实现该服务的服务接口\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
            }

            if (havedoExecuteFlag == 0) {
                if (s1.contains(" doExecute")) {
                    havedoExecuteFlag = 1;
                    //doExecute函数以ATS输入类作为输入，以OperationResponse<ATS输出类>模板类作为输出
                    if (doExecuteFlag == 0) {
                        if (s1.contains(" doExecute")) {
                            if (s1.contains("OperationResponse<" + ATSName + "Output> doExecute(" + ATSName + "Input")) {
                                doExecuteFlag = 1;
                            } else {
                                doExecuteFlag = 2;
                                checkResult.append(String.valueOf(errCount) + "、错误项目：doExecute函数以ATS输入类作为输入，以OperationResponse<ATS输出类>模板类作为输出\n");
                                checkResult.append("  行号：" + line + "\n");
                                checkResult.append("  \n");
                                errCount++;
                            }
                            continue;
                        }
                    }
                    continue;
                }
            }



            if (annotationFlag == 0) {
                if (s1.contains("@AppTransactionService")) {
                    annotationFlag = 1;
                    if (!s1.contains(ATSName + "Input.class") || !s1.contains("inputClz")) {
                        annotationFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：@AppTransactionService标注中填写输入通讯区类inputClz\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }

                    if (!s1.contains(ATSName + "Output.class") || !s1.contains("outputClz")) {
                        annotationFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：@AppTransactionService标注中填写输出通讯区类outputClz\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }

                    continue;
                }
            }

            //ACS调用时，使用this.invokeService("ACS服务名","差异路由条件",acs输入通讯区);方法，返回OperationResponse<ACS输出类>
            if (afterCallACSFlag == 0 || afterCallACSFlag == 3) {
                if (s1.contains("this.invokeService(")) {
                    if (callCheckFovaChannelACS == 0){
                        if (s1.contains("this.invokeService") && s1.contains("\"checkFovaChannelACS\"")){
                            callCheckFovaChannelACS = 1;
                        }
                    }
                    afterCallACSFlag = 3;
                    String serviceCalling = s1;
                    int maxLine = 0;
                    while (scan1.hasNext() && maxLine < 2) {
                        String s2 = scan1.nextLine();
                        maxLine++;

                        if (s2.contains("if") && s2.contains("{")) {
                            if (s2.contains("getResult().compareTo(OperationResult.FAIL) == 0")) {
                                afterCallACSFlag = 1;
                                break;
                            } else {

                            }
                        }

                    }
                    if (afterCallACSFlag == 1){
                        continue;
                    }
                    callACSFlag = 2;
                    afterCallACSFlag = 3;
                    checkResult.append(String.valueOf(errCount) + "、错误项目：调用acs后，需要进行状态判断\n");
                    checkResult.append("   调用服务:" + serviceCalling + "\n");
                    checkResult.append("  行号：" + line + "\n");
                    checkResult.append("  \n");
                    errCount++;
                    continue;
                }
            }




            if (ATSEndflag != 2) {
                if (s1.contains("response.setResult(OperationResult.SUCCESS);")) {
                    ATSEndflag = 1;
                    continue;
                }
                if (s1.contains("response.setData(")) {
                    ATSEndflag = 2;
                    continue;
                }

            }

        }

        if (havedoExecuteFlag == 0) {
            checkResult.append(String.valueOf(errCount) + "、错误项目：ATS服务必须实现doExecute方法\n\n");
            errCount++;
        }

        if (annotationFlag == 0) {
            checkResult.append(String.valueOf(errCount) + "、错误项目：ATS服务必须定义@AppTransactionService标注\n\n");
            errCount++;
        }

        if (ATSEndflag != 2) {
            checkResult.append(String.valueOf(errCount) + "、错误项目：ats所有处理均成功，则返回结果\n\n");
            errCount++;
        }

        if (callCheckFovaChannelACS == 0){
            checkResult.append(String.valueOf(errCount) + "、错误项目：每个ats必须调用checkFovaChannelACS渠道检查服务\n\n");
            errCount++;
        }

        checkResult.append("未成功进行检查项目：\n");
        int skipCount = 0;
        if (packageFlag == 0) {//包名是否检查是否正确flag

            checkResult.append(String.valueOf(skipCount) + "、程序包需要在service项目中创建，package参考com.icbc.应用名.业务对象.ats\n");
            skipCount++;
        }

        if (nameFlag == 0) {//名称是否检查是否包含ATS三个字的FLAG
            checkResult.append(String.valueOf(skipCount) + "、在service项目中创建，服务命名应为动词+业务对象的英文名+ATS\n");
            skipCount++;
        }

        if (extendsFlag == 0){//检查是否继承了基类flag
            checkResult.append(String.valueOf(skipCount) + "、ATS服务必须继承基类AbstractExposeService\n");
            skipCount++;
        }

        if (implementsFlag == 0) {//检查是否实现了服务接口
            checkResult.append(String.valueOf(skipCount) + "、ATS服务必须实现该服务的服务接口\n");
            skipCount++;
        }

        if (doExecuteFlag == 0) {//检查doExecute方法的输入输出
            checkResult.append(String.valueOf(skipCount) + "、doExecute函数以ATS输入类作为输入，以OperationResponse<ATS输出类>模板类作为输出\n");
            skipCount++;
        }

        if (afterCallACSFlag == 0){//调用acs之后的flag检查
            checkResult.append(String.valueOf(skipCount) + "、调用acs后，需要进行状态判断\n");
            skipCount++;
        }

        return checkResult.toString();
    }




}

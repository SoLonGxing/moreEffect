package com.xingpk.xiazhuji.analysis.ruleCheck;


import com.util.CommonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**规范
 *1、程序包
 *  在service项目中创建，程序包package命名参考com.icbc.应用名.业务对象.acs
 *2、服务定义
 *  （1）在service项目中创建，服务命名应为动词+业务对象的英文名+ACS   【无法检查动名词】
 *  （2）ACS服务必须继承基类AbstractExposeService或AbstractCompensateService，不支持柔性事务的继承AbstractExposeService，支持柔性事务的继承AbstractCompensateService
 *  （3）ACS服务必须定义@AppComponentService标注，标注中填写路由主键值RouteKey、服务名ServiceName
 *3、服务IO
 *  （1）、在service项目中创建，报名使用com.icbc.应用名.业务对象.acs，类名参考服务类名Input、服务类名+Output   【在IO检查中做】
 *  （2）、服务IO类必须继承OperationCommExt基类                                                         【在IO检查中做】
 *  （3）、服务IO类中只有简单的get，set方法，不应编写实际业务逻辑                                           【在IO检查中做】
 *4、服务开发
 *  （1）、ACS主要写调用bos的条件，场景等业务规则和检查准入
 *  （2）、doExecute函数以acs输入类为输入，以OperationResponse<ACS输出类>模板类作为输出
 *  （3）、调用BOS时，使用ServiceExcutor.executeService("BOS服务名","路由主键值",ACS输入通讯区);方法，返回ActionResult<BOS输出类>
 *  （4）、调用BOS后，需要进行状态判断
 *          isOk()
 *  （5）、ACS所有处理均成功，返回
 *        response和FovaTx
 * */
public class CheckACS {
    //所有flag，字典0-未检查，1-检查正常、2-检查不正常
    private static int packageFlag = 0;//检查package是否正确
    private static int nameFlag = 0;//检查名称是否正确
    private static int extendsFlag = 0;//检查是否继承
    private static int annotationFlag = 0;//检查标注是否正确
    private static int doExcuteFlag = 0;//检查doExecute函数输入输出
    private static int callBosFlag = 0;//检查调用bos书写是否正确
    private static int afterCallBosFlag = 0;//检查调用bos后的检查是否正确
    private static int ACSEnd = 0;//含茶acs结束的输出设置是否正确
    private static String serviceName = "";
    private static String ACSName = "";

    public static String check(String filePath, File file, String fileName) throws FileNotFoundException {
        ACSName = fileName.substring(0, fileName.indexOf("."));

        serviceName = CommonUtil.lowerCaseFirstCharacter(ACSName);
        StringBuffer checkResult = new StringBuffer();
        int errCount = 0;
        if (nameFlag == 0) {
            if (fileName.contains("ACS.java") && filePath.contains("service\\src")) {
                nameFlag = 1;
            } else {
                nameFlag = 2;
                checkResult.append(String.valueOf(errCount) + "、错误项目：在service项目中创建，服务命名应为动词+业务对象的英文名+ATS\n");
                checkResult.append("  文件名称：" + fileName + "\n");
                errCount++;
            }
        }


        Scanner scan1 = new Scanner(file, "utf-8");
        int line = 0;
        while (true) {
            if (scan1.hasNext() == false) break;
            String s1 = scan1.nextLine();//获取当前需要检查的行

            line++;//当前行数

            if (packageFlag == 0){
                if (s1.contains("package ")) {
                    if (s1.contains(".acs;")) {
                        packageFlag = 1;
                    } else {
                        packageFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：在service项目中创建，程序包package命名参考com.icbc.应用名.业务对象.acs\n");
                        checkResult.append("   当前包路径：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
            }


            //ACS服务必须继承基类AbstractExposeService或AbstractCompensateService，不支持柔性事务的继承AbstractExposeService，支持柔性事务的继承AbstractCompensateService
            if (extendsFlag == 0){
                if (s1.contains("extends ")){
                    if (s1.contains("AbstractExposeService") || s1.contains("AbstractCompensateService")){
                        extendsFlag = 1;
                    }else{
                        extendsFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：ACS服务必须继承基类AbstractExposeService或AbstractCompensateService，不支持柔性事务的继承AbstractExposeService，支持柔性事务的继承AbstractCompensateService\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;

                    }
                    continue;
                }
            }

            //ACS服务必须定义@AppComponentService标注，标注中填写路由主键值RouteKey、服务名ServiceName
            if (annotationFlag == 0){
                if (s1.contains("@AppComponentService")){
                    annotationFlag = 1;
                    if (!(s1.contains("RouteKey") && s1.contains("ServiceName"))){
                        annotationFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：ACS服务必须定义@AppComponentService标注，标注中填写路由主键值RouteKey、服务名ServiceName\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
            }

            //doExecute函数以acs输入类为输入，以OperationResponse<ACS输出类>模板类作为输出
            if (doExcuteFlag == 0){
                if (s1.contains("doExecute")){
                    if (s1.contains("OperationResponse<" + ACSName + "Output>") && s1.contains("doExecute(" + ACSName + "Input")){
                        doExcuteFlag = 1;
                    }else{
                        doExcuteFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：doExecute函数以acs输入类为输入，以OperationResponse<ACS输出类>模板类作为输出\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
            }

            //调用BOS时，使用ServiceExcutor.executeService("BOS服务名","路由主键值",ACS输入通讯区);方法，返回ActionResult<BOS输出类>
            if (callBosFlag == 0){
                if (s1.contains("ServiceExcutor.executeService(")){
                    callBosFlag = 1;
                    if (!s1.contains("ActionResult<")){
                        callBosFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：调用BOS时，使用ServiceExcutor.executeService(\"BOS服务名\",\"路由主键值\",ACS输入通讯区);方法，返回ActionResult<BOS输出类>\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }

                    if (afterCallBosFlag == 0){
                        String serviceCalling = s1;
                        int maxLine = 0;
                        while (scan1.hasNext() && maxLine < 2) {
                            String s2 = scan1.nextLine();
                            maxLine++;
                            if (s2.contains("if") && s2.contains("{")) {
                                if (s2.contains("isOK()")) {
                                    afterCallBosFlag = 1;
                                    break;
                                } else {
                                    afterCallBosFlag = 2;
                                    checkResult.append(String.valueOf(errCount) + "、错误项目：调用acs后，需要进行状态判断\n");
                                    checkResult.append("   调用服务:" + serviceCalling + "\n");
                                    checkResult.append("  行号：" + line + "\n");
                                    checkResult.append("  \n");
                                    errCount++;
                                    break;
                                }
                            }

                        }
                    }

                    continue;
                }
            }


            if (ACSEnd != 2){
                if (s1.contains("response.setData")){
                    ACSEnd = 1;
                    continue;
                }
                if (s1.contains("response.setResult(OperationResult.SUCCESS);")){
                    ACSEnd = 2;
                    continue;
                }
            }

        }

        if (annotationFlag == 0) {
            checkResult.append(String.valueOf(errCount) + "、错误项目：ACS服务必须定义@AppComponentService标注，标注中填写路由主键值RouteKey、服务名ServiceName\n");
            checkResult.append("  行号：" + line + "\n");
            checkResult.append("  \n");
            errCount++;
        }

        if (ACSEnd == 0){
            checkResult.append(String.valueOf(errCount) + "、错误项目：ACS所有处理均成功，返回数据和成功标志\n");
            checkResult.append("  行号：" + line + "\n");
            checkResult.append("  \n");
            errCount++;
        }


        checkResult.append("未成功进行检查项目：");
        int skipCount = 0;
        if (doExcuteFlag == 0){
            checkResult.append(String.valueOf(skipCount) + "、doExecute函数以acs输入类为输入，以OperationResponse<ACS输出类>模板类作为输出\n");
            checkResult.append("  行号：" + line + "\n");
            checkResult.append("  \n");
            skipCount++;
        }


        return checkResult.toString();
    }
}

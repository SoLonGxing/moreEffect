package com.xingpk.xiazhuji.analysis.ruleCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**规则
 * 1、程序包
 *   在service项目中创建，程序包package命名参考com.icbc.应用名.业务对象.bos
 * 2、服务定义
 *   （1）、在service项目中创建，服务命名应为动词+业务对象的英文名
 *   （2）、BOS服务必须继承基类AbstractBussObjService，必须实现Execute方法
 *   （3）、BOS服务必须定义@BussObjectService标注，标注中填写路由主键值RouteKey，服务名ServiceName
 * 3、服务IO
 *   （1）、在service项目中创建，包名使用com.icbc.应用名.业务对象.bos，类名参考服务类名+Input，服务列明+Output   【在io检查里做检查】
 *   （2）、服务IO类必须继承OperationCommExt基类                                                           【在io检查里做检查】
 *   （3）、服务IO类中只有简单的get，set方法，不应该编写时机业务逻辑                                           【在io检查里做检查】
 * 4、服务开发
 *   （1）、BOS中值写有关于该bos的业务规则
 *   （2）、execute函数以BOS输入类作为输入，以ActionResult<BOS输出类>模板类作为输出
 *   （3）、BOS中涉及数据库操作均通过DAO层实现   【目前无法检查】
 *   （4）、每个业务步骤完成后，需要进行状态判断
 *      判断数据是否等于null
 *    (5)BOS所有处理成功，均返回
 *      setOutput
 * */

public class CheckBOS {
    private static int packageFlag = 0;//包路径检查
    private static int nameFlag = 0;//建立路径检查
    private static int extendsFlag = 0;//继承检查
    private static int doExecuteFlag = 0;//实现execute检查
    private static int executeIOFlag = 0;//execute的输入输出检查
    private static int annotationFlag = 0;//定义标注检查
    private static int returnFlag = 0;//返回resultObject标志
    private static String BOSName = "";

    public static String check(String filePath, File file, String fileName) throws FileNotFoundException {
        BOSName = fileName.substring(0, fileName.indexOf("."));


        StringBuffer checkResult = new StringBuffer();
        int errCount = 0;

        if (nameFlag == 0) {
            if (filePath.contains("service\\src")) {
                nameFlag = 1;
            } else {
                nameFlag = 2;
                checkResult.append(String.valueOf(errCount) + "、错误项目：在service项目中创建\n");
                checkResult.append("  文件路径：" + filePath + "\n\n");
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
                if (s1.contains("package")){
                    if (s1.contains(".bos;")){
                        packageFlag = 1;
                    }else{
                        packageFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：程序包package命名参考com.icbc.应用名.业务对象.bos\n");
                        checkResult.append("   当前包路径：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
            }

            if (annotationFlag != 3){
                if (s1.contains("@BussObjectService")){
                    annotationFlag = 1;
                }
                if (s1.contains("RouteKey") && annotationFlag == 1){
                    annotationFlag++;
                }
                if (s1.contains("ServiceName") && annotationFlag == 2){
                    annotationFlag++;
                }
            }

            if (extendsFlag == 0){
                if (s1.contains("extends ")){
                    if (s1.contains("extends AbstractBussObjService")){
                        extendsFlag = 1;
                    }else{
                        extendsFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：BOS服务必须继承基类AbstractBussObjService，必须实现Execute方法\n");
                        checkResult.append("   当前包路径：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
            }


            if (doExecuteFlag == 0 || executeIOFlag == 0){
                if (s1.contains("execute")){
                    if (doExecuteFlag == 0){
                        doExecuteFlag = 1;
                    }

                    if (executeIOFlag == 0){
                        if (s1.contains("ActionResult<" + BOSName + "Output>") && s1.contains("(" + BOSName + "Input")){
                            executeIOFlag = 1;
                        }
                        else{
                            executeIOFlag = 2;
                            checkResult.append(String.valueOf(errCount) + "、错误项目：execute函数以BOS输入类作为输入，以ActionResult<BOS输出类>模板类作为输出\n");
                            checkResult.append("   当前包路径：" + s1 + "\n");
                            checkResult.append("  行号：" + line + "\n");
                            checkResult.append("  \n");
                        }
                    }
                    continue;
                }
            }


            if (returnFlag == 0){
                if (s1.contains("output.setResultObject(")){
                    returnFlag = 1;
                }
            }


        }



        if (annotationFlag != 3){
            checkResult.append(String.valueOf(errCount) + "、错误项目：BOS服务必须定义@BussObjectService标注，标注中填写路由主键值RouteKey，服务名ServiceName\n");
            checkResult.append("\n");
        }

        if (doExecuteFlag == 0){
            checkResult.append(String.valueOf(errCount) + "、错误项目：必须实现Execute方法\n");
            checkResult.append("\n");
        }

        if (returnFlag == 0){
            checkResult.append(String.valueOf(errCount) + "、错误项目：BOS所有处理成功，均返回成功数据\n");
            checkResult.append("\n");
        }

        return checkResult.toString();
    }
}

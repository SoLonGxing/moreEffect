package com.xingpk.xiazhuji.analysis.ruleCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**规则
 * 1、程序包
 *      在service项目中创建，程序包package命名参考com.icbc.应用名.业务对象.io
 * 2、IO组件定义
 *      （1）IO实体命名应为对应服务英文名+Input或对应服务英文名+Output，例如CheckFovaChannelACSInput
 *      （2）所有IO实体必须继承OperationCommExt，ATS的IO组件在stub项目创建，其他服务IO组件在service项目创建 【现在都在service项目里】
 *      （3）IO组件必须定义@Component标注，防止服务在访问IO组件时无法装配bean*/
public class CheckIO {
    private static int packageFlag = 0;//包路径检查
    private static int nameFlag = 0;//名称检查
    private static int extendsFlag = 0;//继承检查
    private static int annotationFlag = 0;//标注检查
    private static String IOName = "";

    public static String check(String filePath, File file, String fileName) throws FileNotFoundException {
        IOName = fileName.substring(0, fileName.indexOf("."));


        StringBuffer checkResult = new StringBuffer();
        int errCount = 0;

        if (nameFlag == 0) {
            if (filePath.contains("service\\src") && (IOName.endsWith("Input") || IOName.endsWith("Output"))) {
                nameFlag = 1;
            } else {
                nameFlag = 2;
                checkResult.append(String.valueOf(errCount) + "、错误项目：在service项目中创建，IO实体命名应为对应服务英文名+Input或对应服务英文名+Output\n");
                checkResult.append("  文件路径和文件名：" + filePath + "\n\n");
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
                    if (s1.contains(".io;")){
                        packageFlag = 1;
                    }else{
                        packageFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：程序包package命名参考com.icbc.应用名.业务对象.io\n");
                        checkResult.append("  package路径：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                }
            }

            if (annotationFlag == 0){
                if (s1.contains("@Component")){
                    annotationFlag = 1;
                }
            }

            if (extendsFlag == 0){
                if (s1.contains("extends")){
                    if (s1.contains("extends OperationCommExt")){
                        extendsFlag = 1;
                    }else{
                        extendsFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：所有IO实体必须继承OperationCommExt\n");
                        checkResult.append("  package路径：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }

                }
            }
        }


        if (packageFlag == 0){
            checkResult.append(String.valueOf(errCount) + "、错误项目：程序包package命名参考com.icbc.应用名.业务对象.io\n");
            checkResult.append("\n");
            errCount++;
        }

        if (annotationFlag == 0){
            checkResult.append(String.valueOf(errCount) + "、错误项目：IO组件必须定义@Component标注\n");
            checkResult.append("\n");
            errCount++;
        }

        return checkResult.toString();
    }

}

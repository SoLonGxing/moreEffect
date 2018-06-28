package com.xingpk.xiazhuji.analysis.ruleCheck;


import com.util.CommonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**规则
 * 1、程序包
 *      在service项目中创建，程序包package命名参考com.icbc.应用名.业务对象.bo
 *
 * 2、BO定义
 *      （1）在service项目中创建，BO命名应为业务实体英文名，例如FovaChannel   【目前无法检查名称】
 *      （2）BO中值包括实体属性寄get、set方法，不应包含实际业务逻辑
 *      （3）BO组件必须定义@Comonent、@Scope标注，防止BOS在访问BO时无法装配多例Bean
 *
 */

public class CheckBO {
    private static int packageFlag = 0;//package路径检查
    private static int nameFlag = 0;//文件项目路径检查
    private static int beanFlag = 0;//setter,getter检查
    private static int annotationFlag = 0;//标注检查
    private static String BOName = "";

    public static String check(String filePath, File file, String fileName) throws FileNotFoundException {
        BOName = fileName.substring(0, fileName.indexOf("."));


        StringBuffer checkResult = new StringBuffer();
        int errCount = 0;

        if (nameFlag == 0){
            if (filePath.contains("service\\src")){
                nameFlag = 1;
            }else{
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
                    if (s1.contains(".bo")){
                        packageFlag = 1;
                        continue;
                    }else{
                        packageFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：程序包package命名参考com.icbc.应用名.业务对象.bo\n");
                        checkResult.append("  package路径：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                        continue;
                    }
                }
            }

            if (beanFlag == 0){
                if (s1.contains("()")){//检查getter
                    if (s1.contains("get")){
                        beanFlag = 1;
                    }else {
                        beanFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：BO中值包括实体属性寄get、set方法，不应包含实际业务逻辑-getter\n");
                        checkResult.append("  错误地点：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
                if (s1.contains("public void")){//检查setter
                    if (s1.contains("set")){
                        beanFlag = 1;
                    }else{
                        beanFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：BO中值包括实体属性寄get、set方法，不应包含实际业务逻辑-setter\n");
                        checkResult.append("  错误地点：" + s1 + "\n");
                        checkResult.append("  行号：" + line + "\n");
                        checkResult.append("  \n");
                        errCount++;
                    }
                    continue;
                }
            }

            if (annotationFlag == 0){
                if (s1.contains("@Component")){
                    annotationFlag = 1;
                    continue;
                }
                if (s1.contains("@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)")){
                    annotationFlag = 2;
                    continue;
                }
            }


        }


        if (annotationFlag != 2){
            checkResult.append(String.valueOf(errCount) + "、错误项目：BO组件必须定义@Component、@Scope标注，防止BOS在访问BO时无法装配多例Bean\n");
            checkResult.append("\n");
            errCount++;
        }

        return checkResult.toString();

    }
}

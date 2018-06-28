package com.xingpk.xiazhuji.analysis.ruleCheck;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**规则
 * 1、程序包
 *      在service项目中创建，程序包package命名参考com.icbc.应用名.业务对象.dao
 * 2、DAO定义
 *      （1）在service项目中创建，DAO命名应为业务实体名英文名+DAO例如RemitOrderDAO
 *      （2）Dao组件必须定义@Component标注，防止BOS在访问DAO时无法装配bean
 *
 * 3、编写DAO:
 *      DAO组件中执行Mapper中定义的SQL时，统一使用LocalTransaction接口处理，包括selectOne，selectList，insert，update等操作 【无法检查】
 * */
public class CheckDAO {
    private static int packageFlag = 0;//包路径检查flag
    private static int nameFlag = 0;//路径和名称检查
    private static int annotationFlag = 0;//标注检查
    private static String DAOName = "";

    public static String check(String filePath, File file, String fileName) throws FileNotFoundException {
        DAOName = fileName.substring(0, fileName.indexOf("."));


        StringBuffer checkResult = new StringBuffer();
        int errCount = 0;

        if (nameFlag == 0){
            if (filePath.contains("service\\src") && DAOName.contains("DAO")) {
                nameFlag = 1;
            } else {
                nameFlag = 2;
                checkResult.append(String.valueOf(errCount) + "、错误项目：在service项目中创建，DAO命名应为业务实体名英文名+DAO例如RemitOrderDAO\n");
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

            if (packageFlag == 0) {
                if (s1.contains("package")){
                    if (s1.contains(".dao;")){
                        packageFlag = 1;
                    }else{
                        packageFlag = 2;
                        checkResult.append(String.valueOf(errCount) + "、错误项目：在service项目中创建，程序包package命名参考com.icbc.应用名.业务对象.dao\n");
                        checkResult.append("  package路径：" + s1 + "\n\n");
                        errCount++;
                    }
                }
            }

            if (annotationFlag == 0){
                if (s1.contains("@Component")){
                    annotationFlag = 1;
                }
            }


        }


        if (annotationFlag == 0){
            checkResult.append(String.valueOf(errCount) + "、错误项目：Dao组件必须定义@Component标注，防止BOS在访问DAO时无法装配bean\n");
            checkResult.append("\n");
            errCount++;
        }
        return checkResult.toString();

    }

    }

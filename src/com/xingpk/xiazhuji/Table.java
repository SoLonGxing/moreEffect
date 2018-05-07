package com.xingpk.xiazhuji;

import sun.plugin2.message.GetAppletMessage;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private List<Column> columnList = new ArrayList<>();
    private List<String> keyValue = new ArrayList<>();
    private List<Column> keyColumn = new ArrayList<>();
    private String upperCaseColumnNameInSql = "";
    private String columnWithTypeInSql = "";
    private String columnWithIfNull = "";
    private String columnWithTypeAndIfNull4Insert = "";
    private String columnWithTypeAndIfNull4Update = "";
    private String keyColumnInWhere = "";

    private List<Column> columnWithoutKey = new ArrayList<>();

    public Table(String tableName, List<Column> columnList, List<String> keyValue) {
        this.tableName = tableName;
        this.columnList = columnList;
        this.keyValue = keyValue;
    }

    public void setKeyColumnInWhere(String keyColumnInWhere) {
        for (int i =0;i<keyColumn.size()-1;i++){
            Column cl = this.keyColumn.get(i);
            this.keyColumnInWhere += cl.getName().toUpperCase() + " = #{" + cl.getName().toLowerCase() + ", jdbcType=" + cl.getType().toUpperCase() + "}\nand";
        }
        Column endCl = this.keyColumn.get(this.keyColumn.size());
        this.keyColumnInWhere += endCl.getName().toUpperCase() + " = #{" + endCl.getName().toLowerCase() + ", jdbcType=" + endCl.getType().toUpperCase() + "}\n";
    }

    public String getKeyColumnInWhere() {
        if (keyColumnInWhere.equals(""))
            setKeyColumnInWhere("");
        return keyColumnInWhere;
    }

    public String getColumnWithIfNull() {
        if (columnWithIfNull.equals(""))
            setColumnWithIfNull("");
        return columnWithIfNull;
    }

    public String getColumnWithTypeAndIfNull4Insert() {
        if (columnWithTypeAndIfNull4Insert.equals(""))
            setColumnWithTypeAndIfNull4Insert("");
        return columnWithTypeAndIfNull4Insert;
    }

    public void setColumnWithIfNull(String columnWithIfNull) {
        for (int i =0;i<columnList.size();i++){
            this.columnWithIfNull += "              <if test=\"" + this.columnList.get(i).getName().toLowerCase() + " != null\">\n";
            this.columnWithIfNull += "                  " + this.columnList.get(i).getName().toUpperCase() + ",\n";
            this.columnWithIfNull += "              </if>\n";
        }

    }

    public void setColumnWithTypeAndIfNull4Insert(String columnWithTypeAndIfNull4Insert) {
        for (int i =0;i<columnList.size();i++){
            this.columnWithTypeAndIfNull4Insert += "              <if test=\"" + this.columnList.get(i).getName().toLowerCase() + " != null\">\n";
            this.columnWithTypeAndIfNull4Insert += "                  #{" + this.columnList.get(i).getName().toLowerCase() + ", jdbcType=" + this.columnList.get(i).getType().toUpperCase() + "},\n";
            this.columnWithTypeAndIfNull4Insert += "              </if>\n";
        }
    }

    public void setColumnWithTypeAndIfNull4Update(String columnWithTypeAndIfNull4Update) {
        for (int i =0;i<columnList.size();i++){
            this.columnWithTypeAndIfNull4Insert += "              <if test=\"" + this.columnList.get(i).getName().toLowerCase() + " != null\">\n";
            this.columnWithTypeAndIfNull4Insert += "                  " + this.columnList.get(i).getName().toUpperCase() + " = #{" + this.columnList.get(i).getName().toLowerCase() + ", jdbcType=" + this.columnList.get(i).getType().toUpperCase() + "},\n";
            this.columnWithTypeAndIfNull4Insert += "              </if>\n";
        }
    }

    public String getColumnWithTypeAndIfNull4Update() {
        if (columnWithTypeAndIfNull4Update.equals(""))
            setColumnWithTypeAndIfNull4Update("");
        return columnWithTypeAndIfNull4Update;
    }

    public List<Column> getColumnWithoutKey() {
        if (columnWithoutKey.size() <= 0)
            setColumnWithoutKey(null);
        return columnWithoutKey;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public List<String> getKeyValue() {
        return keyValue;
    }

    public void setTableName(String tableName) {

        this.tableName = tableName;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public void setKeyValue(List<String> keyValue) {
        this.keyValue = keyValue;
    }

    public String getUpperCaseColumnNameInSql() {
        if (upperCaseColumnNameInSql.equals(""))
            setUpperCaseColumnNameInSql("");
        return upperCaseColumnNameInSql;
    }

    public String getColumnWithTypeInSql() {
        if (columnWithTypeInSql.equals(""))
            setColumnWithTypeInSql("");
        return columnWithTypeInSql;
    }

    public void setUpperCaseColumnNameInSql(String upperCaseColumnNameInSql) {
        //upperCaseColumnNameInSql初始化
        for (int i = 0; i<this.columnList.size()-1; i++){
            this.upperCaseColumnNameInSql += this.columnList.get(i).getName().toUpperCase() + ", ";
        }
        this.upperCaseColumnNameInSql += this.columnList.get(this.columnList.size()).getName();
    }

    public void setColumnWithTypeInSql(String columnWithTypeInSql) {
        //columnWithTypeInSql初始化
        this.columnWithTypeInSql += "(";
        for (int i = 0; i<this.columnList.size()-1; i++){
            this.columnWithTypeInSql += "#{" + this.columnList.get(i).getName().toLowerCase() + ",jdbcType=" + this.columnList.get(i).getType().toUpperCase() + "}, ";
        }
        this.columnWithTypeInSql += "#{" + this.columnList.get(this.columnList.size()).getName().toLowerCase() + ",jdbcType=" + this.columnList.get(this.columnList.size()).getType().toUpperCase() + "})";

    }

    public void setColumnWithoutKey(List<Column> columnWithoutKey) {
        for(int i=0;i<columnList.size();i++){
            for (int j=0;j<keyValue.size();j++){
                if (ifNotExistInList(keyValue.get(j),columnList)){
                    this.columnWithoutKey.add(columnList.get(i));
                }else{
                    this.keyColumn.add(columnList.get(i));
                }
            }
        }
    }

    private boolean ifNotExistInList(String inputString, List<Column> inputList){
        for (int i =0;i<inputList.size();i++){
            if (inputString.equalsIgnoreCase(inputList.get(i).getName())){
                return false;
            }
        }
        return true;
    }


    public void genMapperFile(String boPath) {
        String IOJavaFileString = "";
        IOJavaFileString += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        IOJavaFileString += "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n";
        IOJavaFileString += "<mapper namespace=\"" + this.tableName + "Mapper" + "\">\n";

        //resultmap
        IOJavaFileString += "   <resultMap id=\"BaseResultMap\" type=\"" + boPath + "\">\n";

        for(int i=0;i<columnList.size();i++){
            String name = columnList.get(i).getName();
            String type = columnList.get(i).getType();
            String summary = columnList.get(i).getSummary();
            IOJavaFileString += "       <result column=\"" + name.toUpperCase() + "\" jdbcType=\"" + type.toUpperCase() + "\" property=\"" + name.toLowerCase() + "\" />\n";
        }
        IOJavaFileString += "   </resultMap>\n";

        //columnList
        IOJavaFileString += "   <sql id=\"Base_Column_List\">\n";
        IOJavaFileString += "   ";
        IOJavaFileString += getUpperCaseColumnNameInSql().toLowerCase() + "\n";
        IOJavaFileString += "   </sql>\n\n";


        //insert
        IOJavaFileString += "   <insert id=\"AP_ADD_" + this.tableName.toUpperCase() + "\" parameterType=\"" + boPath + "\">\n";
        IOJavaFileString += "       insert into " + this.tableName.toLowerCase() + " (\n";
        IOJavaFileString += this.getUpperCaseColumnNameInSql() + ")\n";
        IOJavaFileString += "values " + this.getColumnWithTypeInSql();
        IOJavaFileString += "   <insert>\n\n";

        //


    }
}

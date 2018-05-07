package com.xingpk.xiazhuji;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private List<Column> columnList = new ArrayList<>();
    private List<String> keyValue = new ArrayList<>();
    private String upperCaseColumnNameInSql = "";
    private String columnWithTypeInSql = "";
    private String columnWithIfNull = "";
    private String columnWithiTypeAndIfNull = "";
    private List<Column> columnWithoutKey = new ArrayList<>();

    public Table(String tableName, List<Column> columnList, List<String> keyValue) {
        this.tableName = tableName;
        this.columnList = columnList;
        this.keyValue = keyValue;
    }

    public List<Column> getColumnWithoutKey() {
        if (columnWithoutKey.size() <= 0)
            setColumnWithoutKey();
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
            setUpperCaseColumnNameInSql();
        return upperCaseColumnNameInSql;
    }

    public String getColumnWithTypeInSql() {
        if (columnWithTypeInSql.equals(""))
            setColumnWithTypeInSql();
        return columnWithTypeInSql;
    }


    public void setUpperCaseColumnNameInSql() {
        //upperCaseColumnNameInSql初始化
        for (int i = 0; i<this.columnList.size()-1; i++){
            upperCaseColumnNameInSql += this.columnList.get(i).getName().toUpperCase() + ", ";
        }
        upperCaseColumnNameInSql += this.columnList.get(this.columnList.size()).getName();
    }

    public void setColumnWithTypeInSql() {
        //columnWithTypeInSql初始化
        columnWithTypeInSql += "(";
        for (int i = 0; i<this.columnList.size()-1; i++){
            columnWithTypeInSql += "#{" + this.columnList.get(i).getName().toLowerCase() + ",jdbcType=" + this.columnList.get(i).getType().toUpperCase() + "}, ";
        }
        columnWithTypeInSql += "#{" + this.columnList.get(this.columnList.size()).getName().toLowerCase() + ",jdbcType=" + this.columnList.get(this.columnList.size()).getType().toUpperCase() + "})";

    }

    public void setColumnWithoutKey() {
        for(int i=0;i<columnList.size();i++){
            for (int j=0;j<keyValue.size();j++){
                if (ifNotExistInList(keyValue.get(j),columnList)){
                    columnWithoutKey.add(columnList.get(i));
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
}

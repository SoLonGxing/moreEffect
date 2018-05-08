package com.xingpk.xiazhuji;

import com.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String tableName;
    private String beanName;
    private String packagePath;
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

    public Table(String tableName, String beanName, String packagePath, List<Column> columnList, List<String> keyValue) {
        this.tableName = tableName;
        this.beanName = beanName;
        this.columnList = columnList;
        this.keyValue = keyValue;
        this.packagePath = packagePath;
        setColumnWithoutPrimaryKey(null);
    }

    /**生成 where里的
     * DAPCODE = #{dapcode, jdbcType=DECIMAL},
     * and ZONENO = #{zoneno, jdbcType=DECIMAL}
     *
     * 最后一个字段没有逗号
     * */
    public void setKeyColumnInWhere(String keyColumnInWhere) {
        for (int i =0;i<keyColumn.size()-1;i++){
            Column cl = this.keyColumn.get(i);
            this.keyColumnInWhere += cl.getName().toUpperCase() + " = #{" + cl.getName().toLowerCase() + ", jdbcType=" + cl.getType().toUpperCase() + "}\n          and ";
        }
        Column endCl = this.keyColumn.get(this.keyColumn.size()-1);
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

    /**生成 insert里的
     * <if test="dapcode != null">
     *      DAPCODE,
     * </if>
     *
     * 最后一个字段没有逗号
     * */

    public void setColumnWithIfNull(String columnWithIfNull) {
        for (int i =0;i<columnList.size()-1;i++){
            this.columnWithIfNull += "              <if test=\"" + this.columnList.get(i).getName().toLowerCase() + " != null\">\n";
            this.columnWithIfNull += "                  " + this.columnList.get(i).getName().toUpperCase() + ",\n";
            this.columnWithIfNull += "              </if>\n";
        }
        this.columnWithIfNull += "              <if test=\"" + this.columnList.get(this.columnList.size()).getName().toLowerCase() + " != null\">\n";
        this.columnWithIfNull += "                  " + this.columnList.get(this.columnList.size()).getName().toUpperCase() + "\n";
        this.columnWithIfNull += "              </if>\n";
    }

    /**生成 insert里的
     * <if test="dapcode != null">
     *      #{dapcode, jdbcType=CHAR},
     * </if>
     *
     * 最后一个字段没有逗号
     * */

    public void setColumnWithTypeAndIfNull4Insert(String columnWithTypeAndIfNull4Insert) {
        for (int i =0;i<columnList.size()-1;i++){
            String name = this.columnList.get(i).getName();
            this.columnWithTypeAndIfNull4Insert += "              <if test=\"" + name.toLowerCase() + " != null\">\n";
            this.columnWithTypeAndIfNull4Insert += "                  #{" + name.toLowerCase() + ", jdbcType=" + this.columnList.get(i).getType().toUpperCase() + "},\n";
            this.columnWithTypeAndIfNull4Insert += "              </if>\n";
        }
        String nameEnd = this.columnList.get(this.columnList.size()-1).getName();
        this.columnWithTypeAndIfNull4Insert += "              <if test=\"" + nameEnd.toLowerCase() + " != null\">\n";
        this.columnWithTypeAndIfNull4Insert += "                  #{" + nameEnd.toLowerCase() + ", jdbcType=" + this.columnList.get(this.columnList.size()-1).getType().toUpperCase() + "}\n";
        this.columnWithTypeAndIfNull4Insert += "              </if>\n";
    }

    /**生成 update里的
     * <if test="dapcode != null">
     *     DAPCODE = #{dapcode, jdbcType=CHAR},
     * </if>
     *
     * 最后一个字段没有逗号
     * */

    public void setColumnWithTypeAndIfNull4Update(String columnWithTypeAndIfNull4Update) {
        for (int i =0;i<columnList.size()-1;i++){
            String name = this.columnList.get(i).getName();
            this.columnWithTypeAndIfNull4Insert += "              <if test=\"" + name.toLowerCase() + " != null\">\n";
            this.columnWithTypeAndIfNull4Insert += "                  " + name.toUpperCase() + " = #{" + name.toLowerCase() + ", jdbcType=" + this.columnList.get(i).getType().toUpperCase() + "},\n";
            this.columnWithTypeAndIfNull4Insert += "              </if>\n";
        }
        String nameEnd = this.columnList.get(this.columnList.size()-1).getName();
        this.columnWithTypeAndIfNull4Insert += "              <if test=\"" + nameEnd.toLowerCase() + " != null\">\n";
        this.columnWithTypeAndIfNull4Insert += "                  " + nameEnd.toUpperCase() + " = #{" + nameEnd.toLowerCase() + ", jdbcType=" + this.columnList.get(this.columnList.size()-1).getType().toUpperCase() + "}\n";
        this.columnWithTypeAndIfNull4Insert += "              </if>\n";
    }

    public String getColumnWithTypeAndIfNull4Update() {
        if (columnWithTypeAndIfNull4Update.equals(""))
            setColumnWithTypeAndIfNull4Update("");
        return columnWithTypeAndIfNull4Update;
    }

    public List<Column> getColumnWithoutKey() {
        if (columnWithoutKey.size() <= 0)
            setColumnWithoutPrimaryKey(null);
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
        this.upperCaseColumnNameInSql += this.columnList.get(this.columnList.size()-1).getName();
    }

    //生成 values 后面的    (xxxx = #{xxxx， jdbcType="CHAR"}, yyyy.....)
    public void setColumnWithTypeInSql(String columnWithTypeInSql) {
        //columnWithTypeInSql初始化
        this.columnWithTypeInSql += "(";
        for (int i = 0; i<this.columnList.size()-1; i++){
            this.columnWithTypeInSql += "#{" + this.columnList.get(i).getName().toLowerCase() + ",jdbcType=" + this.columnList.get(i).getType().toUpperCase() + "}, ";
        }
        this.columnWithTypeInSql += "#{" + this.columnList.get(this.columnList.size()-1).getName().toLowerCase() + ",jdbcType=" + this.columnList.get(this.columnList.size()-1).getType().toUpperCase() + "})";

    }

    public void setColumnWithoutPrimaryKey(List<Column> columnWithoutKey) {
        for(int i=0;i<columnList.size();i++){
            for (int j=0;j<keyValue.size();j++){
                if (!keyValue.get(j).equalsIgnoreCase(columnList.get(i).getName())){
                    this.columnWithoutKey.add(columnList.get(i));

                }
            }
        }
        setColumnPrimaryKey(null);
    }

    public void setColumnPrimaryKey(List<Column> columnWithoutKey) {
        for (int j=0;j<keyValue.size();j++){
            for(int i=0;i<columnList.size();i++){
                if (keyValue.get(j).equalsIgnoreCase(columnList.get(i).getName())){
                    this.keyColumn.add(columnList.get(i));
                    continue;
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


    public void genMapperFile() {
        String mapperXmlString = "";
        mapperXmlString += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        mapperXmlString += "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n";
        mapperXmlString += "<mapper namespace=\"" + this.beanName + "Mapper" + "\">\n";

        //resultmap
        mapperXmlString += "   <resultMap id=\"BaseResultMap\" type=\"" + this.packagePath+ ".bo." + this.beanName + "\">\n";

        for(int i=0;i<columnList.size();i++){
            String name = columnList.get(i).getName();
            String type = columnList.get(i).getType();
            String summary = columnList.get(i).getSummary();
            mapperXmlString += "       <result column=\"" + name.toUpperCase() + "\" jdbcType=\"" + type.toUpperCase() + "\" property=\"" + name.toLowerCase() + "\" />\n";
        }
        mapperXmlString += "   </resultMap>\n";

        //columnList
        mapperXmlString += "   <sql id=\"Base_Column_List\">\n";
        mapperXmlString += "   ";
        mapperXmlString += getUpperCaseColumnNameInSql().toLowerCase() + "\n";
        mapperXmlString += "   </sql>\n\n";


        //insert
        mapperXmlString += "   <insert id=\"insert" + CommonUtil.upperCaseFirstCharacter(this.tableName.toLowerCase()) + "\" parameterType=\"" + this.packagePath+ "." + this.beanName + "\">\n";
        mapperXmlString += "       insert into " + this.tableName.toLowerCase() + " (\n";
        mapperXmlString += this.getUpperCaseColumnNameInSql() + ")\n";
        mapperXmlString += "values " + this.getColumnWithTypeInSql() + "\n";
        mapperXmlString += "   <insert>\n\n";

        //update
        mapperXmlString += "   <update id=\"updateByPrimaryKey\" parameterType=\"" + this.packagePath+ ".bo." + this.beanName + "\">\n";
        mapperXmlString += "       update " + this.tableName.toLowerCase() + "\n";
        mapperXmlString += "       <set>\n";
        mapperXmlString += "       " + this.getColumnWithTypeAndIfNull4Insert();
        mapperXmlString += "       </set>\n";
        mapperXmlString += "       where " + this.getKeyColumnInWhere();
        mapperXmlString += "   </update>\n\n";

        //selete
        mapperXmlString += "   <select id=\"selectByPrimaryKey\" parameterType=\"" + this.packagePath+ ".bo." + this.beanName + "\" resultMap=\"BaseResultMap\">\n";
        mapperXmlString += "       select\n";
        mapperXmlString += "       <include refid=\"Base_Column_List\" />\n";
        mapperXmlString += "       from " + this.tableName + "\n";
        mapperXmlString += "       where " + this.getKeyColumnInWhere();
        mapperXmlString += "   </select>\n";

        //delete

        mapperXmlString += "</mapper>";
        System.out.println(mapperXmlString);
    }

    public void genDaoFile() {
        String daoJavaString ="";
        daoJavaString += "package " + this.packagePath + ".dao;\n\n";
        daoJavaString += "import java.math.BigDecimal;\n";
        daoJavaString += "import java.util.ArrayList;\n";
        daoJavaString += "import java.util.HashMap;\n";
        daoJavaString += "import java.util.List;\n";
        daoJavaString += "import java.util.Map;\n";
        daoJavaString += "import org.springframework.stereotype.Component;\n";
        daoJavaString += "import com.icbc.cte.logging.LogFactory;\n";
        daoJavaString += "import com.icbc.stars.mybatis.tx.LocalTransaction;\n";
        //TODO bo的import

        //TODO 注释部分

        daoJavaString += "@Component\n";
        daoJavaString += "public class " + this.beanName + "Dao {\n";

        //insert方法
        daoJavaString += "  public boolean insert" + this.tableName.toUpperCase() + "(" + this.beanName + " param){\n";
        daoJavaString += "      int rst = LocalTransaction.insert(\"" + this.beanName + "Mapper.insert\"" + CommonUtil.upperCaseFirstCharacter(this.tableName.toLowerCase()) + "\", param);\n";
        daoJavaString += "      if(rst==1){\n";
        daoJavaString += "          return true;\n";
        daoJavaString += "      }\n";
        daoJavaString += "  return false;\n";
        daoJavaString += "  }\n\n";

        //update方法
        daoJavaString += "  public boolean update" + this.tableName.toUpperCase() + "(" + this.beanName + " param){\n";
        daoJavaString += "      int rst = LocalTransaction.update(\"" + this.beanName + "Mapper.updateByPrimaryKey\", param);\n";
        daoJavaString += "      if(rst==1){\n";
        daoJavaString += "          return true;\n";
        daoJavaString += "      }\n";
        daoJavaString += "  return false;\n";
        daoJavaString += "  }\n\n";


        //select方法
        daoJavaString += "  public " + this.beanName + " query" + this.tableName.toUpperCase() + "(" + this.beanName + " param){\n";
        daoJavaString += "      param = LocalTransaction.selectOne(\"" + this.beanName + "Mapper.selectByPrimaryKey\", param);\n";
        daoJavaString += "      return param;\n\n";
        daoJavaString += "  }\n\n";

        //结束
        daoJavaString += "}\n";

        System.out.println(daoJavaString);

    }
}

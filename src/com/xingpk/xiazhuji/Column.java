package com.xingpk.xiazhuji;

public class Column {
    private String name;
    private String type;
    private String summary;
    private String dic;

    public Column(String name, String type, String summary, String dic) {
        this.name = name;
        this.type = type;
        this.summary = summary;
        this.dic = dic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSummary() {
        return summary;
    }

    public String getDic() {
        return dic;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }
}

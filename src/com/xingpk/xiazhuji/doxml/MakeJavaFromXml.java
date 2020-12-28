package com.xingpk.xiazhuji.doxml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class MakeJavaFromXml {
    private String xmlString;
    private String javaString;

    public MakeJavaFromXml(String xmlString) {
        this.xmlString = xmlString;
    }


    public String makePostJava(int genFlag){
        //genflag 1-按照标签名称生成标签   2-按照标签的name项生成(这时候固定按照标签name生成setText

        javaString = "";
        //解析xml文件
        Document doc = null;
        SAXBuilder sax = new SAXBuilder();
        try {
            doc = sax.build(new StringReader(xmlString));
        } catch (JDOMException e) {
            e.printStackTrace();
            return "解析xml文件出错：" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "解析xml文件出错：" + e.getMessage();
        }

        Element root = doc.getRootElement();
        String elementName = "";
        if (genFlag == 2 ){
            elementName = root.getAttributeValue("name");
        }else{
            elementName = root.getName();
        }
        javaString += "Document doc = new Document();\n";
        javaString += "Element "+ elementName + " = new Element(\"" + elementName + "\");\n";
        javaString += "doc" + ".setRootElement(" + elementName +");\n";


        elementName(root,genFlag);
        return javaString;
    }


    private void elementName(Element element, int genFlag){
        if (!element.getName().equals("")) {
            if (genFlag == 2 ){

            }else {
                if (!element.getText().replace("\n", "").trim().equals("")) {
//                System.out.println(element.getName() + ".setText();");
                    javaString += element.getName() + ".setText();\n";
                }
            }
            String elementNameString = "";
            for(int i =0;i<element.getChildren().size();i++){
//                System.out.println("Element " + element.getChildren().get(i).getName() + " = new Element(" + element.getChildren().get(i).getName() + ");");
//                System.out.println(element.getName() + ".addContent(" + element.getChildren().get(i).getName() + ")");
                if(genFlag == 2) {
                    elementNameString = element.getAttributeValue("name");

                    String childElementName = element.getChildren().get(i).getAttributeValue("name");
                    javaString += "Element " + childElementName + " = new Element(\"" + childElementName + "\");\n";
                    javaString += childElementName + ".setText();\n";
                    javaString += elementNameString + ".addContent(" + childElementName + ");\n";
                    elementName(element.getChildren().get(i), genFlag);

                }else{
                    elementNameString = element.getChildren().get(i).getName();
                    javaString += "Element " + elementNameString + " = new Element(\"" + elementNameString + "\");\n";
                    elementName(element.getChildren().get(i), genFlag);
                    javaString += element.getName() + ".addContent(" + elementNameString + ");\n";
                }
                }

        }

    }
}

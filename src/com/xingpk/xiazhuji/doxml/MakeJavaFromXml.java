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


    public String makePostJava(){
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


        javaString += "Element "+ root.getName() + " = new Element(\"" + root.getName() + "\");\n";
        javaString += root.getName() + ".setRootElement(root);\n";



        elementName(root);
        return javaString;
    }


    private void elementName(Element element){
        if (!element.getName().equals("")) {
            if (!element.getText().replace("\n","").trim().equals("")) {
//                System.out.println(element.getName() + ".setText();");
                javaString += element.getName() + ".setText();\n";
            }

            for(int i =0;i<element.getChildren().size();i++){
//                System.out.println("Element " + element.getChildren().get(i).getName() + " = new Element(" + element.getChildren().get(i).getName() + ");");
//                System.out.println(element.getName() + ".addContent(" + element.getChildren().get(i).getName() + ")");
                javaString += "Element " + element.getChildren().get(i).getName() + " = new Element(\"" + element.getChildren().get(i).getName() + "\");\n";
                elementName(element.getChildren().get(i));
                javaString += element.getName() + ".addContent(" + element.getChildren().get(i).getName() + ")\n";
            }
        }

    }
}

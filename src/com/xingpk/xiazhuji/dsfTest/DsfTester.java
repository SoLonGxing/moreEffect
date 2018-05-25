package com.xingpk.xiazhuji.dsfTest;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.util.CommonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

public class DsfTester {
    private String ipAddress = "";
    private String group = "";
    private String interfaceName = "";
    private String filePath = "";
    private List<String> nameList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private List<Object> valueList = new ArrayList<>();

    public DsfTester(String ipAddress, String group, String interfaceName, String filePath) {
        this.ipAddress = "http://" + ipAddress + "/icbc/cocoa/json/" + interfaceName + "/1.0/executeService";
        this.group = group;
        this.interfaceName = interfaceName;
        this.filePath = filePath;
    }




    public String getResult()   {
        try {
            String postString = makePostString();
            return doHttiPost(ipAddress, postString);
        }catch (IOException e){
            return e.getMessage();
        }catch (Exception e1){
            return CommonUtil.getStackTrace(e1);
        }
    }

    public String makePostString() throws Exception {
        if (filePath.equalsIgnoreCase("")){
            return "";
        }

        String postString ="";
        FileInputStream is = null;

            is = new FileInputStream(filePath);

        HSSFWorkbook excel= null;

            excel = new HSSFWorkbook(is);

        //获取sheet1
        //初始化input字段和数值
        HSSFSheet sheet1 = null;
        sheet1 = excel.getSheetAt(0);//字段sheet

        int rowNumber1 = sheet1.getPhysicalNumberOfRows();
        for (int i =1;i<rowNumber1;i++)
        {
            HSSFRow row1 = sheet1.getRow(i);
            if (!row1.getCell(0).getRichStringCellValue().toString().trim().equals("")) {
                /**
                 * 0-字段英文名
                 * 1-类型
                 * 2-数据*/
                String name = row1.getCell(0).getRichStringCellValue().toString().trim();
                String type = row1.getCell(1).getRichStringCellValue().toString().trim();
                Object value = row1.getCell(2).getRichStringCellValue().toString().trim();

                nameList.add(name);
                typeList.add(CommonUtil.getColumnTypeCorrect(type));
                valueList.add(value);
            }
        }

        //获取sheet2
        //初始化input里的json字段

        HSSFSheet sheet2 = null;
        sheet2 = excel.getSheetAt(1);//字段sheet
        JSONObject json = new JSONObject();

        int rowNumber2 = sheet2.getPhysicalNumberOfRows();
        for (int i =1;i<rowNumber2;i++)
        {
            HSSFRow row1 = sheet2.getRow(i);
            if (row1 != null) {
                HSSFCell cell = row1.getCell(0);

                if (cell != null && !("").equals(cell.getRichStringCellValue().toString().trim())) {
                    /**
                     * 0-字段英文名
                     * 1-类型
                     * 2-数据*/
                    String name = row1.getCell(0).getRichStringCellValue().toString().trim();
                    String type = row1.getCell(1).getRichStringCellValue().toString().trim();
                    Object value = row1.getCell(2).getRichStringCellValue().toString().trim();

                    json.put(name, getValueCorrect(type, value));
                }
            }
        }

        JSONObject input = new JSONObject();

            for (int i = 0;i<nameList.size();i++){
                input.put(nameList.get(i),getValueCorrect(typeList.get(i),valueList.get(i)));

            }

        input.put(sheet2.getSheetName(),json.toJSONString());


        Object[] a = new Object[1];
        a[0] = input;

        postString = JSON.toJSONString(a);

        return postString;

    }

    private Object getValueCorrect(String type, Object value) throws Exception{

            switch (type) {
                case "String":
                    return String.valueOf(value);
                case "int":
                    return value;
                case "long":
                    return value;
                case "BigDecimal":
                    return new BigDecimal(String.valueOf(value));
                case "Date":
                    return String.valueOf(value);
            }

        return "";
    }


    public String doHttiPost(String url, String data) throws IOException {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);

            HttpPost httpPost = new HttpPost(url.trim());

            httpPost.setEntity(new StringEntity(data,"utf-8"));
            httpPost.setHeader("Dsf-Group", this.group);
            HttpResponse response = httpClient.execute(httpPost);


            HttpEntity entity = response.getEntity();
            if (entity != null){
                StringBuilder result = new StringBuilder("");
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), HTTP.UTF_8));

                String line = null;
                while ((line = reader.readLine()) != null){
                    result.append(line); }
                    return result.toString().replace(". ", "\n");
            }


        return "no response";

    }


}

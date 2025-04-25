package com.dk.common.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SmsUtil {
    private static final String SMS_URL =  "http://gateway.iems.net.cn/GsmsHttp";

    public static void sendSMS(String toPhone,String msg){
        try {
            URL url = new URL(SMS_URL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            String sendSmsData = organizationData(toPhone,msg);
            System.out.println(sendSmsData);
            out.write(sendSmsData);
            out.flush();
            out.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            StringBuffer buf = new StringBuffer();
            while ( (line = br.readLine()) != null ) {
                buf.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String organizationData(String  toPhone,String content) throws UnsupportedEncodingException {
        StringBuilder sendBuilder = new StringBuilder();
        sendBuilder.append("username=69576:admin");//机构ID:用户登录名
        sendBuilder.append("&password=8669667");//密码

//        sendBuilder.append("&from=13049881352");//发送手机号码
        sendBuilder.append("&to=").append(toPhone);//接收手机号，多个号码间以逗号分隔且最大不超过100个号码
//        sendBuilder.append("&expandPrefix=102");//自扩展端口(可选,如果没有传入空即可)
        content = URLEncoder.encode(content,"GBK");
        sendBuilder.append("&content=").append(content);//发送内容,标准内容不能超过70个汉字
//        sendBuilder.append("&presendTime=2015-08-05 09:32:00");//发送时间
        sendBuilder.append("&isVoice = 0|0|0|0");//是否语音,参见文档
        /**
         * 是否语音短信,若为空默认为普通短信.该参数格式:是否语音(0表示普通短信,1表示语音短信)|重听次数|重拨次数|是否回复(0表示不回复,1表示回复)
         * 如:isVoice=”1|1|2|0” 即:语音短信,重听次数1,重拨次数2,不回复.
         */
        return sendBuilder.toString();
    }
}

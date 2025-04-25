package com.dk.common.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SmsUtil {
    private static final String SMS_URL = "http://gateway.iems.net.cn/GsmsHttp";

    public static void sendSMS(String toPhone, String msg) {
        try {
            URL url = new URL(SMS_URL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            String sendSmsData = organizationData(toPhone, msg);
            System.out.println(sendSmsData);
            out.write(sendSmsData);
            out.flush();
            out.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            StringBuffer buf = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buf.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String organizationData(String toPhone, String content) throws UnsupportedEncodingException {
        StringBuilder sendBuilder = new StringBuilder();
        sendBuilder.append("username=69576:admin"); // Organization ID: User login name
        sendBuilder.append("&password=8669667"); // Password

        // sendBuilder.append("&from=13049881352"); // Sender phone number
        sendBuilder.append("&to=").append(toPhone); // Recipient phone number, multiple numbers separated by commas, up to 100 numbers
        // sendBuilder.append("&expandPrefix=102"); // Custom extension port (optional, leave empty if not used)
        content = URLEncoder.encode(content, "GBK");
        sendBuilder.append("&content=").append(content); // Message content, standard content must not exceed 70 Chinese characters
        // sendBuilder.append("&presendTime=2015-08-05 09:32:00"); // Send time
        sendBuilder.append("&isVoice=0|0|0|0"); // Whether it is a voice message, refer to documentation
        /**
         * Whether it is a voice SMS, defaults to regular SMS if empty. Format: isVoice|retryCount|redialCount|isReply
         * (0 for regular SMS, 1 for voice SMS) | retry count | redial count | reply (0 for no reply, 1 for reply)
         * Example: isVoice="1|1|2|0" means: voice SMS, retry 1 time, redial 2 times, no reply.
         */
        return sendBuilder.toString();
    }
}
package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnectionUtil {


    public static String postRequest(String pURL, String pList) {

        String myResult = "";

        try {
            //   URL 설정하고 접속하기 
            URL url = new URL(pURL); // URL 설정 

            HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속 

            http.setDefaultUseCaches(false);
            http.setDoInput(true); // 서버에서 읽기 모드 지정 
            http.setDoOutput(true); // 서버로 쓰기 모드 지정  
            http.setRequestMethod("POST"); // 전송 방식은 POST

            http.setRequestProperty("content-type", "application/json");
            System.out.println(pURL+"POST PURL ------------------------------------------");
            System.out.println(pList+"POST PList ----------------------------------------------------");

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(pList);
            writer.flush();


            //--------------------------
            //   Response Code
            //--------------------------
            //http.getResponseCode();


            //-------------------------- 
            //   서버에서 전송받기 
            //-------------------------- 
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }
            myResult = builder.toString();
            return myResult;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResult;
    }

}
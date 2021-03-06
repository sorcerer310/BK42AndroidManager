package com.bsu.bk42androidmanager.net;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http帮助类
 * Created by fengchong on 15/12/20.
 */
public class HttpUtils  {
    private URL url = null;
    private HttpURLConnection urlConn = null;
    private String urlpath = "";
    public HttpUtils(String u) throws IOException {
        urlpath = u;
    }

    /**
     * 发送命令
     * @param param         命令参数
     * @throws IOException
     */
    public void sendCommand(String param) throws IOException {
        if(param==null || param.equals(""))
            return;
        System.out.println(urlpath+param);
        url = new URL(urlpath+param);
        urlConn = (HttpURLConnection)url.openConnection();
        urlConn.setConnectTimeout(2000);
        InputStreamReader isr = new InputStreamReader(urlConn.getInputStream());
        BufferedReader buffer = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String inputLine = null;
        while(((inputLine = buffer.readLine())!=null)) {
            sb.append(inputLine);
        }

        System.out.println(sb);



        isr.close();
        urlConn.disconnect();
    }

}

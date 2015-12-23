package com.bsu.bk42androidmanager.net;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

/**
 * 用于异步执行http的任务类
 * Created by fengchong on 15/12/23.
 */
public class HttpTask extends AsyncTask<String,Integer,String> {
    private HttpUtils httputils = null;                                         //执行http请求的工具类
    private String urlparams = null;                                            //要执行的参数
    private Context context = null;
    public HttpTask(HttpUtils hu,String up,Context c){
        httputils = hu;
        urlparams = up;
        context = c;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            httputils.sendCommand(urlparams);
        } catch (IOException e) {
            Toast.makeText(context,"连接服务器失败:"+e.getMessage(),Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        return null;
    }
}

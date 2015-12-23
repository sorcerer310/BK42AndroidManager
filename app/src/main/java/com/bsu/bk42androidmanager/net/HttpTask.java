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
    private OnTaskThrowExceptionListener listener = null;                       //监听任务异常的监听器
    public HttpTask(HttpUtils hu,String up,OnTaskThrowExceptionListener l){
        httputils = hu;
        urlparams = up;
        listener = l;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            httputils.sendCommand(urlparams);
        } catch (IOException e) {
            if(listener!=null)
                listener.taskException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置监听任务异常的监听器
     * @param l 监听器对象
     */
    public void setOnTaskThrowExceptionListener(OnTaskThrowExceptionListener l){
        listener = l;
    }

    /**
     * 监听请求任务的异常
     */
    public interface OnTaskThrowExceptionListener{
        public void taskException(Exception e);
    }
}

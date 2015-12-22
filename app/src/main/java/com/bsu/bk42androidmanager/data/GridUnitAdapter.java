package com.bsu.bk42androidmanager.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.bsu.bk42androidmanager.R;
import com.bsu.bk42androidmanager.net.HttpUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GridView界面数据代理
 * Created by fengchong on 15/12/20.
 */
public class GridUnitAdapter extends SimpleAdapter {
    private Context context = null;
    private static HttpUtils http = null;
    private Thread httpthread = null;
    public GridUnitAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        try {
            http = new HttpUtils("http://192.168.1.112:8080/pgc2/plc_send_serial?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                http.sendCommand(msg.getData().getString("Cmd"));
            }catch (Exception e) {
                Toast.makeText(GridUnitAdapter.this.context, "连接服务器失败", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }
    };

    private Runnable runnable = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_cell,null);
        //获得单元数据
        final HashMap<String,GridUnitData> data = (HashMap<String, GridUnitData>) getItem(position);
        //设置textview属性
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(data.get("button").text);
        //设置ImageButton属性
        final ImageButton ib = (ImageButton) convertView.findViewById(R.id.imageButton);
        ib.setBackgroundResource(R.drawable.bt_icon1);
        ib.setOnTouchListener(new OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN || motionEvent.getAction()==MotionEvent.ACTION_UP ||
                    motionEvent.getAction()==MotionEvent.ACTION_CANCEL ) {
                    final Message msg = new Message();
                    Bundle b = new Bundle();
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        if (handler != null)
                            b.putString("Cmd", data.get("button").up);
                        ib.setAlpha(0.5f);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        if (handler != null)
                            b.putString("Cmd", data.get("button").down);
                        ib.setAlpha(1.0f);
                    }
                    msg.setData(b);

                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (handler != null) {
                                handler.sendMessage(msg);
                            }
                        }
                    };


                    httpthread = new Thread(runnable);
                    httpthread.start();
                }
                return false;
            }
        });

        return convertView;
    }

}



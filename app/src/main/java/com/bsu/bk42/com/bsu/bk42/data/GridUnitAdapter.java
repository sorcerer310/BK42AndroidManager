package com.bsu.bk42.com.bsu.bk42.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.bsu.bk42.R;
import com.bsu.bk42.com.bsu.bk42.net.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GridView界面数据代理
 * Created by fengchong on 15/12/20.
 */
public class GridUnitAdapter extends SimpleAdapter {
    private  Context context = null;
    private HttpUtils http = null;
    public GridUnitAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        try {
            http = new HttpUtils("http://192.168.1.112:8080/pgc2/plc_send_serial?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                try {
                    if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                        System.out.println("ACTION_DOWN");
                        http.sendCommand(data.get("button").down);
                        ib.setAlpha(0.5f);
                    }else if(motionEvent.getAction()==MotionEvent.ACTION_UP || motionEvent.getAction()==MotionEvent.ACTION_CANCEL){
                        System.out.println("ACTION_UP");
                        http.sendCommand(data.get("button").up);
                        ib.setAlpha(1.0f);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        return convertView;
    }
}

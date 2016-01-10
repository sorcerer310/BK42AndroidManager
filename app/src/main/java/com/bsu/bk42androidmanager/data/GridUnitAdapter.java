package com.bsu.bk42androidmanager.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.bsu.bk42androidmanager.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GridView界面数据代理
 * Created by fengchong on 15/12/20.
 */
public class GridUnitAdapter extends SimpleAdapter {
    private Context context = null;
    private OnHttpRequestListener listener = null;

    public GridUnitAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_cell,null);
        //获得单元数据
        final HashMap<String,GridUnitData> data = (HashMap<String, GridUnitData>) getItem(position);
        //设置textview属性
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(data.get("button").text);
        if(data.get("button").text.equals("敲鼓状态")){
            ImageButton ib = (ImageButton) convertView.findViewById(R.id.imageButton);
//            ib.setBackgroundResource(R.drawable.dump_is_not_ready);
            ib.setBackgroundResource(data.get("button").imgresources);
        }else {
            //设置ImageButton属性
            final ImageButton ib = (ImageButton) convertView.findViewById(R.id.imageButton);
            ib.setBackgroundResource(data.get("button").imgresources);
            ib.setOnTouchListener(new OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_UP ||
                            motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        String urlparam = null;
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            urlparam = data.get("button").down;
                            ib.setAlpha(0.5f);
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                            urlparam = data.get("button").up;
                            ib.setAlpha(1.0f);
                            //请求http数据
                            if(listener!=null)
                                listener.request(urlparam,ib,data);
                        }
                    }
                    return false;
                }
            });
        }
        return convertView;
    }

    /**
     * 设置http请求的监听器
     * @param l http请求监听器
     */
    public void setOnHttpRequestListener(OnHttpRequestListener l){
        listener = l;
    }

    /**
     * 当有http请求时，通知监听器
     */
    public static interface OnHttpRequestListener{
        public void request(String path,ImageButton ib,HashMap<String,GridUnitData> data);
    }
}



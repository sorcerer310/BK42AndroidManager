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
    private List<? extends Map<String,?>> allDatas = null;

    public GridUnitAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        allDatas = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_cell,null);
        //获得单元数据
        final HashMap<String,GridUnitData> data = (HashMap<String, GridUnitData>) getItem(position);
        //设置textview属性
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(data.get("unit").text);
//        if(data.get("unit").text.equals("敲鼓状态")){
        GridUnitData gud = data.get("unit");
        //当类型为显示状态
//        if(gud.type== GridUnitData.Type.state){
//            ImageButton ib = (ImageButton) convertView.findViewById(R.id.imageButton);
//            ib.setBackgroundResource(data.get("unit").imgresources);
//        }
//        //当类型为按钮
//        else if(gud.type == GridUnitData.Type.button){
            //设置ImageButton属性
            final ImageButton ib = (ImageButton) convertView.findViewById(R.id.imageButton);
            ib.setBackgroundResource(data.get("unit").imgresources);
            ib.setOnTouchListener(new OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_UP ||
                            motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        String urlparam = null;
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            urlparam = data.get("unit").down;
                            ib.setAlpha(0.5f);
                            //请求http数据
                            if(listener!=null && !urlparam.equals(""))
                                listener.request(urlparam,ib,data);
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                            urlparam = data.get("unit").up;
                            ib.setAlpha(1.0f);
                            //请求http数据
                            if(listener!=null && !urlparam.equals(""))
                                listener.request(urlparam,ib,data);
                        }
                    }
                    return false;
                }
            });
//        }
        return convertView;
    }

    /**
     * 获得数据源所有数据
     * @return
     */
    public List<? extends Map<String, ?>> getAllDatas() {
        return allDatas;
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



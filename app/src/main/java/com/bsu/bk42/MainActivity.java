package com.bsu.bk42;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.GridView;
import com.bsu.bk42.com.bsu.bk42.data.GridUnitAdapter;
import com.bsu.bk42.com.bsu.bk42.data.GridUnitData;
import info.hoang8f.android.segmented.SegmentedGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //用来处理android.os.NetworkOnMainThreadException异常
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        gridViewInit();
    }

    /**
     * SegmentedGroup初始化
     */
    private void segmentedGroupInit(){
        SegmentedGroup sg = (SegmentedGroup) findViewById(R.id.segmented);
//        sg.setBackgroundColor(Color.rgb());
    }

    /**
     * 初始化表格视图部分
     */
    private void gridViewInit(){
        GridView gv = (GridView) this.findViewById(R.id.gridView);
//        ArrayList<GridUnitData> datas = new ArrayList<GridUnitData>();
        ArrayList<HashMap<String,GridUnitData>> datas = new ArrayList<HashMap<String,GridUnitData>>();
        datas.add(makeHashMapData("bt_icon1.png","复位","type=click&area=w&address1=000500&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","通道锁1","type=click&area=w&address1=000501&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","通道锁2","type=click&area=w&address1=000502&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","船舱锁","type=click&area=w&address1=000503&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","祭坛锁","type=click&area=w&address1=000504&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","大道锁","type=click&area=w&address1=000505&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","华容道锁","type=click&area=w&address1=000506&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","通关锁","type=click&area=w&address1=000507&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData("bt_icon1.png","星阵门开"
                ,"type=h-bridge&area=w&address1=000600&address2=000700&val1=00&val2=01&readOrWrite=write"
                ,"type=nomal&area=w&address1=000700&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData("bt_icon1.png","星阵门关"
                ,"type=h-bridge&area=w&address1=000600&address2=000700&val1=01&val2=00&readOrWrite=write"
                ,"type=nomal&area=w&address1=000600&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData("bt_icon1.png","地图门开"
                ,"type=h-bridge&area=w&address1=000601&address2=000701&val1=00&val2=01&readOrWrite=write"
                ,"type=nomal&area=w&address1=000701&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData("bt_icon1.png","地图门关"
                ,"type=h-bridge&area=w&address1=000601&address2=000701&val1=01&val2=00&readOrWrite=write"
                ,"type=nomal&area=w&address1=000601&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData("bt_icon1.png","祭坛门开"
                ,"type=h-bridge&area=w&address1=000602&address2=000702&val1=00&val2=01&readOrWrite=write"
                ,"type=nomal&area=w&address1=000702&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData("bt_icon1.png","祭坛门关"
                ,"type=h-bridge&area=w&address1=000602&address2=000702&val1=01&val2=00&readOrWrite=write"
                ,"type=nomal&area=w&address1=000602&val1=00&readOrWrite=write"));

        gridView = (GridView) findViewById(R.id.gridView);
//        colcount = (int)(((getResources().getDisplayMetrics().widthPixels))/110);
//        gridView.setNumColumns(colcount);

        GridUnitAdapter adapter = new GridUnitAdapter(this,datas,R.layout.grid_view_cell,
                new String[]{"button"},
                new int[]{R.id.textView});
        gridView.setAdapter(adapter);
    }

    /**
     * 生成Apater使用的HashMap数据
     * @param ip    图片路径
     * @param text  标签名
     * @param down  按下发送的链接
     * @param up    抬起发送的链接
     * @return
     */
    private HashMap<String,GridUnitData> makeHashMapData(String ip,String text,String down,String up){
        HashMap<String,GridUnitData> data = new HashMap<String, GridUnitData>();
        data.put("button",new GridUnitData(ip,text,down,up));
        return data;
    }
}

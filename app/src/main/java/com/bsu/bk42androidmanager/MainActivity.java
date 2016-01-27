package com.bsu.bk42androidmanager;

import android.app.Activity;
import android.os.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import com.bsu.bk42androidmanager.data.GridUnitAdapter;
import com.bsu.bk42androidmanager.data.GridUnitData;
import com.squareup.okhttp.*;
import info.hoang8f.android.segmented.SegmentedGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private GridView gridView;
    private GridUnitAdapter adapter0,adapter1,adapter2;
    private BGARefreshLayout refreshLayout;
//    private HttpUtils http;
//    private Handler uihandler;

    private OkHttpClient httpclient = new OkHttpClient();
    private String url = "http://192.168.1.112:8080/pgc2/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpclient.setConnectTimeout(2000, TimeUnit.MILLISECONDS);
        gridView = (GridView) this.findViewById(R.id.gridView);

        initAdapter0();
        initAdapter1();
        initAdapter2();

        segmentedGroupInit();
//        gridViewInit();

    }

    /**
     * SegmentedGroup初始化
     */
    private void segmentedGroupInit(){
        SegmentedGroup sg = (SegmentedGroup) findViewById(R.id.segmented);

        RadioButton rb0 = (RadioButton) sg.getChildAt(0);
        RadioButton rb1 = (RadioButton) sg.getChildAt(1);
        RadioButton rb2 = (RadioButton) sg.getChildAt(2);

        rb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridView.setAdapter(adapter0);
                setOnHttpRequest(adapter0);
            }
        });
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridView.setAdapter(adapter1);
                setOnHttpRequest(adapter1);
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridView.setAdapter(adapter2);
                setOnHttpRequest(adapter2);
            }
        });

        gridView.setAdapter(adapter0);                                                                                  //设置数据源代理
        setOnHttpRequest(adapter0);
        rb0.setChecked(true);

        initRefreshLayout();
    }

    /**
     * 初始化第一个界面的按钮数据代理
     */
    private void initAdapter0(){
        ArrayList<HashMap<String,GridUnitData>> datas = new ArrayList<HashMap<String,GridUnitData>>();
        datas.add(makeHashMapData(R.drawable.dump_is_not_ready,"敲鼓状态","plc_state_query?point=dumpIsReady",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"复位","plc_send_serial?type=click&area=w&address1=000500&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"通道锁1","plc_send_serial?type=click&area=w&address1=000501&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"通道锁2","plc_send_serial?type=click&area=w&address1=000502&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"船舱锁","plc_send_serial?type=click&area=w&address1=000503&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"祭坛锁","plc_send_serial?type=click&area=w&address1=000504&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"大道锁","plc_send_serial?type=click&area=w&address1=000505&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"华容道锁","plc_send_serial?type=click&area=w&address1=000506&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"通关锁","plc_send_serial?type=click&area=w&address1=000507&val1=01&readOrWrite=write",""));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"星阵门开"
                ,"plc_send_serial?type=h-bridge&area=w&address1=000600&address2=000700&val1=00&val2=01&readOrWrite=write"
                ,"plc_send_serial?type=nomal&area=w&address1=000700&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"星阵门关"
                ,"plc_send_serial?type=h-bridge&area=w&address1=000600&address2=000700&val1=01&val2=00&readOrWrite=write"
                ,"plc_send_serial?type=nomal&area=w&address1=000600&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"地图门开"
                ,"plc_send_serial?type=h-bridge&area=w&address1=000601&address2=000701&val1=00&val2=01&readOrWrite=write"
                ,"plc_send_serial?type=nomal&area=w&address1=000701&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"地图门关"
                ,"plc_send_serial?type=h-bridge&area=w&address1=000601&address2=000701&val1=01&val2=00&readOrWrite=write"
                ,"plc_send_serial?type=nomal&area=w&address1=000601&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"祭坛门开"
                ,"plc_send_serial?type=h-bridge&area=w&address1=000602&address2=000702&val1=00&val2=01&readOrWrite=write"
                ,"plc_send_serial?type=nomal&area=w&address1=000702&val1=00&readOrWrite=write"));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"祭坛门关"
                ,"plc_send_serial?type=h-bridge&area=w&address1=000602&address2=000702&val1=01&val2=00&readOrWrite=write"
                ,"plc_send_serial?type=nomal&area=w&address1=000602&val1=00&readOrWrite=write"));


        adapter0 = new GridUnitAdapter(this,datas,R.layout.grid_view_cell,
                new String[]{"button"},
                new int[]{R.id.textView});
    }
    private void initAdapter1(){
        ArrayList<HashMap<String,GridUnitData>> datas = new ArrayList<HashMap<String,GridUnitData>>();
        datas.add(makeHashMapData(R.drawable.bt_icon1,"八阵图","",""));

        adapter1 = new GridUnitAdapter(this,datas,R.layout.grid_view_cell,
                new String[]{"button"},
                new int[]{R.id.textView});
    }
    private void initAdapter2(){
        ArrayList<HashMap<String,GridUnitData>> datas = new ArrayList<HashMap<String,GridUnitData>>();
        datas.add(makeHashMapData(R.drawable.bt_icon1,"凶宅","",""));

        adapter2 = new GridUnitAdapter(this,datas,R.layout.grid_view_cell,
                new String[]{"button"},
                new int[]{R.id.textView});
    }

    /**
     * 设置对应的数据代理的http请求
     * @param gua
     */
    private void setOnHttpRequest(GridUnitAdapter gua){
        //设置gridView中的http请求监控
        gua.setOnHttpRequestListener(new GridUnitAdapter.OnHttpRequestListener() {
            @Override
            public void request(String path,ImageButton ib,HashMap<String,GridUnitData> data) {

                final GridUnitData gud = data.get("button");
                final ImageButton ib_dump = ib;
                final String ppath = path;
                System.out.println("----------->"+ppath);
                new AsyncTask<Void,Void,Boolean>(){
                    private String httpresult = "";
                    private Boolean retval = false;
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        Request request = new Request.Builder().url(url + ppath).build();
                        Call call = httpclient.newCall(request);

//                        call.enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Request request, IOException e) {
//                                httpresult = e.getMessage();
//                                System.out.println("错误信息:"+httpresult);
//                                retval = false;
//                            }
//
//                            @Override
//                            public void onResponse(Response response) throws IOException {
////                                String resstr = response.body().string();
////                                if(gud.text.equals("敲鼓状态") && ib_dump!=null){
////                                    if(resstr.equals("true")) {
////                                        ib_dump.setBackgroundResource(R.drawable.dump_is_ready);
////                                    }else if(resstr.equals("false")){
////                                        ib_dump.setBackgroundResource(R.drawable.dump_is_not_ready);
////                                    }
////                                }
//                                retval = true;
//                            }
//                        });
                        try {
                            System.out.println("--------->url:"+request.urlString());
                            Response res = call.execute();
//                            System.out.println("--------->res:"+res.message());
                            retval = true;
                        } catch (IOException e) {
                            retval = false;
                            httpresult = e.getMessage();
                            System.out.println("-------------"+e.getMessage());
                        }
                        return retval;
                    }

                    @Override
                    protected void onPostExecute(Boolean b) {
                        super.onPostExecute(b);
                        if(!b){
                            Toast.makeText(MainActivity.this,"连接服务器失败:"+httpresult
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();

            }
        });

    }

    /**
     * 初始化下拉控件
     */
//    private void initRefreshLayout(BGARefreshLayout refreshLayout){
    private void initRefreshLayout(){
        refreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        refreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this,true);
        refreshLayout.setRefreshViewHolder(refreshViewHolder);

        //设置正在加载的文本
        refreshViewHolder.setLoadingMoreText("正在加载更多内容");

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {

        GridUnitAdapter adapter = (GridUnitAdapter) gridView.getAdapter();
        HashMap<String,GridUnitData> data = (HashMap<String, GridUnitData>) adapter.getItem(0);
        final GridUnitData gud = data.get("button");

//        ViewGroup vg = (ViewGroup) adapter.getView(0, null, null);
//        View v = vg.getChildAt(0);
//        final ImageButton ib_dump;
//        if(v instanceof ImageButton)
//            ib_dump = (ImageButton) v;
//        else
//            ib_dump = null;

        /**
         *  okhttp操作返回的数据
         */
        class AsyncTaskReturnData{
            public boolean success = false;
            public String retstring = "";
            public AsyncTaskReturnData(boolean b,String rs){
                success = b;
                retstring = rs;
            }
        }

        new AsyncTask<Void,Void,AsyncTaskReturnData>(){
            @Override
            protected AsyncTaskReturnData doInBackground(Void... voids) {
                Request request = new Request.Builder().url(url+gud.down).build();
                Call call = httpclient.newCall(request);
                try {
                    Response res = call.execute();
                    ResponseBody rb = res.body();
                    String rbs = rb.string();
                    return new AsyncTaskReturnData(true,rbs);

                } catch (IOException e) {
                    return new AsyncTaskReturnData(false,e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(AsyncTaskReturnData d) {
                super.onPostExecute(d);
                if(d.success){
                    if(d.retstring.equals("true"))
                        gud.imgresources = R.drawable.dump_is_ready;
                    else
                        gud.imgresources = R.drawable.dump_is_not_ready;
                }else {
                        Toast.makeText(MainActivity.this, "连接服务器失败:" + d.retstring
                                , Toast.LENGTH_LONG).show();
                    gud.imgresources = R.drawable.dump_is_not_ready;
                }
                refreshLayout.endRefreshing();
            }
        }.execute();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        return false;
    }

    /**
     * 生成Apater使用的HashMap数据
     * @param ip    图片路径
     * @param text  标签名
     * @param down  按下发送的链接
     * @param up    抬起发送的链接
     * @return
     */
    private HashMap<String,GridUnitData> makeHashMapData(int ip,String text,String down,String up){
        HashMap<String,GridUnitData> data = new HashMap<String, GridUnitData>();
        data.put("button",new GridUnitData(ip,text,down,up));
        return data;
    }


}

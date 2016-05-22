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
    private String url_sanguo = "http://192.168.1.112:8080/pgc2/";
//    private String url_sanguo2 = "http://192.168.1.113:8080/pgc2/";
    private String url_sanguo2 = "http://192.168.199.137:8080/pgc2/";
    private String url_xiongzhai = "http://192.168.1.111:8080/pgc2/";
    private String url_current = url_sanguo;
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
    }

    /**
     * SegmentedGroup初始化
     */
    private void segmentedGroupInit(){
        SegmentedGroup sg = (SegmentedGroup) findViewById(R.id.segmented);

        RadioButton rb0 = (RadioButton) sg.getChildAt(0);
        RadioButton rb1 = (RadioButton) sg.getChildAt(1);
        RadioButton rb2 = (RadioButton) sg.getChildAt(2);

        //点击tab1时初始化三国主题数据
        rb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridView.setAdapter(adapter0);
                setOnHttpRequest(adapter0);
                url_current = url_sanguo;
            }
        });
        //点击tab2时初始化三国2主题数据
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridView.setAdapter(adapter1);
                setOnHttpRequest(adapter1);
                url_current = url_sanguo2;
            }
        });
        //点击tab3时初始化凶宅主题数据
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridView.setAdapter(adapter2);
                setOnHttpRequest(adapter2);
                url_current = url_xiongzhai;
            }
        });

        gridView.setAdapter(adapter0);                                                                                  //设置数据源代理
        setOnHttpRequest(adapter0);
        rb0.setChecked(true);

        initRefreshLayout();
    }

    /**
     * 初始化卧龙的考验界面
     */
    private void initAdapter0(){
        ArrayList<HashMap<String,GridUnitData>> datas = new ArrayList<HashMap<String,GridUnitData>>();
        datas.add(makeHashMapDataWithType(R.drawable.dump_is_not_ready, "敲鼓状态", "plc_state_query?point=dumpIsReady", "", GridUnitData.Type.state));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"复位","",CmdMaker.clickWWrite("000500","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"通道锁1","",CmdMaker.clickWWrite("000501","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"通道锁2","",CmdMaker.clickWWrite("000502","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"船舱锁","",CmdMaker.clickWWrite("000503","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"祭坛锁","",CmdMaker.clickWWrite("000504","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"大道锁","",CmdMaker.clickWWrite("000505","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"华容道锁","",CmdMaker.clickWWrite("000506","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"通关锁","",CmdMaker.clickWWrite("000507","01")));
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
                new String[]{"unit"},
                new int[]{R.id.textView});
    }

    /**
     * 初始化八阵图界面
     */
    private void initAdapter1(){
        ArrayList<HashMap<String,GridUnitData>> datas = new ArrayList<HashMap<String,GridUnitData>>();
//        datas.add(makeHashMapData(R.drawable.bt_icon1, "复位", "",CmdMaker.clickWWrite("000500","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "复位", CmdMaker.clickWWrite("000500","01"),CmdMaker.clickWWrite("00050A","01")));
        datas.add(makeHashMapDataWithType(R.drawable.bt_icon1, "七星灯", "plc_leftlife", "", GridUnitData.Type.number));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"大门开","",CmdMaker.clickWWrite("00070D","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "续命灯开", "",CmdMaker.clickWWrite("000501","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"命灯复位","",CmdMaker.clickWWrite("000601","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"朱雀门开","",CmdMaker.clickWWrite("000700", "01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"朱雀通道","",CmdMaker.clickWWrite("000701","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"朱雀武器","",CmdMaker.clickWWrite("000702","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"朱雀通关","",CmdMaker.clickWWrite("000708","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"白虎门","",CmdMaker.clickWWrite("000703","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"白虎铁链","",CmdMaker.clickWWrite("000704","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"车零件1","",CmdMaker.clickWWrite("000705","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"车零件2","",CmdMaker.clickWWrite("000706","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"白虎武器","",CmdMaker.clickWWrite("000709","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"玄武门","",CmdMaker.clickWWrite("000707","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"分贝通关","",CmdMaker.clickWWrite("000408","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"玄武酒坛","",CmdMaker.clickWWrite("000509","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"玄武武器","",CmdMaker.clickWWrite("00070A","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"青龙沙盘","",CmdMaker.clickWWrite("00070B","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"青龙通道","",CmdMaker.clickWWrite("00070C","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"青龙武器","",CmdMaker.clickWWrite("00070F","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"大厅印升",CmdMaker.hbWWrite("000505","01","000605","00")
                ,CmdMaker.nomalWWrite("000505", "00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"大厅印降",CmdMaker.hbWWrite("000605","01","000505","00")
                ,CmdMaker.nomalWWrite("000605","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"白虎车退",CmdMaker.hbWWrite("000603","01","000503","00")
                ,CmdMaker.nomalWWrite("000603","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"白虎车进",CmdMaker.hbWWrite("000503","01","000603","00")
                ,CmdMaker.nomalWWrite("000503","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"玄武桥升",CmdMaker.hbWWrite("000604","01","000504","00")
                ,CmdMaker.nomalWWrite("000604","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"玄武桥降",CmdMaker.hbWWrite("000504","01","000604","00")
                ,CmdMaker.nomalWWrite("000504","00")));

        adapter1 = new GridUnitAdapter(this,datas,R.layout.grid_view_cell,
                new String[]{"unit"},
                new int[]{R.id.textView});
    }

    /**
     * 凶宅
     */
    private void initAdapter2(){
        ArrayList<HashMap<String,GridUnitData>> datas = new ArrayList<HashMap<String,GridUnitData>>();
        datas.add(makeHashMapDataState(R.drawable.dump_is_not_ready, "浇花状态", "plc_state_query?point=i0.11", true));
        datas.add(makeHashMapDataWithType(R.drawable.dump_is_not_ready, "敲门状态", "plc_state_query?point=i0.09", "", GridUnitData.Type.state));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "复位", "",CmdMaker.clickWWrite("000100","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "脚踏灯亮", "",CmdMaker.clickWWrite("000505", "01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "通风口开", "",CmdMaker.clickWWrite("00010A","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "梳妆台锁", "",CmdMaker.clickWWrite("000700","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "娃娃区锁", "",CmdMaker.clickWWrite("000600","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "投影仪亮", "",CmdMaker.clickWWrite("00010C","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "屏风门开", "",CmdMaker.clickWWrite("00010B","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "四画灯灭", "",CmdMaker.clickWWrite("000601","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "掉画上电", "",CmdMaker.clickWWrite("000602","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1, "迷宫音效", "",CmdMaker.clickWWrite("000800","01")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"床抽屉回",CmdMaker.hbWWrite("000000","01","000200","00")
                ,CmdMaker.nomalWWrite("000000", "00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"床抽屉出",CmdMaker.hbWWrite("000200","01","000000","00")
                ,CmdMaker.nomalWWrite("000200","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"结婚照开",CmdMaker.hbWWrite("000201","01","000001","00")
                ,CmdMaker.nomalWWrite("000201","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"结婚照关",CmdMaker.hbWWrite("000001","01","000201","00")
                ,CmdMaker.nomalWWrite("000001","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"女鬼回",CmdMaker.hbWWrite("000004","01","000003","00")
                ,CmdMaker.nomalWWrite("000004","00")));
        datas.add(makeHashMapData(R.drawable.bt_icon1,"女鬼出",CmdMaker.hbWWrite("000003","01","000004","00")
                ,CmdMaker.nomalWWrite("000003","00")));

        adapter2 = new GridUnitAdapter(this,datas,R.layout.grid_view_cell,
                new String[]{"unit"},
                new int[]{R.id.textView});
    }

    /**
     * 设置对应的数据代理的http请求
     * @param gua
     */
    private void setOnHttpRequest(GridUnitAdapter gua){
        //设置gridView中的http请求监控,响应按钮点击后的操作,
        // 按钮按下和抬起都有可能请求http操作，但两种操作请求的
        //http操作不一样
        gua.setOnHttpRequestListener(new GridUnitAdapter.OnHttpRequestListener() {
            @Override
            public void request(String path,ImageButton ib,HashMap<String,GridUnitData> data) {
                final GridUnitData gud = data.get("unit");
                final ImageButton ib_dump = ib;
                final String ppath = path;
                System.out.println("----------->"+ppath);
                new AsyncTask<Void,Void,String>(){
                    private String httpresult = "";
                    private String retval = null;
                    @Override
                    protected String doInBackground(Void... voids) {
                        Request request = new Request.Builder().url(url_current + ppath).build();
                        Call call = httpclient.newCall(request);
                        try {
                            Response res = call.execute();
                            retval = res.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                            retval = null;
                        }
                        return retval;
                    }

                    @Override
                    protected void onPostExecute(String r) {
                        super.onPostExecute(r);
                        if(r==null){
                            Toast.makeText(MainActivity.this,"连接服务器失败:"+httpresult
                                    ,Toast.LENGTH_LONG).show();
                        }else{
                            //如果当前的按钮为状态显示时
                            if(gud.type==GridUnitData.Type.state){
                                if(r.equals("true")){
                                    if(gud.backTypeState){
                                        gud.imgresources = R.drawable.dump_is_not_ready;
                                    }else{
                                        gud.imgresources = R.drawable.dump_is_ready;
                                    }
                                }else if(r.equals("false")){
                                    if(gud.backTypeState){
                                        gud.imgresources = R.drawable.dump_is_ready;
                                    }else{
                                        gud.imgresources = R.drawable.dump_is_not_ready;
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this, "获得状态数据失败。"
                                            , Toast.LENGTH_LONG).show();
                                    gud.imgresources = R.drawable.dump_is_not_ready;

                                }
                            }
                            else if(gud.type==GridUnitData.Type.number){
                                if(r.equals("0")){
                                    gud.imgresources = R.drawable._0;
                                }else if(r.equals("1")){
                                    gud.imgresources = R.drawable._1;
                                }else if(r.equals("2")){
                                    gud.imgresources = R.drawable._2;
                                }else if(r.equals("3")){
                                    gud.imgresources = R.drawable._3;
                                }else if(r.equals("4")){
                                    gud.imgresources = R.drawable._4;
                                }else if(r.equals("5")){
                                    gud.imgresources = R.drawable._5;
                                }else if(r.equals("6")){
                                    gud.imgresources = R.drawable._6;
                                }
//                                MainActivity.this.gridView.deferNotifyDataSetChanged();
//                                ListViewAdapter adapter =
//                                MainActivity.this.gridView.getAdapter().
                                //只有八阵图用了number类型数据,所以只刷新adapter1即可
                                adapter1.notifyDataSetChanged();
                            }
                            //如果当前的按钮为功能开关时
                            else{

                            }
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

    /**
     * 下拉刷新操作
     * @param bgaRefreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {

        GridUnitAdapter adapter = (GridUnitAdapter) gridView.getAdapter();
        ArrayList<GridUnitData> gudlist = new ArrayList<GridUnitData>();
        //获得所有要刷新状态的state,为每个要刷新状态的状态按钮执行访问服务器的数据
        ArrayList<HashMap<String,GridUnitData>> datas = (ArrayList<HashMap<String, GridUnitData>>) adapter.getAllDatas();
        for(HashMap<String,GridUnitData> hm:datas){
            if(hm.get("unit").type == GridUnitData.Type.state) {
                gudlist.add(hm.get("unit"));
            }
        }
        //如果没找到要刷新数据的状态按钮直接结束刷新
        //否则为每个状态按钮请求状态数据，刷新状态
        if(gudlist.size() == 0) {
            refreshLayout.endRefreshing();
            return;
        }else{
            for(GridUnitData gud:gudlist){
                final GridUnitData fgud = gud;
                new AsyncTask<Void,Void,AsyncTaskReturnData>(){
                    @Override
                    protected AsyncTaskReturnData doInBackground(Void... voids) {
                        Request request = new Request.Builder().url(url_current+fgud.down).build();
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
                                //如果取反标识为true，设置未准备好标识
                                if(fgud.backTypeState)
                                    fgud.imgresources = R.drawable.dump_is_not_ready;
                                //取反标识为false，设置准备好标识
                                else
                                    fgud.imgresources = R.drawable.dump_is_ready;
                            else
                                if(fgud.backTypeState)
                                    fgud.imgresources = R.drawable.dump_is_ready;
                                else
                                    fgud.imgresources = R.drawable.dump_is_not_ready;
                        }else {
                            Toast.makeText(MainActivity.this, "连接服务器失败:" + d.retstring
                                    , Toast.LENGTH_LONG).show();
                            fgud.imgresources = R.drawable.dump_is_not_ready;
                        }
                        refreshLayout.endRefreshing();
                    }
                }.execute();
            }
        }
//        HashMap<String,GridUnitData> data = (HashMap<String, GridUnitData>) adapter.getItem(0);
//        final GridUnitData gud = data.get("unit");
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        return false;
    }

    /**
     * 生成Apater使用的HashMap数据，默认为按钮类型
     * @param ip    图片路径
     * @param text  标签名
     * @param down  按下发送的链接
     * @param up    抬起发送的链接
     * @return
     */
    private HashMap<String,GridUnitData> makeHashMapData(int ip,String text,String down,String up){
        HashMap<String,GridUnitData> data = new HashMap<String, GridUnitData>();
        data.put("unit",new GridUnitData(ip,text,down,up));
        return data;
    }

    /**
     * 生成Apater使用的带有类型的HashMap数据，可以设置为按钮类型或者状态显示
     * @param ip    图片路径
     * @param text  标签名
     * @param down  按下发送的链接
     * @param up    抬起发送的链接
     * @param gt    设置该单元类型，可设置按钮类型或显示状态类型
     * @return
     */
    private HashMap<String,GridUnitData> makeHashMapDataWithType(int ip,String text,String down,String up,GridUnitData.Type gt){
        HashMap<String,GridUnitData> data = new HashMap<String, GridUnitData>();
        data.put("unit",new GridUnitData(ip,text,down,up,gt,false));
        return data;
    }

    /**
     * 用来生成状态按钮的代理数据
     * @param ip        图片资源路径
     * @param text      标签名
     * @param down      按下发送的url
     * @param backState 状态是否取反，如果为false，当服务器返回true为正常绿色状态；如果为true，当服务器返回true为不正常红色状态
     * @return
     */
    private HashMap<String,GridUnitData> makeHashMapDataState(int ip,String text,String down,boolean backState){
        HashMap<String,GridUnitData> data = new HashMap<String,GridUnitData>();
        data.put("unit",new GridUnitData(ip,text,down,"", GridUnitData.Type.state,backState));
        return data;
    }
}

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

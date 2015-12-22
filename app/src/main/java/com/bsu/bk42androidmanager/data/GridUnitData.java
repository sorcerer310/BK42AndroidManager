package com.bsu.bk42androidmanager.data;

/**
 * 表格按钮数据
 * Created by fengchong on 15/12/19.
 */
public class GridUnitData {
    public String imgpath = "";                                                //图标路径
    public String text = "";                                                   //显示文本
    public String down = "";                                                   //按下发送的url
    public String up = "";                                                     //抬起发送的url

    public GridUnitData(String ip,String txt,String d,String u){
        imgpath = ip;
        text = txt;
        down= d;
        up = u;
    }
}

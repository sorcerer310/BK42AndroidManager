package com.bsu.bk42androidmanager.data;

/**
 * 表格按钮数据
 * Created by fengchong on 15/12/19.
 */
public class GridUnitData {
    public enum Type {button,state};
    public int imgresources = -1;                                              //图标路径
    public String text = "";                                                   //显示文本
    public String down = "";                                                   //按下发送的url
    public String up = "";                                                     //抬起发送的url
    public GridUnitData.Type type = Type.button;                               //该元素类型，默认为按钮
    public boolean backTypeState = false;                                      //当数据为state类型是否取反值

    public GridUnitData(int ip,String txt,String d,String u){
        imgresources = ip;
        text = txt;
        down= d;
        up = u;
    }

    public GridUnitData(int ip,String txt,String d,String u,GridUnitData.Type gt,boolean back){
        imgresources = ip;
        text = txt;
        down= d;
        up = u;
        type = gt;
        backTypeState = back;
    }
}

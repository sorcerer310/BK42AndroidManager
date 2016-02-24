package com.bsu.bk42androidmanager;

/**
 * 用于生成服务器使用的url命令
 * Created by fengchong on 16/2/22.
 */
public class CmdMaker {
    private static String action = "plc_send_serial";
    /**
     * 按钮为点击类型，向w区某地址写入数据
     * @param address   要写入的地址，一般格式为"000500"，表示地址5.00，前4位为地址5，后2位表示16位的通道值
     * @param value     要写入的值，一般格式为"00"或“01”
     * @return          返回生成的url字符串
     */
    public static String clickWWrite(String address,String value){
        StringBuffer sb = new StringBuffer();
        sb.append(action)
                .append("?type=click&area=w&address1=")
                .append(address)
                .append("&val1=")
                .append(value)
                .append("&readOrWrite=write");
        return sb.toString();
    }
//    String makeStr = CmdMaker.clickWWrite("000500","01");
//    String result = "plc_send_serial?type=click&area=w&address1=000500&val1=01&readOrWrite=write";
//    String message = makeStr+"\n"+result;
//    assertEquals(message,makeStr,result);

    /**
     * h桥电路接通，需要设置两个地址的值
     * @param address1  地址1
     * @param value1    值1
     * @param address2  地址2
     * @param value2    值2
     * @return          返回生成的URL
     */
    public static String hbWWrite(String address1,String value1,String address2,String value2){
        StringBuffer sb = new StringBuffer();
        sb.append(action).append("?type=h-bridge&area=w&address1=")
                .append(address1).append("&address2=").append(address2)
                .append("&val1=").append(value1).append("&val2=").append(value2)
                .append("&readOrWrite=write");
        return sb.toString();
    }

    /**
     * 当h桥类持续按的功能恢复后执行的操作
     * @param address  要恢复的第一个地址
     * @param value    恢复第一个地址的值
     * @return          返回生成的URL
     */
    public static String nomalWWrite(String address,String value){
        StringBuffer sb = new StringBuffer();
        sb.append(action).append("?type=nomal&area=w&address1=")
                .append(address)
                .append("&val1=").append(value)
                .append("&readOrWrite=write");
        return sb.toString();
    }

    public static void main(String args[]){
        System.out.println(CmdMaker.clickWWrite("000500","01"));
        System.out.println(CmdMaker.hbWWrite("000600","00","000700","01"));
        System.out.println(CmdMaker.nomalWWrite("000700","00"));
    }

}

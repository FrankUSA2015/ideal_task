/**
 * @author 葛鑫
 * @Description
 * @create 2021-08-09 16:07
 *
 *内网ip：
 * A类  10.0.0.0-10.255.255.255
 * B类  172.16.0.0-172.31.255.255
 * C类  192.168.0.0-192.168.255.255
 */
import sun.net.util.IPAddressUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class judge_ip {

    public static boolean judge_Internal_outside_default(String IP){
        if(!ipLegal(IP)){
            return false;
        }
        byte[] addr = IPAddressUtil.textToNumericFormatV4(IP);
        return internalIp(addr);
    }
    /*
    * 判断ip是内网还是外网（默认的内网网段）。
    * */
    public static boolean internalIp(byte[] addr){
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        //10的十六进制值
        final byte SECTION_1 = 0x0A;
        //127的十六进制的值
        final byte SECTION_2 = (byte) 0xAC;
        //16的十六进制
        final byte SECTION_3 = (byte) 0x10;
        //31的十六进制
        final byte SECTION_4 = (byte) 0x1F;
        //192的十六进制
        final byte SECTION_5 = (byte) 0xC0;
        //168的十六进制
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0){
            case SECTION_1: return true;
            case SECTION_2: if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                return true;
            }
            case SECTION_5: switch(b1){
                case SECTION_6: return true;
            }
            default: return false;
        }
    }
    /*
     * 把自定义的单独的ip存储在Map中并且返回
     */
   public static Map<String,Boolean> get_custom_IP(List<String> IP){

       Map<String,Boolean> ip_map = new HashMap<>();
        for(String ip:IP){
            if(!judge_Internal_outside_default(ip)){
                ip_map.put(ip,true);
            }
        }
        return ip_map;
   }
   /*
   * 判断某个ip是否是给定的IP
   * */
    public static boolean is_Existence_in_range(String ip,Map<String,Boolean> map){
        if(!ipLegal(ip)){
            return false;
        }
        if(map.containsKey(ip)){
            return true;
        }
        return false;
    }
   /*
   * 把ip地址转换成十进制的值
   * */
    public static long get_IP_to_long(String IP){
        if(!ipLegal(IP)){
            return 0l;
        }
        IP = IP.trim();
        String[] ips = IP.split("\\.");
        long ip1 = Integer.parseInt(ips[0]);
        long ip2 = Integer.parseInt(ips[1]);
        long ip3 = Integer.parseInt(ips[2]);
        long ip4 = Integer.parseInt(ips[3]);
        long ip2long =1L* ip1 * 256 * 256 * 256 + ip2 * 256 * 256 + ip3 * 256 + ip4;
        return ip2long;
    }

    /*
    * 判断IP是否在给定的区间之内,
    * 输入的startIP《=endIP；
    * */
    public static boolean ipExistsInRange(String ip, String startIP, String endIP){
        return (get_IP_to_long(startIP)<=get_IP_to_long(ip)) && (get_IP_to_long(ip)<=get_IP_to_long(endIP));
    }
    /*
    * 判断输入的ip地址是否合法
    * */
    public static boolean ipLegal(String ip){
        ip = ip.trim();
        String[] ips = ip.split("\\.");
        for( int i=0;i<4;i++){
            int ip_each=Integer.parseInt(ips[i]);
            if(ip_each>255){
                System.out.printf("第%d位输入超过有效位，输入错误",i+1);
                return false;
            }
        }
        return true;
    }
}

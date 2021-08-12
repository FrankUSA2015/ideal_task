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


import java.util.*;

public class JudgeIP {
    private final static int ipv6_link_first = 65152;
    //febf的十进制
    private final static int ipv6_link_second = 65215;

    /*
    * 判断正常ip的是否是默认的内网地址。如：192.168.0.1
    * */
    public static boolean judgeInternaloutsidedefault(String IP){

        int[] addr = getIPToNumber(IP);
        return internalIp(addr);
    }

    private static int[] getIPToNumber(String IP){
        int[] number = new int[4];
        IP = IP.trim();
        String[] ips = IP.split("\\.");
        number[0]=Integer.parseInt(ips[0]);
        number[1]=Integer.parseInt(ips[1]);
        number[2]=Integer.parseInt(ips[2]);
        number[3]=Integer.parseInt(ips[3]);
        return number;
    }
    /*
    * 判断ip是内网还是外网（默认的内网网段）。
    * */
    private static boolean internalIp(int[] addr){

        final int b0 = addr[0];
        final int b1 = addr[1];
        final int SECTION_1 = 10;
        final int SECTION_2 = 127;
        final int SECTION_3 = 16;
        final int SECTION_4 = 31;
        final int SECTION_5 = 192;
        final int SECTION_6 = 168;
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
   * 判断某个ip是否是给定的IP(ipv6也适用这种方法)
   * */
    public static boolean isExistenceInRange(String ip,HashSet<String> hashset){

        if(hashset.contains(ip)){
            return true;
        }
        return false;
    }
   /*
   * 把ip地址转换成十进制的值
   * */
    private static long getIPtolong(String IP){

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
        return (getIPtolong(startIP)<=getIPtolong(ip)) && (getIPtolong(ip)<=getIPtolong(endIP));
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

    /*
    * 判断ip是否在给定的网段中，如192.168.1.127，是否在192.168.1.64/26
    * */
    public static boolean isInRange(String ip, String cidr) {
         if(!ipLegal(ip)){
            return false;
        }
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);

        return (ipAddr & mask) == (cidrIpAddr & mask);
    }
    /*
    *IPv6 判断是否是内网,ipv6的默认的内网，这里认为是链路本地地址，fe80::/10
    * */
    public static boolean judgeIPV6Internaloutsidedefault(String IP){
         if(!judgeIpv6(IP)){
             System.out.println("输入的ipv6不合法");
             return false;
         }
         IP = IP.trim();
         String[] ips = IP.split(":");
         int first = Integer.parseInt(ips[0], 16);
         if(first>=ipv6_link_first&&first<=ipv6_link_second){
             return true;
         }
         return false;
    }
    /*
    * 检查ipv6输入值得合法性,
    * */
    public static boolean judgeIpv6(String IP){
        IP = IP.trim();
        if(IP.length()!=39){
           IP=getFullIPv6(IP);
           IP=IP.toLowerCase(Locale.ROOT);
        }
        String[] ips = IP.split(":");
        for(int i=0;i<ips.length;i++){
            for(int j=0;j<4;j++){
            if(ips[i].toLowerCase(Locale.ROOT).charAt(j)>'f'){
                System.out.printf("IPV6中的第%d位的第%d个字符，输入非法",i+1,j+1);
              return false;
            }
            }
        }
        return true;
    }
    /*
    * 检查ipv6的值是否在指定的范围内
    * */
    public static boolean ipv6IsInRange(String ip,String rang_ip){
        //控制范围的最大值。
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,3);
        map.put(3,7);
        ip= ip.trim().toLowerCase(Locale.ROOT);
        rang_ip=rang_ip.trim();
        String range_number = rang_ip.split("/")[1];
        int number_range = Integer.parseInt(range_number);
        int range_4 = number_range/4;
        int range_num = number_range%4;
        String rang_ip_v6 = rang_ip.split("/")[0];
        rang_ip_v6.toLowerCase(Locale.ROOT);
        String ip_no = ip.replaceAll(":","");
        String range_ip_no = rang_ip_v6.replaceAll(":","");
          int n=0;
        if(range_4>0){
            n=range_4;
        }
        //先判断给定的位数是否相等
        if(ip_no.substring(0,n).equals(range_ip_no.substring(0,n))){
            int ip_digt = Integer.parseInt(ip_no.substring(n,n+1),16);
            int range_ip_digt = Integer.parseInt(range_ip_no.substring(n,n+1),16) +map.get(4-range_num);
            //判断有拆分的位中，ip是否在范围之内。
            if(ip_digt<=range_ip_digt && ip_digt>=Integer.parseInt(rang_ip_v6.substring(n,n+1),16)){
                return true;
            }
        }
        return false;
    }
    /*
    *判断ipv6是否在给定的网段，给定的网段必须连续.
    * start<=end
    * */
    public static boolean judegeRange(String ip,String start,String end){
       ip = getStandard(ip);
       start = getStandard(start);
       end = getStandard(end);
       if(HexadecimalAdditionSubtraction.judgeSize(start,ip)&&HexadecimalAdditionSubtraction.judgeSize(ip,end)){
           return true;
       }
       return false;
    }

    /*
    * 去掉ipv6的中的“：”，并且把他们全部变成小写
    * */
    private static String getStandard(String ip){
        ip = ip.replaceAll(":","");
        System.out.println(ip);
        ip= ip.trim().toLowerCase(Locale.ROOT);
        System.out.println(ip);
        return ip;
    }
    /*
    * ipv6缩写的IPV6，变为完整的IPv6
    * */
     public static String getFullIPv6(String ipv6){
        //入参为::时，此时全为0
        if (ipv6.equals("::")){
            return "0000:0000:0000:0000:0000:0000:0000:0000";
        }
        //入参已::结尾时，直接在后缀加0
        if (ipv6.endsWith("::")) {
            ipv6 += "0";
        }
        String[] arrs=ipv6.split(":");
        String symbol="::";
        int arrleng=arrs.length;
//        System.out.println(arrleng);
        while (arrleng<8){
            symbol+=":";
            arrleng++;
        }
        ipv6=ipv6.replace("::",symbol);
//        System.out.println("ipv6:"+ipv6);
        String fullip="";
        for (String ip:ipv6.split(":")){
            while (ip.length()<4){
                ip="0"+ip;
            }
            fullip+=ip+':';
        }
        return fullip.substring(0,fullip.length()-1);
    }
}

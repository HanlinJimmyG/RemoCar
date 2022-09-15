package com.example.remocar;

public class Tools {
    public static boolean isRightIP (String ip){
        // 排除空值
        if(ip == null){
            return false;
        }
        // 基于IP地址长度进行排除
        if(ip.length()<7 || ip.length()>15){
            return false;
        }
        // 避免Integer.parseInt转换时报错
        if(ip.charAt(0) == '.'  || ip.charAt(ip.length()-1) == '.' ){
            return false;
        }
        // 使用“."将字符串分割成四个部分
        String[] Inip= ip.split("\\.");
        if (Inip.length != 4){
            return false;
        }
        //判断所有的字符是否均是数字
        for(int i=0;i<4;i++){
            for(int j=0;j<Inip[i].length();j++){
                if(Inip[i].charAt(j) < '0' || Inip[i].charAt(j) > '9'){
                    return false;
                }
            }
        }
        //IP范围进行判断（0.0.0.0-255.255.255.255）
        for(int i=0;i < Inip.length;i++){
            int temp = Integer.parseInt(Inip[i]);
            if(temp < 0 || temp > 255){
                return false;
            }
        }
        return true;
    }

    /**
     * 输入字符串，检测该字符串是否符合port标准，即 5001 - 65535
     * @param port
     * @return boolean
     */
    public static boolean isRightPort (String port){
        // 排除空值
        if(port == null){
            return false;
        }
        // 基于port长度进行排除
        if(port.length()<4 || port.length()>5){
            return false;
        }
        //判断所有的字符是否均是数字
        for (int i=0;i<port.length(); i++){
            if(port.charAt(i)<'0' || port.charAt(i) > '9'){
                return false;
            }
        }
        //port范围进行判断（5001 - 65535）
        int temp = Integer.parseInt(port);
        if(temp < 5001 || temp > 65535){
            return false;
        }
        return true;
    }
}

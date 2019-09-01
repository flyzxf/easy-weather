package cn.tedu.weather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 80 on 2019/6/24.
 * 判断网络的状态
 */

public class NetUtil {
    public static final int NETWORK_NONE=0;//没有网络
    public static final int NETWORK_MOBILE=1;//手机网络
    public static final int NETWORK_WIFI=2;//WIFI网络
    /**
     * 获取网络的状态
     * @return
     */
    public static int getNetWorkState(Context context){
        //获取网络管理的类
        ConnectivityManager connManager=
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取当前网络状态
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();
        if(networkInfo==null){
            return NETWORK_NONE;
        }
        //获取当前手机的联网状态
        int netWorkType=networkInfo.getType();
        switch (netWorkType){
            case ConnectivityManager.TYPE_MOBILE:
                return NETWORK_MOBILE;
            case ConnectivityManager.TYPE_WIFI:
                return NETWORK_WIFI;

        }
        return NETWORK_NONE;
    }
}

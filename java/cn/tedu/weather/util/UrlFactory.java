package cn.tedu.weather.util;

/**
 * Created by 80 on 2019/7/2.
 * 所有的请求路径
 */

public class UrlFactory {
    /**
     * 获取请求天气的路径
     * @param number
     * @return
     */
    public static String getWeatherUrl(String number){
        String path="http://t.weather.sojson.com/api/weather/city/"+number;
        return path;
    }
}

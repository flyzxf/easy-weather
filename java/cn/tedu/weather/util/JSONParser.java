package cn.tedu.weather.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.tedu.weather.R;
import cn.tedu.weather.entity.CityInfo;
import cn.tedu.weather.entity.Quality;
import cn.tedu.weather.entity.Result;
import cn.tedu.weather.entity.UpdateWeather;
import cn.tedu.weather.entity.WeatherToday;

/**
 * Created by 80 on 2019/7/2.
 */

public class JSONParser {
    /**
     * 传入天气的JsonObject返回Result对象
     * @param obj
     * @return
     * @throws JSONException
     */
    public static Result getResult(JSONObject obj) throws JSONException{
        JSONObject dataObj=obj.getJSONObject("data");//天气数据
        JSONObject cityInfoObj=obj.getJSONObject("cityInfo");//城市数据
        //当前城市信息
        String city=cityInfoObj.getString("city");//衢州市
        Integer cityId=Integer.parseInt(cityInfoObj.getString("cityId"));//101211001
        CityInfo cityInfo=new CityInfo();
        cityInfo.setCity(city);
        cityInfo.setCityId(cityId);

        //最新天气信息
        String updateTime=cityInfoObj.getString("updateTime");//08:45
        String humidity =dataObj.getString("shidu");
        String wendu=dataObj.getString("wendu");
        UpdateWeather updateWeather=new UpdateWeather();
        updateWeather.setHumidity(humidity);
        updateWeather.setUpdateTime(updateTime);
        updateWeather.setWendu(wendu);

        //空气质量
        Double pm25=Double.parseDouble(dataObj.getString("pm25"));
        String strQuality=dataObj.getString("quality");

        int qualityRes;
        if(pm25<=100){
            qualityRes=R.mipmap.weather_0_100;
        }else if(pm25>100&&pm25<=150){
            qualityRes=R.mipmap.weather_101_150;
        }else if(pm25>150&&pm25<=200){
            qualityRes=R.mipmap.weather_151_200;
        }else if(pm25>200&&pm25<=300){
            qualityRes=R.mipmap.weather_201_300;
        }else{
            qualityRes=R.mipmap.weather_greater_300;
        }
        Quality quality=new Quality();//空气质量
        quality.setQualityRes(qualityRes);
        quality.setPm25(pm25);
        quality.setQuality(strQuality);

        //今日天气概况
        JSONArray forecastArray=dataObj.getJSONArray("forecast");//未来天气
        JSONObject todayObj = forecastArray.getJSONObject(0);//今日天气概况
        String high=todayObj.getString("high");//高温
        high=high.substring(3,high.length());
        String low=todayObj.getString("low");//低温
        low=low.substring(3,low.length());
        String week=todayObj.getString("week");//星期
        String type=todayObj.getString("type");//小雨
        String fx=todayObj.getString("fx");//风向
        int weatherTodayResId=R.mipmap.weather_qing;

        switch (type) {
            case "小雨":
            case "小到中雨":
                weatherTodayResId=R.mipmap.weather_xiaoyu;
                break;
            case "中雨":
            case "中到大雨":
                weatherTodayResId=R.mipmap.weather_zhongyu;
                break;
            case "大雨":
            case "大到暴雨":
                weatherTodayResId=R.mipmap.weather_dayu;
                break;
            case "暴雨":
            case "暴雨到大暴雨":
                weatherTodayResId=R.mipmap.weather_baoyu;
                break;
            case "大暴雨":
            case "大暴雨到特大暴雨":
                weatherTodayResId=R.mipmap.weather_dabaoyu;
                break;
            case "特大暴雨":
                weatherTodayResId=R.mipmap.weather_tedabaoyu;
                break;
            case "冻雨":
                weatherTodayResId=R.mipmap.weather_xiaoyu;
                break;
            case "阵雨":
                weatherTodayResId=R.mipmap.weather_zhenyu;
                break;
            case "雷阵雨":
                weatherTodayResId=R.mipmap.weather_leizhenyu;
                break;
            case "雨夹雪":
                weatherTodayResId=R.mipmap.weather_yujiaxue;
                break;
            case "雷阵雨伴有冰雹":
                weatherTodayResId=R.mipmap.weather_leizhenyubingbao;
                break;
            case "小雪":
            case "小到中雪":
                weatherTodayResId=R.mipmap.weather_xiaoxue;
                break;
            case "中雪":
            case "中到大雪":
                weatherTodayResId=R.mipmap.weather_zhongxue;
                break;
            case "大雪":
            case "大到暴雪":
                weatherTodayResId=R.mipmap.weather_daxue;
                break;
            case "暴雪":
                weatherTodayResId=R.mipmap.weather_baoxue;
                break;
            case "阵雪":
                weatherTodayResId=R.mipmap.weather_zhenyu;
                break;
            case "晴":
                weatherTodayResId= R.mipmap.weather_qing;
                break;
            case "多云":
                weatherTodayResId=R.mipmap.weather_duoyun;
                break;
            case "阴":
                weatherTodayResId=R.mipmap.weather_yin;
                break;
            case "沙尘暴":
            case "强沙尘暴":
            case "扬沙":
            case "浮尘":
                weatherTodayResId=R.mipmap.weather_shachenbao;
                break;
            case "雾":
            case "霾":
                weatherTodayResId=R.mipmap.weather_wu;
                break;
        }
        WeatherToday weatherToday=new WeatherToday();//今日天气概况
        weatherToday.setHigh(high);
        weatherToday.setLow(low);
        weatherToday.setWeek(week);
        weatherToday.setType(type);
        weatherToday.setFx(fx);
        weatherToday.setWeatherTodayResId(weatherTodayResId);


        Result result=new Result();//封装所有的数据
        result.setCityInfo(cityInfo);
        result.setQuality(quality);
        result.setUpdateWeather(updateWeather);
        result.setWeatherToday(weatherToday);
        return result;
    }
}

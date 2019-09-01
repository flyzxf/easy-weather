package cn.tedu.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import cn.tedu.weather.entity.Result;
import cn.tedu.weather.util.Constant;
import cn.tedu.weather.util.HttpUtil;
import cn.tedu.weather.util.JSONParser;
import cn.tedu.weather.util.PreferencesUtil;
import cn.tedu.weather.util.UrlFactory;

public class WeatherService extends Service {
    PreferencesUtil preferencesUtil;

    public WeatherService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferencesUtil=PreferencesUtil.getInstance();
        //应用程序可以能被意外销毁,service是粘性的，会自动重启,
        //重启时需要重新初始化preferencesUtil.init();
        preferencesUtil.init(this);
    }

    @Override//启动Service就会执行
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestWeather(intent.getStringExtra("number"));
        return Service.START_REDELIVER_INTENT;//粘性Service重启后自带Intent
    }


    /**
     * 发生网络请求,获取天气数据
     */
    public void requestWeather(final String number) {
        new Thread(){
            String message="";
            @Override
            public void run() {
                try {
                    //获取请求路径
                    String path = UrlFactory.getWeatherUrl(number);
                    //get请求获取输入流
                    InputStream is= HttpUtil.getInputStream(path);
                    //将从服务端获取的输入流转换成字符串
                    String data=HttpUtil.isToString(is);
                    //obj: JSON数据
                    JSONObject obj=new JSONObject(data);
                    //将JSONObject封装成Result实体类
                    Result result= JSONParser.getResult(obj);
                    Log.e("tedu","result="+result);
                    //将天气数据保存到偏好设置
                    preferencesUtil.saveParam("result",result);
                    //发生广播通知Activity更新界面
                    message= Constant.MESSAGE_UPDATE_WEATHER;
                } catch (IOException e) {
                    message=Constant.MESSAGE_TIME_OUT;
                    e.printStackTrace();
                } catch (JSONException e) {
                    //JSON解析异常
                    e.printStackTrace();
                }finally{
                    Intent intent=new Intent(message);
                    sendBroadcast(intent);
                }
            }
        }.start();

    }
}

package cn.tedu.weather.ui;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.tedu.weather.R;
import cn.tedu.weather.entity.CityInfo;
import cn.tedu.weather.entity.Quality;
import cn.tedu.weather.entity.Result;
import cn.tedu.weather.entity.UpdateWeather;
import cn.tedu.weather.entity.WeatherToday;
import cn.tedu.weather.service.WeatherService;
import cn.tedu.weather.util.Constant;
import cn.tedu.weather.util.HttpUtil;
import cn.tedu.weather.util.JSONParser;
import cn.tedu.weather.util.NetUtil;
import cn.tedu.weather.util.PreferencesUtil;
import cn.tedu.weather.util.UrlFactory;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final int MESSAGE_TIME_OUT =0 ;//超时
    public static final int MESSAGE_UPDATE_WEATHER=1;//更新成功
    private static final int REQUEST_CODE_CHOOSE_CITY = 5;//请求码，选择城市
    //Title区域
    ImageView ivUpdate;//更新按钮
    ImageView ivLocation;//定位按钮
    ImageView ivShared;//分享按钮
    ImageView ivCityManager;//城市选择按钮
    TextView tvTitleCityName;//标题栏城市名称
    //当前天气情况
    TextView tvCity;//城市名称
    TextView tvTime;//发布时间
    TextView tvHumidity;//当前湿度
    TextView tvTempInfo;//当前温度
    //空气质量
    TextView tvPmData;//PM2.5
    ImageView ivPm2_5;//空气质量图
    TextView tvQuality;//空气质量

    //今日天气情况
    ImageView ivWeather;//天气图片
    TextView tvWeek;//星期
    TextView tvTemperature;//今日温度范围
    TextView tvClimate;//气候
    TextView tvWind;//风向

    //菊花圈
    ProgressBar pbProgress;
    //提示文件
    TextView tvMessage;

//    ScrollView svScroll;

    SwipeRefreshLayout srlRefresh;
    private String number="101211001";
    private PreferencesUtil preferencesUtil;
    private WeatherReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Title区域
        ivUpdate=findViewById(R.id.iv_title_update);
        ivLocation=findViewById(R.id.iv_title_location);
        ivShared=findViewById(R.id.iv_title_share);
        ivCityManager=findViewById(R.id.iv_title_city_manager);
        tvTitleCityName=findViewById(R.id.tv_title_city_name);

        //当前天气情况
        tvCity=findViewById(R.id.tv_city);
        tvTime=findViewById(R.id.tv_time);
        tvHumidity=findViewById(R.id.tv_humidity);
        tvTempInfo=findViewById(R.id.tv_temp_info);

        //空气质量
        tvPmData=findViewById(R.id.tv_pm_data);
        ivPm2_5=findViewById(R.id.iv_pm2_5);
        tvQuality=findViewById(R.id.tv_quality);

        //今日天气情况
        ivWeather=findViewById(R.id.iv_weather);
        tvWeek=findViewById(R.id.tv_week);
        tvTemperature=findViewById(R.id.tv_temperature);
        tvClimate=findViewById(R.id.tv_climate);
        tvWind=findViewById(R.id.tv_wind);

        //菊花圈，文字
        pbProgress=findViewById(R.id.pb_Progress);
        tvMessage=findViewById(R.id.tv_message);
        //下拉刷新
        srlRefresh=findViewById(R.id.srl_refresh);




        //四个ImageView添加监听器
        ivUpdate.setOnClickListener(this);
        ivLocation.setOnClickListener(this);
        ivShared.setOnClickListener(this);
        ivCityManager.setOnClickListener(this);
        //下拉刷新控件
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {//下拉时，该方法执行
                startWeatherService();

            }
        });

        //初始化偏好设置工具类
        preferencesUtil=PreferencesUtil.getInstance();
        //从偏好设置中获取天气信息
        Result result= (Result) preferencesUtil.getParam("result",null);
        if(result!=null){
            //有缓存,直接显示
            setViews(result);
        }

        receiver=new WeatherReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constant.MESSAGE_TIME_OUT);
        filter.addAction(Constant.MESSAGE_UPDATE_WEATHER);
        //注册广播接收器
        registerReceiver(receiver,filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_update:
                //判断网络状态
                int netWorkState=NetUtil.getNetWorkState(this);
                switch (netWorkState){
                    case NetUtil.NETWORK_NONE:
                        Toast.makeText(this,"没有网络",Toast.LENGTH_SHORT).show();
                        break;
                    case NetUtil.NETWORK_MOBILE:
                    case NetUtil.NETWORK_WIFI:
                        requestWeatherNormal();
                        break;
                }
                break;
            case R.id.iv_title_share:
                Toast.makeText(this,"分享",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_title_location:
                Toast.makeText(this,"定位",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_title_city_manager:
                Intent intent=new Intent(MainActivity.this,ChooseCityActivity.class);
                startActivityForResult(intent,REQUEST_CODE_CHOOSE_CITY);//启动一个页面为了拿到该页面销毁时返回的结果
                break;
        }
    }

    class WeatherReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {//接受Service发生的广播
            String action=intent.getAction();//获取频道号

                    switch (action){
                        case Constant.MESSAGE_TIME_OUT://网络超时
                            Toast.makeText(MainActivity.this,"网络超时",Toast.LENGTH_SHORT).show();
                            ViewGone();
                            break;
                        case Constant.MESSAGE_UPDATE_WEATHER://更新天气
                            Result result = (Result) preferencesUtil.getParam("result", null);
                            setViews(result);
                            ViewGone();
                            break;
                    }
        }
    }

    //
    private void startWeatherService(){
        Intent service=new Intent(MainActivity.this, WeatherService.class);
        service.putExtra("number",number);
        startService(service);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CHOOSE_CITY&&resultCode==RESULT_OK){//获取的是城市number
            number=data.getStringExtra("number");
            requestWeatherNormal();
        }
    }

    /**
     * 请求天气显示菊花圈
     */
    private void requestWeatherNormal(){
        if(pbProgress.getVisibility()==View.GONE){//如果pbProgress不可见,没有在刷新网络
            pbProgress.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.VISIBLE);
            startWeatherService();
        }
    }



    private void setViews(Result result) {
        CityInfo cityInfo=result.getCityInfo();//当前城市信息
        Quality quality=result.getQuality();//空气质量
        UpdateWeather updateWeather=result.getUpdateWeather();//最新天气信息
        WeatherToday weatherToday=result.getWeatherToday();//今天天气概况

        ivUpdate=findViewById(R.id.iv_title_update);
        ivLocation=findViewById(R.id.iv_title_location);
        ivShared=findViewById(R.id.iv_title_share);
        ivCityManager=findViewById(R.id.iv_title_city_manager);
        tvTitleCityName=findViewById(R.id.tv_title_city_name);

        tvTitleCityName.setText(cityInfo.getCity()+"天气");
        tvCity.setText(cityInfo.getCity());
        //当前天气情况
        tvTime.setText("今天"+updateWeather.getUpdateTime()+"发布");
        tvHumidity.setText("湿度:"+updateWeather.getHumidity());
        tvTempInfo.setText("温度:"+updateWeather.getWendu());

        //空气质量
        tvPmData.setText(quality.getPm25()+"");
//                    ivPm2_5 思考图片如何显示?
        ivPm2_5.setImageResource(quality.getQualityRes());
        tvQuality.setText(quality.getQuality());

        //今日天气情况
//                    ivWeather  思考图片如何显示?
        ivWeather.setImageResource(weatherToday.getWeatherTodayResId());
        tvWeek.setText(weatherToday.getWeek());
        tvTemperature.setText(weatherToday.getLow()+"~"+weatherToday.getHigh());
        tvClimate.setText(weatherToday.getType());
        tvWind.setText(weatherToday.getFx());
    }

    /**
     * 将显示进度取消
     */
    private void ViewGone() {
        pbProgress.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
    }

    /**
     * back键改home键
     */
    @Override
    public void onBackPressed() {//重写back按钮的功能
        //回退键改home
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);//取消广播注册
        super.onDestroy();
    }
}


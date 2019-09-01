package cn.tedu.weather.application;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import cn.tedu.weather.entity.City;
import cn.tedu.weather.ui.MainActivity;

/**
 * Created by 80 on 2019/6/28.
 * Application 应用程序启动时首先加载MyApplication类,该类中所的数据允许在整个APP的生命周期
 *
 * 在该类中加载城市数据
 */

public class MyApplication extends Application {
    List<City>cities;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


}

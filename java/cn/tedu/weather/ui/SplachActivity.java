package cn.tedu.weather.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.weather.application.MyApplication;
import cn.tedu.weather.entity.City;
import cn.tedu.weather.util.PreferencesUtil;

public class SplachActivity extends AppCompatActivity {
    private static final int MESSAGE_LOAD_CITY_COMPLETE = 20 ;
    private PreferencesUtil preferencesUtil;//偏好设置的工具类
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           switch (msg.what){
               case MESSAGE_LOAD_CITY_COMPLETE:
                   //1.检测偏好设置是否是否存在“某个值”，如果不存，表示第一次安装，跳转到GuideActivity,
                   //
                   boolean isFirst =preferencesUtil.isFirst();//是第一次安装
                   if(isFirst){
                       startGuideActivity();
                   }else{
                       //2.第二次打开APP时，检测偏好设置是否是否存在“某个值”,存在,直接跳转到MainActivity
                       startMainActivity();
                   }


                   break;
           }
       }
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将PreferencesUtil初始化
         preferencesUtil=PreferencesUtil.getInstance();//单利模式创建对象,内存中只有一份
        //初始化一次
        preferencesUtil.init(this);
        //创建子线程加载数据
        new Thread(){
            public void run() {
                try {

                    initCityList();//耗时操作加载数据
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    private void initCityList() throws IOException {//在子线程加载城市数据,
        List<City> cities=new ArrayList<>();
        String pathFile = createDB();//创建数据库文件
        //alt+shift+m 快速生成方法
        SQLiteDatabase db=openOrCreateDatabase(pathFile,MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("SELECT * FROM city", null);

        while(cursor.moveToNext()){
            String strcity=cursor.getString(cursor.getColumnIndex("city"));
            String province = cursor.getString(cursor.getColumnIndex("province"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            City city=new City(province,strcity,number,null,null,null);
            cities.add(city);
        }
        //数据加载完毕,将数据封装到Application中,销毁SplashActivity
        MyApplication application= (MyApplication) getApplication();
        application.setCities(cities);

        handler.sendEmptyMessage(MESSAGE_LOAD_CITY_COMPLETE);
    }

    /**
     *
     * @return 文件路径
     * @throws IOException
     */
    private String createDB() throws IOException {
        //1.确定city.db文件在手机sd卡中的位置 /data/data/cn/tedu/weather/citydatabases/city.db
        String pathFolder="/data"+ Environment.getDataDirectory().getAbsolutePath()+ File.separator+getPackageName()+File.separator+"citydatabases";
        String pathFile=pathFolder+File.separator+"city.db";

        File fileFloder=new File(pathFolder);//文件夹陆军
        File filePath=new File(pathFile);//文件路径
        //2.如果文件夹不存在或者等于null,创建文件夹
        if(!fileFloder.exists()||fileFloder==null){
            fileFloder.mkdirs();//创建文件夹
        }
        //3.如果文件不存在或者等于null或者文件长度为0,创建文件
        if(!filePath.exists()||filePath==null||filePath.length()<=0){
            filePath.createNewFile();//创建新文件

            InputStream is = getAssets().open("city.db");//将assets中的city.db文件读取到内存中
            FileOutputStream fos=new FileOutputStream(filePath);//文件输出流,指定文件的路径

            int len=0;//每次读取的字节数
            byte []buffer=new byte[1024];//缓冲区 1024字节
            while((len=is.read(buffer))!=-1){//每次读取1024个字节,如果读取到-1表示读取完毕
                fos.write(buffer,0,len);//buffer数据 0从buffer数组的下标0开始写出len个
                fos.flush();//刷出
            }
            fos.close();
            is.close();
        }
        return pathFile;
    }

    private void startGuideActivity() {
        startActivity(new Intent(SplachActivity.this,GuideActivity.class));
        finish();
    }
    private void startMainActivity() {
        startActivity(new Intent(SplachActivity.this,MainActivity.class));
        finish();
    }
}

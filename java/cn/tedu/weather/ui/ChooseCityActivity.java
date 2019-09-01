package cn.tedu.weather.ui;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.tedu.weather.R;
import cn.tedu.weather.application.MyApplication;
import cn.tedu.weather.entity.City;

public class ChooseCityActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lvProvince;
    ListView lvCities;
    List<City>cities=new ArrayList<>();//所有城市数据
    Set<String> setProvinces=new HashSet<>();//所有省份的Set集合   Set集合:无序,并且不重复
    List<String> provinces=new ArrayList<>();//所有省份
    List<City>usingCities=new ArrayList<>();//要显示的城市
    private ArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        lvProvince=findViewById(R.id.lv_province);
        lvCities=findViewById(R.id.lv_city);
        loadCities();//加载城市数据
        loadProvinces();//加载省份数据

        lvProvince.setOnItemClickListener(this);
        lvCities.setOnItemClickListener(this);
    }

    private void loadProvinces() {
        for(City c:cities){ //遍历所有城市数据
            if(setProvinces.add(c.getProvince())){//Set集合不重复,如果添加成功返回值为true,否则为false
                provinces.add(c.getProvince());//不重复的城市放到provinces集合中
            }
//            if(!provinces.contains(c.getProvince())){//使用集合的contains方法判断该数据是否在集合,效率低，不推荐使用
//                provinces.add(c.getProvince());
//            }
        }

        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.item_list_city_province,provinces);
        lvProvince.setAdapter(adapter);

    }

    private void loadCities() {
        MyApplication app = (MyApplication) getApplication();//获取Application
        cities= app.getCities();
/**
 *      Context :this
 *      int     :R.layout.item_list_city_province 模板 ListView 每一项的样子
 *      List<T> :cities 要显示的数据
 */

    }
    //

    /**
     * 点击ListView列表项执行该方法
     * @param parent   点击哪一个ListView导致该方法执行
     * @param view
     * @param position 列表项的下标
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.lv_province:
                String province = provinces.get(position);//拿到点击的城市
//                Toast.makeText(this,province,Toast.LENGTH_SHORT).show();
                //如何实现联动效果
                //清空usingCities
                usingCities.clear();//清空集合
                for(City c:cities){//遍历所有城市
                    if(c.getProvince().equals(province)){//如果城市对应的省份和点击的省份一样
                        usingCities.add(c);
                    }
                }
                if(cityAdapter==null){
                    cityAdapter=new ArrayAdapter(this,R.layout.item_list_city_province,usingCities);
                    lvCities.setAdapter(cityAdapter);
                }else{
                    cityAdapter.notifyDataSetChanged();//通知数据发生了改变
                }

                break;
            case R.id.lv_city:
                //获取城市信息,返回到MainActivity
               String  number= usingCities.get(position).getNumber();//获取点击的城市是编码
                Intent data=new Intent();
                data.putExtra("number",number);
                setResult(RESULT_OK,data);
                finish();
                break;
        }
    }
}

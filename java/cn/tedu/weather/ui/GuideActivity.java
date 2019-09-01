package cn.tedu.weather.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.tedu.weather.R;
import cn.tedu.weather.util.PreferencesUtil;

/**
 * 导航页面,应用程序第一次安装时加载
 */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    ViewPager vpContainer;
    PagerAdapter adapter=new InnerAdapter();
    List<View>views=new ArrayList<>();
    RadioGroup rgTotalSpot;
    Button btStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //在GuideActivity将“某个值”存入偏好设置中,
        PreferencesUtil preferencesUtil=PreferencesUtil.getInstance();
        preferencesUtil.setFirst(false);

        //在onCreate()方法中调用父类的findViewById方法只能找到当前布局下对应的控件
        vpContainer=findViewById(R.id.vp_container);
        rgTotalSpot=findViewById(R.id.rg_total_spot);


        //需要将布局文件加载成View对象 getLayoutInflater():可以将布局文件加载成View
        View page01=getLayoutInflater().inflate(R.layout.page_01,null);
        View page02=getLayoutInflater().inflate(R.layout.page_02,null);
        View page03=getLayoutInflater().inflate(R.layout.page_03,null);
        View page04=getLayoutInflater().inflate(R.layout.page_04,null);

        //因为btStart按钮在page04页面中,可以使用view.findViewById方法找到对应的控件
       btStart =page04.findViewById(R.id.bt_guide_start);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
            }
        });
        views.add(page01);
        views.add(page02);
        views.add(page03);
        views.add(page04);

        vpContainer.setAdapter(adapter);

        //为vpContainer添加滑动监听器
        vpContainer.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
        //当页面被选中时执行
    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                rgTotalSpot.check(R.id.rb_spot_1);
                break;
            case 1:
                rgTotalSpot.check(R.id.rb_spot_2);
                break;
            case 2:
                rgTotalSpot.check(R.id.rb_spot_3);
                break;
            case 3:
                rgTotalSpot.check(R.id.rb_spot_4);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class InnerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return views.size();
        }

        @Override  //初始化页面
        public Object instantiateItem(ViewGroup container, int position) {
            //获取第position个Item(View对象)
                View view=views.get(position);
            //将第position个Item(View对象)添加到container容器中
            container.addView(view);
            return view;
        }

        @Override//页面销毁
        public void destroyItem(ViewGroup container, int position, Object object) {
                  //获取第position个Item(View对象)
                View view=views.get(position);
                //从容器container中移除第position个Item
                container.removeView(view);

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}

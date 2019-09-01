package cn.tedu.weather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 80 on 2019/7/2.
 * Http请求的工具类
 */

public class HttpUtil {
    /**
     *
     * @param path 请求路径
     * @return 服务端返回的输入流
     * @throws IOException 网络异常
     */
    public static InputStream getInputStream(String path) throws IOException {
        //        1.创建URL对象
        URL url=new URL(path);//要访问的数据接口
//        2.通过URL对象获取HttpURLConnection
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//        3.设置请求方式(get  post)
        conn.setRequestMethod("GET");
//        4.发生请求,使用流获取服务器响应的数据
        InputStream is= conn.getInputStream();//字节流
        return is;
    }

    /**
     * 将服务端的输入流转换成字符串
     * @param is
     * @return
     * @throws IOException
     */
    public static String isToString(InputStream is) throws IOException {
        InputStreamReader isr= new InputStreamReader(is);//把字节流转换成字符流
        BufferedReader br=new BufferedReader(isr);//缓冲字节输入流,可以一次读取一行文字
        String line=null;
        StringBuffer sb=new StringBuffer();//可变字符串
//        5.读取流中的数据
        while((line=br.readLine())!=null){//读取的不是空行
            sb.append(line);
        }
        //当循环结束,数据读取完毕
        String data=sb.toString();
        return data;
    }
}

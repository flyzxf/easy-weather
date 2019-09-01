package cn.tedu.weather.entity;

import java.io.Serializable;

/**
 * Created by 80 on 2019/6/26.
 */

public class UpdateWeather implements Serializable {
    String updateTime;//08:45
    String humidity;
    String wendu;

    public UpdateWeather() {
    }

    public UpdateWeather(String updateTime, String humidity, String wendu) {
        this.updateTime = updateTime;
        this.humidity = humidity;
        this.wendu = wendu;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    @Override
    public String toString() {
        return "UpdateWeather{" +
                "updateTime='" + updateTime + '\'' +
                ", humidity='" + humidity + '\'' +
                ", wendu='" + wendu + '\'' +
                '}';
    }
}

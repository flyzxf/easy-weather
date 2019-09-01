package cn.tedu.weather.entity;

import java.io.Serializable;

/**
 * Created by 80 on 2019/6/26.
 */

public class WeatherToday implements Serializable {
    private String high;
    private String low;
    private String week;
    private String type;
    private String fx;
    private int weatherTodayResId;

    public WeatherToday() {
    }

    public WeatherToday(String high, String low, String week, String type, String fx, int weatherTodayResId) {
        this.high = high;
        this.low = low;
        this.week = week;
        this.type = type;
        this.fx = fx;
        this.weatherTodayResId = weatherTodayResId;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public int getWeatherTodayResId() {
        return weatherTodayResId;
    }

    public void setWeatherTodayResId(int weatherTodayResId) {
        this.weatherTodayResId = weatherTodayResId;
    }

    @Override
    public String toString() {
        return "WeatherToday{" +
                "high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", week='" + week + '\'' +
                ", type='" + type + '\'' +
                ", fx='" + fx + '\'' +
                ", weatherTodayResId=" + weatherTodayResId +
                '}';
    }
}

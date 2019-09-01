package cn.tedu.weather.entity;

import java.io.Serializable;

/**
 * Created by 80 on 2019/6/26.
 */

public class Result implements Serializable{
    private CityInfo CityInfo;
    private  Quality quality;
    private UpdateWeather updateWeather;
    private WeatherToday weatherToday;

    public Result() {
    }

    public Result(cn.tedu.weather.entity.CityInfo cityInfo, Quality quality, UpdateWeather updateWeather, WeatherToday weatherToday) {
        CityInfo = cityInfo;
        this.quality = quality;
        this.updateWeather = updateWeather;
        this.weatherToday = weatherToday;
    }

    public cn.tedu.weather.entity.CityInfo getCityInfo() {
        return CityInfo;
    }

    public void setCityInfo(cn.tedu.weather.entity.CityInfo cityInfo) {
        CityInfo = cityInfo;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public UpdateWeather getUpdateWeather() {
        return updateWeather;
    }

    public void setUpdateWeather(UpdateWeather updateWeather) {
        this.updateWeather = updateWeather;
    }

    public WeatherToday getWeatherToday() {
        return weatherToday;
    }

    public void setWeatherToday(WeatherToday weatherToday) {
        this.weatherToday = weatherToday;
    }

    @Override
    public String toString() {
        return "Result{" +
                "CityInfo=" + CityInfo +
                ", quality=" + quality +
                ", updateWeather=" + updateWeather +
                ", weatherToday=" + weatherToday +
                '}';
    }
}

package cn.tedu.weather.entity;

import java.io.Serializable;

/**
 * Created by 80 on 2019/6/26.
 */

public class CityInfo implements Serializable {
    private String city;
    private Integer cityId;

    public CityInfo() {
    }

    public CityInfo(String city, Integer cityId) {
        this.city = city;
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "city='" + city + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}

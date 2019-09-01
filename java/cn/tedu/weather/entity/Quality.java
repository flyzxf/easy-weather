package cn.tedu.weather.entity;

import java.io.Serializable;

/**
 * Created by 80 on 2019/6/26.
 */

public class Quality implements Serializable {
    private Double pm25;
    private String quality;
    private int qualityRes;

    public Quality() {
    }
    public Quality(Double pm25, String quality, int qualityRes) {

        this.pm25 = pm25;
        this.quality = quality;
        this.qualityRes = qualityRes;
    }
    public Double getPm25() {
        return pm25;
    }

    public void setPm25(Double pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getQualityRes() {
        return qualityRes;
    }

    public void setQualityRes(int qualityRes) {
        this.qualityRes = qualityRes;
    }


}

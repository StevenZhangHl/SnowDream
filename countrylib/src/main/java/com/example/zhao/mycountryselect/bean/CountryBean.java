package com.example.zhao.mycountryselect.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;

/**
 * 国家地区Bean
 */
public class CountryBean extends BaseIndexPinyinBean implements Serializable{

    private String city;//国家名字
    private String zone;//国家区号(发短信用)

    public CountryBean() {
    }

    public CountryBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public CountryBean setCity(String city) {
        this.city = city;
        return this;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String getTarget() {
        return city;
    }
}

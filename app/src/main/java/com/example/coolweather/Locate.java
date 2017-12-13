package com.example.coolweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by xchen on 16/12/23.
 */

public class Locate extends Activity{
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private String mLocCityCode;
    private String weatherId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        initLocation();
        mLocationClient.start();

        final Intent intent= new Intent(this,WeatherActivity.class);
        intent.putExtra("weather_id",weatherId);
    }
    void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }
    public void requestCityId(final String cityId){
        String cityUrl = "http://guolin.tech/api/search?city="+"cityId";
        HttpUtil.sendOkHttpRequest(cityUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
            }
        });
    }
}
class MyLocationListener implements BDLocationListener{
    String cityName;
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        cityName = bdLocation.getCity();

    }
}

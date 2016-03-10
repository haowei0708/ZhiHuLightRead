package com.example.kevin.zhihulightread.protocol;

import com.example.kevin.zhihulightread.base.BaseProtocol;
import com.example.kevin.zhihulightread.model.StartImageBean;
import com.google.gson.Gson;

/**
 * 作者：Created by Kevin on 2016/3/2.
 * 邮箱：haowei0708@163.com
 * 描述：
 */
public class SplashProtocol extends BaseProtocol<StartImageBean> {
    public SplashProtocol(String url) {
        super(url);
    }

    @Override
    public StartImageBean parseData(String jsonString) {
        Gson gson = new Gson();
        StartImageBean startImageBean = gson.fromJson(jsonString, StartImageBean.class);

        mDatas = startImageBean;

        return startImageBean;
    }
}

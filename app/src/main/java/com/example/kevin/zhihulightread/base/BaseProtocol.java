package com.example.kevin.zhihulightread.base;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 作者：Created by Kevin on 2016/3/1.
 * 邮箱：haowei0708@163.com
 * 描述：
 */
public abstract class BaseProtocol<T> {

    private String url;
    public T mDatas;

    public BaseProtocol(String url) {
        this.url = url;
    }

    /**
     * 有了url加载网络数据以及加载本地数据
     */
    public void loadData() {
//        String localData = getDataFromLocal();
//        if (localData != null){
//            return parseData(localData);
//        } else {
//            String dataFromServer = getDataFromServer();
//            return parseData(dataFromServer);
//        }

        getDataFromServer();
    }

    /**
     * 从本地获取数据
     *
     * @return
     */
    private String getDataFromLocal() {
        return null;
    }

    /**
     * 从网络获取数据
     *
     * @return
     */
    private void getDataFromServer() {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;

                parseData(jsonString);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("error:" + e);
            }
        });

    }

    /**
     * 解析数据
     *
     * @param jsonString json字符串
     */
    public abstract T parseData(String jsonString);

}

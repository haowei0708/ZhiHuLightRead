package com.example.kevin.zhihulightread.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.activity.WebNewsActivity;
import com.example.kevin.zhihulightread.bean.LatestNewsBean;
import com.example.kevin.zhihulightread.global.Constants;
import com.example.kevin.zhihulightread.utils.LogUtils;
import com.example.kevin.zhihulightread.utils.UIUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 作者：Created by Kevin on 2016/2/19.
 * 邮箱：haowei0708@163.com
 * 描述：主页面内容的Fragment
 */
public class ContentFragment extends Fragment implements AdapterView.OnItemClickListener {
    public Activity mActivity;
    private ListView lvContent;
    private LatestNewsBean newsBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    //处理fragment的布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }



    //依附的Activity被创建完成
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    /**
     * 初始化布局
     * @return
     */
    private View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content,null);
        lvContent = (ListView) view.findViewById(R.id.lv_content);
        return view;
    }


    /**
     * 初始化数据
     */
    private void initData() {
        getDataFromServer();
    }

    /**
     * 从网络请求数据
     */
    private void getDataFromServer() {
        String url = "http://news-at.zhihu.com/api/4/news/latest";
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                LogUtils.s(result);

                parseData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("error:" + e);
            }
        });
    }

    /**
     * 解析json数据
     * @param result
     */
    private void parseData(String result) {
        Gson gson = new Gson();
        newsBean = gson.fromJson(result, LatestNewsBean.class);

        //设置listView
        ContentAdapter adapter = new ContentAdapter();
        lvContent.setAdapter(adapter);


        lvContent.setOnItemClickListener(this);
    }

    /**
     * listView的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //获取到点击的item的id
        String newsID = newsBean.stories.get(position).id;
        String url = Constants.URLS.BASEURL + newsID;
        LogUtils.s(url);

        Intent intent = new Intent(mActivity, WebNewsActivity.class);
        intent.putExtra("webUrl",url);//该url获取的数据是一个bean，也需要解析
        startActivity(intent);
    }


    private class ContentAdapter extends BaseAdapter{

        private final BitmapUtils bitmapUtils;

        public ContentAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
        }

        @Override
        public int getCount() {
            return newsBean.stories.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;


            if (convertView == null){
                convertView = View.inflate(mActivity,R.layout.item_listview_content,null);
                holder = new ViewHolder();

                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.icon = (ImageView) convertView.findViewById(R.id.iv_content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvTitle.setText(newsBean.stories.get(position).title);

            bitmapUtils.display(holder.icon,newsBean.stories.get(position).images.get(0));

            return convertView;
        }
    }

    class ViewHolder {
        ImageView icon;
        TextView tvTitle;
    }
}

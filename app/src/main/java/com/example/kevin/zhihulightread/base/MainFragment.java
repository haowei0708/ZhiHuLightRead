package com.example.kevin.zhihulightread.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.activity.MainActivity;
import com.example.kevin.zhihulightread.activity.WebNewsActivity;
import com.example.kevin.zhihulightread.base.BaseFragment;
import com.example.kevin.zhihulightread.bean.ThemeBean;
import com.example.kevin.zhihulightread.global.Constants;
import com.example.kevin.zhihulightread.utils.BitmapHelper;
import com.example.kevin.zhihulightread.utils.LogUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 作者：Created by Kevin on 2016/3/3.
 * 邮箱：haowei0708@163.com
 * 描述：
 */
public abstract class MainFragment extends BaseFragment {

    private ViewPager mViewPager;
    private ListView mListView;
    private ImageView mIvBackground; //背景图片
    private ThemeBean themeBean;
    private SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = View.inflate(mActivity, R.layout.fragment_main, null);
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        View headerView = View.inflate(mActivity, R.layout.header_main_fragment, null);
        mIvBackground = (ImageView) headerView.findViewById(R.id.iv_background);
        mListView.addHeaderView(headerView);



        return rootView;
    }

    @Override
    protected void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        String url = "http://news-at.zhihu.com/api/4/theme/" + getThemeID();
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;
                parseData(jsonString);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("error" + error);
            }
        });
    }

    public abstract String getThemeID();

    private void parseData(String jsonString) {
        Gson gson = new Gson();
        themeBean = gson.fromJson(jsonString, ThemeBean.class);

        initView();
    }

    private void initView() {
        BitmapHelper.display(mIvBackground, themeBean.getImage());

        //      mListView.addHeaderView(mIvBackground);

        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);

        initEvent();

    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {//由于有headView
                    //获取到点击的item的id
                    int newsID = themeBean.getStories().get(position - 1).getId();
                    String url = Constants.URLS.NEWSURL + newsID;

                    Intent intent = new Intent(mActivity, WebNewsActivity.class);
                    intent.putExtra("webUrl", url);//该url获取的数据是一个bean，也需要解析
                    startActivity(intent);
                }
            }
        });
        //下拉刷新按钮的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark);
        //下拉刷新的监听事件
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 2016/3/2 主线程阻塞，待解决！
//                getDataFromServer();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //所以这里模拟一个下拉刷新
                        SystemClock.sleep(2000);
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });

                    }
                }).start();

            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return themeBean.getStories().size();
        }

        @Override
        public Object getItem(int position) {
            return themeBean.getStories().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;


            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_listview_content, null);
                holder = new ViewHolder();

                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.icon = (ImageView) convertView.findViewById(R.id.iv_content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvTitle.setText(themeBean.getStories().get(position).getTitle());

            if (themeBean.getStories().get(position).getImages() != null){
                holder.icon.setVisibility(View.VISIBLE);
                BitmapHelper.display(holder.icon, themeBean.getStories().get(position).getImages().get(0));
            } else {
                holder.icon.setVisibility(View.GONE);
            }


            return convertView;
        }
    }

    class ViewHolder {
        ImageView icon;
        TextView tvTitle;
    }
}

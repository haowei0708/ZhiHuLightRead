package com.example.kevin.zhihulightread.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.activity.WebNewsActivity;
import com.example.kevin.zhihulightread.base.BaseFragment;
import com.example.kevin.zhihulightread.model.LatestNewsBean;
import com.example.kevin.zhihulightread.global.Constants;
import com.example.kevin.zhihulightread.utils.ACache;
import com.example.kevin.zhihulightread.utils.DensityUtil;
import com.example.kevin.zhihulightread.utils.LogUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 作者：Created by Kevin on 2016/2/19.
 * 邮箱：haowei0708@163.com
 * 描页面内容的Fragment
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView lvContent;
    private LatestNewsBean newsBean;
    private ViewPager mViewPager;
    private TextView mTvTitle;
    private LinearLayout llContainer; //点的容器
    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新
    private ListViewContentAdapter mAdapter;
    private boolean isNoDot = true;
    private int refreshTimes = 0;//加载更多次数，用于获取过往的日期
    private boolean isLoadMore;//是否正在加载更多
    private List<LatestNewsBean.StoriesEntity> storiesEntities = new ArrayList<LatestNewsBean.StoriesEntity>();
    private ACache mACache;
    String url = "http://news-at.zhihu.com/api/4/news/latest";

    private String date;
    private boolean isLoading = false;//判断是否正在加载更多数据


    /**
     * 初始化数据
     */
    public void initData() {

        mACache = ACache.get(mActivity);
        //如果没有网络
        String jsonString = mACache.getAsString(url);
        if (jsonString != null){
            parseData(jsonString);
        }

        getDataFromServer();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        lvContent = (ListView) view.findViewById(R.id.lv_content);
        View headerView = View.inflate(mActivity, R.layout.header_view_pager, null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.view_pager);
        llContainer = (LinearLayout) headerView.findViewById(R.id.ll_container);//小点的容器

        mTvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        //添加头布局
        lvContent.addHeaderView(headerView);


        return view;
    }

    /**
     * 从网络请求数据
     */
    private void getDataFromServer() {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;

                //缓存文件
                mACache.put(url,jsonString,ACache.TIME_DAY);

                parseData(jsonString);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("error:" + e);
            }
        });
    }

    /**
     * 解析json数据
     *
     * @param result
     */
    private void parseData(String result) {
        Gson gson = new Gson();
        newsBean = gson.fromJson(result, LatestNewsBean.class);
        //设置viewpager
        ViewPagerContentAdapter viewPageradapter = new ViewPagerContentAdapter();
        mViewPager.setAdapter(viewPageradapter);

        if (isNoDot) {
            //动态添加点
            addPoints(newsBean.getTop_stories().size());

            isNoDot = false;
        }


        //实现无限轮播的左边
        int extra = Integer.MAX_VALUE / 2 % newsBean.getTop_stories().size();
        int index = Integer.MAX_VALUE / 2 - extra;
        mViewPager.setCurrentItem(index);


        //设置listView
        mAdapter = new ListViewContentAdapter();
        storiesEntities = newsBean.getStories();
        lvContent.setAdapter(mAdapter);

        lvContent.setOnItemClickListener(this);


        //初始化第一个图像的标题
        mTvTitle.setText(newsBean.getTop_stories().get(0).getTitle());

        initEvent();
    }


    /**
     * 初始化事件
     */
    private void initEvent() {


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % newsBean.getTop_stories().size();
                mTvTitle.setText(newsBean.getTop_stories().get(position).getTitle());

                for (int i = 0; i < newsBean.getTop_stories().size(); i++) {
                    position = position % newsBean.getTop_stories().size();
                    //还原背景
                    View indicatorView = llContainer.getChildAt(i);
                    indicatorView.setBackgroundResource(R.drawable.dot_normal);

                    if (i == position) {
                        indicatorView.setBackgroundResource(R.drawable.dot_focus);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //下拉刷新按钮的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark);
        //下拉刷新的监听事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });

                    }
                }).start();

            }
        });

        lvContent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {

                    if (lvContent.getLastVisiblePosition() == lvContent.getCount() - 1) {//当划到最后一个的时候
                        //加载更多数据

                        if (!isLoading){
                            //得到before的日期：知乎birthday 2013年5月19 之前没内容，但是我不相信有人能滑到这个时间。、所以不处理
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.DAY_OF_MONTH, -refreshTimes);
                            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
                            date = dateFormat.format(calendar.getTime());
                            String dateUrl = Constants.URLS.BEFORENEWSURL + date;
                            LogUtils.sf(dateUrl);

                            isLoading = true;//表示正在加载
                            loadMoreData(dateUrl);
                        }


                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 加载更多数据
     *
     * @param dateUrl
     */
    private void loadMoreData(String dateUrl) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, dateUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;

                parseBeforeData(jsonString);


            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("error:" + error);
            }
        });
    }

    /**
     * 解析过往消息
     *
     * @param jsonString
     */
    private void parseBeforeData(String jsonString) {
        Gson gson = new Gson();
        LatestNewsBean beforeNewsBean = gson.fromJson(jsonString, LatestNewsBean.class);
        List<LatestNewsBean.StoriesEntity> stories = beforeNewsBean.getStories();

        storiesEntities.addAll(stories);

        mAdapter.notifyDataSetChanged();

        isLoading = false;//表示加载结束

        refreshTimes++;//刷新次数增加一，改变时间
    }

    /**
     * 动态添加indicator的小点
     *
     * @param size
     */
    private void addPoints(int size) {
        for (int i = 0; i < size; i++) {
            View indicatorView = new View(mActivity);
            indicatorView.setBackgroundResource(R.drawable.dot_normal);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mActivity, 8), DensityUtil.dip2px(mActivity, 8));
            params.leftMargin = DensityUtil.dip2px(mActivity, 8);
            params.bottomMargin = DensityUtil.dip2px(mActivity, 8);
            indicatorView.setLayoutParams(params);
            //默认选中第一个
            if (i == 0) {
                indicatorView.setBackgroundResource(R.drawable.dot_focus);
            }

            llContainer.addView(indicatorView);
        }
    }

    /**
     * listView的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position > 0) {//由于有headView
            //获取到点击的item的id
            int newsID = newsBean.getStories().get(position - 1).getId();
            String url = Constants.URLS.NEWSURL + newsID;

            Intent intent = new Intent(mActivity, WebNewsActivity.class);
            intent.putExtra("webUrl", url);//该url获取的数据是一个bean，也需要解析
            startActivity(intent);
        }

    }


    /**
     * ListView的adapter
     */
    private class ListViewContentAdapter extends BaseAdapter {

        private final BitmapUtils bitmapUtils;

        public ListViewContentAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
        }

        @Override
        public int getCount() {
            return storiesEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return storiesEntities.get(position);
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

            holder.tvTitle.setText(storiesEntities.get(position).getTitle());

            bitmapUtils.display(holder.icon, storiesEntities.get(position).getImages().get(0));

            return convertView;
        }
    }

    class ViewHolder {
        ImageView icon;
        TextView tvTitle;
    }

    /**
     * ViewPager的adapter
     */
    private class ViewPagerContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            //实现无限轮播
            position = position % newsBean.getTop_stories().size();

            final LatestNewsBean.TopStoriesEntity topStoriesEntity = newsBean.getTop_stories().get(position);

            ImageView iv = new ImageView(mActivity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

//            TextView tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
            BitmapUtils bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.display(iv, topStoriesEntity.getImage());

//            TextView tv = new TextView(mActivity);
//            tv.setText(top_stories.title);
//            tvTitle.setText(top_stories.title);
            container.addView(iv);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newsID = topStoriesEntity.getId();
                    String url = Constants.URLS.NEWSURL + newsID;

                    Intent intent = new Intent(mActivity, WebNewsActivity.class);
                    intent.putExtra("webUrl", url);//该url获取的数据是一个bean，也需要解析
                    startActivity(intent);
                }
            });
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }


}

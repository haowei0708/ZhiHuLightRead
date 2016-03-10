package com.example.kevin.zhihulightread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.factory.FragmentFactory;
import com.example.kevin.zhihulightread.utils.LogUtils;
import com.example.kevin.zhihulightread.view.NoScrollViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_CONTENT = "fragment_content";
    private long exitTime = 0;
    private NoScrollViewPager mViewPager;
    private Toolbar toolbar;
    //    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
//        initFragment();

        initEvent();

    }

    /**
     * 初始化view
     */
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("今日热闻");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);


//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
    }

    private void initEvent() {
//                //下拉刷新按钮的颜色
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark);
//        //下拉刷新的监听事件
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshFragment();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 填充菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, AboutMeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //先得到contentFragment，再用ViewPager替换页面

        // 处理navigation view条目
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            mViewPager.setCurrentItem(0, false);
            toolbar.setTitle("今日热闻");
        } else if (id == R.id.nav_psychology) {
            mViewPager.setCurrentItem(1, false);
            toolbar.setTitle("日常心理学");
        } else if (id == R.id.nav_recommend) {
            mViewPager.setCurrentItem(2, false);
            toolbar.setTitle("用户推荐日报");
        } else if (id == R.id.nav_movie) {
            mViewPager.setCurrentItem(3, false);
            toolbar.setTitle("电影日报");
        } else if (id == R.id.nav_no_bored) {
            mViewPager.setCurrentItem(4, false);
            toolbar.setTitle("不许无聊");
        } else if (id == R.id.nav_design) {
            mViewPager.setCurrentItem(5, false);
            toolbar.setTitle("设计日报");
        } else if (id == R.id.nav_company) {
            mViewPager.setCurrentItem(6, false);
            toolbar.setTitle("大公司日报");
        } else if (id == R.id.nav_business) {
            mViewPager.setCurrentItem(7, false);
            toolbar.setTitle("财经日报");
        } else if (id == R.id.nav_net_safe) {
            mViewPager.setCurrentItem(8, false);
            toolbar.setTitle("互联网安全");
        } else if (id == R.id.nav_start_game) {
            mViewPager.setCurrentItem(9, false);
            toolbar.setTitle("开始游戏");
        } else if (id == R.id.nav_music) {
            mViewPager.setCurrentItem(10, false);
            toolbar.setTitle("音乐日报");
        } else if (id == R.id.nav_cartoon) {
            mViewPager.setCurrentItem(11, false);
            toolbar.setTitle("动漫日报");
        } else if (id == R.id.nav_sports) {
            mViewPager.setCurrentItem(12, false);
            toolbar.setTitle("体育日报");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;//复写返回键的单击事件
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 按两次返回键退出
     */
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    private class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LogUtils.sf("正在加载第" + position + "个页面");
            return FragmentFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            return 13;
        }
    }
}

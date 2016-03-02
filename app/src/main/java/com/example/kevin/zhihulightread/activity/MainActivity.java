package com.example.kevin.zhihulightread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.fragment.ContentFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_CONTENT = "fragment_content";
    private long exitTime = 0;
//    private SwipeRefreshLayout swipeRefreshLayout;//下拉刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("今日热闻");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        initFragment();

        initEvent();

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

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启事务
        transaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT_CONTENT);

        transaction.commit();//提交事务
    }

    /**
     * 刷新Fragment带动画
     */
    private void refreshFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启事务
        //切换的动画
        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        transaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT_CONTENT);

        transaction.commit();//提交事务
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
            startActivity(new Intent(this,AboutMeActivity.class));
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

        if (id == R.id.nav_psychology) {

        } else if (id == R.id.nav_recommend) {

        } else if (id == R.id.nav_movie) {

        } else if (id == R.id.nav_no_bored) {

        } else if (id == R.id.nav_design) {

        } else if (id == R.id.nav_company) {

        } else if (id == R.id.nav_business) {

        } else if (id == R.id.nav_net_safe) {

        } else if (id == R.id.nav_start_game) {

        } else if (id == R.id.nav_music) {

        } else if (id == R.id.nav_cartoon) {

        } else if (id == R.id.nav_sports) {

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
}

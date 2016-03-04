package com.example.kevin.zhihulightread.factory;

import android.util.SparseArray;

import com.example.kevin.zhihulightread.base.BaseFragment;
import com.example.kevin.zhihulightread.fragment.BusinessFragment;
import com.example.kevin.zhihulightread.fragment.CartoonFragment;
import com.example.kevin.zhihulightread.fragment.CompanyFragment;
import com.example.kevin.zhihulightread.fragment.DesignFragment;
import com.example.kevin.zhihulightread.fragment.HomeFragment;
import com.example.kevin.zhihulightread.fragment.MovieFragment;
import com.example.kevin.zhihulightread.fragment.MusicFragment;
import com.example.kevin.zhihulightread.fragment.NetSafeFragment;
import com.example.kevin.zhihulightread.fragment.NoBoredFragment;
import com.example.kevin.zhihulightread.fragment.PsychologyFragment;
import com.example.kevin.zhihulightread.fragment.RecommendFragment;
import com.example.kevin.zhihulightread.fragment.SportsFragment;
import com.example.kevin.zhihulightread.fragment.StartGameFragment;

/**
 * 作者：Created by Kevin on 2016/1/26.
 * 邮箱：haowei0708@163.com
 * 描述：Fragment的工厂
 */
public class FragmentFactory {

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_PSYCHOLOGY = 1;
    public static final int FRAGMENT_RECOMMEND = 2;
    public static final int FRAGMENT_MOVIE = 3;
    public static final int FRAGMENT_NO_BORED = 4;
    public static final int FRAGMENT_DESIGN = 5;
    public static final int FRAGMENT_COMPANY = 6;
    public static final int FRAGMENT_BUSINESS = 7;
    public static final int FRAGMENT_NET_SAFE = 8;
    public static final int FRAGMENT_START_GAME = 9;
    public static final int FRAGMENT_MUSIC = 10;
    public static final int FRAGMENT_CARTOON = 11;
    public static final int FRAGMENT_SPORTS = 12;
    //fragment的缓存
    static SparseArray<BaseFragment> caches = new SparseArray<BaseFragment>();

    public static BaseFragment getFragment(int position) {

        BaseFragment fragment = null;
        BaseFragment tmpFragment = caches.get(position);
        if (tmpFragment != null) {
            return tmpFragment;
        }
        switch (position) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_PSYCHOLOGY:
                fragment = new PsychologyFragment();
                break;
            case FRAGMENT_RECOMMEND:
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_MOVIE:
                fragment = new MovieFragment();
                break;
            case FRAGMENT_NO_BORED:
                fragment = new NoBoredFragment();
                break;
            case FRAGMENT_DESIGN:
                fragment = new DesignFragment();
                break;
            case FRAGMENT_COMPANY:
                fragment = new CompanyFragment();
                break;
            case FRAGMENT_BUSINESS:
                fragment = new BusinessFragment();
                break;
            case FRAGMENT_NET_SAFE:
                fragment = new NetSafeFragment();
                break;
            case FRAGMENT_START_GAME:
                fragment = new StartGameFragment();
                break;
            case FRAGMENT_MUSIC:
                fragment = new MusicFragment();
                break;
            case FRAGMENT_CARTOON:
                fragment = new CartoonFragment();
                break;
            case FRAGMENT_SPORTS:
                fragment = new SportsFragment();
                break;

        }

        caches.put(position,fragment);

        return fragment;
    }
}

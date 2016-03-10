package com.example.kevin.zhihulightread.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.model.NewsDetailBean;
import com.example.kevin.zhihulightread.utils.ACache;
import com.example.kevin.zhihulightread.utils.LogUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;

/**
 * 内容的HTML网页数据
 */
public class WebNewsActivity extends AppCompatActivity {

    private WebView mWebView;
    private ImageView imageView;//文章的标题图像
    private TextView tvTitle;//文章的标题
    private TextView tvDesc; //图片的版权信息
    private NewsDetailBean detailBean;
    private ProgressBar progressBar;//进度条
    private ACache mACache;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_news);
        mWebView = (WebView) findViewById(R.id.wv_web);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mWebView.setVerticalScrollBarEnabled(false);
        imageView = (ImageView) findViewById(R.id.iv_detail);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);


        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 开启database storage API功能
        settings.setDatabaseEnabled(true);
        // 开启Application Cache功能
        settings.setAppCacheEnabled(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        mWebView.setWebViewClient(new WebViewClient() {
            //开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressBar.setVisibility(View.VISIBLE);

                //ystem.out.println("网页加载开始...");
            }

            //网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                System.out.println("网页加载结束...");

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); //表示不跳转 在WebView中加载
                return super.shouldOverrideUrlLoading(view, url);
            }

        });


        mUrl = getIntent().getStringExtra("webUrl");

        //读取缓存
        mACache = ACache.get(this);
        //如果没有网络
        String jsonString = mACache.getAsString(mUrl);
        if (jsonString != null){
            parseData(jsonString);
        }
        getDataFromServer();


    }

    /**
     * 从网络获取数据
     *
     */
    private void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;

                //缓存文件
                mACache.put(mUrl,jsonString,ACache.TIME_DAY);

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
     * @param jsonString
     */
    private void parseData(String jsonString) {
        Gson gson = new Gson();
        detailBean = gson.fromJson(jsonString, NewsDetailBean.class);

        //标题图片加载
        Picasso.with(this).load(detailBean.getImage()).into(imageView);

        //webView加载内容
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + detailBean.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);

        tvTitle.setText(detailBean.getTitle());
        tvDesc.setText(detailBean.getImage_source());

    }
}

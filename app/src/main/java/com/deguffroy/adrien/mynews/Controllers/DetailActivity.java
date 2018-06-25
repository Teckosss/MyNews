package com.deguffroy.adrien.mynews.Controllers;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;
import com.deguffroy.adrien.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.simple_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_detail_webview)
    WebView mWebView;
    @BindView(R.id.activity_detail_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureSwipeRefreshLayout();

        this.displayWebview();
    }

    // -------------
    // CONFIGURATION
    // -------------

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureSwipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayWebview();
            }
        });
    }

    // -------------
    // ACTION
    // -------------

    private void displayWebview(){
        String url = getIntent().getStringExtra(MainFragment.URL);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient());
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

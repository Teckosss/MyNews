package com.deguffroy.adrien.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.deguffroy.adrien.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.fragment_detail_webview) WebView mWebView;

    private static final String URL = "URL";

    private String mUrl;

    public static DetailFragment newInstance(String url) {
        DetailFragment detailFragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putString(URL,url);
        detailFragment.setArguments(args);

        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        mUrl = getArguments().getString(URL);
        if ( mUrl != null){
            Log.e("TAG", "onCreateView: URL : " + mUrl );
            //mWebView.getSettings().setJavaScriptEnabled(true);
            //mWebView.getSettings().setDomStorageEnabled(true);
            //mWebView.getSettings().setLoadsImagesAutomatically(true);
            //mWebView.setWebViewClient(new TestWebViewClient());
            mWebView.loadUrl(mUrl);
        }


        return view;
    }

    private void displayWebPageFromUrl(String url){
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return false;
            }
        });

    }

    public class TestWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("TAG", "shouldOverrideUrlLoading: URL " + url );
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e("WEB_VIEW_TEST", "error code:" + errorCode);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}

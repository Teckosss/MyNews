package com.deguffroy.adrien.mynews.Controllers;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;
import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Models.Search.Doc;
import com.deguffroy.adrien.mynews.R;
import com.deguffroy.adrien.mynews.Utils.DividerItemDecoration;
import com.deguffroy.adrien.mynews.Utils.ItemClickSupport;
import com.deguffroy.adrien.mynews.Utils.NYTimesStreams;
import com.deguffroy.adrien.mynews.Views.NewsAdapter;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;


public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.activity_search_result_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.simple_toolbar) Toolbar mToolbar;

    private Disposable disposable;
    private List mResults;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        this.mResults = new ArrayList<>();
        this.adapter = new NewsAdapter(this.mResults);
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    // -----------------
    // ACTION
    // -----------------

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Doc url = adapter.getUrlFromSearch(position);
                        openDetailActivity(url.getWebUrl());
                    }
                });
    }

    private void openDetailActivity(String url){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MainFragment.URL,url);
        startActivity(intent);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        String query = getIntent().getStringExtra(SearchArticlesActivity.QUERY);
        String beginDate = getIntent().getStringExtra(SearchArticlesActivity.BEGIN_DATE);
        String endDate = getIntent().getStringExtra(SearchArticlesActivity.END_DATE);
        List<String> filterQuery = getIntent().getStringArrayListExtra(SearchArticlesActivity.FILTER_QUERY);

        this.disposable = NYTimesStreams.streamFetchSearchResultFilterDate(query,filterQuery, beginDate, endDate).subscribeWith(createObserver());
    }

    private <T> DisposableObserver<T> createObserver(){
        return new DisposableObserver<T>() {
            @Override
            public void onNext(T t) {
                if (t instanceof NYTimesResultAPI){
                    updateUI(((NYTimesResultAPI) t));
                }
            }
            @Override
            public void onError(Throwable e) {handleError(e);}
            @Override
            public void onComplete() {}
        };
    }

    private void handleError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int statusCode = httpException.code();
            Log.e("HttpException", "Error code : " + statusCode);
            Toast.makeText(this, "HttpException, Error code : " + statusCode, Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof SocketTimeoutException) {
            Log.e("SocketTimeoutException", "Timeout from retrofit");
            Toast.makeText(this, "Request timeout, please check your internet connection", Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof IOException) {
            Log.e("IOException", "Error");
            Toast.makeText(this, "An error occurred, please check your internet connection", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("Generic handleError", "Error");
            Toast.makeText(this, "An error occurred, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(NYTimesResultAPI result){
        mResults.clear();
        if (result.getResponse().getDocs().isEmpty()){
            Toast.makeText(this, "No result found !", Toast.LENGTH_SHORT).show();
        }else{
            mResults.addAll(result.getResponse().getDocs());
        }
        adapter.notifyDataSetChanged();
    }
}

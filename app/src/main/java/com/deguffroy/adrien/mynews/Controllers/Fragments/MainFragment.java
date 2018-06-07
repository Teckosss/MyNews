package com.deguffroy.adrien.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.deguffroy.adrien.mynews.Controllers.DetailActivity;
import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.Models.Search.Doc;
import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Models.News.Result;
import com.deguffroy.adrien.mynews.R;
import com.deguffroy.adrien.mynews.Utils.DividerItemDecoration;
import com.deguffroy.adrien.mynews.Utils.ItemClickSupport;
import com.deguffroy.adrien.mynews.Utils.NYTimesStreams;
import com.deguffroy.adrien.mynews.Views.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String IDENTIFIER = "Identifier";
    public static final String URL = "URL";

    private Disposable disposable;
    private List mResults;
    private NewsAdapter adapter;

    private int identifierID;

    public static MainFragment newInstance(int Identifier) {
        MainFragment mainFragment = new MainFragment();

        Bundle args = new Bundle();
        args.putInt(IDENTIFIER, Identifier);
        mainFragment.setArguments(args);

        return mainFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.configureSwipeRefreshLayout();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private int getIdentifier(){
        return identifierID = getArguments().getInt(IDENTIFIER, 0);
    }

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        this.mResults = new ArrayList<>();
        this.adapter = new NewsAdapter(this.mResults, Glide.with(this));
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void configureSwipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
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
                        if (getIdentifier() == MainActivity.FRAGMENT_BUSINESS){
                            Doc url = adapter.getUrlFromSearch(position);
                            openDetailActivity(url.getWebUrl());
                        }else{
                            Result url = adapter.getUrl(position);
                            openDetailActivity(url.getUrl());
                        }
                    }
                });
    }

    private void openDetailActivity(String url){
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(URL,url);
        startActivity(intent);
    }


    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        mSwipeRefreshLayout.setRefreshing(true);
        if (getIdentifier() == MainActivity.FRAGMENT_TOP_STORIES) {
            this.disposable = NYTimesStreams.streamFetchTopStoriesNews("home").subscribeWith(createObserver());
        }
        else if (getIdentifier() == MainActivity.FRAGMENT_MOST_POPULAR){
            this.disposable = NYTimesStreams.streamFetchMostPopularNews("all-sections", "7").subscribeWith(createObserver());
        }
        else if (getIdentifier() == MainActivity.FRAGMENT_BUSINESS){
            this.disposable = NYTimesStreams.streamFetchBusinessNews("business").subscribeWith(createObserver());
        }
    }

    private <T> DisposableObserver<T> createObserver(){
        return new DisposableObserver<T>() {
            @Override
            public void onNext(T t) {
                if (t instanceof NYTimesResultAPI){
                    updateUI(((NYTimesResultAPI) t),getIdentifier());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(NYTimesResultAPI result, int identifier){
        mSwipeRefreshLayout.setRefreshing(false);
        switch (identifier){
            case MainActivity.FRAGMENT_TOP_STORIES:
                mResults.clear();
                mResults.addAll(result.getResults());
                break;
            case MainActivity.FRAGMENT_MOST_POPULAR:
                mResults.clear();
                mResults.addAll(result.getResults());
                break;
            case MainActivity.FRAGMENT_BUSINESS:
                mResults.clear();
                mResults.addAll(result.getResponse().getDocs());
                break;
        }
        adapter.notifyDataSetChanged();
    }

}

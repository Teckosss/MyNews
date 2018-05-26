package com.deguffroy.adrien.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.deguffroy.adrien.mynews.Models.TopStories.ResultTopStories;
import com.deguffroy.adrien.mynews.Models.TopStories.TopStoriesNews;
import com.deguffroy.adrien.mynews.R;
import com.deguffroy.adrien.mynews.Utils.DividerItemDecoration;
import com.deguffroy.adrien.mynews.Utils.NYTimesStreams;
import com.deguffroy.adrien.mynews.Views.TopStoriesNewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends Fragment {

    @BindView(R.id.fragment_top_stories_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_top_stories_swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;

    private Disposable disposable;
    private List<ResultTopStories> mResultTopStories;
    private TopStoriesNewsAdapter adapter;


    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this,view);
        this.configureRecyclerView();
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

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        this.mResultTopStories = new ArrayList<>();
        this.adapter = new TopStoriesNewsAdapter(this.mResultTopStories, Glide.with(this),1);
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

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        mSwipeRefreshLayout.setRefreshing(true);
        this.disposable = NYTimesStreams.streamFetchTopStoriesNews("home").subscribeWith(new DisposableObserver<TopStoriesNews>() {
            @Override
            public void onNext(TopStoriesNews resultTopStories) {
                updateUI(resultTopStories.getResults());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<ResultTopStories> resultTopStories){
        mSwipeRefreshLayout.setRefreshing(false);
        mResultTopStories.clear();
        mResultTopStories.addAll(resultTopStories);
        adapter.notifyDataSetChanged();
    }

}

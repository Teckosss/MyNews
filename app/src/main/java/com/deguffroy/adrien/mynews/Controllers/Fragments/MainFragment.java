package com.deguffroy.adrien.mynews.Controllers.Fragments;


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
import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.Models.MostPopular.MostPopularNews;
import com.deguffroy.adrien.mynews.Models.MostPopular.ResultMostPopular;
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
public class MainFragment extends Fragment {

    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String IDENTIFIER = "Identifier";

    private Disposable disposable;
    private List<ResultTopStories> mResultTopStories;
    private List<ResultMostPopular> mResultMostPopulars;
    private TopStoriesNewsAdapter adapter;

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
        if (getIdentifier() == MainActivity.FRAGMENT_TOP_STORIES){
            this.mResultTopStories = new ArrayList<>();
            this.adapter = new TopStoriesNewsAdapter(this.mResultTopStories, Glide.with(this), getIdentifier());
        }
        else if (getIdentifier() == MainActivity.FRAGMENT_MOST_POPULAR){
            this.mResultMostPopulars = new ArrayList<>();
            this.adapter = new TopStoriesNewsAdapter(this.mResultMostPopulars, Glide.with(this), getIdentifier());
        }
        else if (getIdentifier() == MainActivity.FRAGMENT_BUSINESS){

        }
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
        if (getIdentifier() == MainActivity.FRAGMENT_TOP_STORIES) {
            this.disposable = NYTimesStreams.streamFetchTopStoriesNews("home").subscribeWith(new DisposableObserver<TopStoriesNews>() {
                @Override
                public void onNext(TopStoriesNews resultTopStories) {
                    updateUI(resultTopStories.getResults(),getIdentifier());
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("TOP STORIES", "onError: " + e );
                }

                @Override
                public void onComplete() {
                }
            });
        }
        else if (getIdentifier() == MainActivity.FRAGMENT_MOST_POPULAR){
            this.disposable = NYTimesStreams.streamFetchMostPopularNews().subscribeWith(new DisposableObserver<MostPopularNews>() {
                @Override
                public void onNext(MostPopularNews resultMostPopular) {
                    updateUI(resultMostPopular.getResults(),getIdentifier());
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("MOST POPULAR", "onError: " + e );
                }

                @Override
                public void onComplete() {
                }
            });
        }
        else if (getIdentifier() == MainActivity.FRAGMENT_BUSINESS){

        }
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List result, int identifier){
        mSwipeRefreshLayout.setRefreshing(false);
        switch (identifier){
            case MainActivity.FRAGMENT_TOP_STORIES:
                mResultTopStories.clear();
                mResultTopStories.addAll(result);
                break;
            case MainActivity.FRAGMENT_MOST_POPULAR:
                mResultMostPopulars.clear();
                mResultMostPopulars.addAll(result);
                break;
            case MainActivity.FRAGMENT_BUSINESS:

                break;
        }
        adapter.notifyDataSetChanged();
    }

}

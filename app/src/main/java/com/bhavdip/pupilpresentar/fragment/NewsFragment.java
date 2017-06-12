package com.bhavdip.pupilpresentar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.adapter.NewsAdapter;
import com.bhavdip.pupilpresentar.listner.ApiInterface;
import com.bhavdip.pupilpresentar.model.News;
import com.bhavdip.pupilpresentar.model.NewsResponse;
import com.bhavdip.pupilpresentar.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private static final String TAG = NewsFragment.class.getSimpleName();
    private final static String API_KEY = "416681c59bb84778a3a4b24b4acf5a4b";
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        context = getContext();
        if (API_KEY.isEmpty()) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Please obtain your API KEY first from newsapi.org", Snackbar.LENGTH_LONG).show();
            return view;
        }


        recyclerView = (RecyclerView) view.findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        //swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                refreshLatestNews();
            }
        });


        refreshLatestNews();


        return view;
    }

    private void refreshLatestNews() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<NewsResponse> call = apiService.getTopRatedMovies("the-hindu", "top", API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                List<News> news = response.body().getArticles();
                Log.d(TAG, "Number of news received: " + news.size());
                recyclerView.setAdapter(new NewsAdapter(news, R.layout.list_item_news, context));
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }
}

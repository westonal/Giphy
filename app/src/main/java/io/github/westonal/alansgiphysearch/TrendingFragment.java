package io.github.westonal.alansgiphysearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.westonal.alansgiphysearch.trending.GifPagedListAdapter;
import io.github.westonal.alansgiphysearch.trending.TrendingViewModel;
import io.github.westonal.giphyapi.GiphyService;

public final class TrendingFragment extends Fragment {

    @Inject
    GiphyService giphyService;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trending, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view_trending_gifs);

        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        final GifPagedListAdapter adapter = new GifPagedListAdapter();

        final TrendingViewModel trendingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TrendingViewModel.class);
        trendingViewModel.getGifs().observe(this, adapter::submitList);

        recyclerView.setAdapter(adapter);

        return view;
    }
}

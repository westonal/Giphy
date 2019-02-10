package io.github.westonal.alansgiphysearch;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.github.westonal.alansgiphysearch.gifdata.GifListViewModel;
import io.github.westonal.alansgiphysearch.gifdata.GifPagedListAdapter;
import io.github.westonal.alansgiphysearch.gifdata.NetworkState;
import timber.log.Timber;

public final class GifListFragment extends Fragment {

    @Inject
    @SuppressWarnings("WeakerAccess")
    ViewModelProvider.Factory viewModelFactory;

    private GifListViewModel gifListViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Snackbar snackbar;

    @Override
    public void onAttach(Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gif_list, container, false);

        gifListViewModel = getViewModel();

        setupRecyclerView(view.findViewById(R.id.recycler_view_gifs), gifListViewModel);

        setupSearch(view.findViewById(R.id.edit_text_search), gifListViewModel);

        setupSwipeToRefresh(view.findViewById(R.id.swipe_refresh), gifListViewModel);

        return view;
    }

    @NonNull
    private GifListViewModel getViewModel() {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(GifListViewModel.class);
    }

    private void setupRecyclerView(final RecyclerView recyclerView, final GifListViewModel gifListViewModel) {
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        final GifPagedListAdapter adapter = new GifPagedListAdapter();

        gifListViewModel.getGifs().observe(this, adapter::submitList);
        gifListViewModel.getNetworkState().observe(this, this::networkStateChange);
        recyclerView.setAdapter(adapter);
    }

    private static void setupSearch(final TextView textView, final GifListViewModel gifListViewModel) {
        textView.addTextChangedListener(new DebouncedTextWatcher((searchTerm) -> {
            Timber.d("New search term (debounced) \"%s\"", searchTerm);
            gifListViewModel.setSearchTerm(searchTerm);
        }, 500L));
    }

    private void setupSwipeToRefresh(final SwipeRefreshLayout swipeRefreshLayout, final GifListViewModel gifListViewModel) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(gifListViewModel::refresh);
    }

    private void networkStateChange(NetworkState networkState) {
        Timber.d("Network state %s", networkState);
        if (networkState == NetworkState.FAILED) {
            snackbar = Snackbar
                    .make(getActivity().findViewById(R.id.coordinator_layout),
                            getString(R.string.loading_failed),
                            Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.retry), view -> gifListViewModel.retry(new Handler()));
            snackbar.show();
        } else {
            if (snackbar != null) snackbar.dismiss();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

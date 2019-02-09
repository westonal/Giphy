package io.github.westonal.alansgiphysearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.Pagination;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public final class TrendingFragment extends Fragment {

    @Inject
    GiphyService giphyService;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public TrendingFragment() {
    }

    @Override
    public void onAttach(Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trending, container, false);
        view.findViewById(R.id.start_other_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(TrendingFragmentDirections.actionTrendingFragmentToGifFragment());
            }
        });

        compositeDisposable.add(giphyService.getTrending(12)
                .doOnError(Timber::e)
                .subscribe(trendingResponse -> {
                    final Pagination pagination = trendingResponse.getPagination();
                    Timber.d("Paging info %d/%d/%d ", pagination.getCount(), pagination.getOffset(), pagination.getTotalCount());
                }));

        return view;
    }

}

package io.github.westonal.alansgiphysearch;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import io.github.westonal.giphyapi.GiphyService;
import io.github.westonal.giphyapi.dto.TrendingResponse;
import io.reactivex.functions.Consumer;

public final class TrendingFragment extends Fragment {

    @Inject
    GiphyService giphyService;

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


        giphyService.getTrending(12)
                .subscribe(new Consumer<TrendingResponse>() {
                    @Override
                    public void accept(TrendingResponse trendingResponse) {
                        Log.d("ALAN", "result size: " + trendingResponse.getPagination().getCount());
                    }
                });

        return view;
    }

}

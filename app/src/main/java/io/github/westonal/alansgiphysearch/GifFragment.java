package io.github.westonal.alansgiphysearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public final class GifFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gif, container, false);
        final ImageView imageView = view.findViewById(R.id.image_view_gif);
        Glide.with(imageView.getContext())
                .load(GifFragmentArgs.fromBundle(requireArguments()).getGifUrl())
                .into(imageView);
        return view;
    }

    @NonNull
    private Bundle requireArguments() {
        final Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalStateException("Fragment " + this + " has no arguments.");
        }
        return arguments;
    }
}

package io.github.westonal.alansgiphysearch.gifdata;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.github.westonal.alansgiphysearch.GifListFragmentDirections;
import io.github.westonal.alansgiphysearch.R;
import io.github.westonal.giphyapi.dto.Gif;
import io.github.westonal.giphyapi.dto.Images;

public final class GifPagedListAdapter extends PagedListAdapter<Gif, GifPagedListAdapter.GifViewHolder> {

    public GifPagedListAdapter() {
        super(new Diff());
    }

    @NonNull
    @Override
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gif, parent, false);
        return new GifViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull GifViewHolder holder, int position) {
        final Gif item = getItem(position);
        if (item == null) return;
        final Images images = item.getImages();
        final String url = images.getFixedWidth().getUrl();
        final String originalUrl = images.getOriginal().getUrl();

        Glide.with(holder.imageView.getContext())
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> {
                    Navigation.findNavController(view)
                            .navigate(GifListFragmentDirections.actionViewGif(originalUrl));
                }
        );
    }

    static class GifViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final View progressBar;

        private GifViewHolder(final View root) {
            super(root);
            imageView = root.findViewById(R.id.image_view_gif);
            progressBar = root.findViewById(R.id.progress);
        }
    }

    static class Diff extends DiffUtil.ItemCallback<Gif> {

        @Override
        public boolean areItemsTheSame(@NonNull Gif oldItem, @NonNull Gif newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Gif oldItem, @NonNull Gif newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }

    @Override
    public void onViewRecycled(@NonNull GifViewHolder holder) {
        holder.imageView.setOnClickListener(null);
        holder.imageView.setImageDrawable(null);
        super.onViewRecycled(holder);
    }
}

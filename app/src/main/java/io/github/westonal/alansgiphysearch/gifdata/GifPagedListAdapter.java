package io.github.westonal.alansgiphysearch.gifdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.github.westonal.alansgiphysearch.GifListFragmentDirections;
import io.github.westonal.alansgiphysearch.R;
import io.github.westonal.giphyapi.dto.Gif;

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
        final String url = getItem(position).getImages().getFixedWidth().getUrl();

        Glide.with(holder.imageView.getContext())
                .load(url)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view ->
                Navigation.findNavController(view)
                        .navigate(GifListFragmentDirections.actionViewGif(url))
        );
    }

    static class GifViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        private GifViewHolder(final View root) {
            super(root);
            imageView = root.findViewById(R.id.image_view_gif);
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

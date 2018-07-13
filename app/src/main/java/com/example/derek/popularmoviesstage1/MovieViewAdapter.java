package com.example.derek.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.MovieViewHolder> {

    RequestOptions options;
    Context mContext;
    private List<Movie> mData;

    public MovieViewAdapter(List list ) {
        this.mData = list;
        options = new RequestOptions()
                .centerCrop();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        mContext = parent.getContext();
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        holder.titleTextView.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getMovie_poster()).apply(options).into(holder.posterImageView);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        public final ImageView posterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            titleTextView =  itemView.findViewById(R.id.tv_title);
            posterImageView =  itemView.findViewById(R.id.iv_poster);



        }

        void bind(int listIndex) {
            titleTextView.setText(String.valueOf(listIndex));
            Picasso.get().load("http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(posterImageView);
        }

    }
}

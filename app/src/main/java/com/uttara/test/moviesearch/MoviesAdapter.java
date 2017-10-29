package com.uttara.test.moviesearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.uttara.test.moviesearch.MovieDetailActivity.IMAGE_URL;

/**
 * Created by Mahe on 10/29/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {


    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView  releasDate, voteCount, rating, title;
        ImageView wallImageUrl;


        public MovieViewHolder(View itemView) {
            super(itemView);
            wallImageUrl = (ImageView) itemView.findViewById(R.id.wall_image);
            releasDate = (TextView) itemView.findViewById(R.id.releaseDate);
            voteCount = (TextView) itemView.findViewById(R.id.voteCount_tv);
            rating = (TextView) itemView.findViewById(R.id.rating_tv);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
    private Context mContext;
    List<MovieBean> beanList;

    public MoviesAdapter(Context context, List<MovieBean> beanList) {
        this.beanList =beanList;
        this.mContext = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movies_card,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MovieViewHolder holder, int position) {
        holder.title.setText(beanList.get(position).getOriginalTitle());
        holder.rating.setText(Double.toString(beanList.get(position).getVoteAverage()));
        holder.releasDate.setText(beanList.get(position).getReleaseDate());
        holder.voteCount.setText(String.valueOf(beanList.get(position).getVoteCount()));
        Picasso.with(mContext).load(IMAGE_URL+""+beanList.get(position).getPosterPath()).into(holder.wallImageUrl);

    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }
}

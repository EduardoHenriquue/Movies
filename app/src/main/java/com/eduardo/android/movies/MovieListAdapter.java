package com.eduardo.android.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/**
 * Created by Eduardo on 08/04/2016.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {
    private int imageHeight;

    public MovieListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.poster);
            viewHolder.title=(TextView)convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Movie movie=getItem(position);
        if (movie.getCachedPosterPath()==null) {
            movie.setCachedPosterPath("http://image.tmdb.org/t/p/w" + String.valueOf(imageWidth) + "/" + movie.getPosterPath());
        }
        viewHolder.title.setText(movie.getTitle());
        viewHolder.image.setMinimumHeight(imageHeight);
        viewHolder.image.setMinimumWidth(imageWidth);
        Picasso.with(getContext()).load(movie.getCachedPosterPath()).into(viewHolder.image);

        return convertView;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView title;
    }
    int imageWidth;

    public void setImageWidth(int imageWidth) {
        if (imageWidth>700) {
            this.imageWidth = 500;
        }else if(imageWidth>500){
            this.imageWidth = 500;
        }else if (imageWidth>300){
            this.imageWidth=342;
        }else{
            this.imageWidth=185;
        }

    }

}

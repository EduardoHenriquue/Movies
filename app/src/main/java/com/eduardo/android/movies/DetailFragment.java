package com.eduardo.android.movies;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Eduardo on 08/04/2016.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Movie> {

    private View rootView;
    @Bind(R.id.title)
    TextView titleView;

    @Bind(R.id.poster)
    ImageView posterImageView;
    private Movie movie;
    @Bind(R.id.year)
    TextView yearView;
    @Bind(R.id.rating)
    TextView ratingView;
    @Bind(R.id.overview)
    TextView overviewView;
    @Bind(R.id.runtime)
    TextView runtimeView;

    boolean detailsLoaded=false;

    public DetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        ButterKnife.bind(this, rootView);
        if (savedInstanceState == null) {
            movie = getArguments().getParcelable("movie");
        } else {
            movie = savedInstanceState.getParcelable("movie");
            ratingView.setText(movie.getVoteAvarage() + "/10");
            runtimeView.setText(String.valueOf(movie.getRuntime()) + "min");
            yearView.setText(movie.getReleaseYear());
            detailsLoaded=true;

        }

        titleView.setText(movie.getTitle());
        overviewView.setText(movie.getOverview());
        Picasso.with(getActivity()).load(movie.getCachedPosterPath()).placeholder(R.drawable.notification_template_icon_bg).into(posterImageView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!detailsLoaded) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
        }
    }

    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {

        MovieLoader loader = new MovieLoader(getActivity());
        loader.setId(movie.getId());
        return loader;

    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if (data == null) {
            Toast.makeText(getActivity(), getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
            return;
        }
        movie.setVoteAvarage( data.getVoteAvarage());
        movie.setReleaseDate(data.getReleaseDate());
        movie.setRuntime(data.getRuntime());
        ratingView.setText(data.getVoteAvarage() + "/10");
        runtimeView.setText(String.valueOf(data.getRuntime()) + "min");
        yearView.setText(data.getReleaseYear());


    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movie", movie);
    }

    static class MovieLoader extends AsyncTaskLoader<Movie> {

        String id;

        public void setId(String id) {
            this.id = id;
        }

        public MovieLoader(Context context) {
            super(context);
        }

        @Override
        public Movie loadInBackground() {
            try {
                return APIClient.getInstance().getMovieDetails(id);
            } catch (Exception ex) {
                return null;
            }
        }

    }


}

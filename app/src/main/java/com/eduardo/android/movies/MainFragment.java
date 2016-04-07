package com.eduardo.android.movies;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Eduardo on 01/04/2016.
 */
public class MainFragment extends Fragment{

    private FilmeAdapter mFilmeAdapter;
    private GridView mGridview;

    private ArrayList<Filme> mFilmes = null;

    /*
    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_IMAGE,
            MovieContract.MovieEntry.COLUMN_IMAGE2,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_DATE
    };

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_IMAGE = 3;
    public static final int COL_IMAGE2 = 4;
    public static final int COL_OVERVIEW = 5;
    public static final int COL_RATING = 6;
    public static final int COL_DATE = 7;
    */
    public interface Callback{
        void onItemSelected(Filme filme);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInsranceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mGridview = (GridView) view.findViewById(R.id.filme_gridview);
        mFilmeAdapter = new FilmeAdapter(getActivity(), new ArrayList<Filme>());
        mGridview.setAdapter(mFilmeAdapter);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Filme filme = (Filme) mFilmeAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(filme);
            }
        });

        return view;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Filme>> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private List<Filme> getFilmesDataFromJson(String jsonStr) throws JSONException{
            JSONObject filmeJson = new JSONObject(jsonStr);
            JSONArray filmeArray = filmeJson.getJSONArray("resultados");

            List<Filme> resultados = new ArrayList<>();
            resultados.addAll((Collection)filmeArray);

            return resultados;
        }

        @Override
        protected List<Filme> doInBackground(String... params) {

            if(params.length == 0){
                return null;
            }
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String jsonStr = null;

            String key = "9041cbac86ccb06dc583aadb53f255d7";
            String language = "&language=br";
            String imageLang = "&include_image_language=br,null";
            try{
                final String BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";
                final String KEY = "key";
                final String LANG = "language";
                final String IMAGE_LANGUAGE = "imageLang";

                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(KEY, key)
                        .build();
                URL url = new URL(uri.toString());

                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream stream = connection.getErrorStream();
                StringBuffer buffer = new StringBuffer();
                if (stream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(stream));
                String linha;
                while ((linha = reader.readLine())!= null){
                    buffer.append(linha + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                jsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                        e.printStackTrace();
                    }
                }
            }

            try{
                return getFilmesDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<Filme> filmes){
            if(filmes != null){

            }
            mFilmes = new ArrayList<>();
            mFilmes.addAll(filmes);
        }
    }

}



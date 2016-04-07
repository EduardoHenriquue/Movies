package com.eduardo.android.movies;

import android.content.Context;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eduardo on 31/03/2016.
 */
public class FilmeAdapter extends BaseAdapter{

    private Context mContext;
    private final LayoutInflater mInflater;
    private final Filme mLock = new Filme();
    private List<Filme> filmesObjetc;


    public FilmeAdapter(Context context,  List<Filme> objects) {
        this.mContext = context;
        this.filmesObjetc = objects;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public Context getContext(){
        return mContext;
    }
    public void add(Filme obj){
        synchronized (mLock){
            filmesObjetc.add(obj);
        }
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return filmesObjetc.size();
    }

    @Override
    public Object getItem(int posicao) {
        return filmesObjetc.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent){
        View view = convertView;
        ViewHolder viewHolder;
        if(view != null) {
            view = mInflater.inflate(R.layout.adapter_filme_grid, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        final Filme filme = (Filme) getItem(posicao);
        String urlImagem = "http://image.tmdb.org/t/p/w185" + filme.getPoster();

        viewHolder = (ViewHolder)view.getTag();
        Picasso.with(getContext()).load(urlImagem).into(viewHolder.imageView);

        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;
        //public final TextView titleView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.imagem);
            //titleView = (TextView) view.findViewById(R.id.);
        }
    }


}



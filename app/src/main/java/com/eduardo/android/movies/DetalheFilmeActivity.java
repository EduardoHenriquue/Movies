package com.eduardo.android.movies;

import android.app.Activity;
import android.os.Bundle;

public class DetalheFilmeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detalhe_filme);
    }
}

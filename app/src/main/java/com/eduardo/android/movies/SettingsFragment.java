package com.eduardo.android.movies;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Eduardo on 08/04/2016.
 */
public class SettingsFragment extends PreferenceFragment {


    public SettingsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }
}

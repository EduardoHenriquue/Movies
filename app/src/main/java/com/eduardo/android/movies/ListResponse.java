package com.eduardo.android.movies;

import java.util.List;

/**
 * Created by Eduardo on 08/04/2016.
 */
public class ListResponse {
    List<Movie> results;
    int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}

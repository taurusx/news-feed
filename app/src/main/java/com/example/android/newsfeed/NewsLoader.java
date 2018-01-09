package com.example.android.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of {@link NewsObject}s by using an {@link AsyncTaskLoader} to perform the
 * network request to the given URL.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsObject>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * Query URL
     */
    private String mStringUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context   of the activity
     * @param stringUrl to load data from
     */
    public NewsLoader(Context context, String stringUrl) {
        super(context);
        mStringUrl = stringUrl;
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsObject> loadInBackground() {
        if (mStringUrl == null) {
            return null;
        }

        // Perform the HTTP request for NewsObject data and process the response.
        List<NewsObject> result = QueryUtils.fetchNewsObjectData(mStringUrl);
        return result;
    }
}

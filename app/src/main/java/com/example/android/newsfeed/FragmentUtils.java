package com.example.android.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Helper methods related to NewsObjects in Fragments.
 */

public final class FragmentUtils {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = FragmentUtils.class.getName();

    /**
     * URL for NewsObjects data from The Guardian API (base).
     */
    private static final String GUARDIAN_API_BASE_URL =
            "https://content.guardianapis.com/search";

    /**
     * API KEY for NewsFeed app to use in NewsObjects API requests.
     */
    private static final String API_KEY = "76b27996-1c8f-4b10-9181-a408024d474f";

    /**
     * Constant value for the news loader ID (important when using multiple loaders).
     */
    public static final int NEWS_LOADER_ID_TECH = 0;
    public static final int NEWS_LOADER_ID_WORLD = 1;
    public static final int NEWS_LOADER_ID_SCIENCE = 2;
    public static final int NEWS_LOADER_ID_BUSINESS = 3;

    /**
     * Simple public constructor
     */
    public FragmentUtils() {
    }

    /**
     * Helper method for parsing URL from clicked News and setting Intent
     */
    public static Intent setOnNewsClickedAction(AdapterView<?> parent, int position) {
        // Get the {@link NewsObject} object located at this position in the list
        NewsObject currentNewsObject = (NewsObject) parent.getItemAtPosition(position);


        // Get the URL for the NewsObject item.
        String currentUrl = currentNewsObject.getNewsUrl();
        if (!currentUrl.startsWith("http://") && !currentUrl.startsWith("https://")) {
            currentUrl = "http://" + currentUrl;
        }

        // Open implicit intent with URL provided for current NewsObject
        Intent openUrl = new Intent(Intent.ACTION_VIEW);
        openUrl.setData(Uri.parse(currentUrl));


        Log.d(LOG_TAG, "Current URL: " + currentUrl + "\nCurrent newsObject: " + currentNewsObject);
        return openUrl;
    }

    public static boolean isMyInternetConnected(Context context) {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        // Method getActiveNetworkInfo() may return null - check isConnected().
        return (activeNetwork != null &&
                activeNetwork.isConnected());
    }

    public static void displayNoInternetInfo(Context context) {
        // Inform user about invalid Internet connection
        Toast toast = Toast.makeText(context, context.getResources().getString(R.string.fragment_textview_nointernet), Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        TextView text = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    @NonNull
    public static String buildRequestUrl(String sectionName) {
        Uri baseUri = Uri.parse(GUARDIAN_API_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("section", sectionName); // e.g. section=technology
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        Log.d(LOG_TAG, "Request URL: " + uriBuilder.toString());
        return uriBuilder.toString();
    }


    @NonNull
    public static String buildRequestUrl(String tagOne, String tagTwo) {
        Uri baseUri = Uri.parse(GUARDIAN_API_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("tag", tagOne + "/" + tagTwo); // e.g. tag=technology/technology gives more results than section=technology
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("api-key", API_KEY);

        Log.d(LOG_TAG, "Request URL: " + uriBuilder.toString());
        return uriBuilder.toString();
    }
}

package com.example.android.newsfeed;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScienceFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsObject>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = ScienceFragment.class.getName();

    /**
     * Adapter for the list of {@link NewsObject}s
     */
    private NewsAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * Loadeing indicator that is displayed when the list is being loaded
     */
    private ProgressBar mLoadingIndicator;

    public ScienceFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listingview, container, false);

        // Create an {@link NewsAdapter}, whose data source is an empty list of {@link NewsObject}s.
        mAdapter = new NewsAdapter(this.getActivity(), new ArrayList<NewsObject>());

        // Find the {@link ListView} object in the view hierarchy of the root view {@link View}
        // declared in the layout file.
        ListView newsObjectListView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link NewsAdapter} above, so it displays
        // list items for each {@link NewsObject} in the list.
        newsObjectListView.setAdapter(mAdapter);

        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        newsObjectListView.setEmptyView(mEmptyStateTextView);

        // Set empty state text to display when the app starts and loads data.
        mEmptyStateTextView.setText(R.string.fragment_textview_loading);

        // Set loading indicator to display when the list is loading data.
        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);

        // Check Internet connection and if network is available, initialize LoaderManager.
        if (FragmentUtils.isMyInternetConnected(getContext())) {

            Log.d(LOG_TAG, "Network is active");

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getActivity().getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(FragmentUtils.NEWS_LOADER_ID_SCIENCE, null, this);
        } else {
            mAdapter.clear();

            // Set empty state text to display "No Internet connection."
            mEmptyStateTextView.setText(R.string.fragment_textview_nointernet);
        }


        // Set a click listener to show article in browser when the list item is clicked on
        newsObjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (FragmentUtils.isMyInternetConnected(getContext())) {
                    // Use the helper method to extract URL to be opened in a browser
                    Intent openUrl = FragmentUtils.setOnNewsClickedAction(parent, position);
                    // Use returned Intent
                    startActivity(openUrl);
                } else {
                    // Inform user about invalid Internet connection
                    FragmentUtils.displayNoInternetInfo(getContext());
                }
            }
        });
        return rootView;
    }


    @Override
    public Loader<List<NewsObject>> onCreateLoader(int i, Bundle bundle) {

        // Show loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);

        // Use helper method to build request URL for this category
        String requestUrl = FragmentUtils.buildRequestUrl(getString(R.string.category_science),
                getString(R.string.category_science));

        return new NewsLoader(this.getContext(), requestUrl);
    }


    @Override
    public void onLoadFinished(Loader<List<NewsObject>> loader, List<NewsObject> data) {

        // Hide loading indicator
        mLoadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link NewsObject}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            // Update the information displayed to the user.
            mAdapter.addAll(data);
        } else {
            // Set empty state text to display "No news found."
            mEmptyStateTextView.setText(R.string.fragment_textview_nonews);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsObject>> loader) {
        // Clear the adapter of previous news data
        mAdapter.clear();
    }
}

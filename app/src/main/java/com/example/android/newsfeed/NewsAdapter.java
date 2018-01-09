package com.example.android.newsfeed;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A custom adapter {@link NewsAdapter} knows how to create a list item layout for each newsObject
 * in the data source (a list of {@link NewsObject} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be populated and displayed to the user.
 */

public class NewsAdapter extends ArrayAdapter<NewsObject>{

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = NewsAdapter.class.getName();

    /**
     * This is custom constructor.
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists ({@link NewsObject}  details).
     *
     * @param context The current context. Used to inflate the layout file.
     * @param newsObjects   A List of NewsObject objects to display in a list
     */
    public NewsAdapter(Activity context, ArrayList<NewsObject> newsObjects) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // The second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for few TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, newsObjects);
    }


    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_news, parent, false);

            // Set up the ViewHolder:
            // Find the TextViews in the item_newsObject.xml layout with corresponding IDs.
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = convertView.findViewById(R.id.textview_listview_title);
            viewHolder.dateTextView = convertView.findViewById(R.id.textview_listview_date);
            viewHolder.categoryTextView = convertView.findViewById(R.id.textview_listview_category);

            // Store the holder with the view.
            convertView.setTag(viewHolder);

        } else {
            // Use the preset viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }


        // Get the {@link NewsObject} object located at this position in the list
        NewsObject currentNewsObject = getItem(position);

        // Get the title from the current NewsObject object and
        // set this text on the titleTextView
        viewHolder.titleTextView.setText(currentNewsObject.getTitle());

        // Get the date of publication and publisher from the current NewsObject object and
        // set this text on the Info TextView
        String dateString = currentNewsObject.getPublishedDate();

        // Change given date to a desirable time and date format.
        // First set parsing format for given String (parsed from JSON)
        Locale currentLocale = getContext().getResources().getConfiguration().locale;
        SimpleDateFormat parsingDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'", currentLocale);
        parsingDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            // Try parsing date from given String
            Date currentNewsDate = parsingDateFormat.parse(dateString);
            // Format parsed Date object with desired format
            // example, get time and date from locale:
            // String formattedDate = DateFormat.getDateTimeInstance().format(currentNewsDate);
            // or in relation to now:
            long milliseconds = currentNewsDate.getTime();
            long timeNow = System.currentTimeMillis();
            String formattedDate = DateUtils.getRelativeTimeSpanString(milliseconds, timeNow, DateUtils.MINUTE_IN_MILLIS).toString();

            // Set news date on dateTextView
            viewHolder.dateTextView.setText(formattedDate);
        }
        catch (ParseException e) {
            Log.e(LOG_TAG,"Parsing Date from a String failed", e);
        }

        // Get the category from the current NewsObject object and
        // set this text on the categoryTextView
        if (currentNewsObject.getAuthor() == null || currentNewsObject.getAuthor().isEmpty()) {
            viewHolder.categoryTextView.setText(currentNewsObject.getCategory().toUpperCase());
        } else {
            StringBuilder infoText = new StringBuilder();
            infoText.append(currentNewsObject.getAuthor()).append("  @  ")
                    .append(currentNewsObject.getCategory().toUpperCase());
            viewHolder.categoryTextView.setText(infoText);
        }

        // Return the whole list item layout so that it can be shown in the ListView
        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView categoryTextView;
    }
}

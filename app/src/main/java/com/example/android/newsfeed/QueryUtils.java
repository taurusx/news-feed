package com.example.android.newsfeed;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving NewsObjects data from Guardian API.
 */

public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Guardian API and return an {@link ArrayList<NewsObject>} object to represent
     * NewsObjects to show to the user.
     */
    public static ArrayList<NewsObject> fetchNewsObjectData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link ArrayList<NewsObject>} object
        ArrayList<NewsObject> newsObject = extractNewsObjectsFromJson(jsonResponse);

        // Return the {@link ArrayList<NewsObject>}
        return newsObject;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link ArrayList<NewsObject>} , a list of {@link NewsObject} objects
     * that has been built up from parsing the input NewsObject JSON string response.
     */
    private static ArrayList<NewsObject> extractNewsObjectsFromJson(String newsObjectJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsObjectJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding newsObjects to
        ArrayList<NewsObject> newsObjects = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by newsObjectJSON string and
            // build up a list of NewsObject objects with the corresponding data.
            JSONObject rootJsonObject = new JSONObject(newsObjectJSON);
            JSONObject rootJsonResponse = rootJsonObject.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of results (NewsObjects).
            JSONArray newsObjectResultsArray = rootJsonResponse.optJSONArray("results");

            // For each newsObject in the newsObjectArray, create a {@link NewsObject} object
            for (int i = 0; i < newsObjectResultsArray.length(); i++) {

                // Get a single newsObject at position 'i' within the list of newsObjects
                JSONObject currentNewsObject = newsObjectResultsArray.optJSONObject(i);

                // For a given newsObject, extract the Strings associated with the
                // keys called "webTitle", "sectionName", "webPublicationDate" and
                // "webUrl" which represents a title, date and link for that newsObject.
                // Received date format is: "yyyy-MM-dd'T'HH:mm:ss'Z'"
                String title = currentNewsObject.getString("webTitle");
                String category = currentNewsObject.getString("sectionName");
                String publishedDate = currentNewsObject.getString("webPublicationDate");
                String newsUrl = currentNewsObject.getString("webUrl");

                // Get array of tags, which should contain 'contributor' (author) if available.
                JSONArray tagsArray = currentNewsObject.getJSONArray("tags");

                StringBuilder authorBuilder = new StringBuilder();
                for (int j = 0; j < tagsArray.length(); j++) {
                    JSONObject currentTagsObject = tagsArray.getJSONObject(j);
                    if (currentTagsObject.getString("type").equalsIgnoreCase("contributor")) {
                        if (authorBuilder.toString().equals("")) {
                            authorBuilder.append(currentTagsObject.getString("webTitle"));
                        } else {
                            authorBuilder.append(", ");
                            authorBuilder.append(currentTagsObject.getString("webTitle"));
                        }
                    }
                }

                NewsObject nextNewsObjectObject;
                if (authorBuilder.toString().isEmpty()) {
                    // Create a new {@link NewsObject} object with the title, category,
                    // publication date and URL info from the JSON response.
                    nextNewsObjectObject = new NewsObject(title, category, publishedDate, newsUrl);
                } else {
                    // Create a new {@link NewsObject} object with the title, category, author,
                    // publication date and URL info from the JSON response.
                    String author = authorBuilder.toString();
                    nextNewsObjectObject = new NewsObject(title, category, publishedDate, newsUrl, author);
                }

                // Add the new {@link NewsObject} to the list of newsObjects.
                newsObjects.add(nextNewsObjectObject);
            }
            // Return the list of newsObjects {@link ArrayList<NewsObject>}
            return newsObjects;

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the NewsObject JSON results", e);
        }
        return null;
    }
}

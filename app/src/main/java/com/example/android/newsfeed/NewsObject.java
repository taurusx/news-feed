package com.example.android.newsfeed;

/**
 * A {@link NewsObject} object contains information related to a single newsObject.
 * Contains information about Title, Date of publication and Article URL.
 */

public class NewsObject {

    /**
     * String value of the newsObject title
     */
    private String mTitle;

    /**
     * String value of the newsObject category
     */
    private String mCategory;

    /**
     * String value of the newsObject publication date
     */
    private String mPublishedDate;

    /**
     * String value of the newsObject web URL
     */
    private String mNewsUrl;

    /**
     * String value of the newsObject author
     */
    private String mAuthor = null;


    /**
     * Create a new {@link NewsObject} object with initial values of title, newsUrl and publishedDate.
     *
     * @param title         is the title of the NewsObject
     * @param publishedDate is the date of publication of the NewsObject
     * @param newsUrl       is the web Url of the NewsObject
     */
    public NewsObject(String title, String publishedDate, String newsUrl) {
        mTitle = title;
        mPublishedDate = publishedDate;
        mNewsUrl = newsUrl;
    }

    /**
     * Create a new {@link NewsObject} object with initial values of title,
     * category, newsUrl and publishedDate.
     *
     * @param title         is the title of the NewsObject
     * @param category      is the category of the NewsObject
     * @param publishedDate is the date of publication of the NewsObject
     * @param newsUrl       is the web Url of the NewsObject
     */
    public NewsObject(String title, String category, String publishedDate, String newsUrl) {
        mTitle = title;
        mCategory = category;
        mPublishedDate = publishedDate;
        mNewsUrl = newsUrl;
    }

    /**
     * Create a new {@link NewsObject} object with initial values of title,
     * category, newsUrl and publishedDate.
     *
     * @param title         is the title of the NewsObject
     * @param category      is the category of the NewsObject
     * @param publishedDate is the date of publication of the NewsObject
     * @param newsUrl       is the web Url of the NewsObject
     * @param author        is the author of the NewsObject
     */
    public NewsObject(String title, String category, String publishedDate, String newsUrl,
                      String author) {
        mTitle = title;
        mCategory = category;
        mPublishedDate = publishedDate;
        mNewsUrl = newsUrl;
        mAuthor = author;
    }


    /**
     * Get the string value representing Title of the NewsObject.
     *
     * @return title of the newsObject.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the string value representing category of the NewsObject.
     *
     * @return category of the newsObject.
     */
    public String getCategory() {
        return mCategory;
    }

    /**
     * Get the string value of the NewsObject class representing date of publication.
     *
     * @return publication date.
     */
    public String getPublishedDate() {
        return mPublishedDate;
    }

    /**
     * Get the string value representing web URL of the NewsObject.
     *
     * @return web URL of the newsObject.
     */
    public String getNewsUrl() {
        return mNewsUrl;
    }

    /**
     * Get the string value representing author of the NewsObject.
     *
     * @return author of the newsObject.
     */
    public String getAuthor() {
        return mAuthor;
    }


    @Override
    public String toString() {
        return "NewsObject{" +
                "mTitle='" + mTitle + '\'' +
                "mCategory='" + mCategory + '\'' +
                ", mPublishedDate='" + mPublishedDate + '\'' +
                ", mNewsUrl='" + mNewsUrl + '\'' +
                "mAuthor='" + mAuthor + '\'' +
                '}';
    }

}

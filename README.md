# NewsFeed App by [taurusx](https://taurusx.github.io/)

App preview:

- Check for Internet connection (while fetching articles' list or trying to access article):

![NewsFeed App Internet Check][screenshot-1] ![NewsFeed App Internet Check][screenshot-2]

- List of articles on given topic (with a sample of external `Intent` to read article in a browser):

![NewsFeed App Technology Category][screenshot-3] ![NewsFeed App Technology Article Example][screenshot-4] 

![NewsFeed App World News Category][screenshot-5] ![NewsFeed App Science Category][screenshot-6] 
![NewsFeed App Business Category][screenshot-7]

## General Description

Done as a part of [Android Basics Udacity's course][udacity-course]

NewsFeed App queries **The Guardian API** for fresh articles list on specified topic (currently hardcoded 4 categories: Technology, World News, Science and Business). It displays all list of articles under Tabs so they can easily be changed (also by swiping). Clicking on an articles opens *The Guardian* webpage with its content.

## Main Goals

**Main Goals** of the task:
1. Main layout contains `ViewPager` with Tabs to switch between articles lists (`Fragments`) populated by `FragmentPagerAdapter`.
2. `LoaderManager` and custom `AsyncTaskLoader` is used instead of `AsyncTask` for performing background thread tasks (Internet querying)
3. Connecting to a network, querying public API (*The Guardian API*) and fetching results.
4. Parsing received results (JSON file) and picking correct data, stored then in a custom object.
5. Create custom `ArrayAdapter` and populate Views in a `ListView` with given data (with a use of `ViewHolder` for better performance)
6. Using the implicit `Intent`s to open article links in a browser

## Related Work

Check out my next app: [DecorsInventory][decors-inventory].

Previous repo: [BookSearch][book-search].

[udacity-course]: https://eu.udacity.com/course/android-basics-nanodegree-by-google--nd803
[screenshot-1]: https://raw.githubusercontent.com/taurusx/news-feed/gh-pages/assets/images/news-feed-screenshot-1.png
[screenshot-2]: https://raw.githubusercontent.com/taurusx/news-feed/gh-pages/assets/images/news-feed-screenshot-2.png
[screenshot-3]: https://raw.githubusercontent.com/taurusx/news-feed/gh-pages/assets/images/news-feed-screenshot-3.png
[screenshot-4]: https://raw.githubusercontent.com/taurusx/news-feed/gh-pages/assets/images/news-feed-screenshot-4.png
[screenshot-5]: https://raw.githubusercontent.com/taurusx/news-feed/gh-pages/assets/images/news-feed-screenshot-5.png
[screenshot-6]: https://raw.githubusercontent.com/taurusx/news-feed/gh-pages/assets/images/news-feed-screenshot-6.png
[screenshot-7]: https://raw.githubusercontent.com/taurusx/news-feed/gh-pages/assets/images/news-feed-screenshot-7.png
[decors-inventory]: https://github.com/taurusx/decors-inventory
[book-search]: https://github.com/taurusx/book-search


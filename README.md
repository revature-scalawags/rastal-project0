# Hashtag Analyzer

**Rastal's Project 0**

*Pulls tweets associated with a hashtag from MongoDB and stores a Document containing up to 100 of the most frequently occurring words in MongoDB.*

*Currently, the only hashtag in the database is #gamedev. Deal with it.*

### Running the App

The simplest way to run the app is with `sbt run` while in the project folder. This will store a Document containing the default of 100 words with their counts into MongoDB.

Alternatively, the analyzer takes a single argument with `run`, an integer for limiting the number of results to display (up to a maximum of 100).

`sbt "run [maxResults]"`

In other words, `sbt "run 42"` will store the top 42 most occurring words in MongoDB, along with their counts.

### Testing

If you just want to make sure all the tests are passing without actually running the app, use `sbt test`.
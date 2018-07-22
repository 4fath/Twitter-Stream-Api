package org.interview.oauth.twitter;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.Sets;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.interview.oauth.twitter.model.Tweet;
import org.interview.oauth.twitter.model.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Main {

    private static final String CONSUMER_KEY = "vp8qXAMoZzy6jowJdtouPLUUb";
    private static final String CONSUMER_SECRET = "IMx3eIRfXXbRimoIz7cNpZCl0dr9dYEdRuDVTr2C4LdResXjN7";
    private static final String STREAM_URL = "https://stream.twitter.com/1.1/statuses/filter.json?track=bieber";

    private static final int MAX_SECOND = 30;
    private static final int MAX_TWEET_COUNT = 100;

    public static void main(String[] args) throws TwitterAuthenticationException, IOException {

        TwitterAuthenticator authenticator = new TwitterAuthenticator(System.out, CONSUMER_KEY, CONSUMER_SECRET);
        HttpRequestFactory requestFactory = authenticator.getAuthorizedHttpRequestFactory();
        HttpRequest httpRequest = requestFactory.buildGetRequest(new GenericUrl(STREAM_URL));

        HttpResponse response = httpRequest.execute();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(response.getContent(), "UTF-8"));

        Set<Tweet> resultList = Sets.newHashSet();
        long start = System.currentTimeMillis();

        Tweet tweet;
        Gson gson = new Gson();
        while ((tweet = gson.fromJson(jsonReader, Tweet.class)) != null) {
            resultList.add(tweet);
            long now = System.currentTimeMillis();
            long second = (now - start) / 1000;

            if (second >= MAX_SECOND || resultList.size() >= MAX_TWEET_COUNT) {
                printTweetList(resultList);
                start = now;
                resultList.clear();
            }
        }
    }

    private static void printTweetList(Set<Tweet> list) {
        // group tweets
        Map<User, List<Tweet>> userToTweet = list.stream().collect(Collectors.groupingBy(Tweet::getUser));
        // sort users according to its createDate
        List<User> sortedUser = userToTweet.keySet().stream().sorted(User::compareTo).collect(Collectors.toList());

        for (User user : sortedUser) {
            // sort user's tweet according to tweet's createDate
            List<Tweet> sortedTweet = userToTweet.get(user).stream().sorted(Tweet::compareTo).collect(Collectors.toList());
            for (Tweet tweet : sortedTweet) {
                printTweet(tweet);
            }
        }
    }

    private static void printTweet(Tweet tweet) {
        StringJoiner joiner = new StringJoiner(" | "); // PS: it uses StringBuilder under the hood
        joiner.add(tweet.getIdAsString());
        joiner.add(tweet.getCreated_at());
        joiner.add(tweet.getText());
        joiner.add(tweet.getUser().getIdAsString());
        joiner.add(tweet.getUser().getCreated_at());
        joiner.add(tweet.getUser().getScreen_name());

        System.out.println(joiner.toString());
    }
}
package org.interview.oauth.twitter;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

import java.io.IOException;

public class Main {

    private static final String CONSUMER_KEY = "vp8qXAMoZzy6jowJdtouPLUUb";
    private static final String CONSUMER_SECRET = "IMx3eIRfXXbRimoIz7cNpZCl0dr9dYEdRuDVTr2C4LdResXjN7";

    public static void main(String[] args) throws TwitterAuthenticationException, IOException {

        TwitterAuthenticator authenticator = new TwitterAuthenticator(System.out, CONSUMER_KEY, CONSUMER_SECRET);
        HttpRequestFactory requestFactory = authenticator.getAuthorizedHttpRequestFactory();

        HttpRequest postRequest = requestFactory.buildPostRequest(null, null);

        HttpResponse response = postRequest.execute();


    }
}

package com.gregator.auth;


import net.dean.jraw.RedditClient;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.UUID;


@Configuration
@PropertySource("classpath:reddit.properties")
public class RedditApiConfig {

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Bean
    public RedditClient redditClient() {

        // Assuming we have a 'script' reddit app
        Credentials oauthCreds = Credentials.userless(clientId, clientSecret, UUID.randomUUID());

        // Create a unique User-Agent for our bot
        UserAgent userAgent = new UserAgent("gregator");

        // Authenticate our client
        RedditClient reddit = OAuthHelper.automatic(new OkHttpNetworkAdapter(userAgent), oauthCreds);

        return reddit;
    }


}

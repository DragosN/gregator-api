package com.gregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GregatorApiApplication {

    /**
     * formula should keep in mind how old is the account, number of comments,
     * number of upvotes/downvotes overall trendiness and comments sentiment
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(GregatorApiApplication.class, args);
    }

}

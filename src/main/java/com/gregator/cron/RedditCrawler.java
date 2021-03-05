package com.gregator.cron;


import com.fasterxml.jackson.databind.JsonNode;
import net.dean.jraw.RedditClient;
import net.dean.jraw.models.*;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dean.jraw.pagination.Paginator;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class RedditCrawler {

    private final RedditClient redditClient;

    @Autowired
    public RedditCrawler(RedditClient redditClient) {
        this.redditClient = redditClient;
    }


    @PostConstruct
    public void postConstruct() {
        crawlReddit();
    }

//    @Scheduled(cron = "0 0 * * * *")
    public void crawlReddit() {
        List<String> subreddits = List.of("CryptoMoonShots");
        subreddits.forEach(subredditName -> {
            DefaultPaginator<Submission> paginator = redditClient.subreddit(subredditName)
                    .posts()
                    .sorting(SubredditSort.TOP)
                    .timePeriod(TimePeriod.DAY)
                    .limit(Paginator.RECOMMENDED_MAX_LIMIT).build();
            Listing<Submission> mostPopular = paginator.next();

            mostPopular.forEach(submission -> {
                RootCommentNode root = redditClient.submission(submission.getId()).comments();
                // walkTree() returns a Kotlin Sequence. Since we're using Java, we're going to have to
                // turn it into an Iterator to get any use out of it.
                Submission post = root.getSubject();
                List<CommentNode<Comment>> commentNodes = root.getReplies();

                // todo: parse post
                // todo: parse commentNodes
            });
            System.out.println(mostPopular.get(0));

        });

        // get posts from reddit EX: https://www.reddit.com/r/javascript/rising.json
        // get comments for each post EX: https://www.reddit.com/r/CryptoMoonShots/comments/lnq7nu.json
        //        https://www.reddit.com/r/CryptoMoonShots/comments/lnq7nu.json
        // first element of the array has the post
        // second element has the comments

        // save object into db
    }
}

package fgafa.design.systemdesign.miniTwitter;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TwitterPull {

    class Tweet{
        int tweetId;
        long timestamp;

        Tweet(int tweetId, long timestamp){
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }
    }

    private class Entity{
        List<Tweet> list; // in ascend order
        int index;

        Entity(List<Tweet> list){
            this.list = list;
            this.index = list.size() - 1;
        }

        long timestamp(){
            return list.get(index).timestamp;
        }

        int tweetId(){
            return list.get(index).tweetId;
        }
    }

    Map<Integer, Set<Integer>> followers;  // follower -> List<followee>
    Map<Integer, Queue<Tweet>> tweets;  // user -> Deque<Tweet>

    /** Initialize your data structure here. */
    public TwitterPull() {
        followers = new HashMap<>();
        tweets = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, System.nanoTime());

        tweets.putIfAbsent(userId, new LinkedList<>());
        addTweet(tweets.get(userId), tweet);
    }

    private void addTweet(Queue<Tweet> queue, Tweet tweet){
        if(queue.size() == 10){
            queue.poll();
        }

        queue.add(tweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        LinkedList<Integer> result = new LinkedList<>();

        PriorityQueue<Entity> maxHeap = new PriorityQueue<>((e1, e2) -> (Long.compare(e2.timestamp(), e1.timestamp())));

        if(tweets.containsKey(userId)){
            maxHeap.add(new Entity(new LinkedList<>(tweets.get(userId))));
        }

        if(followers.containsKey(userId)) {
            for (Integer followee : followers.get(userId)) {
                if (!tweets.containsKey(followee)) {
                    continue;
                }

                maxHeap.add(new Entity(new LinkedList<>(tweets.get(followee))));
            }
        }

        Entity entity;
        for(int i = 10; i > 0 && !maxHeap.isEmpty(); i--){
            entity = maxHeap.poll();

            result.addLast(entity.tweetId());

            if(entity.index > 0){
                entity.index--;
                maxHeap.add(entity);
            }
        }

        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId == followeeId){
            return;
        }

        followers.putIfAbsent(followerId, new HashSet<>());
        followers.get(followerId).add(followeeId);

    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId || !followers.containsKey(followerId)){
            return;
        }

        followers.get(followerId).remove(followeeId);
    }


    @Test
    public void test1(){
//        String[] cmd = {"Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"};
//        int[][] data = {{},{1,5},{1},{1,2},{2,6},{1},{1,2},{1}};

        postTweet(1, 5);
        //Assert.assertEquals("[5]", getNewsFeed(1).toString());
        follow(1, 2);
        postTweet(2, 6);
        Assert.assertEquals("[6, 5]", getNewsFeed(1).toString());
        unfollow(1, 2);
        Assert.assertEquals("[5]", getNewsFeed(1).toString());


    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
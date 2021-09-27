package design.systemdesign.miniTwitter;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * pull or push ?
 *
 * here it's push
 *
 */

public class TwitterPush {

    class Tweet{
        int tweetId;
        long timestamp;

        int userId;

        Tweet(int tweetId, int userId, long timestamp){
            this.tweetId = tweetId;
            this.userId = userId;
            this.timestamp = timestamp;
        }
    }

    class User{
        int userId;
        String name;
    }

    //follower -> followee
    Map<Integer, Set<Integer>> followers;  // store user and use's followers
    Map<Integer, Set<Integer>> followees;  // store user and use's followees
    Map<Integer, Deque<Tweet>> feeds;  // store the top 10 of user's tweet and his followee's
    Map<Integer, Deque<Tweet>> tweets; // store the top 10 of user's tweet himself


    /** Initialize your data structure here. */
    public TwitterPush() {
        followers = new HashMap<>();
        followees = new HashMap<>();
        feeds = new HashMap<>();
        tweets = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, userId, System.currentTimeMillis());
        addTweet(userId, tweets, tweet);
        addTweet(userId, feeds, tweet);

        if(followers.containsKey(userId)){
            for(Integer followerId : followers.get(userId)){
                addTweet(followerId, feeds, tweet);
            }
        }

    }

    private void addTweet(int userId, Map<Integer, Deque<Tweet>> map, Tweet tweet){
        map.putIfAbsent(userId, new LinkedList<>());
        Deque<Tweet> tweets = map.get(userId);

        if(tweets.size() == 10){
            tweets.pollFirst();
        }

        tweets.addLast(tweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        if(!feeds.containsKey(userId)) {
            return Collections.EMPTY_LIST;
        }

        if(!feeds.containsKey(userId) || feeds.get(userId).isEmpty()){
            return Collections.emptyList();
        }

        LinkedList<Integer> result = new LinkedList<>();
        for(Tweet t : feeds.get(userId)){
            result.addFirst(t.tweetId);
        }

//        List<Integer> result = feeds.get(userId).stream().map(t -> t.tweetId).collect(Collectors.toList());
//        Collections.reverse(result);

        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        followers.putIfAbsent(followeeId, new HashSet<>());
        followers.get(followeeId).add(followerId);

        followees.putIfAbsent(followerId, new HashSet<>());
        followees.get(followerId).add(followeeId);

        //add followeeId's tweets into followerId's feeds
        if(!tweets.containsKey(followeeId)){
            return;
        }

        List<Tweet> followerFeeds = new ArrayList<>(tweets.get(followeeId));

        if(feeds.containsKey(followerId)){
            followerFeeds.addAll(feeds.get(followerId));
        }

        Collections.sort(followerFeeds, ((t1, t2) -> Double.compare(t1.timestamp, t2.timestamp)));
        int size = followerFeeds.size();
        for(int i = Math.max(size - 10, 0); i < size; i++){
            addTweet(followerId, feeds, followerFeeds.get(i));
        }

    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {

        followers.get(followeeId).remove(followerId);
        followees.get(followerId).remove(followeeId);

        //remove followeeId's tweets from followerId's feeds
        if(!tweets.containsKey(followeeId) || !feeds.containsKey(followerId)){
            return;
        }

        PriorityQueue<Tweet> minHeap = new PriorityQueue<Tweet>(10, (t1, t2) -> Double.compare(t1.timestamp, t2.timestamp) );

        for(Tweet t : feeds.get(followerId)){
            if(t.userId != followeeId){
                minHeap.add(t);
            }
        }

        for(Integer followee : followees.get(followerId)){
            for(Tweet t : tweets.get(followee)){
                if(t.timestamp <= minHeap.peek().timestamp){
                    continue;
                }

                if(minHeap.size() == 10){
                    minHeap.poll();
                }
                minHeap.add(t);
            }
        }

        feeds.get(followerId).clear();
        for(int i = 10; i > 0 && !minHeap.isEmpty(); i--){
            addTweet(followerId, feeds, minHeap.poll());
        };

    }


    @Test public void test1(){
//        String[] cmd = {"Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"};
//        int[][] data = {{},{1,5},{1},{1,2},{2,6},{1},{1,2},{1}};

        postTweet(1, 5);
        Assert.assertEquals("[5]", getNewsFeed(1).toString());
        follow(1, 2);
        postTweet(2, 6);
        Assert.assertEquals("[6, 5]", getNewsFeed(1).toString());
        unfollow(1, 2);
        Assert.assertEquals("[5]", getNewsFeed(1).toString());


    }

    public static void main(String[] args){

        String[] cmd = {"Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"};
        int[][] data = {{},{1,5},{1},{1,2},{2,6},{1},{1,2},{1}};

        try {
            Class<TwitterPush> cls = (Class<TwitterPush>)Class.forName(cmd[0]);
            Object obj = cls.newInstance();

            Map<String, Method> methodMap = new HashMap<>();
            methodMap.put("postTweet", cls.getDeclaredMethod("postTweet", Integer.TYPE, Integer.TYPE));
            methodMap.put("getNewsFeed", cls.getDeclaredMethod("getNewsFeed", Integer.TYPE));
            methodMap.put("follow", cls.getDeclaredMethod("follow", Integer.TYPE, Integer.TYPE));
            methodMap.put("unfollow", cls.getDeclaredMethod("unfollow", Integer.TYPE, Integer.TYPE));


            for(int i = 0; i < cmd.length; i++){

                methodMap.get(cmd[i]).invoke(obj, data[0]);

                //Method method = Method.
            }

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

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
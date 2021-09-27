package design.systemdesign.miniTwitter;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TwitterPull2 {

    Map<Integer, Set<Integer>> followers;  // follower -> List<followee>
    Map<Integer, LinkedList<long[]>> tweets;  // user -> Deque<Tweet>

    /** Initialize your data structure here. */
    public TwitterPull2() {
        followers = new HashMap();
        tweets = new HashMap();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(tweets.containsKey(userId)){
            if(tweets.get(userId).size() == 10){
                tweets.get(userId).removeLast();
            }
        }else{
            tweets.put(userId, new LinkedList<>());
        }

        tweets.get(userId).addFirst(new long[]{tweetId, System.nanoTime()});
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>(10);

        PriorityQueue<LinkedList<long[]>> maxHeap = new PriorityQueue<>((l1, l2) -> (Long.compare(l2.getFirst()[1], l1.getFirst()[1])));

        if(tweets.containsKey(userId)){
            maxHeap.add(new LinkedList<>(tweets.get(userId)));
        }

        if(followers.containsKey(userId)) {
            for (Integer followee : followers.get(userId)) {
                if (tweets.containsKey(followee)) {
                    maxHeap.add(new LinkedList<>(tweets.get(followee)));
                }
            }
        }

        LinkedList<long[]> list;
        for(int i = 10; i > 0 && !maxHeap.isEmpty(); i--){
            list = maxHeap.poll();

            result.add((int)list.removeFirst()[0]);

            if(!list.isEmpty()){
                maxHeap.add(list);
            }
        }

        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId == followeeId){
            return;
        }

        followers.computeIfAbsent(followerId, x -> new HashSet<>()).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId)) {
            followers.get(followerId).remove(followeeId);
        }
    }


    @Test
    public void test1(){
//        String[] cmd = {"Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"};
//        int[][] data = {{},{1,5},{1},{1,2},{2,6},{1},{1,2},{1}};

        postTweet(1, 5);
        //Assert.assertEquals("[5]", getNewsFeed(1).toString());
        follow(1, 2);
        postTweet(2, 6);

        //System.out.println(getNewsFeed(1).toString());

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
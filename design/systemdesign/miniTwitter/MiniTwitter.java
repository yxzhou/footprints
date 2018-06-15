package fgafa.design.systemdesign.miniTwitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Implement a mini Twitter, support the following features
 * 1 postTweet(user_id, tweet_text)
 * 2 List<Tweet> getTimeline(user_id),  return the latest 10 tweets of this user posted, order by post time desc. 
 * 3 List<Tweet> getNewsFeed(user_id), return the latest 10 news feed of this user, that posted by the user and his friends. order by post time desc
 * 4 follow(from_user_id, to_user_id)
 * 5 unfollow(from_user_id, to_user_id)
 */

public class MiniTwitter {
    
    Map<String, User> users = new HashMap<>();
    Map<String, Set<String>> user2users = new HashMap<>();
    Map<String, Queue<Tweet>> user2Tweets = new HashMap<>();

    public Tweet postTweet(String user_id, String tweet_text){
        Tweet tweet = null;
        
        if(users.containsKey(user_id)){
            tweet = Tweet.create(user_id, tweet_text);
            
            if(!user2Tweets.containsKey(user_id)){
                user2Tweets.put(user_id, new LinkedList<>());
            }
            
            Queue<Tweet> queue = user2Tweets.get(user_id);
            
            while(queue.size() >= 10){
                queue.poll();
            }
            queue.add(tweet);
        }

        return tweet;
    }
   
    public List<Tweet> getTimeline(String user_id){
        return reverse(user2Tweets.get(user_id));
    }
    
    public List<Tweet> getNewsFeed(String user_id){
        List<Tweet> result = new ArrayList<>();
        
        result = reverse(user2Tweets.get(user_id));
        
        if(user2users.containsKey(user_id)){
            for(String followerId : user2users.get(user_id)){
                List<Tweet> followerTweets = reverse(user2Tweets.get(followerId));
                result = merge(result, followerTweets);
            }  
        }
        
        return result;
    }
    
    public void follow(String from_user_id, String to_user_id){
        if(users.containsKey(to_user_id) && users.containsKey(from_user_id)){

            if(!user2users.containsKey(to_user_id)){
                user2users.put(to_user_id, new HashSet<>());
            }
            
            user2users.get(to_user_id).add(from_user_id);
        }
    }
    
    public void unfollow(String from_user_id, String to_user_id){
        if(user2users.containsKey(to_user_id)){
            user2users.get(to_user_id).remove(from_user_id);
        }
    }
    
    private List<Tweet> reverse(Queue<Tweet> queue){
        List<Tweet> result = new LinkedList<>();

        if(null != queue){
            for(Tweet tweet : queue){
                result.add(0, tweet);
            }
        }
        
        return result;
    }
    
    /**
     *  return the latest 10 Tweet 
     * @param list1, ordered by Tweet.createtime desc
     * @param list2, ordered by Tweet.createtime desc
     * @return
     */
    private List<Tweet> merge(List<Tweet> list1, List<Tweet> list2){
        List<Tweet> result = new LinkedList<>();
        
        int i = 0;
        int j = 0;
        
        while( result.size() < 10 & i < list1.size() && j < list2.size() ){
            if(list1.get(i).createTime > list2.get(j).createTime){
                result.add(list1.get(i));
                i++;
            }else{
                result.add(list2.get(j));
                j++;
            }
        }
        
        while(result.size() < 10 & i < list1.size()){
            result.add(list1.get(i));
            i++;
        }
        
        while(result.size() < 10 & j < list2.size()){
            result.add(list2.get(j));
            j++;
        }
        
        return result;
    }

    
    class CreateTimeComparator implements Comparator<Tweet>{

        @Override
        public int compare(Tweet o1, Tweet o2) {
            return (int)(o1.createTime - o2.createTime);
        }
    }
    
    class User{
        public String user_id; //uuid
        public String name; 
    }
}

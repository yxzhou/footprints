package design.systemdesign.miniTwitter;

import java.util.UUID;

public class Tweet{
    public UUID id; //uuid
    public String user_id; //uuid
    public String value;
    public long createTime;
    
    public static Tweet create(String user_id, String value){
        Tweet tweet = new Tweet();
        tweet.id = UUID.randomUUID();
        tweet.user_id = user_id;
        tweet.value = value;
        tweet.createTime = System.currentTimeMillis();
        
        return tweet;
    }
}
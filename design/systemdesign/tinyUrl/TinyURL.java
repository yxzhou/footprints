package fgafa.design.systemdesign.tinyUrl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * Implement a URL shortener with the following methods:
 * shorten(url), which shortens the url into a six-character alphanumeric string, such as zLg6wl.
 * restore(short), which expands the shortened string into the original url. If no such shortened string exists, return null.
 *
 * Hint: What if we enter the same URL twice?
 *
 */

public class TinyURL {
    final static int size = 62;
    static char[] chars = new char[size];
    static{
        int i = 0;
        for(char c = '0'; c <= '9'; c++){
            chars[i++] = c;
        }
        for(char c = 'a'; c <= 'z'; c++){
            chars[i++] = c;
        }
        for(char c = 'A'; c <= 'Z'; c++){
            chars[i++] = c;
        }
    }

    static volatile AtomicLong count = new AtomicLong();

    static volatile ConcurrentHashMap<String, String> tiny2Long = new ConcurrentHashMap<>();
    static volatile ConcurrentHashMap<String, String> long2Tiny = new ConcurrentHashMap<>();

    public String shorten(String url){
        if(long2Tiny.containsKey(url)){
            return long2Tiny.get(url);
        }

        long id = count.getAndIncrement();

        StringBuilder result = new StringBuilder();
        while(id > 0){
            result.append(chars[(int)(id % size)]);
            id /= size;
        }

        String tinyURL = result.toString();

        tiny2Long.put(tinyURL, url);
        long2Tiny.put(url, tinyURL);

        return tinyURL;
    }

    public String restore(String tinyURL){
        if(tiny2Long.containsKey(tinyURL)){
            return tiny2Long.get(tinyURL);
        }

        throw new IllegalArgumentException("The tinyURL was not shorten before.");
    }
}

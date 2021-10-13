/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapReduce;

import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * Example1

Input:
[("apple",100), ("app",1200), ("app store",1200)]

Output: 
"a": [("app", 1200), ("app store", 1200), ("apple", 100)]
"ap": [("app", 1200), ("app store", 1200), ("apple", 100)]
"app": [("app", 1200), ("app store", 1200), ("apple", 100)]
"app ": [("app store", 1200)]
"app s": [("app store", 1200)]
"app st": [("app store", 1200)]
"app sto": [("app store", 1200)]
"app stor": [("app store", 1200)]
"app store": [("app store", 1200)]
"appl": [("apple", 100)]
"apple": [("apple", 100)]

**Example2**

Input:
[("apple",1200), ("app",1200), ("app store",1200)]

Output: 
"a": [("app", 1200), ("app store", 1200), ("apple", 1200)]
"ap": [("app", 1200), ("app store", 1200), ("apple", 1200)]
"app": [("app", 1200), ("app store", 1200), ("apple", 1200)]
"app ": [("app store", 1200)]
"app s": [("app store", 1200)]
"app st": [("app store", 1200)]
"app sto": [("app store", 1200)]
"app stor": [("app store", 1200)]
"app store": [("app store", 1200)]
"appl": [("apple", 1200)]
"apple": [("apple", 1200)]
 * 
 */
public class GoogleSuggestion {
    
    /** predefined class */
    class Document {
        public int count;
        public String content;
    }
     
    static class Pair {
        private String content;
        private int count;

        public Pair(String key, int value) {
            this.content = key;
            this.count = value;
        }

        public String getContent() {
            return this.content;
        }

        public int getCount() {
            return this.count;
        }
    }
    
    
    public static class Map {

        public void map(Document value,
                OutputCollector<String, Pair> output) {
            String word = value.content;
            String prefix;
            for (int r = 1; r <= word.length(); r++) {
                prefix = word.substring(0, r);

                output.collect(prefix, new Pair(word, value.count));
            }

        }
    }

    public static class Reduce {

        TreeSet<Pair> top10 = new TreeSet<>((p1, p2) -> {
            int diff = p1.getCount() - p2.getCount();
            return diff == 0 ? p2.getContent().compareTo(p1.getContent()) : diff;
        });

        public void setup() {
            // initialize your data structure here
            // top10 = new TreeSet<>( (p1, p2) -> {
            //     int diff = p1.getCount() - p2.getCount();
            //     return diff == 0? p2.getContent().compareTo(p1.getContent()) : diff;
            // } );
        }

        public void reduce(String key, Iterator<Pair> values, OutputCollector<String, Pair> output) {
            Pair curr;
            while (values.hasNext()) {
                curr = values.next();

                if (curr == null) {
                    continue;
                }

                if (top10.size() < 10) {
                    top10.add(curr);
                } else if (top10.first().getCount() <= curr.getCount()) {
                    top10.add(curr);
                    top10.pollFirst();
                }
            }

            while (!top10.isEmpty()) {
                output.collect(key, top10.pollLast());
            }
        }
    }
    
}

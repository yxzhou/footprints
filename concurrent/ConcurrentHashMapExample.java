package concurrent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
 
public class ConcurrentHashMapExample {
 
    public static void main(String[] args) {
 
        //ConcurrentHashMap
        Map<String,String> myMap = new ConcurrentHashMap<String,String>();
        myMap.put("1", "1");
        myMap.put("2", "1");
        myMap.put("3", "1");
        myMap.put("4", "1");
        myMap.put("5", "1");
        myMap.put("6", "1");
        System.out.println("ConcurrentHashMap before iterator: "+myMap);
        Iterator<String> it = myMap.keySet().iterator();
 
        while(it.hasNext()){
            String key = it.next();
            if(key.equals("3")) myMap.put(key+"new", "new3");
        }
        System.out.println("ConcurrentHashMap after iterator: "+myMap);
 
        //HashMap
        myMap = new HashMap<String,String>();
        myMap.put("1", "1");
        myMap.put("2", "1");
        myMap.put("3", "1");
        myMap.put("4", "1");
        myMap.put("5", "1");
        myMap.put("6", "1");
        System.out.println("HashMap before iterator: "+myMap);
        Iterator<String> it1 = myMap.keySet().iterator();
 
        while(it1.hasNext()){
            String key = it1.next();
            if(key.equals("3")){
              //myMap.put(key+"new", "new3");
              myMap.put(key, "new3");  // its actually modifying key value, so there is no structural modification in the HashMap.
              //it1.remove();   // it's ok
              
              //break;   // this is important.  
             }
        }
        System.out.println("HashMap after iterator: "+myMap);
        
        //
        testIterator();
    }
 
    
    public static void testIterator(){
      
      //HashMap
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put("1", "1");
        myMap.put("2", "1");
        myMap.put("3", "1");
        myMap.put("4", "1");
        myMap.put("5", "1");
        myMap.put("6", "1");
        System.out.println("HashMap before iterator: "+myMap);
        Iterator<String> it1 = myMap.keySet().iterator();

        while(it1.hasNext()){
            String key = it1.next();
            if(key.equals("3")) {
              //myMap.put(key+"new", "new3");
              //myMap.remove(key);
              it1.remove();
              //break;
            }  
        }
        System.out.println("HashMap after iterator: "+myMap);

        //HashSet
        Set<String> mySet = new HashSet<String>();
        mySet.add("1");
        mySet.add("2");
        mySet.add("3");
        mySet.add("4");
        mySet.add("5");
        mySet.add("6");
        System.out.println("HashSet before iterator: "+mySet);
        Iterator<String> setIterator = mySet.iterator();
        while(setIterator.hasNext()){
             Object o = setIterator.next();
             if(mySet.contains(o)){
                //setIterator.remove();   //it’s ok
                mySet.remove(o); 
             }
        }
        System.out.println("HashSet after for-loop: "+mySet);
        
        mySet = new HashSet<String>();
        mySet.add("1");
        mySet.add("2");
        mySet.add("3");
        mySet.add("4");
        mySet.add("5");
        mySet.add("6");
        for(Object o : mySet){
          if(mySet.contains(o)){
            mySet.remove(o);     // throw Concurrent
          }
        }
        System.out.println("HashSet after iterator: "+mySet);
    }
}
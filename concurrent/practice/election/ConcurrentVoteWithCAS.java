package fgafa.concurrent.practice.election;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ConcurrentVoteWithCAS implements Vote {

    public static volatile Map<Integer, Integer> votes = new ConcurrentHashMap<>();

    public static volatile Map<Integer, AtomicInteger> counts = new ConcurrentHashMap<>();

    //public static volatile List<Integer> fraud = new CopyOnWriteArrayList<>();

    @Override
    public boolean vote(int voterId, int candidateId){
        //todo
        if(votes.containsKey(voterId)){

//            while(true){
//                candidateId = votes.get(voterId);
//
//                if(!counts.containsKey(candidateId)){
//                    break;
//                }
//
//                AtomicLong atomicLong = counts.get(candidateId);
//
//                int counter = counts.get(candidateId).get();
//                if(counts.get(candidateId).compareAndSet(counter, counter - 1)){
//                    votes.put(voterId, -1);
//                }
//            }

            return false;
        }

        return true;
    }

    @Override
    public List<Integer> top(int k){
        //todo
        return Collections.emptyList();
    }

    @Override
    public List<Integer> getFrauds(){
        return votes.entrySet().stream().filter(entry -> entry.getValue() == -1).map(entry -> entry.getKey()).collect(Collectors.toList());
    }
}

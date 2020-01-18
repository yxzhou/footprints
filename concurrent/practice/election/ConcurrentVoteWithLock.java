package fgafa.concurrent.practice.election;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ConcurrentVoteWithLock implements Vote {

    public static volatile Map<Integer, Integer> votes = new ConcurrentHashMap<>();

    public static volatile Map<Integer, Integer> counts = new ConcurrentHashMap<>();

    //public static volatile List<Integer> fraud = new CopyOnWriteArrayList<>();

    Lock counterLock = new ReentrantLock();

    @Override
    public boolean vote(int voterId, int candidateId){
//todo
        if(votes.containsKey(voterId)){
            candidateId = votes.get(voterId);

            if(candidateId != -1 && counts.containsKey(candidateId)){
                counts.put(candidateId, counts.get(candidateId) - 1);

                votes.put(voterId, -1);
            }

            return false;
        }



        votes.put(voterId, candidateId);
        int counter = counts.containsKey(candidateId)? counts.get(candidateId) : 0;
        counts.put(candidateId, counter + 1);

        return true;
    }

    @Override
    public List<Integer> top(int k){
        PriorityQueue<Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(counts.size(),
                (entry1, entry2) -> Integer.compareUnsigned(entry2.getValue(), entry1.getValue()));

        maxHeap.addAll(counts.entrySet());

        List<Integer> result = new ArrayList<>(k);
        while(!maxHeap.isEmpty() && k > 0){
            result.add(maxHeap.poll().getKey());
        }

        return result;
    }

    @Override
    public List<Integer> getFrauds(){
        return votes.entrySet().stream().filter(entry -> entry.getValue() == -1).map(entry -> entry.getKey()).collect(Collectors.toList());
    }
}

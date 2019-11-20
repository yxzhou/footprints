package fgafa.concurrent.practice.election;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.stream.Collectors;


public class SingleThreadVote implements Vote {

    Map<Integer, Integer> votes = new HashMap<>();
    Map<Integer, Integer> counts = new HashMap<>();

    //Set<Integer> fraud = new HashSet<>();

    @Override
    public boolean vote(int voterId, int candidateId){
        if(votes.containsKey(voterId)){
            //fraud.add(voterId);

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

    /**
     *
     * refer to https://www.michaelpollmeier.com/selecting-top-k-items-from-a-list-efficiently-in-java-groovy
     *
     */
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

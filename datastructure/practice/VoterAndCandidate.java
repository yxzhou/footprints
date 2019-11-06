package fgafa.datastructure.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

import javafx.util.Pair;

/**
 *
 * This problem was asked by Uber.
 *
 * On election day, a voting machine writes data in the form (voter_id, candidate_id) to a text file.
 * Write a program that reads this file as a stream and returns the top 3 candidates at any given time.
 * If you find a voter voting more than once, report this as fraud.
 *
 * Thoughts
 *    Single thread
 *    Multiple thread
 *
 *    Cluster
 */

public class VoterAndCandidate {
    Set<Integer> voterIds = new HashSet<>();
    Map<Integer, Integer> candidateTickets = new HashMap<>();

    List<Integer> fraud = new LinkedList<>();

    public List<Integer> top(List<Pair<Integer, Integer>> votes, int k){


        votes.stream().forEach( pair -> {
            int voterid = pair.getKey();
            int candidate = pair.getValue();

            if(voterIds.contains(voterid)){
                fraud.add(voterid);
            }else{
                voterIds.add(voterid);

                if( candidateTickets.putIfAbsent(candidate, 1) != null ){
                    candidateTickets.put(candidate, candidateTickets.get(candidate) + 1);
                }

            }
        });

        return heapSelect(candidateTickets, k);

    }

    /**
     *
     * refer to https://www.michaelpollmeier.com/selecting-top-k-items-from-a-list-efficiently-in-java-groovy
     *
     */
    private List<Integer> heapSelect(Map<Integer, Integer> candidateTickets, int k){
        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>(candidateTickets.size(), (entry1, entry2) -> Integer.compareUnsigned(entry2.getValue(), entry1.getValue()));

        heap.addAll(candidateTickets.entrySet());

        List<Integer> result = new ArrayList<>(k);
        while(!heap.isEmpty() && k > 0){
            result.add(heap.poll().getKey());
        }

        return result;
    }

    private List<Integer> quickSelect(Map<Integer, Integer> candidateTickets, int k){
        //todo

        return Collections.emptyList();
    }
}

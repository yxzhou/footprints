package fgafa.concurrent.practice.election;

import java.util.List;

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
 *    Cluster / sharding
 */

public interface Vote {

    boolean vote(int voterId, int candidateId);

    List<Integer> top(int k);

    List<Integer> getFrauds();
}

package fgafa.topCoder.graph;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import fgafa.graph.traversal.TraversalLevalOrder;

/**
 *
 *  * http://community.topcoder.com/stat?c=problem_statement&pm=2288&rd=4725
 *
 * The KiloMan series has always had a consistent pattern: you start off with one
 * (rather weak) default weapon, and then defeat some number of bosses. When you defeat
 * a boss, you then acquire his weapon, which can then be used against other bosses, and
 * so on. Usually, each boss is weak against some weapon acquired from another boss, so
 * there is a recommended order in which to tackle the bosses. You have been playing for
 * a while and wonder exactly how efficient you can get at playing the game. Your metric
 * for efficiency is the total number of weapon shots fired to defeat all of the bosses.
 *
 * You have a chart in front of you detailing how much damage each weapon does to each
 * boss per shot, and you know how much health each boss has. When a boss's health is
 * reduced to 0 or less, he is defeated. You start off only with the Kilo Buster, which
 * does 1 damage per shot to any boss. The chart is represented as a String[], with the
 * ith element containing N one-digit numbers ('0'-'9'), detailing the damage done to
 * bosses 0, 1, 2, ..., N-1 by the weapon obtained from boss i, and the health is
 * represented as a int[], with the ith element representing the amount of health that
 * boss i has.
 *
 * Given a String[] damageChart representing all the weapon damages, and a int[]
 * bossHealth showing how much health each boss has, your method should return an int
 * which is the least number of shots that need to be fired to defeat all of the bosses.
 *
 *
 * Constraints
 * =================
 * -	damageChart will contain between 1 and 15 elements, inclusive.
 * -	each element of damageChart will be of the same length, which will be the same as the number of elements in damageChart.
 * -	each element of damageChart will contain only the characters '0'-'9'.
 * -	bossHealth will contain between 1 and 15 elements, inclusive.
 * -	damageChart and bossHealth will contain the same number of elements.
 * -	each element in bossHealth will be between 1 and 1000000, inclusive.
 *
 * 0)
 * {"070","500","140"}
 * {150,150,150}
 * Returns: 218
 * The best way to go is to use the KiloBuster to defeat boss 2 (indexed from 0), then use the weapon from boss 2 to defeat boss 1,
 * and then use the weapon from boss 1 to defeat boss 0. This leads to total damage of 150 + 38 + 30 = 218.
 *
 * 1)
 * {"1542", "7935", "1139", "8882"}
 * {150,150,150,150}
 * Returns: 205
 * Defeat boss 2, use his weapon to defeat boss 3, and then use boss 3's weapon to defeat both bosses 0 and 1, for a total shot count of 150 + 17 + 19 + 19 = 205. It would be more efficient to defeat boss 1 with his own weapon, but it would be cheating to get his weapon before defeating him.
 *
 * 2)
 * {"07", "40"}
 * {150,10}
 * Returns: 48
 *
 * 3)
 * {"198573618294842",
 * "159819849819205",
 * "698849290010992",
 * "000000000000000",
 * "139581938009384",
 * "158919111891911",
 * "182731827381787",
 * "135788359198718",
 * "187587819218927",
 * "185783759199192",
 * "857819038188122",
 * "897387187472737",
 * "159938981818247",
 * "128974182773177",
 * "135885818282838"}
 *
 * {157, 1984, 577, 3001, 2003, 2984, 5988, 190003, 9000, 102930, 5938, 1000000, 1000000, 5892, 38}
 *
 * Returns: 260445
 *
 * 4)
 * {"02111111", "10711111", "11071111", "11104111","41110111", "11111031", "11111107", "11111210"}
 * {28,28,28,28,28,28,28,28}
 * Returns: 92
 *
 * Thoughts:
 *   1 it's n boss, so the possible shot sequence is n!
 *   brute force,  recursive
 *
 *   2 similar with topological order or graph.traversal.TraversalLevelOrder
 *   2.1 calculate the "level" in some vertices, ( it's all vertices at the beginning )
 *   2.2 from the higher level to lower level,
 *      if it's 1 vertex in this level,  calculated directly
 *      if it's multiple vertices in this level, (there is 1 or more circle ), for every vertex each:
 *        loop 2.1 with the vertices in this level execept this one.
 *
 *
 */

//todo the detail implement and more thought
public class KiloManX {

    public int leastShots(String[] damageChart, int[] bossHealth){
        int length = bossHealth.length;

        boolean[] visited = new boolean[length];
        int[] maxDamages = new int[length];
        for(int i = 0; i < length; i++){
            maxDamages[i] = 1;
        }

        int[][] damages = new int[length][length];
        int[] indegrees = new int[length];
        for(int i = 0; i < length; i++){
            int j = 0;
            for(int damage : damageChart[i].toCharArray()){
                damages[i][j++] = damage;

                if(i != j && damage != 0){
                    indegrees[j]++;
                }
            }
        }

        int result = 0;
        int diff = Integer.MAX_VALUE;
        while(diff > 0) {
            // shot the bosses whose inDegree are 0
            diff = shotIndegree0Bosses(damages, bossHealth, indegrees, maxDamages, visited, null);

            // shot the bosses whose is in circle, find start from the potential bosses
            for (int boss = 0; boss < length; boss++) {
                if (visited[boss]) {
                    for (int nextBoss = 0; nextBoss < length; nextBoss++) {
                        if (!visited[nextBoss] && damages[boss][nextBoss] > 0) {
                            Set<Integer> inCircleBosses = findCircle_dfs(damages, visited, nextBoss);

                            int min = Integer.MAX_VALUE;
                            for (int inCircleBoss : inCircleBosses) {
                                indegrees[inCircleBoss]--;

                                min = Math.min(min,
                                        shotIndegree0Bosses(damages, bossHealth, Arrays.copyOf(indegrees, length),
                                                Arrays.copyOf(maxDamages, length), Arrays.copyOf(visited, length),
                                                inCircleBosses));

                                indegrees[inCircleBoss]++;
                            }

                            diff += min;
                            for (int inCircleBoss : inCircleBosses) {
                                visited[inCircleBoss] = true;
                            }
                        }
                    }
                }
            }

            result += diff;
        }

        return result;
    }

    private int shotIndegree0Bosses(int[][] damages, int[] bossHealth, int[] inDegrees, int[] maxDamages, boolean[] visited, Set<Integer> scope){
        int result = 0;
        int length = bossHealth.length;

        Queue<Integer> inDegree0sQueue = new LinkedList<>();
        for(int boss = 0; boss < length; boss++){
            if(inDegrees[boss] == 0 && (scope.contains(boss) || scope == null)){
                inDegree0sQueue.add(boss);
            }
        }

        while(!inDegree0sQueue.isEmpty()) {
            for (int i = inDegree0sQueue.size(); i > 0; i--) {
                int boss = inDegree0sQueue.poll();

                visited[boss] = true;
                result += bossHealth[boss] / maxDamages[boss] + (bossHealth[boss] % maxDamages[boss] == 0 ? 0 : 1);

                for (int j = 0; j < length; j++) {
                    if (boss != j && damages[boss][j] > 0 && !visited[j]) {
                        maxDamages[j] = Math.max(maxDamages[j], damages[boss][j]);
                        inDegrees[j]--;

                        if (inDegrees[j] == 0 && (scope.contains(boss) || scope == null)) {
                            inDegree0sQueue.add(j);
                        }
                    }
                }
            }
        }

        return result;
    }

    private Set<Integer> findCircle_dfs(int[][] damages, boolean[] visited, int boss){
        Set<Integer> inCircleBosses = new HashSet<>();
        inCircleBosses.add(boss);

        if(findCircle_dfs(damages, visited, boss, inCircleBosses)){
            return inCircleBosses;
        }

        return new HashSet<>();
    }

    private boolean findCircle_dfs(int[][] damages, boolean[] visited, int boss, Set<Integer> circleBosses){
        for(int nextBoss = 0; nextBoss < visited.length; nextBoss++ ){
            if(nextBoss != boss && !visited[nextBoss] && damages[boss][nextBoss] > 0){

                if(circleBosses.contains(nextBoss)){
                    return true;
                }

                circleBosses.add(nextBoss);
                if(findCircle_dfs(damages, visited, boss, circleBosses)){
                    return true;
                }
                circleBosses.remove(nextBoss);
            }
        }

        return false;
    }


    public static void main(String[] args){

        String[][] cases = {
                {"070","500","140"},
                {"1542", "7935", "1139", "8882"},
                {"07", "40"},

        };

    }
}

package graph.traversal;

import util.Misc;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Leetcode #399
 *
 * Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.
 *
 * Example:
 * Given a / b = 2.0, b / c = 3.0.
 * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
 * return [6.0, 0.5, -1.0, 1.0, -1.0 ].
 *
 * The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.
 *
 * According to the example above:
 *
 * equations = [ ["a", "b"], ["b", "c"] ],
 * values = [2.0, 3.0],
 * queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
 *
 *
 * The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
 *
 */

public class EvaluateDivision {

    @Test public void test(){


        List<List<String>> equations = Misc.convert(new String[][]{{"x1","x2"}, {"x2","x3"}, {"x3","x4"}, {"x4","x5"}});
        double[] values = {3.0,4.0,5.0,6.0};
        List<List<String>> queries = Misc.convert(new String[][]{{"x1","x5"}, {"x5","x2"}, {"x2","x4"}, {"x2","x2"}, {"x2","x9"}, {"x9","x9"}});

        double[] expects = new double[]{360.00000,0.00833,20.00000,1.00000,-1.00000,-1.00000};
        Assert.assertEquals( Misc.array2String(expects), Misc.array2String(calcEquation(equations, values, queries)));
    }


    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        String a;
        String b;
        double v;
        for(int i = 0, n = values.length; i < n; i++){
            a = equations.get(i).get(0);
            b = equations.get(i).get(1);
            v = values[i];

            graph.computeIfAbsent(a, k -> new HashMap<>()).put(b, v);
            graph.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1 / v);
        }

        int n = queries.size();
        double[] result = new double[n];
        Set<String> visited = new HashSet<>(); //default all are false
        for(int i = 0; i < n; i++ ){
            a = queries.get(i).get(0);
            b = queries.get(i).get(1);

            if(!graph.containsKey(a) || !graph.containsKey(b) ){
                result[i] = -1;
                continue;
            }

            if(a.equals(b) ){
                result[i] = 1;
                continue;
            }

            result[i] = dfs(graph, a, b, 1, visited);
        }

        return result;
    }

    // simple dfs, optimization with Dijkstra
    private double dfs(Map<String, Map<String, Double>> graph, String s, String e, double x, Set<String> visited){
        visited.add(s);

        double max = -1; //all the value is postive
        Map<String, Double> nexts = graph.get(s);

        if(nexts.containsKey(e)){
            max = x * nexts.get(e);
        }else{
            double r;
            for(String next : nexts.keySet()){
                if(!visited.contains(next) ){
                    r = dfs(graph, next, e, 1, visited);

                    if(r != -1){
                        max = Math.max(max, x * nexts.get(next) * r);
                    }
                }
            }
        }

        //backtracking
        visited.remove(s);

        return max;
    }

}

package graph.traversal;

import util.Misc;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Leetcode #399
 * _https://www.lintcode.com/problem/1257
 *
 * Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real
 * number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.
 *
 * Example:
 * equations = [ ["a", "b"], ["b", "c"] ],
 * values = [2.0, 3.0],
 * queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ]
 * return [6.0, 0.5, -1.0, 1.0, -1.0 ]
 *
 * Notes:
 * The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is
 * no contradiction.
 *
 * Thoughts:
 * 1) case 1, to equation a / b = x, the query maybe a / b, or b / a, 
 *    case 2, to equation a / b = x, b / c = y,  the query maybe c / a, 
 *    so it need store the edge a-b and b-a both. 
 * 2) based on the above example, 
 *    a/e = -1.0, because e is not found 
 *    a/a = 1.0
 *    x/x = -1.0 
 *    the rule to query (a, b) is:
 *      if a or b is not the vertex, return -1.0
 *      or if a equals b, return 1.0
 *      or try to find a path from a to b, 
 * 
 */

public class EvaluateDivision {
    
    /**
     * @param equations: 
     * @param values: 
     * @param queries: 
     * @return: return a double type array
     */
    public double[] calcEquation_2(List<List<String>> equations, double[] values, List<List<String>> queries) {
        
        Map<String, Map<String, Double>> operands = new HashMap<>();

        List<String> pair;
        String a;
        String b;
        for(int i = 0, n = equations.size(); i < n; i++ ){
            pair = equations.get(i);
            a = pair.get(0);
            b = pair.get(1);

            operands.computeIfAbsent(a, x -> new HashMap<>()).put(b, values[i]);
            operands.computeIfAbsent(b, x -> new HashMap<>()).put(a, values[i] == 0 ? Double.NaN : 1 / values[i]);
        
        }

        double[] result = new double[queries.size()];
        Set<String> visited = new HashSet<>();

        for(int i = 0; i < queries.size(); i++){
            pair = queries.get(i);
            a = pair.get(0);
            b = pair.get(1);

            if(!operands.containsKey(a) || !operands.containsKey(b)){
                result[i] = -1.0;
            }else if(a.equals(b)){
                result[i] = 1.0;
            }else{
                result[i] = dfs(operands, a, b, visited, 1);

                if ( Double.isNaN(result[i]) ){
                    result[i] = -1.0;
                } 
            }
        }

        return result;
    }

    private double dfs(Map<String, Map<String, Double>> operands, String start, String end, Set<String> visited, double value){
        Map<String, Double> nexts = operands.get(start);

        if(nexts.containsKey(end)){
            Double x = nexts.get(end);
            return Double.isNaN(x)? x : value * nexts.get(end);
        }

        double result = Double.NaN;
        visited.add(start);

        for(String next : nexts.keySet() ){
            if( !visited.contains(next) ){

                result = dfs(operands, next, end, visited, value * nexts.get(next));
                if( !Double.isNaN( result ) ){
                    break;
                }
            }
        }
        
        visited.remove(start);
        return result;
    }
    
    public static void main(String[] args){
        
        String[][][][] inputs = {
            {
                {{"a","b"}, {"c","d"}},  //equations
                {{"a","c"}, {"b","d"}, {"b","a"}, {"d","c"}} //queries
            },
            {
                {{"a","b"}, {"b","c"}},
                {{"a","c"}, {"b","a"}, {"a","e"}, {"a","a"}, {"x","x"}}
            },
            {
                {{"x1","x2"}, {"x2","x3"}, {"x3","x4"}, {"x4","x5"}},
                {{"x1","x5"}, {"x5","x2"}, {"x2","x4"}, {"x2","x2"}, {"x2","x9"}, {"x9","x9"}}
            }
            
        };
        
        double[][][] doubles = {
            {
                {1.0,1.0},
                {-1.00,-1.00,1.00,1.00}
            
            },
            {
                {2.0,3.0},
                {6.00,0.50,-1.00,1.00,-1.00}
            },
            {
                {3.0,4.0,5.0,6.0},
                {360.00000,0.00833,20.00000,1.00000,-1.00000,-1.00000}
            },
        }; 

        EvaluateDivision sv = new EvaluateDivision();

        for(int i = 0; i < inputs.length; i++){
            System.out.println(String.format("\n %s \n%s  %s", Misc.array2String(inputs[i][0]), Misc.array2String(doubles[i][0]), Misc.array2String(inputs[i][1]) ));

            Assert.assertArrayEquals("", doubles[i][1], sv.calcEquation_2( Misc.convert(inputs[i][0]), doubles[i][0], Misc.convert(inputs[i][1]) ), 0.00001); 
            
        }
        
    }
}

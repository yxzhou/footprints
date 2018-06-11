package fgafa.todo.micoo;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * A two fork tree with n nodes and a root node of 1. Each edge is described by two vertices x[i] and y[i],
 * and the weight of each point is described by d[i].
 * We define the weights of all nodes from the root node to the leaf node path to be x. Find the maximum value of x % 1e9+7.
 *
 */

public class MaximumProductPath {

    public int maxProduct(int[] x, int[] y, int[] d){
        if(null == x || 0 == x.length || null == y || x.length != y.length || null == d || 0 == d.length){
            return 0;
        }

        HashMap<Integer, List<Integer>> edges = new HashMap<>(x.length);
        for(int i = 0; i < x.length; i++){
            if(!edges.containsKey(x[i])){
                edges.put(x[i], new ArrayList<>());
            }

            edges.get(x[i]).add(y[i]);
        }

        helper(1, 1, d, edges);

        return maxProduct;
    }

    static int maxProduct = 0;
    static final int MODE = 1000000007; //1e9+7;

    private void helper(int curr, int product, int[] d, Map<Integer, List<Integer>> edges){
        product = (product * (d[curr] % MODE)) % MODE;

        if(!edges.containsKey(curr)){  //when it's leaf
            maxProduct = Math.max(maxProduct, product);
        }else{
            for(Integer next : edges.get(curr)){
                helper(next, product, d, edges);
            }
        }
    }

}

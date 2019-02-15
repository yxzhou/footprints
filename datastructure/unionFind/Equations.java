package fgafa.datastructure.unionFind;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * Given an array equations of strings that represent relationships between variables, each string equations[i] has length 4 and takes one of two different forms: "a==b" or "a!=b".  Here, a and b are lowercase letters (not necessarily different) that represent one-letter variable names.
 *
 * Return true if and only if it is possible to assign integers to variable names so as to satisfy all the given equations.
 *
 *
 *
 * Example 1:
 *
 * Input: ["a==b","b!=a"]
 * Output: false
 * Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.  There is no way to assign the variables to satisfy both equations.
 * Example 2:
 *
 * Input: ["b==a","a==b"]
 * Output: true
 * Explanation: We could assign a = 1 and b = 1 to satisfy both equations.
 * Example 3:
 *
 * Input: ["a==b","b==c","a==c"]
 * Output: true
 * Example 4:
 *
 * Input: ["a==b","b!=c","c==a"]
 * Output: false
 * Example 5:
 *
 * Input: ["c==c","b==d","x!=z"]
 * Output: true
 *
 *
 * Note:
 *
 * 1 <= equations.length <= 500
 * equations[i].length == 4
 * equations[i][0] and equations[i][3] are lowercase letters
 * equations[i][1] is either '=' or '!'
 * equations[i][2] is '='
 *
 */

public class Equations {


    public boolean equationsPossible(String[] equations) {

        if(equations == null){
            return true;
        }

        int[] parentIds = new int[26]; //default all are 0
        for(int i = 0; i < parentIds.length; i++){
            parentIds[i] = -1;
        }

        for(String equation : equations){
            boolean isEqual = (equation.charAt(1) == '=');
            int c1 = equation.charAt(0) - 'a';
            int c2 = equation.charAt(3) - 'a';

            if(isEqual){
                connect(parentIds, c1, c2);
            }
        }

        for(String equation : equations){
            boolean isEqual = (equation.charAt(1) == '=');
            int c1 = equation.charAt(0) - 'a';
            int c2 = equation.charAt(3) - 'a';

            if(!isEqual){
                parentIds[c1] = parentIds[c1] == -1 ? c1 : parentIds[c1];
                parentIds[c2] = parentIds[c2] == -1 ? c2 : parentIds[c2];

                if(parentIds[c1] == parentIds[c2]){
                    return false;
                }
            }
        }

        return true;
    }

    private void connect(int[] parentIds, int c1, int c2){
        if(parentIds[c1] == -1 && parentIds[c2] == -1 ){
            parentIds[c1] = c1;
            parentIds[c2] = c1;
        } else if(parentIds[c1] == -1){
            parentIds[c1] = parentIds[c2];
        }else if(parentIds[c2] == -1){
            parentIds[c2] = parentIds[c1];
        }else{
            for(int i = 0; i < parentIds.length; i++){
                if(parentIds[i] == parentIds[c2]){
                    parentIds[i] = parentIds[c1];
                }
            }
        }
    }

    @Test
    public void test(){

//        Assert.assertEquals(false, equationsPossible(new String[]{"a==b","b!=a"}));
//        Assert.assertEquals(true, equationsPossible(new String[]{"b==a","a==b"}));
//        Assert.assertEquals(true, equationsPossible(new String[]{"a==b","b==c","a==c"}));
        Assert.assertEquals(false, equationsPossible(new String[]{"a==b","b!=c","c==a"}));
        Assert.assertEquals(true, equationsPossible(new String[]{"c==c","b==d","x!=z"}));

    }


}
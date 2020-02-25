package fgafa.datastructure.unionFind;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

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


    //union find
    public boolean equationsPossible(String[] equations) {
        int[] groupIds = new int[26];
        Arrays.fill(groupIds, -1);

        for(String s : equations){
            if(s.charAt(1) == '='){
                union(s.charAt(0) - 'a', s.charAt(3) - 'a', groupIds);
            }
        }

        int c1;
        int c2;
        for(String s : equations){
            if (s.charAt(1) == '!' ){
                c1 = s.charAt(0) - 'a';
                c2 = s.charAt(3) - 'a';
                c1 = (groupIds[c1]  == -1 ? c1 : groupIds[c1]);
                c2 = (groupIds[c2]  == -1 ? c2 : groupIds[c2]);

                if(c1 == c2){
                    return false;
                }
            }
        }

        return true;
    }

    private void union(int c1, int c2, int[] groupIds){

        if(groupIds[c1] == -1 && groupIds[c2] == -1){
            groupIds[c1] = c1;
            groupIds[c2] = c1;
        }else if(groupIds[c1] == -1){
            groupIds[c1] = groupIds[c2];
        }else if(groupIds[c2] == -1){
            groupIds[c2] = groupIds[c1];
        }else if(groupIds[c1] != groupIds[c2]){
            int tmp = groupIds[c2];
            for(int i = 0; i < 26; i++){
                if(groupIds[i] == tmp){
                    groupIds[i] = groupIds[c1];
                }
            }
        }

    }

    @Test
    public void test(){

//        Assert.assertEquals(false, equationsPossible(new String[]{"a==b","b!=a"}));
//        Assert.assertEquals(true, equationsPossible(new String[]{"b==a","a==b"}));
//        Assert.assertEquals(true, equationsPossible(new String[]{"a==b","b==c","a==c"}));
//        Assert.assertEquals(false, equationsPossible(new String[]{"a==b","b!=c","c==a"}));
//        Assert.assertEquals(true, equationsPossible(new String[]{"c==c","b==d","x!=z"}));
        Assert.assertEquals(false, equationsPossible(new String[]{"a==b","e==c","b==c","a!=e"}));
    }


}
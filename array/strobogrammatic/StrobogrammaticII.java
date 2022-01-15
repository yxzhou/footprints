package array.strobogrammatic;

import java.util.LinkedList;
import java.util.List;

import util.Misc;

/**
 * _https://www.lintcode.com/problem/776
 * 
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down). For
 * example, the numbers "69", "88", and "818" are all strobogrammatic.
 * 
 *  Find all strobogrammatic numbers that are of length = n.
 *  
 *  For example,
 *  Given n = 2, return ["11","69","88","96"].
 *  Given n = 1, return ["0","1","8"].
 *
 */

public class StrobogrammaticII {
    
    char[] selfable = {'0', '1', '8'}; // the digit in the middle, it's same after rotating,
    char[] mirrorable = {'0', '1', '6', '8', '9'}; // the digit except in the middle, note, 0 can't be in the left-most
    char[] mirrors = {'0', '1', '#', '#', '#', '#', '9', '#', '8', '6'};
    
    /**
     *
     * @param n: the length of strobogrammatic number
     * @return: All strobogrammatic numbers
     */
    public List<String> findStrobogrammatic(int n) {
        List<String> result = new LinkedList<>();

        dfs(new char[n], 0, n - 1, result );

        return result;
    }

    private void dfs(char[] arr, int l, int r, List<String> result){
        if(l > r){
            result.add(String.valueOf(arr));
            return;
        }

        if(l == r){
            for(char x : selfable){
                arr[l] = x;
                dfs(arr, l + 1, r - 1, result);
            }
            return;
        }

        //l < r
        for(char x : mirrorable){
            if(l == 0 && x == '0'){
                continue;
            }

            arr[l] = x;
            arr[r] = mirrors[x - '0'];
            dfs(arr, l + 1, r - 1, result);
        }
        
    }
    
    public static void main(String[] args){

        StrobogrammaticII sv = new StrobogrammaticII();
        
        for(int i = 0; i < 9; i++){
            System.out.println(String.format("Input %d ", i)); 
            Misc.printArrayList(sv.findStrobogrammatic(i));
        }
    }
}

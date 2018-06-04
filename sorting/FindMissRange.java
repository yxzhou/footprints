package fgafa.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Given a sorted integer array where the range of elements are [lower, upper] inclusive, return its missing ranges.
    For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return ["2", "4->49", "51->74", "76->99"].
 *
 */

public class FindMissRange {

    public List<String> findMissingRanges(int[] A, int lower, int upper) {  
        List<String> result = new ArrayList<>();
        
        int end = A.length - 1;
        if(null == A || lower >= upper || lower < 0 || lower >= end){
            return result;
        }
        
        int left, right;
        for(int i = 0; i < end; i++){
            if(A[i] <= lower){
                continue;
            }
            if(A[i] >= upper){
                break;
            }
            
            left = A[i] + 1;
            right = A[i + 1] - 1;
                        
            if(left == right){
                result.add(String.valueOf(left));
            }else if( left < right){
                result.add(left + "->" + right);
            }
        }
        
        if(A[end] == upper - 1){
            result.add(String.valueOf(upper));
        }else if(A[end] < upper - 1){
            result.add((A[end] + 1) + "->" + upper);
        }
        
        return result;
    }
}

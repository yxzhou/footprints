package fgafa.dailyCoding;

/**
 *
 * Given an array of strictly the characters 'R', 'G', and 'B', segregate the values of the array so that all the Rs come first, the Gs come second, and the Bs come last. You can only swap elements of the array.

 Do this in linear time and in-place.

 For example, given the array ['G', 'B', 'R', 'R', 'B', 'R', 'G'], it should become ['R', 'R', 'R', 'G', 'G', 'B', 'B'].
 *
 */

public class SortColors {

    /**
     * sort in place, R-0, G-1, B-2
     *
     * @param colors
     */
    public void sort(int[] colors){
        if(null == colors){
            return;
        }

        for(int left = 0, right = colors.length - 1, i = 0; i <= right; i++){
            if(colors[i] == 0){
                swap(colors, i, left);
                left++;
            }else if(colors[i] == 2){
                swap(colors, i, right);
                right--;
                i--;
            }
        }

    }

    private void swap(int[] colors, int i, int j){
        int tmp = colors[i];
        colors[i] = colors[j];
        colors[j] = tmp;
    }

}

package sweepLine;

import org.junit.Assert;

import java.util.*;

/**
 * _https://leetcode.com/problems/rectangle-area-ii/
 * 
 * We are given a list of (axis-aligned) rectangles. Each rectangle[i] = [x1, y1, x2, y2] denotes the i_th rectangle, 
 * where (x1, y1) are the coordinates of the bottom-left corner, (x2, y2) are the coordinates of the top-right corner.
 *
 * Find the total area covered by all rectangles in the plane. Since the answer may be too large, 
 * return it modulo 10^9 + 7.
 *
 * Example 1: 
 * Input: [[0,0,2,2],[1,0,2,3],[1,0,3,1]] 
 * Output: 6 
 *
 * Example 2: 
 * Input: [[0,0,1000000000,1000000000]] 
 * Output: 49 
 * Explanation: The answer is 10^18 modulo (10^9 + 7), which is (10^9)^2 = (-7)^2 = 49.
 *
 * Note: 
 *   1 <= rectangles.length <= 200 
 *   rectanges[i].length = 4 
 *   0 <= rectangles[i][j] <= 10^9 
 *   The total area covered by all rectangles will never exceed 2^63 - 1 and thus will fit in a 64-bit signed integer.
 *
 *
 */

public class RectangleAreaII {

    /**
     *  sweep line
     *
     *  define n = rectangles.length, it's [1, 200];  m = rectangles[i][j], it's [0, 10^9]
     *  Time complexity O( n * n * logn ), Space O(n)
     */
    public int rectangleArea(int[][] rectangles) {
        final int BASE = (int)1e9 + 7; //1_000_000_000 + 7; 
        
        final int LEFT = 1;
        final int RIGHT = -1;

        int[][] events = new int[rectangles.length * 2][4];
        int i = 0;
        for(int[] r : rectangles){
            events[i++] = new int[]{r[0], LEFT, r[1], r[3]}; // rectangle's left side line {minX, flag, minY, maxY}
            events[i++] = new int[]{r[2], RIGHT, r[1], r[3]}; // rectangle's right side line {maxX, flag, minY, maxY}
        }

        //Arrays.sort(events, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0])); wrong for case: rectangles [166, 0, 166, 808]
        //Arrays.sort(events, (a, b) -> a[0] == b[0]? Integer.compare(b[1], a[1]) : Integer.compare(a[0], b[0]));
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0])); // LEFT is added before RIGHT

        long result = 0;
        List<int[]> yPairs = new ArrayList<>(); //{minY, maxY}
        int preX = events[0][0];
        
        for(int[] event : events){

            if(event[0] > preX){
                result =( result + ((event[0] - preX) * getHeight(yPairs)) % BASE ) % BASE;
                
                preX = event[0];
            }

            if(event[1] == LEFT) {
                yPairs.add(new int[]{event[2], event[3]});

                Collections.sort(yPairs, (a, b) -> Integer.compare(a[0], b[0]));
            }else{ // RIGHT               
                
                Iterator<int[]> itr = yPairs.iterator();
                int[] pair;
                while(itr.hasNext()){
                    pair = itr.next();
                    
                    if(pair[0] == event[2] && pair[1] == event[3]){
                        itr.remove();
                        break;
                    }
                }
            }

        }

        return (int)result;
    }
    
    private long getHeight(List<int[]> yPairs){
        long height = 0;
        int preY = 0;
        
        for(int[] pair : yPairs){
            preY = Math.max(preY, pair[0]);
            height += Math.max(pair[1] - preY, 0);
            preY = Math.max(preY, pair[1]);
        }
        
        return height;
    }
    
    /**
     *  sweep line
     *
     *  define n = rectangles.length, it's [1, 200];  m = rectangles[i][j], it's [0, 10^9]
     *  Time complexity O( n * n ), Space O(n)
     */
    public int rectangleArea_n(int[][] rectangles) {

        final int BASE = (int)1e9 + 7; //1_000_000_000 + 7; 
        int LEFT = 1;
        int RIGHT = 0;
        
        int[][] events = new int[rectangles.length << 1][4];
        int[] rect;
        for(int i = 0, j = 0; j < rectangles.length; j++){
            rect = rectangles[j];
            events[i++] = new int[]{rect[0], LEFT, j}; // rectangle's left side line {minX, flag, rectangle index}
            events[i++] = new int[]{rect[2], RIGHT, j}; // rectangle's right side line {maxX, flag, minY, rectangle index}
        }

        //Arrays.sort(events, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0])); wrong for case: rectangles [166, 0, 166, 808]
        //Arrays.sort(events, (a, b) -> a[0] == b[0]? Integer.compare(b[1], a[1]) : Integer.compare(a[0], b[0]));
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0])); // LEFT is added before RIGHT

        long result = 0;
        TreeMap<Integer, Set<Integer>> yPairs = new TreeMap<>(); //TreeMap<minY, rectangle index>
        int preX = events[0][0];
        
        for(int[] event : events){
            if( event[0] > preX ){                           
                result =( result + ((event[0] - preX) * getHeight(yPairs, rectangles)) % BASE ) % BASE;
                
                preX = event[0];
            }

            if(event[1] == LEFT) {
                yPairs.computeIfAbsent(rectangles[event[2]][1], o -> new HashSet<>()).add(event[2]);
            }else{
                
                yPairs.get(rectangles[event[2]][1]).remove(event[2]);

            }
        }

        return (int)result;
    }
    
    private long getHeight(TreeMap<Integer, Set<Integer>> yPairs, int[][] rectangles){
        long height = 0;
        int preY = 0;
        
        for(Map.Entry<Integer, Set<Integer>> entry : yPairs.entrySet()){ 
            for(int id : entry.getValue()){
                preY = Math.max(preY, rectangles[id][1] );
                            
                height += Math.max(rectangles[id][3] - preY, 0);
                preY = Math.max(preY, rectangles[id][3]);
            }
        }
        
        return height;
    }

    public int rectangleArea_x(int[][] rectangles) {

        final int BASE = (int)1e9 + 7; //1_000_000_000 + 7; 
        int LEFT = 1;
        int RIGHT = -1;
        
        TreeMap<Integer, List<int[]>> events = new TreeMap<>();//map, x value ot a list of rectangles
        int[] rect;
        for(int i = 0; i < rectangles.length; i++){
            rect = rectangles[i];
            events.computeIfAbsent( rect[0], o -> new ArrayList<>()).add(new int[]{i, LEFT}); // rectangle's left side line 
            events.computeIfAbsent( rect[2], o -> new ArrayList<>()).add(new int[]{i, RIGHT}); // rectangle's right side line 
        }

        long result = 0;
        TreeMap<Integer, Integer> ymap = new TreeMap<>(); //map: y value to rectangle counts
        int preX = 0;
        for(int x : events.keySet()){
             
            int py = 0;
            int count = 0;
            long height = 0;

            for(int y : ymap.keySet()){
                if(count > 0){
                    height += y - py;
                }
                
                py = y;
                count += ymap.get(y);
            }

            result =( result + ((x - preX) * height) % BASE ) % BASE;

            preX = x;

            //
            for(int[] m : events.get(x)){ //update y values
//                if(m[1] == LEFT ){
//                    ymap.put(rectangles[m[0]][1], ymap.getOrDefault(rectangles[m[0]][1], 0) + LEFT);
//                    ymap.put(rectangles[m[0]][3], ymap.getOrDefault(rectangles[m[0]][3], 0) - LEFT);
//                }else { //RIGHT
//                    ymap.put(rectangles[m[0]][1], ymap.getOrDefault(rectangles[m[0]][1], 0) + RIGHT);
//                    ymap.put(rectangles[m[0]][3], ymap.getOrDefault(rectangles[m[0]][3], 0) - RIGHT);
//                }
                
                ymap.put(rectangles[m[0]][1], ymap.getOrDefault(rectangles[m[0]][1], 0) + m[1]);
                ymap.put(rectangles[m[0]][3], ymap.getOrDefault(rectangles[m[0]][3], 0) - m[1]);
                //ymap.merge(rectangles[m[0]][1], m[1], Integer::sum);
                //ymap.merge(rectangles[m[0]][3], -m[1], Integer::sum);
                
                //clean the key if count == 0
//                if(ymap.get(rectangles[m[0]][1]) == 0){
//                    ymap.remove(rectangles[m[0]][1]);
//                }
//                if(ymap.get(rectangles[m[0]][3]) == 0){
//                    ymap.remove(rectangles[m[0]][3]);
//                }
            }

        }

        return (int)result;
    }
    
    
    public static void main(String[] args){
        RectangleAreaII sv = new RectangleAreaII();
        
        int mod = 1_000_000_007;

        System.out.println(mod);


        int[][][][] inputs = {
            //{rectangles, expect area}
            {
                {{0, 0, 2, 2}, {1, 0, 2, 3}, {1, 0, 3, 1}},
                {{6}}
            },
            {
                {{0, 0, 1_000_000_000, 1_000_000_000}},
                {{49}}
            },
            {
                {{471, 0, 947, 999}, {780, 0, 823, 320}, {868, 0, 948, 538}, {907, 0, 911, 673}, {929, 0, 952, 596}, {458, 0, 889, 669}, {156, 0, 364, 754}, {900, 0, 973, 236}, {406, 0, 620, 454}, {773, 0, 946, 538}, {407, 0, 834, 23}, {759, 0, 858, 526}, {431, 0, 776, 599}, {969, 0, 979, 30}, {642, 0, 737, 339}, {239, 0, 448, 183}, {260, 0, 517, 903}, {14, 0, 674, 976}, {251, 0, 850, 112}, {57, 0, 794, 395}, {595, 0, 728, 149}, {970, 0, 989, 36}, {496, 0, 954, 791}, {447, 0, 832, 805}, {829, 0, 939, 100}, {169, 0, 568, 501}, {704, 0, 969, 411}, {607, 0, 609, 221}, {935, 0, 953, 437}, {47, 0, 670, 130}, {794, 0, 799, 230}, {943, 0, 959, 90}, {332, 0, 337, 732}, {123, 0, 228, 344}, {281, 0, 487, 598}, {381, 0, 732, 443}, {235, 0, 391, 548}, {646, 0, 930, 20}, {219, 0, 675, 95}, {8, 0, 212, 227}, {138, 0, 704, 658}, {368, 0, 782, 707}, {810, 0, 826, 957}, {543, 0, 697, 654}, {887, 0, 986, 180}, {837, 0, 900, 228}, {280, 0, 391, 331}, {180, 0, 229, 42}, {201, 0, 489, 687}, {648, 0, 680, 732}, {228, 0, 630, 922}, {886, 0, 960, 56}, {946, 0, 955, 522}, {903, 0, 992, 464}, {557, 0, 860, 38}, {89, 0, 268, 642}, {669, 0, 774, 185}, {1, 0, 724, 374}, {395, 0, 923, 782}, {82, 0, 230, 550}, {166, 0, 166, 808}, {441, 0, 644, 435}, {497, 0, 823, 224}, {372, 0, 973, 556}, {188, 0, 846, 127}, {226, 0, 396, 535}, {869, 0, 945, 575}, {406, 0, 526, 795}, {781, 0, 795, 569}, {563, 0, 831, 991}, {466, 0, 486, 641}, {274, 0, 855, 529}, {61, 0, 819, 364}, {285, 0, 421, 101}, {193, 0, 950, 748}, {320, 0, 655, 836}, {207, 0, 627, 945}, {782, 0, 899, 56}, {578, 0, 970, 913}, {499, 0, 684, 205}, {490, 0, 877, 16}, {483, 0, 668, 915}, {364, 0, 741, 16}},
                {{957901}}
            }
        };

        for(int[][][] input : inputs){
            Assert.assertEquals(input[1][0][0], sv.rectangleArea(input[0]));
            
            Assert.assertEquals(input[1][0][0], sv.rectangleArea_n(input[0]));
            
            Assert.assertEquals(input[1][0][0], sv.rectangleArea_x(input[0]));
        }
    }
}

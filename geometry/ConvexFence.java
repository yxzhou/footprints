/*
 */
package geometry;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import org.junit.Assert;

/**
 * There are some trees, where each tree is represented by (x,y) coordinate in a two-dimensional garden. Your job is to
 * fence the entire garden using the minimum length of rope as it is expensive. The garden is well fenced only if all
 * the trees are enclosed. Your task is to help find the coordinates of trees which are exactly located on the fence
 * perimeter.
 * 
 * Notes:
 *   All trees should be enclosed together. You cannot cut the rope to enclose trees that will separate them in more than one group.
 *   All input integers will range from 0 to 100.
 *   The garden has at least one tree.
 *   All coordinates are distinct.
 *   Input points have NO order. No order required for output.
 * 
 * Example 1:
 * Input: [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
 * Output: [[1,1],[2,0],[4,2],[3,3],[2,4]]
 * 
 * Example 2:
 * Input: [[1,2],[2,2],[4,2]]
 * Output: [[1,2],[2,2],[4,2]]
 * Explanation:
 *   Even you only have trees in a line, you need to use rope to enclose them.
 *
 * 
 * Thoughts:
 * s1, The fence is a convex polygon. 
 *     The left side of fence is the trees which x is minimum. The right fence is the trees which x is maximum. 
 *     The bottom fence is the trees which y is minimum to every x. and it's the convex, the upper fence is the trees 
 *     which y is maximum to every x.
 * 
 *     How to make sure it's convex? 
 *     How to make sure it enclose all trees?
 *     
 * 
 * 
 */
public class ConvexFence {
    
    /**
     * define n as the number of trees
     * Time Complexity O(n), Space O(n)
     * 
     * @param points
     * @return 
     */
    public Point[] outerTrees(Point[] points) {
        if(points == null || points.length < 4){
            return points;
        }
        
        //order points by x and y 
        Arrays.sort(points, (p1, p2) -> p1.x == p2.x? Integer.compare(p1.y, p2.y) : Integer.compare(p1.x, p2.x));
        
        int n = points.length;
        
        //the left fence, points[0] to points[minXEndIndex]
        int minXEndIndex = 1;
        for(int minX = points[0].x; minXEndIndex < n && points[minXEndIndex].x == minX; minXEndIndex++){}
        minXEndIndex--;
        
        //the right fence, points[maxXStartIndex] to points[n - 1]
        int maxXStartIndex = n - 2;
        for(int maxX = points[n - 1].x; maxXStartIndex > -1 && points[maxXStartIndex].x == maxX; maxXStartIndex--){}
        maxXStartIndex++;
                
        //line sweep, the bottom fence, in minYs, the upper fence, in maxYs 
        Queue<Point> minYs = new LinkedList<>(); 
        Queue<Point> maxYs = new LinkedList<>(); 
        for(int i = minXEndIndex + 1, j; i < maxXStartIndex; ){            
            for( j = i, i++ ; i < maxXStartIndex && points[i].x == points[j].x; i++){}
            
            minYs.add(points[j]);
            maxYs.add(points[i - 1]);
        }
        minYs.add(points[maxXStartIndex]);
        maxYs.add(points[n - 1]);
        
        //make the bottom fence convex and enclosed, anticlockwise
        Stack<Point> bottomFence = new Stack<>();
        bottomFence.add(points[0]); 
        
        Point prePoint = minYs.poll();
        double preSlope;
        double currSlope;
        while(!minYs.isEmpty()){
            while((currSlope = getSlope(bottomFence.peek(), minYs.peek())) < (preSlope = getSlope(bottomFence.peek(), prePoint)) && bottomFence.size() > 1){
                prePoint = bottomFence.pop();
            }
            
            if(currSlope >= preSlope){
                bottomFence.add(prePoint);
            }
            
            prePoint = minYs.poll();
        }
        
        //make the upper fence convex and enclosed, clockwise
        Stack<Point> upperFence = new Stack<>();
        upperFence.add(points[minXEndIndex]);
        
        prePoint = maxYs.poll();
        while(!maxYs.isEmpty()){
            while((currSlope = getSlope(upperFence.peek(), maxYs.peek())) > (preSlope = getSlope(upperFence.peek(), prePoint)) && upperFence.size() > 1){
                prePoint = upperFence.pop();
            }
            
            if(currSlope <= preSlope){
                upperFence.add(prePoint);
            }
            
            prePoint = maxYs.poll();
        }
        
        //wrap the result for output, start from the bottom_left point, anticlockwise 
        int m = bottomFence.size();
        Point[] result = new Point[m + (n - maxXStartIndex) + upperFence.size() + (minXEndIndex - 1)  ];
        
        for(int i = m - 1; !bottomFence.isEmpty(); i--){
            result[i] = bottomFence.pop();
        }
        
        System.arraycopy(points, maxXStartIndex, result, m, n - maxXStartIndex);
        
        for(int i = m + n - maxXStartIndex; i < result.length && !upperFence.isEmpty(); i++){//i < result.length for case only one point which x is minimum (minXEndIndex==0)
            result[i] = upperFence.pop();
        }
        
        for( int i = result.length - 1, j = 1; j < minXEndIndex; i--, j++){
            result[i] = points[j];
        }        
       
        return result;
    }
        
    
    private double getSlope(Point p1, Point p2){
        return (double)(p2.y - p1.y) / (p2.x - p1.x);
    }
    
    
    public static void main(String[] args){
        ConvexFence sv = new ConvexFence();
        
        int[][][][] inputs = {
            //{input, output}
            {
                {
                    {1, 1}, {2, 2}, {2, 0}, {2, 4}, {3, 3}, {4, 2}
                },
                {
                    {1, 1}, {2, 0}, {4, 2}, {3, 3}, {2, 4}
                }
            },
            {
                {
                    {1, 2}, {2, 2}, {4, 2}
                },
                {
                    {1, 2}, {2, 2}, {4, 2}
                }
            },
            {
                {{3, 0}, {4, 0}, {5, 0}, {6, 1}, {7, 2}, {7, 3}, {7, 4}, {6, 5}, {5, 5}, {4, 5}, {3, 5}, {2, 5}, {1, 4}, {1, 3}, {1, 2}, {2, 1}, {4, 2}, {0, 3}},
                {{0, 3}, {1, 2}, {2, 1}, {3, 0}, {4, 0}, {5, 0}, {6, 1}, {7, 2}, {7, 3}, {7, 4}, {6, 5}, {5, 5}, {4, 5}, {3, 5}, {2, 5}, {1, 4}}
            },
            {
                {{11, 50}, {43, 16}, {45, 55}, {74, 20}, {67, 33}, {66, 4}, {67, 56}, {37, 32}, {48, 96}, {81, 33}, {22, 17}, {64, 92}, {71, 17}, {3, 25}, {15, 91}, {1, 46}, {70, 76}, {3, 15}, {23, 30}, {19, 17}, {39, 82}, {32, 45}, {94, 61}, {49, 98}, {43, 64}, {85, 28}, {23, 39}, {55, 20}, {41, 12}, {36, 58}, {59, 9}, {50, 5}, {97, 19}, {57, 34}, {10, 33}, {24, 80}, {35, 22}, {41, 62}, {21, 53}, {68, 58}, {32, 24}, {36, 69}, {82, 86}, {75, 31}, {49, 9}, {9, 25}, {51, 4}, {18, 84}, {5, 49}, {25, 69}, {20, 59}, {17, 40}, {15, 28}, {29, 91}, {10, 97}, {28, 37}, {41, 9}, {82, 37}, {13, 21}, {10, 4}, {43, 7}, {66, 44}, {39, 67}, {26, 98}, {75, 93}, {96, 56}, {60, 83}, {9, 92}, {50, 83}, {33, 2}, {24, 94}, {41, 48}, {95, 2}, {13, 84}, {10, 41}, {65, 52}, {69, 91}, {38, 24}, {6, 82}, {59, 67}, {50, 75}, {36, 61}, {38, 49}, {13, 50}, {8, 55}, {15, 31}, {69, 37}, {97, 61}, {28, 48}, {7, 56}, {18, 61}, {0, 22}, {15, 97}, {0, 50}, {30, 71}, {69, 89}, {84, 83}, {96, 49}, {89, 4}},
                {{0, 22}, {3, 15}, {10, 4}, {33, 2}, {95, 2}, {97, 19}, {97, 61}, {84, 83}, {82, 86}, {75, 93}, {49, 98}, {26, 98}, {10, 97}, {6, 82}, {0, 50}}
            }
        };
        
        for(int[][][] input : inputs){
            //System.out.println(Point.toString(sv.outerTrees(Point.build(input[0]))));
            Assert.assertArrayEquals(Point.build(input[1]), sv.outerTrees(Point.build(input[0])));
            //Assert.assertEquals( Misc.array2String(input[1], true), Point.toString(sv.outerTrees(Point.build(input[0]))));

        }
        
    }
    
}

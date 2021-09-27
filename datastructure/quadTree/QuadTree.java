package datastructure.quadTree;

import java.awt.Point;

/**
 * 
 * 关于  quadtree 的 

比如一个二维的 image, 里面的 pixel是 白或者黑,  若果所有的pixel是黑
那么这个 image就是黑(B)的，如果所有的pixel 是白(W)的，那么这个 image就是白的。
否则的话，需要把这个 image等分成四份，如下图

__________                     __________
|        |   等分成四份就变成    |    |   |
|        |                     |____|___|   
|        |                     |    |   |
|________|                     |____|___|

分成四份以后每个小份就是一个  sub-quadtree

问题1 : 为这个 quadtree里面的 node 设计 data structure

然后的问题是关于两个 quadtree 的 intersection, 有两个 quadtree, 它们描述的 
image 是两个相同的 area
比如 都是 [0 1] x [0 1] 这个相同的二维区域的image.

问题二: 写一个函数，返回两个 quadtree的intersection,

这个intersection的规则是: 如果一个区域在 第一个quadtree 里面是
白的，这个相同的区域在 第二个 quadtree里面是黑的，那么intersection
就是白的，简单的说白是 0, 黑是 1, intersection就是两个bit 的 AND
 *
 */

public class QuadTree {
    Point leftUp;
    Point rightDown;
    Point root;
    
    // four subtrees, NW, NE, SE, SW
    QuadTree[] nodes = new QuadTree[4]; //default all are null; 
    
    Enum color; // While, Black

    QuadTree(Point leftUp,
             Point rightDown,
             Point root,
             Enum color) {
        this.leftUp = leftUp;
        this.rightDown = rightDown;
        this.color = color;
    }

    /***************************************************************************
     * helper intersection functions
     ***************************************************************************/
    public QuadTree intersection(QuadTree the, QuadTree other){
        int diff = compare(the.root, other.root);
        if(diff < 0){
            return intersection(other, the);
        }
        
        if(diff == 0){
            for(int i = 0; i < the.nodes.length; i++){
                intersection(the.nodes[i], other.nodes[i]);
            }
        }
        
        if(isHorizonal(the.root, other.root)){
            // TODO: 7/26/2016
            //if()
        }
        
        return the;
    }

    /***************************************************************************
     * helper comparison functions
     ***************************************************************************/

    private boolean less(int k1,
                         int k2) {
        return k1- k2 < 0;
    }

    private boolean eq(int k1,
                       int k2) {
        return k1 == k2;
    }

    private boolean isHorizonal(Point a, Point b){
        return a.x == b.x;
    }
    
    private boolean isVertical(Point a, Point b){
        return a.y == b.y;
    }
    
    private int compare(Point a, Point b){
        if(eq(a.x, b.x)){
            return a.y - b.y;
        }else{
            return a.x - b.x;
        }
    }
    /***************************************************************************
     * test client
     ***************************************************************************/
    /*
    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]); // queries
        int N = Integer.parseInt(args[1]); // points

        QuadTree st = new QuadTree();

        // insert N random points in the unit square
        for (int i = 0; i < N; i++) {
            Integer x = (int) (100 * Math.random());
            Integer y = (int) (100 * Math.random());
            // StdOut.println("(" + x + ", " + y + ")");
            st.insert(x, y, "P" + i);
        }
        StdOut.println("Done preprocessing " + N + " points");

        // do some range searches
        for (int i = 0; i < M; i++) {
            Integer xmin = (int) (100 * Math.random());
            Integer ymin = (int) (100 * Math.random());
            Integer xmax = xmin + (int) (10 * Math.random());
            Integer ymax = ymin + (int) (20 * Math.random());
            Interval<Integer> intX = new Interval<Integer>(xmin, xmax);
            Interval<Integer> intY = new Interval<Integer>(ymin, ymax);
            Interval2D<Integer> rect = new Interval2D<Integer>(intX, intY);
            StdOut.println(rect + " : ");
            st.query2D(rect);
        }
    }
*/
}

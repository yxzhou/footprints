package datastructure.quadTree;

/**
 * design a data structure, implement the following method
 *   void set(int x, int y, int value)
 *   long sum(int x_LeftTop, int y_leftTop, int x_rightBottom, int y_rightBottom)
 *
 *
 */

public class MyQuadTree {
    final static int DEFAULT_CAPACITY = 1024;
    RectNode root;

    public MyQuadTree() {
        init(DEFAULT_CAPACITY);
    }
    
    private void init(int capacity){
        
        Point leftTop = new Point(0, 0);
        Point rightBottom = new Point(capacity, capacity);
        root = build(leftTop, rightBottom);

    }
    
    private RectNode build(Point leftTop, Point rightBottom){
        RectNode node = new RectNode(new Rectangle(leftTop, rightBottom));
        // TODO: 7/26/2016  
        //node.children[0] = build(leftTop, rightBottom);
        return null;
    }

    public void set(Point point, int value) {
        if(!root.rect.contains(point)){
            expand(point);
        }
        
        setDown(root, point, value);
    }

    /*
     * return the diff
     * */
    private long setDown(RectNode node, Point point, int value){
        long diff = 0;
        
        if(node.rect.leftTop.equals(node.rect.rightBottom) && node.rect.leftTop.equals(point)){
            diff = value - node.sum;
            node.sum = value;
            return diff;
        }
            
        for(RectNode child : node.children){
            if(child.rect.contains(point)){
                diff = setDown(child, point, value);
            }
        }
        
        node.sum += diff;
        return diff;
    }
    
    private void expand(Point point){
        
        RectNode curr = root;
        RectNode newRoot;
        
        while( !curr.rect.contains(point) ){

            Point newRightBottom = new Point(curr.rect.rightBottom.x << 1, curr.rect.rightBottom.y << 1);
            Rectangle newRect = new Rectangle(curr.rect.leftTop, newRightBottom);
            newRoot = new RectNode(newRect, curr.sum);
            
            newRoot.children[0] = curr;
            curr = newRoot;
        }
        
        root = curr;
    }
    
    public long sum(Rectangle rect){
        return sum(root, rect);
    }
    private long sum(RectNode node, Rectangle rect){
        //check
        if(null == node || null == rect){
            return 0;
        }
        
        long sum = 0;
        boolean containLeftTop = rect.contains(node.rect.leftTop);
        boolean containRightBottom = rect.contains(node.rect.rightBottom);
        
        if( containLeftTop && containRightBottom ){
            sum += root.sum;
        }else if(containLeftTop || containRightBottom){
            for(RectNode child : node.children){
                sum += sum(child, rect);
            }
        }
            
        return sum;
    }

    class RectNode {
        Rectangle rect;

        long sum;
        
        RectNode[] children = new RectNode[4];// order by NW, NE, SE, SW
        
        public RectNode(Rectangle rect){
            this(rect, 0);
        }
        
        public RectNode(Rectangle rect, long sum){
            this.rect = rect;
            this.sum = sum;
        }
        
    }

    class Rectangle {
        Point leftTop;
        Point rightBottom;

        Rectangle(Point leftTop, Point rightBottom){
            this.leftTop = leftTop; 
            this.rightBottom = rightBottom;
        }

        public boolean contains(Point point) {
            return leftTop.x <= point.x && leftTop.y <= point.y && rightBottom.x >= point.x && rightBottom.y >= point.y;
        }
        
    }
    
    class Point{
        int x;
        int y;
        
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public boolean equals(Point other){
            return x == other.x && y == other.y;
        }
    }

}

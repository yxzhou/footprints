package test;

import java.util.ArrayList;
import java.util.List;

import util.Misc;

public class SpiralMatrix {

    //print a matrix (n, n)
    public List<Point> matrix(int n){
        List<Point> ret = new ArrayList<>();
        
        //check
        if(n < 1) {
            return ret;
        }
        
        for(int downLeft = 0, upRight = n;downLeft < upRight; downLeft++, upRight--){
            //bottom, from left to right
            for(int x = downLeft; x < upRight; x++){
                ret.add(new Point(x, downLeft));
            }
            
            //right, from down to up
            for(int y = downLeft; y < upRight; y++){
                ret.add(new Point(upRight, y));
            }
            
            //top, from right to left
            for(int x = upRight; x > downLeft; x--){
                ret.add(new Point(x, upRight));
            }
            
            //left, from up to down
            for(int y = upRight; y > downLeft; y--){
                ret.add(new Point(downLeft, y));
            }
            
        }
        
        if((n & 1) == 0){// n is even
            n >>= 1;
            ret.add(new Point(n, n));
        }
        
        return ret;
    }
    
    //print a matrix (n, n)
    public List<Point> matrix(int m, int n){
        List<Point> ret = new ArrayList<>();
        
        //check
        if(m < 1 || n < 1) {
            return ret;
        }
        
        int downLeft = 0;
        int upRight_x = m, upRight_y = n;
        for( ; downLeft < upRight_x && downLeft < upRight_y; downLeft++, upRight_x--, upRight_y--){
            //bottom, from left to right
            for(int x = downLeft; x < upRight_x; x++){
                ret.add(new Point(x, downLeft));
            }
            
            //right, from down to up
            for(int y = downLeft; y < upRight_y; y++){
                ret.add(new Point(upRight_x, y));
            }
            
            //top, from right to left
            for(int x = upRight_x; x > downLeft; x--){
                ret.add(new Point(x, upRight_y));
            }
            
            //left, from up to down
            for(int y = upRight_y; y > downLeft; y--){
                ret.add(new Point(downLeft, y));
            }
            
        }
        
        if(downLeft == upRight_x && downLeft == upRight_y){
            ret.add(new Point(downLeft, downLeft));
        } else if(downLeft < upRight_x && downLeft == upRight_y){
            //bottom, from left to right
            for(int x = downLeft; x <= upRight_x; x++){
                ret.add(new Point(x, downLeft));
            }
        } else if(downLeft == upRight_x && downLeft < upRight_y){
            //right, from down to up
            for(int y = downLeft; y <= upRight_y; y++){
                ret.add(new Point(upRight_x, y));
            }
        }
        
        return ret;
    }
    
    public static void main(String[] args) {
        SpiralMatrix sv = new SpiralMatrix();
        
        for(int n = 0; n < 4; n++){
            System.out.println(String.format("\n matrix(%d): ", n));
            Misc.printList(sv.matrix(n));
            Misc.printList(sv.matrix(n, n));
            
            System.out.println(String.format("\n matrix(%d, %d): ", n-1, n));
            Misc.printList(sv.matrix(n - 1, n));
            
            System.out.println(String.format("\n matrix(%d, %d): ", n, n-1));
            Misc.printList(sv.matrix(n, n - 1));
        }
        
    }

    class Point{
        int x;
        int y;
        
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public String toString(){
            return String.format("(%d, %d)", x, y);
        }
    }
    
}

package fgafa.geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import fgafa.util.Misc;

public class Points2DII {

	
    /**
     * @param points an array of point
     * @return an integer
     */
    public int maxPoints(Point[] points) {
        // check
        if(null == points || 0 == points.length){
            return 0;
        }
        if(3 > points.length){
            return points.length;
        }
        
        int max = 0;
        int currMax = 0;
        int size = points.length;
        Map<String, Integer> map;
        int samePointCount = 1;
        String a;  //y = ax + b; 
        for(int i = 0; i < size; i++){
        	map = new HashMap<String, Integer>();
        	samePointCount = 1;

            for(int j = i + 1; j < size; j++){
                if(isSamePoint(points[i], points[j])){
                	samePointCount++;
                	continue;
                }else if(isHorizontal(points[i], points[j])){
                	a = "0";
                }else if(isVertical(points[i], points[j])){
                	a = " ";
                }else{
                	a = String.valueOf((double)(points[i].y - points[j].y) / (points[i].x - points[j].x)); // note
                }
                
            	if(map.containsKey(a)){
            		map.put(a, map.get(a) + 1);
            	}else{
            		map.put(a, 1);
            	}
            }
            
        	currMax = 0;
            for(Integer count : map.values()){
            	currMax = Math.max(currMax, count);
            }
            
            max = Math.max(max, currMax + samePointCount);
        }
        
        //return 
        return max;
    }
    
    private boolean isSamePoint(Point p1, Point p2){
    	//return p1.x == p2.x && p1.y == p2.y;
    	return isHorizontal(p1, p2) && isVertical(p1, p2);
    }

    private boolean isHorizontal(Point p1, Point p2){
    	return p1.y == p2.y;
    }
    
    private boolean isVertical(Point p1, Point p2){
    	return p1.x == p2.x;
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Points2DII sv = new Points2DII();
		
		int[][][] input = {null, 
				{{1,1}},
				{{1,1}, {1,2}},
				{{1,1}, {2,1}},
				{{1,1}, {1,1}},
				{{1,1}, {1,2}, {2,1}, {1,1}, {2,2}, {3,3}},
				{{1,1}, {1,1}, {1,1}},
				{{2,3}, {3,3}, {-5,3}}
				};

		for(int[][] points : input){
			System.out.println("\n Input:" + Misc.array2String(points));
			System.out.println(sv.maxPoints(sv.build(points)));
		}
		
		ArrayList<String> result = new ArrayList<String>();
		result.clear();
		
		String word = "";
		
	}

	class Point {
		int x;
		int y;
		
		Point(int a, int b) { x = a; y = b; }
	}
	

//    class Line implements Comparable<Line>{
//        /* y = ax + b
//         * 
//         * to line:
//         *   x=1;  a = 1, b = 0
//         *   y=1;  a = 0, b = 1
//         */
//        int a;
//        int b;
//        
//        public Line(int a, int b){
//            this.a = a;
//            this.b = b;
//        }
//        
//
//		@Override
//		public int compareTo(Line o) {
//            if(this.a == o.a){
//                return this.b - o.b;
//            }else{
//                return this.a - o.a;
//            }
//		}
//    }

    
	private static Point[] build(int[][] points){
		if(null == points || 0 == points.length)
			return null;
		
		Points2DII sv = new Points2DII();
		Point[] ret = new Point[points.length];
		for(int i=0; i < points.length; i++){
			ret[i] = sv.new Point(points[i][0], points[i][1]);
		}
		return ret;
	}
}

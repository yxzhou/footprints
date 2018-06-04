package fgafa.geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.Math;

import fgafa.util.Misc;

/*
 * Given N points (x1, y1), (x2, y2), …, (xn, yn) in a plane, find a line that passes through the maximum number of points.
 * 
 */

public class Points2D {



	/**
	 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
	 */
	/**
	 * Time O(n^2)  Space O(n)
	 */
	public int maxPoints(Point[] points) {
		int max = 0;
		if(null == points || 0 == points.length)
			return 0;
		
		int diffX = 0, diffY = 0, samePointCount = 0;
		String key; // 
		for(int i=0; i< points.length; i++ ){
			Map<String, Integer> map = new HashMap<>();
			samePointCount = 0;
			for( int j=i + 1; j<points.length; j++){
				diffX = points[i].x - points[j].x;
				diffY = points[i].y - points[j].y;
				if(0 == diffX && 0 == diffY){
					samePointCount ++;
					continue;
				}else if(0 == diffX ){
					key = " ";
				}else if(0 == diffY){
					key ="0.0";
				}else{
					key = String.valueOf((double) diffY / diffX);//note
				}
				
				if(map.containsKey(key)){
					map.put(key, map.get(key)+1 );
				}else{
					map.put(key, 2);
				}
			}
			
			int currMax = 0;
			for(Integer k : map.values() ){
				currMax = Math.max(currMax, k);
			}
			max = Math.max(max, Math.max(currMax, 1) + samePointCount); //note
		}
		
		return max;
	}

	
	/*
	 * 
	 * O(n^2) ?? O(n^3)
	 */
	public List findTheLine(Point[] arr) {
		// check
		if (arr == null || arr.length < 2)
			return null;

		// init
		int len = arr.length;
		Point a;
		Point b;
		Point c;
		int k; //
		// store the checked Point
		Hashtable<Point, String> htTmp = new Hashtable<Point, String>(); 
		// store the line and the Points on it
		Hashtable<String, ArrayList<Point>> htResult = new Hashtable<String, ArrayList<Point>>(); 

		String key;
		for (int i = 0; i < len; i++) {
			a = arr[i];
			for (int j = i + 1; j < len; j++) {
				b = arr[j];

				if (htTmp.contains(b))
					continue;

				if (a.x == b.x)
					k = Integer.MAX_VALUE;
				else
					k = (b.y - a.y) / (b.x - a.x);

				htTmp.clear();
				key = i + " " + j;
				for (int p = j + 1; p < len; p++) {
					c = arr[p];
					if (!htTmp.contains(c) && isCollinear(k, a, c)) {
						htTmp.put(c, " ");

						if (htResult.contains(key)) {
							htResult.get(key).add(c);
						} else {
							ArrayList<Point> list = new ArrayList<Point>();
							list.add(a);
							list.add(b);
							list.add(c);

							htResult.put(key, list);
						}

					}

				}
			}
		}

		// travel the htResult and get the line with most points.
		List listTmp;
		List<Point> listMax = new ArrayList<Point>();
		int lenMax = 0;

		if (htResult.size() == 0) {
			listMax.add(arr[0]);
			listMax.add(arr[1]);
		} else {
			Iterator iterator = htResult.keySet().iterator();

			while (iterator.hasNext()) {
				key = (String) iterator.next();
				listTmp = (ArrayList) htResult.get(key);

				if (listTmp.size() > lenMax) {
					lenMax = listTmp.size();
					listMax = listTmp;
				}
			}

		}

		return listMax;
	}

	/*
	 * Define 3 point, a, b and c. they are collinear if (b.y - a.y) / (b.x -
	 * a.x) = (c.y - a.y) / (c.x - a.x)
	 * 
	 * here k = (b.y - a.y) / (b.x - a.x)
	 */
	private boolean isCollinear(int k, Point a, Point c) {

		if (k == Integer.MAX_VALUE)
			return a.x == c.x;
		else
			return (c.y - a.y) == k * (c.x - a.x);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Points2D sv = new Points2D();
		
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
	}

	class Point {
		int x;
		int y;
		
		Point(int a, int b) { x = a; y = b; }
	}
	
	private Point[] build(int[][] points){
		if(null == points || 0 == points.length)
			return null;
		
		Points2D.Point[] ret = new Points2D.Point[points.length];
		for(int i=0; i < points.length; i++){
			ret[i] = new Point(points[i][0], points[i][1]);
		}
		return ret;
	}
}

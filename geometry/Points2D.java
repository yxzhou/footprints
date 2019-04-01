package fgafa.geometry;

import fgafa.util.Misc;

import java.util.*;

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
		if(null == points || 0 == points.length){
			return 0;
		}

		int result = 1;

		Map<String, Integer> map = new HashMap<>();
		int diffx;
		int diffy;
		String k;
		int duplicate;
		int max;
		for(int i = points.length - 1; i > 0; i-- ){
			map.clear();
			duplicate = 1;

			for(int j = i - 1; j > -1; j--){
				diffx = points[i].x - points[j].x;
				diffy = points[i].y - points[j].y;

				if(diffx == 0 && diffy == 0){
					duplicate++;
				}else{
					if(diffx == 0){
						k = " ";
					} else if(diffy == 0){
						k = "0";
					} else {
						k = String.valueOf((double)diffy / diffx);
					}

					map.put(k, map.containsKey(k)? map.get(k) + 1 : 1);
				}
			}

			max = 0;
			for(int v : map.values()){
				max = Math.max(max, v);
			}

			result = Math.max(result, max + duplicate);
		}

		return result;
	}

	public int maxPoints_n(Point[] points) {
		if(null == points || 0 == points.length){
			return 0;
		}

		Arrays.sort(points, (p1, p2) -> p1.x == p2.x? Integer.compare(p1.y, p2.y) : Integer.compare(p1.x, p2.x));

		Map<Point, Integer> duplicates = new HashMap<>();
		duplicates.put(points[0], 1);
		for(int i = 0, j = 1; j < points.length; j++){
			if(points[i].x == points[j].x && points[i].y == points[j].y){
				duplicates.put(points[i], duplicates.get(points[i]) + 1);
			}else{
				i = j;
				duplicates.put(points[i], 1);
			}
		}

		points = duplicates.keySet().toArray(new Point[duplicates.size()]);

		int result = duplicates.get(points[0]);

		Map<String, Integer> map = new HashMap<>();
		int diffx;
		int diffy;
		String k;
		int max;
		for(int i = points.length - 1; i > 0; i-- ){
			map.clear();

			for(int j = i - 1; j > -1; j--){
				diffx = points[i].x - points[j].x;
				diffy = points[i].y - points[j].y;

				if(diffx == 0){
					k = " ";
				} else if(diffy == 0){
					k = "0";
				} else {
					k = String.valueOf((double)diffy / diffx);
				}

				map.put(k, ( map.containsKey(k)? map.get(k) : 0 ) + duplicates.get(points[j]));
			}

			max = 0;
			for(int v : map.values()){
				max = Math.max(max, v);
			}

			result = Math.max(result, max + duplicates.get(points[i]));
		}

		return result;
	}
	
	/*
	 * 
	 * O(n^2) ?? O(n^3)
	 */
	public List<Point> findTheLine(Point[] arr) {
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
		List<Point> listTmp;
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

		int[][][] input = {null,       //0
				{{1, 1}},                     // 1
				{{1, 1}, {1, 2}},             // 2
				{{1, 1}, {2, 1}},             // 2
				{{1, 1}, {1, 1}},             // 2
				{{1, 1}, {1, 2}, {2, 1}, {1, 1}, {2, 2}, {3, 3}},  // 4
				{{1, 1}, {1, 1}, {1, 1}},                          // 3
				{{2, 3}, {3, 3}, {-5, 3}},                         // 3
				{{0, 0}, {4, 5}, {1, 1}, {3, 6}, {2, 2}}           // 3
		};

		for(int[][] pair : input){
			System.out.println("\n Input:" + Misc.array2String(pair));

			Point[] points = sv.build(pair);

			System.out.println(sv.maxPoints(points));

			System.out.println(sv.maxPoints_n(points));
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

package todo.foo;

import java.util.*;

/**
 * _http://www.jiuzhang.com/solution/rectangle
 *
 * Give a set, if you can find four points that make up a rectangle that is parallel to the coordinate axis and outputs YES or NO.
 */

public class Rectangle {

    public boolean hasRectangle(Point[] points){
        if(null == points || points.length < 4){
            return false;
        }

        Map<Integer, SortedSet<Point>> horizonalLines = new HashMap<>();
        Set<String> pointsSet = new HashSet<>();

        for(Point p : points){
            if(!horizonalLines.containsKey(p.y)){
                horizonalLines.put(p.y, new TreeSet<Point>());
            }
            horizonalLines.get(p.y).add(p);

            pointsSet.add(hash(p.x, p.y));
        }

        List<Integer> lines = new ArrayList(horizonalLines.keySet());
        Collections.sort(lines);

        for(int i = 0; i < lines.size(); i++){
            for(int j = i + 1; j < lines.size(); j++){
                for(Point leftTop : horizonalLines.get(lines.get(i))){
                    for(Point rightBottom : horizonalLines.get(lines.get(j))){
                        if(leftTop.x < rightBottom.x && pointsSet.contains(hash(leftTop.x, rightBottom.y)) && pointsSet.contains(hash(rightBottom.x, leftTop.y))){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private String hash(int x, int y){
        return x + " " + y;
    }

    class Point implements Comparable<Point>{
        int x;
        int y;

        @Override public int compareTo(Point o) {
            if(x == o.x){
                return y - o.y;
            }else{
                return x - o.x;
            }
        }
    }
}

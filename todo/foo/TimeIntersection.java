package fgafa.todo.foo;

import java.util.ArrayList;
import java.util.List;

/**
 * _http://www.jiuzhang.com/solution/time-intersection
 */

public class TimeIntersection {
    public List<Interval> timeIntersection(List<Interval> seqA, List<Interval> seqB){
        if(null == seqA){
            return seqB;
        }else if(null == seqB){
            return seqA;
        }

        List<Interval> result = new ArrayList<>();
        for(int i = 0, j = 0; i < seqA.size() && j < seqB.size(); ){
            if(seqA.get(i).end < seqB.get(j).start){
                i++;
            }else if(seqA.get(i).start > seqB.get(j).end){
                j++;
            }else{
                result.add(new Interval(Math.max(seqA.get(i).start, seqB.get(j).start), Math.min(seqA.get(i).end, seqB.get(j).end)));
                if(seqA.get(i).end < seqB.get(j).end){
                    i++;
                }else if(seqA.get(i).end == seqB.get(j).end){
                    i++;
                    j++;
                }else{
                    j++;
                }
            }
        }

        return result;
    }


    class Interval{
        int start;
        int end;

        Interval(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
}

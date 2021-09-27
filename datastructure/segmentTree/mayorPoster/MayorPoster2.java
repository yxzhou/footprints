package datastructure.segmentTree.mayorPoster;

import java.util.*;

public class MayorPoster2 {

    public int mayorPoster(int[][] posters){
        if(null == posters || 0 == posters.length){
            return 0;
        }

        Map<Integer, List<Integer>> position2Weight = new HashMap<>();

        for(int i = 0, w = 1; i < posters.length; i++, w++){
            if(posters[i][0] < posters[i][1]){
                if(!position2Weight.containsKey(posters[i][0])){
                    position2Weight.put(posters[i][0], new ArrayList<>());
                }
                position2Weight.get(posters[i][0]).add(w);

                if(!position2Weight.containsKey(posters[i][1])){
                    position2Weight.put(posters[i][1], new ArrayList<>());
                }
                position2Weight.get(posters[i][1]).add(-w);
            }
        }

        List<Integer> positions = new ArrayList<>(position2Weight.keySet());
        Collections.sort(positions);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        Set<Integer> visibles = new HashSet<>();

        for(int position : positions){
            for(int w : position2Weight.get(position)){
                if(w >= 0){
                    maxHeap.add(w);
                }else{
                    maxHeap.remove(-w);
                }
            }

            if(!maxHeap.isEmpty()){
                visibles.add(maxHeap.peek());
            }
        }

        return visibles.size();
    }

}

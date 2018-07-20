package fgafa.datastructure.segmentTree.rankScore;

/**
 *
 *  In fact, it's to get the max of a range/segment in a mutable integer array.
 *
 */

public class RankScore {

    int[] origin; //the original scores
    int[] tree;  //interval tree, the node is the max score of a interval.

    public RankScore(int[] initialScores){
        if(null == initialScores || 0 == initialScores.length){
            throw new IllegalArgumentException("---");
        }

        int length = initialScores.length;
        origin = initialScores;

        tree = new int[length * 2 - 1];
        initTree(tree, 0, 0, length - 1, origin);
    }

    private void initTree(int[] tree, int nodeIndex, int nodeStart, int nodeEnd, int[] origin){
        if(nodeStart == nodeEnd){
            tree[nodeIndex] = origin[nodeStart];
        }else{ //nodeStart < nodeEnd

            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2 + 1;

            initTree(tree, leftSon, nodeStart, nodeMiddle, origin);
            initTree(tree, leftSon + 1, nodeMiddle + 1, nodeEnd, origin);

            tree[nodeIndex] = Math.max(tree[leftSon], tree[leftSon + 1]);
        }
    }

    public int query(int startIndex, int endIndex){
        return query(tree, 0, 0, origin.length - 1, startIndex, endIndex);
    }

    private int query(int[] tree, int nodeIndex, int nodeStart, int nodeEnd, int startIndex, int endIndex){
        int result = Integer.MIN_VALUE; // result is the max of the range, default it's MIN_VALUE

        if( endIndex < nodeStart || nodeEnd < startIndex ){ //2 intervals no intersection
            //do nothing, it will return MIN_VALUE
        } else if (startIndex <= nodeStart && nodeEnd <= endIndex){ //checkIn interval includes curr segment
            result = tree[nodeIndex];
        } else { // intersection
            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2 + 1;

            result = Math.max(result, query(tree, leftSon, nodeStart, nodeMiddle, startIndex, endIndex));
            result = Math.max(result, query(tree, leftSon + 1, nodeMiddle + 1, nodeEnd, startIndex, endIndex));
        }

        return result;
    }

    public void update(int index, int newScore){
        if(index < 0 || index >= origin.length){
            throw new IllegalArgumentException("---");
        }

        update(tree, 0, 0, origin.length - 1, origin, index, newScore);
    }

    private void update(int[] tree, int nodeIndex, int nodeStart, int nodeEnd, int[] origin, int index, int newScore){

        if(nodeStart == nodeEnd){
            tree[nodeIndex] = newScore;
        }else if(nodeStart < nodeEnd){
            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2 + 1;

            if(index <= nodeMiddle){
                update(tree, leftSon, nodeStart, nodeMiddle, origin, index, newScore);
            }else{
                update(tree, leftSon + 1, nodeMiddle + 1, nodeEnd, origin, index, newScore);
            }

            tree[nodeIndex] = Math.max(tree[leftSon], tree[leftSon + 1]);
        }
    }
}

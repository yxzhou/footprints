package datastructure.segmentTree.hotel;

/**
 *
 * Give a int roomNumber, implement the following 2 functions 
 * queryAndCheckin(int roomNumberNeed)
 * //when a user want to book some rooms, check room by index from 0 to roomNumber - 1, find the contiguous and empty rooms, 
 * //if find, assigned to the user and return the leftmost room index.
 *
 * checkout(int roomIndexStart, int roomIndexEnd) 
 * //when a user checkout, reset the room from roomIndexStart to roomIndexEnd as empty
 *
 * Solution Define a segment tree, segment node { start, end, max contiguous rooms number, the contiguous rooms number
 * from the start, the contiguous rooms number width at the end }
 *
 */

public class RoomAssignment {
    final int MAX_LENGTH = 50000;

    int length;  //how many rooms totally
    IntervalNode[] tree;  // max Contigurous Rooms that are valid for checkin

    public RoomAssignment(int roomsNumber){
        if(roomsNumber <= 0 || roomsNumber > MAX_LENGTH){
            throw new IllegalArgumentException(" - - ");
        }

        length = roomsNumber;
        tree = new IntervalNode[length * 2 - 1];
        initTree(tree, 0, 0, length - 1);
    }

    private void initTree(IntervalNode[] tree, int nodeIndex, int nodeStart, int nodeEnd){
        int roomNumber = nodeEnd - nodeStart + 1;
        tree[nodeIndex] = new IntervalNode(roomNumber, roomNumber, roomNumber);

        if(nodeStart < nodeEnd){
            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2 + 1;

            initTree(tree, leftSon, nodeStart, nodeMiddle);
            initTree(tree, leftSon + 1, nodeMiddle + 1, nodeEnd);
        }
    }

    /**
     *
     * @param roomsNeed
     * @return 0, if it can't find so many contigurous empty rooms, or the first room number of the contigurous rooms
     *         note: the first room number is 1 and the first room index is 0
     *
     */
    public int queryAndCheckin(int roomsNeed){
        int firstRoomIndex =  queryAndCheckin(tree, 0, 0, length - 1, roomsNeed);

        return firstRoomIndex + 1;
    }

    private int queryAndCheckin(IntervalNode[] tree, int nodeIndex, int nodeStart, int nodeEnd, int roomsNeed){
        int result = -1;

        if(nodeStart == nodeEnd){
            if(1 == roomsNeed){
                result = nodeStart;

                checkin(result, result + roomsNeed - 1);
            }
        }else{
            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2 + 1;

            if(tree[leftSon].max >= roomsNeed){
                result = queryAndCheckin(tree, leftSon, nodeStart, nodeMiddle, roomsNeed);
            }else if(tree[leftSon].last + tree[leftSon + 1].first >= roomsNeed){
                result = nodeMiddle + 1 - tree[leftSon].last;

                checkin(result, result + roomsNeed - 1);
            }else if(tree[leftSon + 1].max >= roomsNeed){
                result = queryAndCheckin(tree, leftSon + 1, nodeMiddle, nodeEnd, roomsNeed);
            }
        }

        return result;
    }

    private void checkin(int startIndex, int endIndex){
        update(tree, 0, 0, length - 1, startIndex, endIndex, true);
    }

    /**
     * checkout the contigurous room that from startIndex to endIndex, inclusive
     * @param startIndex
     * @param endIndex
     */
    public void checkout(int startIndex, int endIndex){
        update(tree, 0, 0, length - 1, startIndex, endIndex, false);
    }

    private void update(IntervalNode[] tree, int nodeIndex, int nodeStart, int nodeEnd, int startIndex, int endIndex, boolean isCheckin){
        if(nodeEnd < startIndex || endIndex < nodeStart){ // no intersection, do nothing
            return;
        }

        IntervalNode currNode = tree[nodeIndex];

        if(startIndex <= nodeStart && nodeEnd <= endIndex){ // this segment is included in the target
            if(isCheckin){
                currNode.set(0, 0, 0);
            }else{ //isCheckout
                int roomNumber = nodeEnd - nodeStart + 1;
                currNode.set(roomNumber, roomNumber, roomNumber);
            }

        }else{ // intersection
            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2 + 1;

            update(tree, leftSon, nodeStart, nodeMiddle, startIndex, endIndex, isCheckin);
            update(tree, leftSon + 1, nodeMiddle + 1, nodeEnd, startIndex, endIndex, isCheckin);

            int max = Math.max(Math.max(tree[leftSon].max, tree[leftSon + 1].max), tree[leftSon].last + tree[leftSon + 1].first);
            int last = tree[leftSon+ 1].isEmpty() ? tree[leftSon].last + tree[leftSon + 1].max : tree[leftSon + 1].max;
            int first = tree[leftSon].isEmpty() ? tree[leftSon].max + tree[leftSon + 1].first : tree[leftSon].first;
            currNode.set(max, last, first);
        }
    }

    class IntervalNode{
        int max;  // the max contigurous valid rooms' number
        int last; //the contigurous valid romms's number at the end, 0 if the room is occupied at the interval's end
        int first; //the contigurous valid rooms' number at the start, 0 if the room is occupied at the interval's start

        IntervalNode(int maxContigurous, int last, int first){
            set(maxContigurous, last, first);
        }

        private void set(int max, int last, int first){
            this.max = max;
            this.last = last;
            this.first = first;
        }

        private boolean isEmpty(){
            return max == last;
        }
    }
}

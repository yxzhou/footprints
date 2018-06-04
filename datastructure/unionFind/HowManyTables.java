package fgafa.datastructure.unionFind;

/**
 * Today is Ignatius' birthday. He invites a lot of friends. Now it's dinner time. Ignatius wants to know how many tables he needs at least.
 * You have to notice that not all the friends know each other, and all the friends do not want to stay with strangers.
 * One important rule for this problem is that if I tell you A knows B, and B knows C, that means A, B, C know each other, so they can stay in one table.
 *
 * For example: If I tell you A knows B, B knows C, and D knows E, so A, B, C can stay in one table, and D, E have to stay in the other one. So Ignatius needs 2 tables at least.
 */

public class HowManyTables {

    public int countTables(int numberOfGuest, int[][] friendships){

        if(numberOfGuest < 1 || null == friendships || 0 == friendships.length){
            return numberOfGuest;
        }

        int numberOfTable = numberOfGuest;

        int[] unionIds = new int[numberOfGuest];
        int[] unionSizes = new int[numberOfGuest];
        for(int i = 0; i < numberOfGuest; i++){
            unionIds[i] = i;
            unionSizes[i] = 1;
        }

        for(int[] pair : friendships){
            numberOfTable = quickUnion(unionIds, unionSizes, pair[0], pair[1], numberOfTable);
        }

        return numberOfTable;
    }


    private int quickUnion(int[] unionIds, int[] unionSizes, int p, int q, int numberOfTable){
        int p_unionId = findUnionIdWithPathCompression(unionIds, p);
        int q_unionId = findUnionIdWithPathCompression(unionIds, q);

        if(p_unionId == q_unionId){
            return numberOfTable;
        }

        if(unionSizes[p] < unionSizes[q]){
            unionIds[p_unionId] = q_unionId;
            unionSizes[q_unionId] += unionSizes[p_unionId];
        }else{
            unionIds[q_unionId] = p_unionId;
            unionSizes[p_unionId] += unionSizes[q_unionId];
        }

        return numberOfTable - 1;
    }

    private int findUnionIdWithPathCompression(int[] unionId, int p){
        while(unionId[p] != p){
            unionId[p] = unionId[unionId[p]];

            p = unionId[p];
        }

        return p;
    }

}

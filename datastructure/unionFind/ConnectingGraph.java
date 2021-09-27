package datastructure.unionFind;

/**
 *  Connecting Graph   _https://www.lintcode.com/problem/589
 *
 *  Description
 *  Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.
 *
 *  You need to support the following method:
 *    connect(a, b), add an edge to connect node a and node b`.
 *    query(a, b), check if two nodes are connected
 *
 */

public class ConnectingGraph {
    int n;
    int[] parentIds;
    int[] groupSizes;

    /*
     * @param n: An integer
     */public ConnectingGraph(int n) {
        this.n = n;
        this.parentIds = new int[n + 1]; //default all are 0
        this.groupSizes = new int[n + 1]; //default all are 0

        for(int i = 0; i <= n; i++){
            parentIds[i] = i;
            groupSizes[i] = 1;
        }
    }

    /*
     * @param a: An integer
     * @param b: An integer
     * @return: nothing
     */
    public void connect(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if(groupSizes[pa] <= groupSizes[pb]){
            parentIds[pa] = pb;
            groupSizes[pb] += groupSizes[pa];
        }else{
            parentIds[pb] = pa;
            groupSizes[pa] += groupSizes[pb];
        }
    }

    /*
     * @param a: An integer
     * @param b: An integer
     * @return: A boolean
     */
    public boolean query(int a, int b) {
        return find(a) == find(b);
    }

    private int find(int id){
        while(id != parentIds[id]){
            id = parentIds[id];
        }

        return id;
    }

}

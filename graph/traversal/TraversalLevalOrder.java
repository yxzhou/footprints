package graph.traversal;

import util.Misc;

/**
 *  Given 1 or more directed graphs, get the sequence id.
 *  example
 *    a -> b -> c,  return 2, 1, 0
 *    a -> b -> c, c -> b,  b -> d, return 2, 1, 1, 0
 *
 */

public class TraversalLevalOrder {

    final static int ORDER_ID_DEFAULT = 0;
    final static int ORDER_ID_DEFAULT_LOOP = -1;

    enum STATUS {
        INIT, READING, READ
    }

    public int[] sequence(int[][] damages, int length){
        int[] orders = new int[length]; // default all are 0
        STATUS[] statuses = new STATUS[length];
        int[] groupId = new int[length];
        for(int i = 0; i < length; i++){
            orders[i] = ORDER_ID_DEFAULT;
            statuses[i] = STATUS.INIT;
            groupId[i] = i;
        }

        for(int i = 0; i < length; i++){
            dfs(damages, length, statuses, orders, groupId, i);
        }

        return orders;
    }

    private int dfs(int[][] damages, int length, STATUS[] status, int[] orders, int[] groupId, int i){

        if(status[i] == STATUS.READ){
            return orders[i];
        }

        if(status[i] == STATUS.READING){ //found loop
            return ORDER_ID_DEFAULT_LOOP;
        }

        status[i] = STATUS.READING;
        boolean findLoop = false;
        for(int j = 0; j < length; j++){
            if(j != i && damages[i][j] > 0){
                int orderId = dfs(damages, length, status, orders, groupId, j);

                if( orderId == ORDER_ID_DEFAULT_LOOP || groupId[j] != j){ //loop
                    findLoop = true;
                    groupId[i] = groupId[j];

                    orders[i] = Math.max(orders[i], orders[j]);
                } else{
                    orders[i] = Math.max(orders[i], orders[j] + 1);
                }
            }
        }
        status[i] = STATUS.READ;

        if(findLoop && groupId[i] == i){ //the start of a loop
            for(int k = 0; k < length; k++ ){
                if(groupId[k] == i){
                    orders[k] = orders[i];
                }
            }
        }

        return orders[i];
    }

    public static void main(String[] args){

        String[][] cases = {
                {"070","500","140"},
                {"1542", "7935", "1139", "8882"},
                {"07", "40"},
                {"02", "00"},
                {"0200", "0030", "0234", "0004"},
                {"02000", "00300", "00040", "02005", "00000"},
                {"02000", "00300", "00045", "02000", "00000"},
                {"020000", "003000", "000400", "000056", "020000", "000000"},
                {"020000", "003000", "000400", "020050", "000006", "000400"}

        };

        int[][] expects = {
                {0, 0, 1},
                {0, 0, 0, 0},
                {0, 0},
                {1, 0},
                {2, 1, 1, 0},
                {2, 1, 1, 1, 0},
                {2, 1, 1, 1, 0},
                {2, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0}
        };

        TraversalLevalOrder sv = new TraversalLevalOrder();

        for(int i = 0; i < cases.length; i++){

            String[] currCase = cases[i];
            int length = currCase.length;

            int[][] weights = new int[length][length];

            for(int node = 0; node < length; node++){
                int neighbor = 0;
                for(int w : currCase[node].toCharArray()){
                    weights[node][neighbor++] = w - '0';
                }
            }

            int[] orders = sv.sequence(weights, length);

            System.out.println(String.format("\n%d: %s\n%s\n%b", i, Misc.array2String(cases[i]), Misc.array2String(orders), Misc.compare(expects[i], orders)));

        }
    }

}

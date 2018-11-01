package fgafa.topCoder.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fgafa.util.Misc;

/**
 *
 * Problem Statement http://community.topcoder.com/stat?c=problem_statement&pm=1524&rd=4472
 * =========================
 *
 * You work for a very large company that markets many different products. In some cases, one product you market competes with another.
 * To help deal with this situation you have split the intended consumers into two groups, namely Adults and Teenagers. If your company
 * markets 2 products that compete with each other, selling one to Adults and the other to Teenagers will help maximize profits. Given a
 * list of the products that compete with each other, you are going to determine whether all can be marketed such that no pair of competing
 * products are both sold to Teenagers or both sold to Adults.
 *
 * If such an arrangement is not feasible your method will return -1. Otherwise, it should return the number of possible ways of marketing
 * all of the products.
 *
 * The products will be given in a String[] compete whose kth element describes product k. The kth element will be a single-space delimited
 * list of integers. These integers will refer to the products that the kth product competes with. For example:
 *            compete = {"1 4", "2","3", "0",  ""}
 * The example above shows product 0 competes with 1 and 4, product 1 competes with 2, product 2 competes with 3, and product 3 competes with
 * 0. Note, competition is symmetric so product 1 competing with product 2 means product 2 competes with product 1 as well.
 *
 * Ways to market:
 *  1) 0 to Teenagers, 1 to Adults, 2 to Teenagers, 3 to Adults, and 4 to Adults
 *  2) 0 to Adults, 1 to Teenagers, 2 to Adults, 3 to Teenagers, and 4 to Teenagers
 * Your method would return 2.
 *
 *
 * Constraints
 * -	compete will contain between 1 and 30 elements, inclusive.
 * -	Each element of compete will have between 0 and 50 characters, inclusive.
 * -	Each element of compete will be a single space delimited sequence of integers such that:
 * 		All of the integers are unique.
 * 		Each integer contains no extra leading zeros.
 *      Each integer is between 0 and k-1 inclusive where k is the number of elements in compete.
 * -	No element of compete contains leading or trailing whitespace.
 * -	Element i of compete will not contain the value i.
 * -	If i occurs in the jth element of compete, j will not occur in the ith element of compete.
 *
 * Examples
 * 0) {"1 4","2","3","0",""}  Returns: 2
 * The example from above.
 * 
 * 1){"1","2","0"} Returns: -1
 * Product 0 cannot be marketed with product 1 or 2. Product 1 cannot be marketed with product 2. There is no way to achieve a viable marketing scheme.
 * 
 * 2){"1","2","3","0","0 5","1"} Returns: 2
 * 
 * 3){"","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""} Returns: 1073741824
 * 
 * 4){"1","2","3","0","5","6","4"} Returns: -1
 *
 * Thoughts:
 *   It's to find all the connected component, and to every component, check if it can set value to every vertex and make 2 adjacent vertex can not be set the same value.
 *
 *   The input is a directed graph. It would be simple to change it to undirect graph
 *
 *
 */

public class Marketing {

    enum VALUE {
        ADULT, TEENAGE;
    }

    enum STATUS {
        READING, READ
    }

    /**
     *
     *
     * @param compete
     * @return
     */
    public long howMany(String[] compete){
        if(null == compete || compete.length == 0){
            return -1;
        }

        int length = compete.length;
        VALUE[] values = new VALUE[length]; //default all null

        Map<Integer, List<Integer>> competeList = new HashMap<>();
        for(int i = 0; i < length; i++) {
            List<Integer> neighbors = new ArrayList<>();
            competeList.put(i, neighbors);

            for(String neighbor : compete[i].split(" ")){
                if (neighbor.trim().isEmpty()) {
                    continue;
                }
                neighbors.add(Integer.parseInt(neighbor));
            }
        }

        int count = 0;
        for(int i = 0; i < length; i++){
            if(values[i] == null){
                count++;
                values[i] = VALUE.ADULT;

                if(pushDown_dfs(competeList, i, values)){
                    return -1;
                }

                for(int j = 0; j < length; j++){
                    if(pushUp_dfs(competeList, j, values, new STATUS[length])){
                        return -1;
                    }
                }
            }
        }

        return 1L << count;
    }

    /**
     * @return true, when there is something wrong
     */
    private boolean pushDown_dfs(Map<Integer, List<Integer>> compete, int i, VALUE[] values){
        VALUE expect = (values[i] == VALUE.ADULT ? VALUE.TEENAGE : VALUE.ADULT);

        for(int neighbor : compete.get(i)){
            if(values[neighbor] != null && values[neighbor] != expect){
                return true;
            }

            if(values[neighbor] == null){
                values[neighbor] = expect;

                if(pushDown_dfs(compete, neighbor, values)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return true, when there is something wrong
     */
    private boolean pushUp_dfs(Map<Integer, List<Integer>> compete, int i, VALUE[] values, STATUS[] statuses){
        if(values[i] != null || statuses[i] != null){
            return false;
        }

        boolean needPushDown = false;
        statuses[i] = STATUS.READING;
        for(int neighbor : compete.get(i)){
            if(pushUp_dfs(compete, neighbor, values, statuses)){
                return true;
            }

            if(values[neighbor] == null){
                needPushDown = true;
                continue;
            }

            VALUE expect = (values[neighbor] == VALUE.ADULT ? VALUE.TEENAGE : VALUE.ADULT);
            if(values[i] != null && values[i] != expect){
                return true;
            }

            values[i] = expect;
        }
        statuses[i] = STATUS.READ;

        if(needPushDown && values[i] != null){
            if(pushDown_dfs(compete, i, values)){
                return true;
            }
        }

        return false;
    }

    /**
     *  convert it to UDG,
     *  count the connected components, and check if the connected component is valid for the rule
     *
     */
    public long howMany_n(String[] compete) {
        if (null == compete || compete.length == 0) {
            return -1;
        }

        int length = compete.length;
        VALUE[] values = new VALUE[length]; //default all null

        int[][] competeMatrix = new int[length][length]; //default all are 0
        for(int i = 0; i < length; i++) {
            for(String neighbor : compete[i].split(" ")){
                if (neighbor.trim().isEmpty()) {
                    continue;
                }
                int j = Integer.parseInt(neighbor);

                competeMatrix[i][j] = 1;
                competeMatrix[j][i] = 1;
            }
        }

        int count = 0;
        for(int i = 0; i < length; i++){
            if(values[i] == null){
                count++;
                values[i] = VALUE.ADULT;

                if(connect(competeMatrix, length, i, values)){
                    return -1;
                }
            }
        }

        return 1L << count; //note, 1L << count vs 1 << count
    }

    private boolean connect(int[][] competeMatrix, int length, int i, VALUE[] values){
        VALUE expect = (values[i] == VALUE.ADULT ? VALUE.TEENAGE : VALUE.ADULT);

        for(int j = 0; j < length; j++){
            if(competeMatrix[i][j] == 0){
                continue;
            }

            if(values[j] != null && values[j] != expect){
                return true;
            }

            if(values[j] == null){
                values[j] = expect;

                if(connect(competeMatrix, length, j, values)){
                    return true;
                }
            }
        }

        return false;
    }


    public static void main(String[] args){
//        System.out.println(Misc.array2String("".split(" ")));
//        System.out.println(Misc.array2String(" ".split(" ")));
//        System.out.println(Misc.array2String("a b".split(" ")));
//
        System.out.println(1L << 1);
        System.out.println(1L << 32);

        String[][] cases = {
                {"1 4","2","3","0",""},
                {"1","2","0"},
                {"1","2","3","0","0 5","1"},
                {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""},
                {"1","2","3","0","5","6","4"}
        };

        int[] expects = {
                2,
                -1,
                2,
                1073741824,
                -1
        };

        Marketing sv = new Marketing();

        for(int i = 0; i < cases.length; i++){
            long result1 = sv.howMany(cases[i]);
            long result2 = sv.howMany_n(cases[i]);

            System.out.println(String.format("\n%d: [%s]\n %b, %b", i, Misc.array2String(cases[i]), (result1 == expects[i]), (result2 == expects[i])));

            if(result1 != expects[i] || result2 != expects[i]){
                System.out.println(String.format("%d\n%d\n%d", expects[i], result1, result2) );
            }
        }

    }

}

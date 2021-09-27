package todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * Q: 一群朋友去度假，有时互相借钱。

例如，爱丽丝为比尔的午餐支付了 10 美元。后来克里斯给爱丽丝 5 美元搭出租车。我们可以假设每笔交易为一个三元组(X,Y,Z)，这意味着第 X 个人借给第 Y 个人 Z 美元。
假设 Alice，Bill 和 Chris 是第0，1，2  个人（0，1，2是他们的ID），他们之间的交易可以表示为[ [ 0，1，10 ]，[ 2，0，5 ] ]。

给定一组人之间的交易清单，返回结算所需的最低交易数量。

样例 1

输入 [[0,1,10], [2,0,5]]

输出 2

样例解释
第 0 个人借给第 1 个人10美元
第 2 个人借给第 0 个人 5美元

需要2笔交易，其中一种方式是第1个人还给第0个人和第2个人各5美元。

样例 2
输入 [[0,1,10], [1,0,1], [1,2,5], [2,0,5]]

输出 1

样例解释
第0个人借给第1个人10美元
第1个人借给第0个人1美元
第1个人借给第2个人5美元
第2个人借给第0个人5美元

只需要1笔交易，第1个人还给第0个人4美元债务就还清了。
 *
 */

public class MinimumTransfers {
	
    /* 
     * 1, If a person's indegree and outdegree are equal, this person needn't be involved any transactions.
     * 
     * 2, After filter out the above person, get a array to record every person's indegree - outdegree, such as [3, 2, -3, -2]
     * 2.1, to any above persons' data A, the sum of A[i] are always 0 
     * 2.2, to any above persons' data A, if there is a sub combination B whose sum is 0, the sum of the rest persons' data is 0
     * the MT (minimum transfers), MT(A) = MT(B) + MT(C)
     * 2.3, to any above persons' data A, if there is no the above sub combination, MT(A) == A.size() - 1. 
     * 
     * example: MT[3, 2, -3, -2] = MT[3,-3] + MT[2,-2] = 1 + 1 = 2
     * 
     * the problem is to find out the max sub group whose sum is 0. Totally the sub group can be 2^(A.size()) - 1
     * 
     */
	public int minTransfers_dp(int[][] transactions){
		//check input
		if(null == transactions || 0 == transactions.length || 0 == transactions[0].length){
			return -1;
		}
		
		//calculate all person's financial status (indegree - outdegree)
		Map<Integer, Integer> debt = new HashMap<>();
		for(int[] t : transactions){
		    debt.put(t[0], debt.getOrDefault(t[0], 0) - t[2]);
		    debt.put(t[1], debt.getOrDefault(t[1], 0) + t[2]);
		}
				
		//filter out the person whose indegree and outdegree are equal
		List<Integer> nums = new ArrayList<>();
		for(Integer person : debt.keySet()){
		    if(0 != debt.get(person)){
		        nums.add(debt.get(person));
		    }
		}
				
		if(nums.isEmpty()){
		    return 0;
		}
		
		//the sub group is a combination, the amount of such combination is 1 << nums.size
		int[] mt = new int[1 << nums.size()]; //default all are 0
		for(int i = 1; i < mt.length; i++){
		    int sum = 0; 
		    int count = 0;
		    
		    //to every combination, calculate the sub sum
		    for(int j = 0; j < nums.size() && ((1 << j) & i) != 0; j++){
		        sum += nums.get(j);
		        count++;
		    }
		        
		    if( 0 == sum){
		        mt[i] = count - 1; // mt[i] > 0 when 0 == sum 
		        //to such combination, calculate the minimum transfers
		        for(int k = 1, limit = (i >> 1); k <= limit && (k & i) == k && mt[k] > 0; k++ ){
		            mt[i] = Math.min(mt[i], mt[k] + mt[i - k]);
		        }
		    }    
		}
		
		return mt[mt.length - 1];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

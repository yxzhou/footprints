package MyBloomFilter;


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
	
	public int minTransfers(int[][] transactions){
		//check input
		if(null == transactions || 0 == transactions.length || 0 == transactions[0].length){
			return -1;
		}
		
		//todo
				
				
		return -1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

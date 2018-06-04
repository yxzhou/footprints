package fgafa.sorting.median;

public class MedianInNBigFiles {
    
    /*
     * Q: 一共有N个机器，每个机器上有N个数。每个机器最多存O(N)个数并对它们操作。如何找到个数中的中数? 
     * 
     * A1: 先大体估计一下这些数的范围，比如这里假设这些数都是32位无符号整数（共有个）。我们把0到的整数划分为N个范围段，每个段包含个整数。比如，第一个段位0到，第二段为到，…，第N个段为到。
     *     然后，扫描每个机器上的N个数，把属于第一个区段的数放到第一个机器上，属于第二个区段的数放到第二个机器上，…，属于第N个区段的数放到第N个机器上。注意这个过程每个机器上存储的数应该是O(N)的。
     *     下面我们依次统计每个机器上数的个数，依次累加，直到找到第k个机器，在该机器上累加的数大于或等于，而在第k-1个机器上的累加数小于，并把这个数记为x。那么我们要找的中位数在第k个机器中，排在第位。
     *     然后我们对第k个机器的数排序，并找出第个数，即为所求的中位数。复杂度是的 O(n*n)。
     * A2:：先对每台机器上的数进行排序。排好序后，to the median in every machine, findmedianEqualLength。
     *      如果m=(n*n)/2，则返回m, 
     *      否则 如果L集合里的数多余一半，则从L集合中找出第n/2小的数 
     *          如果R集合里的数多余一半，R集合的元素个数为k, 则从R集合中找出第k-n/2小的数. 
     *          如此重复迭代调用，直至找到中值.
     *     
     *     Time O(n*n*log(n*n)) 
     */
    public void findMedianInNFile() {
      //TODO
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}

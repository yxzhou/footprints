package fgafa.sorting.median;

import java.util.Hashtable;

import fgafa.util.Misc;

public class WeightedMedian {
    /*
     * weighted (lower) median,  (it's from quick sort + DP)
     * 
     * what's weighted median: 
     * For n distinct x_1, x_2, ---, x_n with positive weight w_1, w_2, ---, w_n. sum(w_1,w_2,---, w_n)=1. 
     * The weighted median is the element x_k satisfying:
     *   1)  sum(w_i) < 1/2  when x_i < x_k
     *   2)  sum(w_i) <=1/2  when x_i > x_k
     * 
     * refer to http://blog.csdn.net/yysdsyl/article/details/4266822
     * 
     * 士兵站队问题
     * 在一个划分成网格的操场上，n个士兵散乱地站在网格点上。网格点由整数坐标(x,y)表示。
     * 士兵们可以沿网格边上、下、左、右移动一步，但在同一时刻任一网格点上只能有一名士兵。
     * 按照军官的命令，士兵们要整齐地列成一个水平队列，即排列成(x,y),(x+1,y),…,(x+n-1,y)。
     * 如何选择x和y的值才能使士兵们以最少的总移动步数排成一列
     * 
     * Time O()
     */
    
   public int WeightedMedian(int seq[], double weight[]){
       System.out.println(" seq[]: " + Misc.array2String(seq) + " weight[]: " + Misc.array2String(weight));
     
       Hashtable<String, String> ht = new Hashtable<String, String>(); // key=seq, value=weight
       for(int i=0; i<seq.length; i++)
         ht.put(String.valueOf(seq[i]), String.valueOf(weight[i]));  // it's hidden trouble if seq[2]==seq[3] and weight[2] != weight[3]
   
       return WeightedMedian(ht, seq, 0, seq.length - 1);
   }

    private int WeightedMedian(Hashtable<String, String> ht, int seq[], int p, int r) {
      //
      
      int q = partition(seq, p, r);
      
      System.out.println("q:" + q +" seq[]: " + Misc.array2String(seq));
      
      double WL = 0.0, WR = 0.0, tmp = 0.0;
      for (int i = p; i < q; i++)
        WL += Float.valueOf(ht.get(String.valueOf(seq[i])));
      
      for (int i = q + 1; i <= r; i++)
        WR += Float.valueOf(ht.get(String.valueOf(seq[i])));
      
      if (WL < 0.5 && WR <= 0.5)
        return seq[q];
      else if (WL >= 0.5) {
        tmp = Float.valueOf(ht.get(String.valueOf(seq[q])));
        tmp += WR;
        ht.put(String.valueOf(seq[q]), String.valueOf(tmp));
        return WeightedMedian(ht, seq, p, q);
      }
      else {
        tmp = Float.valueOf(ht.get(String.valueOf(seq[q])));
        tmp += WL;
        ht.put(String.valueOf(seq[q]), String.valueOf(tmp));
        return WeightedMedian(ht, seq, q, r);
      }
    }

    private static int partition(int[] array, int begin, int end) {

      int index = begin + (int) (Math.random() * ((end - begin) + 1));  // get a random in [begin, end] 

      int pivot = array[index];
      Misc.swap(array, index, end);
      //index is in the end of the smaller elements, so find a smaller element, swap it to the index, then index + 1   
      for (int i = index = begin; i < end; ++i) { 
        if (array[i] <= pivot) {
          Misc.swap(array, index++, i);
        }
      }
      Misc.swap(array, index, end);
      return (index);
    }
    /*
     * Wrong !! 
     * 
     * 
     */
    protected int WeightedMedian(int seq[], double weight[], int p, int r) {
      //
      
      int q = partition(seq, p, r);
      
      System.out.println("q:" + q +" seq[]: " + Misc.array2String(seq));
      
      double WL = 0.0, WR = 0.0;
      for (int i = p; i < q; i++)
        WL += weight[i];
      
      for (int i = q + 1; i <= r; i++)
        WR += weight[i];
      
      if (WL < 0.5 && WR < 0.5)
        return seq[q];
      else if (WL > 0.5) {
        seq[q] += WR;
        return WeightedMedian(seq, weight, p, q);
      }
      else {
        seq[q] += WL;
        return WeightedMedian(seq, weight, q, r);
      }
    }
    
    public static void main(String[] args){
        WeightedMedian sv = new WeightedMedian();
        
        System.out.println("\n =====WeightedMedian =====");
        int seq[]={1,3,2,15,4,18,6,9};
        int[] seq2= seq.clone();
        double w[]={0.1,0.1,0.05,0.2,0.1,0.25,0.1,0.1};
        System.out.println("weighted median of "+Misc.array2String(seq)+" and "+Misc.array2String(w)+" is : "+ sv.WeightedMedian(seq, w));
        System.out.println("weighted median of "+Misc.array2String(seq2)+" and "+Misc.array2String(w)+" is : "+ sv.WeightedMedian(seq2, w, 0, seq.length - 1));

    }
    
}

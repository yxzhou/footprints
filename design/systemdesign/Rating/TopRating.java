package design.systemdesign.Rating;

import java.util.*;
import java.util.stream.Collectors;


/**
 * The user will give rate on product that for sale in Amazon.
 * Design a system,  implement the following API
 *
 *  void rate(int productId, int userId, int rateScore)
 *  List<Integer> getTop5(); // return top5 rating product id in the past one minute
 *
 */

public class TopRating {

    class Rate implements Comparable<Rate>{
        private int productId;

        private int count = 0;
        private int sum = 0;

        private float rate;

        Rate(int productId){
            this.productId = productId;
        }

        public void addRate(int rateScore){
            this.count++;
            this.sum += rateScore;

            calculateRate();
        }

        private void calculateRate(){
            this.rate = (float)sum / count;
        }

        public float getRate(){
            return rate;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Rate){
                return ((Rate) obj).productId == this.productId;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return productId;
        }

        @Override
        public int compareTo(Rate o) {
            return Float.compare(this.getRate(), o.getRate());
        }

        @Override
        public String toString() {
            return String.format("%d-%f", this.productId, this.getRate());
        }
    }

    Map<Integer, Rate> map;

    PriorityQueue<Rate> minHeap;
    Set<Integer> set;

    public TopRating(){
        map = new HashMap();

        minHeap = new PriorityQueue<>();
        set = new HashSet<>();
    }


    public void rate(int productId, int userId, int rateScore){
        map.putIfAbsent(productId, new Rate(productId));
        Rate rate = map.get(productId);

        if(set.contains(productId)){
            minHeap.remove(rate);
            set.remove(productId);
        }

        rate.addRate(rateScore);

        if(minHeap.size() < 3 ){
            minHeap.add(rate);
            set.add(productId);
        }else if(minHeap.peek().getRate() < rate.getRate()){
            set.remove(minHeap.peek().productId);
            minHeap.poll();

            minHeap.add(rate);
            set.add(productId);
        }
    }

    public List<Rate> getTop5(){
       //List<Integer> result = minHeap.stream().sorted().map(r -> r.productId).collect(Collectors.toList());
        List<Rate> result = minHeap.stream().sorted().collect(Collectors.toList());

       return result;
    }


    public static void main(String[] args){

        TopRating sv = new TopRating();

        sv.rate(1, 100, 3);
        sv.rate(2, 100, 3);
        sv.rate(3, 100, 3);
        sv.rate(4, 100, 3);

        sv.rate(4, 100, 5);
        sv.rate(2, 100, 4);

        System.out.println(sv.getTop5());  //[3, 4, 2]

        sv.rate(4, 100, 3);
        System.out.println(sv.getTop5()); //[]


        sv.rate(4, 100, 3);
        System.out.println(sv.getTop5());

        sv.rate(4, 100, 3);
        System.out.println(sv.getTop5());

        sv.rate(3, 100, 5);
        System.out.println(sv.getTop5());
    }

}

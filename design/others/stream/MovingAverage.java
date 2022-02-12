/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design.others.stream;

/**
 * _https://www.lintcode.com/problem/642
 *
 * Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
 *
 * Example Example 1:
 * MovingAverage m = new MovingAverage(3); 
 * m.next(1) = 1 // return 1.00000 
 * m.next(10) = (1 + 10) / 2 // return 5.50000
 * m.next(3) = (1 + 10 + 3) / 3 // return 4.66667 
 * m.next(5) = (10 + 3 + 5) / 3 // return 6.00000
 *
 */
public class MovingAverage {
    int[] datas;  //all datas in the sliding window, it's a circular array.
    int tail;

    long sum;
    int size;
    /*
    * @param size: An integer
    */
    public MovingAverage(int size) {
        datas = new int[size];
        tail = 0;

        sum = 0;
        this.size = 0;
    }

    /*
     * @param val: An integer
     * @return:  
     */
    public double next(int val) {
        if(size == datas.length){
            sum -= datas[tail];
            size--;
        }

        sum += val;
        datas[tail] = val;
        tail = (tail + 1) % datas.length; 

        size++;
        return (double)sum / size;
    }
    
    
}

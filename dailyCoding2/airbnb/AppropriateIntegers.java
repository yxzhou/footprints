package fgafa.dailyCoding2.airbnb;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by Airbnb.
 *
 * You are given an array X of floating-point numbers x1, x2, ... xn. These can be rounded up or down to create a corresponding array Y of integers y1, y2, ... yn.
 *
 * Write an algorithm that finds an appropriate Y array with the following properties:
 *
 * The rounded sums of both arrays should be equal.
 * The absolute pairwise difference between elements is minimized. In other words, |x1- y1| + |x2- y2| + ... + |xn- yn| should be as small as possible.
 * For example, suppose your input is [1.3, 2.3, 4.4]. In this case you cannot do better than [1, 2, 5], which has an absolute difference of |1.3 - 1| + |2.3 - 2| + |4.4 - 5| = 1.
 *
 */

public class AppropriateIntegers {

    class Entity{
        int i;
        float value;

        Entity(int i, float v){
            this.i = i;
            this.value = v;
        }
    }

    public int[] buildAppropriateArray(float[] floats){
        if(null == floats){
            return null;
        }

        if(0 == floats.length){
            return new int[0];
        }

        int size = floats.length;
        int[] result = new int[size];

        float sumOfFloat = 0;
        int sumOfInteger = 0;

        PriorityQueue<Entity>  minHeap = new PriorityQueue<>(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return Float.compare(o1.value, o2.value);
            }
        });

        PriorityQueue<Entity>  maxHeap = new PriorityQueue<>(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return Float.compare(o2.value, o1.value);
            }
        });

        for(int i = 0; i < size; i++){
            result[i] = Math.round(floats[i]);

            sumOfFloat += floats[i];
            sumOfInteger += result[i];

            if(floats[i] < result[i]){
                minHeap.add(new Entity(i, floats[i] + 1 - result[i]));
            }else{
                maxHeap.add(new Entity(i, floats[i] - result[i]));
            }
        }

        int diff = Math.round(sumOfFloat) - sumOfInteger;
        if(diff > 0){
            for(int i = diff; i > 0; i--){
                Entity entity = maxHeap.poll();
                result[entity.i]++;
            }
        }else if(diff < 0){
            for(int i = -diff; i > 0; i--){
                Entity entity = minHeap.poll();
                result[entity.i]--;
            }
        }

        return result;
    }


    @Test
    public void test(){
        Assert.assertArrayEquals(new int[]{1, 2, 5}, buildAppropriateArray(new float[]{1.3f, 2.3f, 4.4f}));
        Assert.assertArrayEquals(new int[]{1, 3, 5}, buildAppropriateArray(new float[]{1.6f, 2.8f, 4.7f}));

    }

}

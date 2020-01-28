package fgafa.basic;

import org.junit.Test;

import java.util.Arrays;

public class ComparatorTest {

    @Test
    public void test(){

        int[][] people = {{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}};
        Arrays.stream(people).forEach(a -> {System.out.print(Arrays.toString(a));});
        System.out.println();

        //Arrays.sort(d, (a, b) -> {return a[1] == b[1] ? a[0] - b[0] : a[1] - b[1];});
        Arrays.sort(people, (a, b) -> {return a[0] == b[0] ? a[1] - b[1] : b[0] - a[0];});

        //System.out.println(Arrays.toString(d));
        Arrays.stream(people).forEach(a -> {System.out.print(Arrays.toString(a));});
        System.out.println();

        for(int i = 0, j = 0; i < people.length; i++){
            j = people[i][1];
            if(i == j){
                continue;
            }

            //insert people[i] to position j, move people[j, i - 1] to [j + 1, i]
            int[] tmp = people[i];

            System.arraycopy(people, j, people, j + 1, i - j);

            people[j] = tmp;
        }

        Arrays.stream(people).forEach(a -> {System.out.print(Arrays.toString(a));});

        int[] p = new int[256];
        Arrays.fill(p, 0);

    }

}

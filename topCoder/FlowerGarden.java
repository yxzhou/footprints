package topCoder;

import util.Misc;

import java.util.*;


/**
 *
 * Problem Statement
 *
 * You are planting a flower garden with bulbs to give you joyous flowers throughout the year. However,
 * you wish to plant the flowers such that they do not block other flowers while they are visible.
 *
 * You will be given a int[] height, a int[] bloom, and a int[] wilt. Each type of flower is represented
 * by the element at the same index of height, bloom, and wilt. height represents how high each type of
 * flower grows, bloom represents the morning that each type of flower springs from the ground, and wilt
 * represents the evening that each type of flower shrivels up and dies. Each element in bloom and wilt
 * will be a number between 1 and 365 inclusive, and wilt[i] will always be greater than bloom[i]. You
 * must plant all of the flowers of the same type in a single row for appearance, and you also want to
 * have the tallest flowers as far forward as possible. However, if a flower type is taller than another
 * type, and both types can be out of the ground at the same time, the shorter flower must be planted in
 * front of the taller flower to prevent blocking. A flower blooms in the morning, and wilts in the evening,
 * so even if one flower is blooming on the same day another flower is wilting, one can block the other.
 *
 * You should return a int[] which contains the elements of height in the order you should plant your
 * flowers to acheive the above goals. The front of the garden is represented by the first element in
 * your return value, and is where you view the garden from. The elements of height will all be unique,
 * so there will always be a well-defined ordering.
 *
 *
 * Constraints
 *   - height will have between 2 and 50 elements, inclusive.
 *   - bloom will have the same number of elements as height
 *   - wilt will have the same number of elements as height
 *   - height will have no repeated elements.
 *   - Each element of height will be between 1 and 1000, inclusive.
 *   - Each element of bloom will be between 1 and 365, inclusive.
 *   - Each element of wilt will be between 1 and 365, inclusive.
 *   - For each element i of bloom and wilt, wilt[i] will be greater than bloom[i].
 *
 * Examples
 *
 * 0)	{5,4,3,2,1} {1,1,1,1,1} {365,365,365,365,365}
 * Returns: { 1,  2,  3,  4,  5 }
 * These flowers all bloom on January 1st and wilt on December 31st. Since they all may block each
 * other, you must order them from shortest to tallest.
 *
 * 1)	{5,4,3,2,1} {1,5,10,15,20} {4,9,14,19,24}
 * Returns: { 5,  4,  3,  2,  1 }
 * The same set of flowers now bloom all at separate times. Since they will never block each other,
 * you can order them from tallest to shortest to get the tallest ones as far forward as possible.
 *
 * 2)   {5,4,3,2,1} {1,5,10,15,20} {5,10,15,20,25}
 * Returns: { 1,  2,  3,  4,  5 }
 * Although each flower only blocks at most one other, they all must be ordered from shortest to
 * tallest to prevent any blocking from occurring.
 *
 * 3)	{5,4,3,2,1} {1,5,10,15,20} {5,10,14,20,25}
 * Returns: { 3,  4,  5,  1,  2 }
 * The difference here is that the third type of flower wilts one day earlier than the blooming of
 * the fourth flower. Therefore, we can put the flowers of height 3 first, then the flowers of height 4,
 * then height 5, and finally the flowers of height 1 and 2. Note that we could have also ordered them
 * with height 1 first, but this does not result in the maximum possible height being first in the garden.
 *
 * 4)	{1,2,3,4,5,6}{1,3,1,3,1,3} {2,4,2,4,2,4}
 * Returns: { 2,  4,  6,  1,  3,  5 }
 *
 * 5)	{3,2,5,4}{1,2,11,10}{4,3,12,13}
 * Returns: { 4,  5,  2,  3 }
 *
 */

public class FlowerGarden {

    public int[] getOrdering(int[] height, int[] bloom, int[] wilt){

        Wrapper[] types = new Wrapper[height.length];

        for(int i = 0; i < height.length; i++){
            types[i] = new Wrapper(height[i], bloom[i], wilt[i]);
        }

        Arrays.sort(types, (Wrapper o1, Wrapper o2) -> {
                    if (o1.bloom == o2.bloom) {
                        return o1.wilt == o2.wilt ? o2.height - o1.height : o1.wilt - o2.wilt;
                    }

                    return o1.bloom - o2.bloom;
                }
        );

        List<Group> groups = new ArrayList<>();
        Group currGroup = new Group(0, 0, 0);

        for(Wrapper type : types){
            if(currGroup.end < type.bloom){
                currGroup = new Group(type.bloom, type.wilt, type.height);
                groups.add(currGroup);
            }else{
                currGroup.end = type.wilt;
                currGroup.maxHeight = Math.max(currGroup.maxHeight, type.height);
            }
            currGroup.types.add(type);
        }

//        Collections.sort(groups, (Group g1, Group g2) -> {
//            return g2.maxHeight - g1.maxHeight;
//        });
        Collections.sort(groups, Comparator.comparing(Group::getMaxHeight, Comparator.reverseOrder()));

        int[] result = new int[height.length];
        int i = 0;
        for (Group group : groups){
//            Collections.sort(group.types, (Wrapper w1, Wrapper w2) -> {
//                return w1.height - w2.height;
//            });
            Collections.sort(group.types, Comparator.comparing(Wrapper::getHeight));

            for (Wrapper type : group.types){
                result[i++] = type.height;
            }
        }
        return result;
    }

    class Wrapper{
        int bloom;
        int wilt;

        int height;
        public int getHeight() {
            return height;
        }

        Wrapper(int height, int bloom, int wilt){
            this.height = height;
            this.bloom = bloom;
            this.wilt = wilt;
        }
    }

    class Group{
        int start;
        int end;

        int maxHeight;
        public int getMaxHeight() {
            return maxHeight;
        }

        List<Wrapper> types = new ArrayList<>();

        Group(int start, int end, int maxHeight){
            this.start = start;
            this.end = end;
            this.maxHeight = maxHeight;
        }
    }

    public static void main(String[] args){
        int[][][] cases = {
                {{5, 4, 3, 2, 1}, {1, 1, 1, 1, 1}, {365, 365, 365, 365, 365}, {1, 2, 3, 4, 5}},
                {{5, 4, 3, 2, 1}, {1, 5, 10, 15, 20}, {4, 9, 14, 19, 24}, {5, 4, 3, 2, 1}},
                {{5, 4, 3, 2, 1}, {1, 5, 10, 15, 20}, {5, 10, 15, 20, 25}, {1, 2, 3, 4, 5}},
                {{5, 4, 3, 2, 1}, {1, 5, 10, 15, 20}, {5, 10, 14, 20, 25}, {3, 4, 5, 1, 2}},
                {{1, 2, 3, 4, 5, 6}, {1, 3, 1, 3, 1, 3}, {2, 4, 2, 4, 2, 4}, {2, 4, 6, 1, 3, 5}},
                {{3, 2, 5, 4}, {1, 2, 11, 10}, {4, 3, 12, 13}, {4, 5, 2, 3}}
        };

        FlowerGarden sv = new FlowerGarden();


        for(int i = 0; i < cases.length; i++){

            int[] result = sv.getOrdering(cases[i][0], cases[i][1], cases[i][2]);

            System.out.println(String.format("\n=%s \n %s \n %s \n-%s \n %s \t %b",
                    Misc.array2String(cases[i][0]),
                    Misc.array2String(cases[i][1]),
                    Misc.array2String(cases[i][2]),
                    Misc.array2String(cases[i][3]),
                    Misc.array2String(result),
                    isSame(result, cases[i][3])));

        }


    }

    private static boolean isSame(int[] a1, int[] a2){
        if(a1.length != a2.length){
            return false;
        }

        for(int i = 0; i < a1.length; i++){
            if(a1[i] != a2[i]){
                return false;
            }
        }

        return true;
    }
}

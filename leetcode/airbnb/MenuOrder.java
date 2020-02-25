package fgafa.leetcode.airbnb;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 点菜，菜价格为double，问如何正好花完手里的钱
 * 解法：把菜单价格*100转成整数，题目转换成leetcode 40.Combination Sum II（https://leetcode.com/problems/combination-sum-ii/?tab=Description）
 *
 */

public class MenuOrder {


    public List<List<Double>> menuOrder(double[] dish, double money) {
        List<List<Double>> result = new ArrayList<>();
        if (dish == null || dish.length < 1 || money < 1) {
            return result;
        }

        int length = dish.length;
        int[] nums = new int[length];
        for (int i = 0; i < length; i++) {
            nums[i] = (int) (dish[i] * 100);
        }
        Arrays.sort(nums);

        helper(result, new ArrayList<Double>(), nums, 0, (int) (money * 100));

        return result;
    }

    void helper(List<List<Double>> result, List<Double> path, int[] dish, int index, int money) {
        if (money == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (index == dish.length || money < 0 || money < dish[index]) {
            return;
        }

        for (int i = index; i < dish.length; i++) {
            if (i > index && dish[i] == dish[i - 1]) { //avoid duplicate
                continue;
            }

            path.add((double) dish[i] / 100);

            helper(result, path, dish, i + 1, money - dish[i]);

            path.remove(path.size() - 1);
        }
    }

    @Test
    public void test(){

    }

}

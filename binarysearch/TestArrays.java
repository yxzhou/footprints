package binarysearch;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestArrays {


    @Test
    /**
     * Arrays.binarySearch(int[] arr, key)
     *
     * return:
     * index of the search key, if it is contained in the array;
     * otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array:
     * the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key.
     * Note that this guarantees that the return value will be >= 0 if and only if the key is found.
     *
     */
    public void testBinarySearch() {
        int[] arr = {1};

        Assert.assertEquals(-1, Arrays.binarySearch(arr, 0));
        Assert.assertEquals(0, Arrays.binarySearch(arr, 1));

        arr = new int[]{1, 4};
        Assert.assertEquals(-1, Arrays.binarySearch(arr, 0));
        Assert.assertEquals(0, Arrays.binarySearch(arr, 1));
        Assert.assertEquals(-2, Arrays.binarySearch(arr, 2));
        Assert.assertEquals(-2, Arrays.binarySearch(arr, 3));
    }

}

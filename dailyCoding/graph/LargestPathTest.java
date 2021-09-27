package dailyCoding.graph;

import org.junit.Assert;
import org.junit.Test;

public class LargestPathTest {
    @Test public void test(){

        LargestPaths sv = new LargestPaths();

        String vertex = "ABACA";
        int[][] edges = { {0, 1}, {0, 2}, {2, 3}, {3, 4} };
        Assert.assertEquals(Integer.valueOf(3), sv.largestPath(vertex, edges));

        vertex = "A";
        edges = new int[][]{{0, 0}};
        Assert.assertEquals(null, sv.largestPath(vertex, edges));
    }

}

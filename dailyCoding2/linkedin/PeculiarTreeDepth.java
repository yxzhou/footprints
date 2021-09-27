package dailyCoding2.linkedin;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * This problem was asked by LinkedIn.
 *
 * You are given a binary tree in a peculiar string representation. Each node is written in the form (lr), where l corresponds to the left child and r corresponds to the right child.
 *
 * If either l or r is null, it will be represented as a zero. Otherwise, it will be represented by a new (lr) pair.
 *
 * Here are a few examples:
 *
 * A root node with no children: (00)
 * A root node with two children: ((00)(00))
 * An unbalanced tree with three consecutive left children: ((((00)0)0)0)
 * Given this representation, determine the depth of the tree.
 *
 */

public class PeculiarTreeDepth {

    public int getDepth(String peculiar){
        if(null == peculiar || peculiar.isEmpty()){
            return 0;
        }

        int max = 0;
        int open = 0;
        for(char c : peculiar.toCharArray()){
            switch (c){
                case '(':
                    open++;
                    max = Math.max(max, open);
                    break;
                case ')':
                    open--;
                    break;
                case '0':
                    //ignore
                    break;
                default:
                    throw new IllegalArgumentException("The input includes invalid character " + c);
                    //break;
            }
        }

        return max;
    }

    @Test
    public void test(){
        Assert.assertTrue( 1 == getDepth("(00)"));
        Assert.assertTrue( 2 == getDepth("((00)(00))"));
        Assert.assertTrue( 4 == getDepth("((((00)0)0)0)"));
    }
}

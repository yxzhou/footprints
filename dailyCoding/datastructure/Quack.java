package dailyCoding.datastructure;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This problem was asked by Google.
 *
 * A quack is a data structure combining properties of both stacks and queues. It can be viewed as a list of elements written left to right such that three operations are possible:
 *
 * push(x): add a new item x to the left end of the list
 * pop(): remove and return the item on the left end of the list
 * pull(): remove the item on the right end of the list.
 *
 * Implement a quack using three stacks and O(1) additional memory, so that the amortized time for any push, pop, or pull operation is O(1).
 *
 * Thoughts:
 * 1) to operate from the two ends of list, a quick solution is a circle array.
 *
 * 2) with three stacks
 */

public class Quack<E> {

    Stack<E> leftEnd = new Stack();
    Stack<E> rightEnd = new Stack();
    Stack tmp = new Stack();

    public void push(E entity){
        leftEnd.push(entity);
    }

    public E pop(){
        if(leftEnd.isEmpty()){
            if(rightEnd.isEmpty()){
                throw new EmptyStackException();
            }

            int nums = rightEnd.size() / 2 ;
            move(rightEnd, tmp,  nums);
            move(rightEnd, leftEnd, rightEnd.size() - nums );
            move(tmp, rightEnd, tmp.size());
        }

        return (E)leftEnd.pop();
    }

    public E pull(){
        if(rightEnd.isEmpty()){
            if(leftEnd.isEmpty()){
                throw new EmptyStackException();
            }

            int nums = leftEnd.size() / 2 ;
            move(leftEnd, tmp,  nums);
            move(leftEnd, rightEnd, leftEnd.size() - nums );
            move(tmp, leftEnd, tmp.size());
        }

        return (E)rightEnd.pop();
    }


    private void move(Stack from, Stack to, int nums){
        for(int i = 0; i < nums; i++){
            to.push(from.pop());
        }
    }
}

package fgafa.game.twentyFour;

import java.util.LinkedList;
import java.util.Random;

public class TwentyFour {
    static LinkedList<Integer> link = new LinkedList<Integer>();
    static StringBuffer buffer;

    public static void main(String[] args) {
        Integer[] array = { 3, 2, 5, 4 };
        myRodem(array, 24);
    }

    private static void myRodem(Integer[] array,
                                int max) {
        Integer tmp = 0;
        while (tmp != max) {
            tmp = 0;
            link.clear();
            buffer = new StringBuffer();
            for (Object o : array) {
                link.add((Integer) o);
            }
            while (link.size() >= 2) {
                tmp = getSum(link);
                if (tmp == null) {
                    tmp = 0;
                    break;
                }
                link.add(tmp);
                buffer.append("|");
            }
            System.out.println(buffer.toString() + "\t\t|" + tmp);
        }
    }

    public static Integer getSum(LinkedList<Integer> link) {
        int sum = 0;
        int first = link.remove(new Random().nextInt(link.size()));
        int next = link.remove(new Random().nextInt(link.size()));
        int symbol = new Random().nextInt(4);

        if (symbol == 0) {
            sum = first + next;
            buffer.append(first + "+" + next + "=" + sum);
        } else if (symbol == 1) {
            sum = first - next;
            buffer.append(first + "-" + next + "=" + sum);
        } else if (symbol == 2) {
            sum = first * next;
            buffer.append(first + "*" + next + "=" + sum);
        } else if (symbol == 3 && next != 0 && first % next == 0) {
            sum = first / next;
            buffer.append(first + "+" + next + "=" + sum);
        } else if (symbol == 3 && first != 0 && next / first == 0) {
            sum = next / first;
            buffer.append(next + "/" + first + "=" + sum);
        } else {
            return null;
        }
        return sum;
    }

    static char[] op = { '+', '-', '*', '/' };

    public static int op(char op,
                         int v1,
                         int v2) {
        switch (op) {
            case '+':
                return v1 + v2;
            case '-':
                return v1 - v2;
            case '*':
                return v1 * v2;
            case '/':
                if (v2 == 0)
                    return -111;
                if (v1 % v2 != 0)
                    return -111;
                return v1 / v2;
            default:
                return 0;
        }
    }

}

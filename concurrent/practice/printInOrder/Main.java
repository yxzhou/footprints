package fgafa.concurrent.practice.printInOrder;

/**
 *
 * Suppose we have a class:
 *
 * public class CASPrinter {
 *   public void first() { print("first"); }
 *   public void second() { print("second"); }
 *   public void third() { print("third"); }
 * }
 * The same instance of CASPrinter will be passed to three different threads. Thread A will call first(), thread B will call second(), and thread C will call third(). Design a mechanism and modify the program to ensure that second() is executed after first(), and third() is executed after second().
 *
 *
 *
 * Example 1:
 *
 * Input: [1,2,3]
 * Output: "firstsecondthird"
 * Explanation: There are three threads being fired asynchronously. The input [1,2,3] means thread A calls first(), thread B calls second(), and thread C calls third(). "firstsecondthird" is the correct output.
 * Example 2:
 *
 * Input: [1,3,2]
 * Output: "firstsecondthird"
 * Explanation: The input [1,3,2] means thread A calls first(), thread B calls third(), and thread C calls second(). "firstsecondthird" is the correct output.
 *
 *
 * Note:
 *
 * We do not know how the threads will be scheduled in the operating system, even though the numbers in the input seems to imply the ordering. The input format you see is mainly to ensure our tests' comprehensiveness.
 *
 */

public class Main {


    public static void printInOrder(final int[] order, Printer printer){
        for( int i : order ){
            new Thread(() -> {
                try{
                    switch (i){
                        case 1:
                            printer.first(() -> System.out.print("first"));
                            break;
                        case 2:
                            printer.second(() -> System.out.print("second"));
                            break;
                        case 3:
                            printer.third(() -> System.out.print("third"));
                            break;
                        default:
                            //ignore

                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }).start();
        }

    }

    public static void main(String[] args){
        int TIMES = 20;
        int[][] orders = {{1, 2, 3},{1, 3, 2}};

        for(int i = 0; i < TIMES; i++){
            for(int[] order : orders){


                //printInOrder(order, new LockerPrinter());
                //printInOrder(order, new SemaphorePrinter());
                printInOrder(order, new CASPrinter());


                try {
                    Thread.sleep(1000);
                    System.out.print("\t" + i + "\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

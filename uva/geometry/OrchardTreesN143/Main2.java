package uva.geometry.OrchardTreesN143;

import java.io.*;
import java.util.*;

class Main2
{
    static final double EPS = 1e-9d;
    static final int MIN = 1;
    static final int MAX = 99;



    private static boolean isEqual(double x, double y) {
        return Math.abs(x - y) < EPS;
    }



    private static void swap(double[] xpoints, int i, int j) {
        double tmp = xpoints[i];
        xpoints[i] = xpoints[j];
        xpoints[j] = tmp;
    }



    private static void swap(double[] xpoints, double[] ypoints, int i, int j) {
        swap(xpoints, i, j);
        swap(ypoints, i, j);

    }



    private static int count(double x1, double x2) {

        int xMax, xMin;

        if (x1 > x2) {
            xMax = (int) Math.min(Math.floor(x1 + EPS), MAX);
            xMin = (int) Math.max(Math.ceil(x2 - EPS), MIN);
        }
        else {
            xMax = (int) Math.min(Math.floor(x2 + EPS), MAX);
            xMin = (int) Math.max(Math.ceil(x1 - EPS), MIN);
        }

        return xMax - xMin + 1;

    }



    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        int n = 3;
        double[] xpoints = new double[n + 1];
        double[] ypoints = new double[n + 1];

        try {
            while (in.hasNext()) {
                boolean isExit = true;
                int xMax = 0, xMin = 100, yMax = 0, yMin = 100;

                // read
                for (int i = 0; i < n; i++) {
                    xpoints[i] = in.nextDouble();
                    ypoints[i] = in.nextDouble();

                    xMax = Math.max(xMax, (int) Math.ceil(xpoints[i]));
                    xMin = Math.min(xMin, (int) Math.floor(xpoints[i]));
                    yMax = Math.max(yMax, (int) Math.ceil(ypoints[i]));
                    yMin = Math.min(yMin, (int) Math.floor(ypoints[i]));


                    isExit = isExit && isEqual(xpoints[i], 0)
                            && isEqual(ypoints[i], 0);
                }

                if (isExit)
                    return;

                // main and output
                //System.out.println("\n"+xpoints[0]+" "+ ypoints[0]+" "+xpoints[1]+" "+ypoints[1]+" "+xpoints[2]+" "+ypoints[2]);
                
                /* if it's a line instead of a triangle */
                int count = 0;
                if (xMin > MAX || xMax < MIN || yMin > MAX || yMax < MIN) {
                    System.out.printf("%4d%n", 0);
                    continue;
                }
                if (xMax == xMin) {
                    count = Math.min(yMax, MAX) - Math.max(yMin, MIN) + 1;
                    System.out.printf("%4d%n", count);                    
                    continue;
                }
                if (yMax == yMin) {
                    count = Math.min(xMax, MAX) - Math.max(xMin, MIN) + 1;
                    System.out.printf("%4d%n", count);  
                    continue;
                }



                /* make y0 >= y1 >= y2 */
                if (ypoints[2] > ypoints[1])
                    swap(xpoints, ypoints, 2, 1);

                if (ypoints[1] > ypoints[0])
                    swap(xpoints, ypoints, 1, 0);

                if (ypoints[2] > ypoints[1])
                    swap(xpoints, ypoints, 2, 1);

                /* make x0<x1 when y0==y1 */
                if (isEqual(ypoints[0], ypoints[1]) && xpoints[0] > xpoints[1])
                    swap(xpoints, ypoints, 1, 0);
                /* make x1>x2 when y1==y2 */
                if (isEqual(ypoints[1], ypoints[2]) && xpoints[1] < xpoints[2])
                    swap(xpoints, ypoints, 1, 2);


                double xLeft = 0d, xRight = 0d;
                int y = (int) Math.max(Math.ceil(ypoints[2]), MIN);
                for (; y <= (int) Math.min(Math.floor(ypoints[1]), MAX); ++y) {

                    //if (isEqual(ypoints[0], ypoints[2]))
                     // impossible, this implies yMax = yMin
                    //    xLeft = xpoints[1]; 
                    //else
                        xLeft = xpoints[2] + (y - ypoints[2])
                                * (xpoints[0] - xpoints[2])
                                / (ypoints[0] - ypoints[2]);

                    //if (isEqual(ypoints[1], ypoints[2]))
                    //    xRight = xpoints[1]; 
                    // impossible?? it should be done in the next FOR loop
                    //else
                        xRight = xpoints[2] + (y - ypoints[2])
                                * (xpoints[1] - xpoints[2])
                                / (ypoints[1] - ypoints[2]);

                    count += count(xLeft, xRight);

                    //System.out.println(y + " = " + xLeft + " " + xRight);
                }


                for (; y <= (int) Math.min(Math.floor(ypoints[0]), MAX); ++y) {

                    //if (isEqual(ypoints[0], ypoints[2]))
                    //    xLeft = xpoints[2]; 
                    // impossible, this implies yMax = yMin
                    //else
                        xLeft = xpoints[2] + (y - ypoints[2])
                                * (xpoints[0] - xpoints[2])
                                / (ypoints[0] - ypoints[2]);

                    //if (isEqual(ypoints[0], ypoints[1]))
                    //    xRight = xpoints[1]; 
                    // impossible?? it should be done in the above FOR loop
                    //else
                        xRight = xpoints[1] + (y - ypoints[1])
                                * (xpoints[0] - xpoints[1])
                                / (ypoints[0] - ypoints[1]);

                    count += count(xLeft, xRight);

                    //System.out.println(y + " " + xLeft + " " + xRight);
                }
                
                System.out.printf("%4d%n", count);
            }
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        finally {
            in.close();
        }

    }

}

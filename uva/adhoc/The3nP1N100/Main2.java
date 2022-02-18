package uva.adhoc.The3nP1N100;

import java.io.*;
import java.util.*;

class Main2 {

    public static void main(String[] args) throws IOException {
        String in;
        while ((in = readLine(512)) != null) {
            StringTokenizer st = new StringTokenizer(in);
            int low = Integer.parseInt(st.nextToken());
            int high = Integer.parseInt(st.nextToken());
            solve(low, high);
        }
    }

    static void solve(int l, int h) {
        int low = Math.min(l, h);
        int high = Math.max(l, h);
        int solution = 0;
        int count;
        for (int i = low; i <= high; i++) {
            int n = i;
            count = 1;
            while (n != 1) {
                if (n % 2 != 0) {
                    n = 3 * n + 1;
                } else {
                    n = n / 2;
                }
                count++;
            }
            if (count > solution) {
                solution = count;
            }
        }
        System.out.println(l + " " + h + " " + solution);
    }

    static String readLine(int maxLg) throws IOException {
        byte[] lin = new byte[maxLg];
        int lg = 0, car = -1;
        while (lg < maxLg) {
            car = System.in.read();
            if (car < 0 || car == '\n') {
                break;
            }
            lin[lg++] += car;
        }
        if (car < 0 && lg == 0) {
            return null;
        }
        return new String(lin, 0, lg);
    }

}

class Main3 {

    public static void main(String[] args) throws IOException {
        String in;
        while ((in = readLine(512)) != null) {
            StringTokenizer st = new StringTokenizer(in);
            solve(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
    }

    static void solve(int l, int h) {
        int count, solution = 0;
        for (int i = Math.min(l, h); i <= Math.max(l, h); i++) {
            int n = i;
            count = 1;
            while (n != 1) {
                if (n % 2 != 0) {
                    n = 3 * n + 1;
                } else {
                    n /= 2;
                }
                count++;
            }
            if (count > solution) {
                solution = count;
            }
        }
        System.out.println(l + " " + h + " " + solution);
    }

    static String readLine(int maxLg) throws IOException {
        byte[] lin = new byte[maxLg];
        int lg = 0, car = -1;
        while (lg < maxLg) {
            car = System.in.read();
            if (car < 0 || car == '\n') {
                break;
            }
            lin[lg++] += car;
        }
        if (car < 0 && lg == 0) {
            return null;
        }
        return new String(lin, 0, lg);
    }
}
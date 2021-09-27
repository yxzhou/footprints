package uva.graph.ArbitrageN104;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import util.Misc;

public class Arbitrage {

    public String arbritrage(double[][] rates){
        if(null == rates || 0 == rates.length || rates.length != rates[0].length){
            return "";
        }

        final int n = rates.length;
        double[][][] bestRates = new double[n][n][n];//default all are 0
        int[][][] bestFootprint = new int[n][n][n];

        for(int i = 0; i < n; i++ ){
            for(int j = 0; j < n; j++){
                bestRates[0][i][j] = rates[i][j];
                bestFootprint[0][i][j] = i;
            }
        }

        for(int step = 1; step < n; step++){
            for(int k = 0; k < n; k++){
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        double currRate = bestRates[step - 1][i][k] * rates[k][j];

                        if(currRate > bestRates[i][j][step]){
                            bestRates[step][i][j] = currRate;
                            bestFootprint[step][i][j] = k;

                            if(i == j && bestRates[step][i][j] > 1.01){
                                int[] path = new int[step + 1];

                                path[step] = bestFootprint[step][i][j];
                                while( --step >= 0){
                                    path[step] = bestFootprint[step][i][path[step + 1]];
                                }

                                StringBuilder sb = new StringBuilder();
                                for(int footprint : path){
                                    sb.append(footprint + 1).append(" ");
                                }
                                sb.append(i + 1);
                                return sb.toString();
                            }

                        }

                    }
                }
            }
        }

        return Main.NOTFOUND;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("===start====");
        String inputFile = "src/fgafa.uva/graph/ArbitrageN104/Arbitrage.txt";

        Arbitrage sv = new Arbitrage();

        try(//Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
                Scanner in = new Scanner(new BufferedInputStream(new FileInputStream(new File(inputFile))), "UTF-8")){

            while(in.hasNext()){
                int n = in.nextInt();
                double[][] rates = new double[n][n];

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if(i != j){
                            rates[i][j] = Math.max(in.nextDouble(), 0d);
                        }else {
                            rates[i][j] = 1d;
                        }
                    }
                }

                System.out.println(sv.arbritrage(rates));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("\n===Ans====");

        Misc.printFile("src/fgafa.uva/graph/ArbitrageN104/Arbitrage_ans.txt");

    }
}

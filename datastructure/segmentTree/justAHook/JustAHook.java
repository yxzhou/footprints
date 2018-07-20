package fgafa.datastructure.segmentTree.justAHook;

public class JustAHook {

    public int calculatorHooks(int hooksNumber, int[][] operators){
        int n = 1;
        while(n < hooksNumber){
            n <<= 1;
        }

        int[] colors = new int[n * 2]; //default all hookValue[i] is 0

        for(int[] operator : operators){
            update(colors, 1, 1, n, operator[0], operator[1], operator[2] - 1);
        }

        return sum(colors, 1, 1, n) + hooksNumber;
    }


    private void update(int[] colors, int nodeIndex, int nodeStart, int nodeEnd, int operationStart, int operationEnd, int operationValue){
        if(colors[nodeIndex] == operationValue){
            return;
        }

        //nodeStart < nodeEnd
        if(operationStart <= nodeStart && nodeEnd <= operationEnd){
            colors[nodeIndex] = operationValue;
        }else if(operationEnd < nodeStart || nodeEnd < operationStart){
            //do nothing
        }else{
            int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
            int leftSon = nodeIndex * 2;

            update(colors, leftSon, nodeStart, nodeMiddle, operationStart, operationEnd, operationValue);
            update(colors, leftSon + 1, nodeMiddle + 1, nodeEnd, operationStart, operationEnd, operationValue);

            //push up
            if(colors[leftSon] == colors[leftSon + 1]){
                colors[nodeIndex] = colors[leftSon];
            }else{
                colors[nodeIndex] = 0;
            }
        }
    }

    private int sum(int[] colors, int nodeIndex, int nodeStart, int nodeEnd){
        if(colors[nodeIndex] != 0){
            return (colors[nodeIndex]) * (nodeEnd - nodeStart + 1);
        }

        if(nodeStart == nodeEnd){
            return colors[nodeIndex];
        }

        //nodeStart < nodeEnd
        int nodeMiddle = nodeStart + (nodeEnd - nodeStart) / 2;
        int leftSon = nodeIndex * 2;

        return sum(colors, leftSon, nodeStart, nodeMiddle) + sum(colors, leftSon + 1, nodeMiddle + 1, nodeEnd);
    }

    public static void main(String[] args){

        int[] hooksNumbers = {10, 100, 100, 100};
        int[][][] operators = {
                {
                    {1,5,2},
                    {5,9,3}
                },{
                    {10,15,2},
                    {15,19,3}
                }, {
                    {10,15,2},
                    {16,19,3}
                }, {
                    {10,14,2},
                    {16,19,3}
                }
        };

        JustAHook sv = new JustAHook();
        for(int i = 0; i < hooksNumbers.length; i++){
            System.out.println(sv.calculatorHooks(hooksNumbers[i], operators[i]));
        }

    }
}

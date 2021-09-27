package todo.twoo;

/**
 *  Q: There are W white beans and R red beans in one bag.
 *  The first step: random touch a bean, touch white beans to eat directly, touch red beans, put back.
 *  The second step: random touch another bean, whether it is red or white, eat.
 *  Then repeat the first and second steps to ask the probability of the last bean being white beans.
 *
 *
 *
 */

public class EatTheBeans {


    public double eatTheBeans(int w, int r){
        if(w <= 0){
            return 0;
        }else if(r <= 0){
            return 1;
        }

        double[][][] p = new double[w+1][r+1][2]; //default all are 0
        p[w][r][0] = 1;

        for(int i = w; i > 0; i--){
            for(int j = r; j > 0; j--){
                for(int k = 0; k < 2; k++){
                    if(k == 0){
                        p[w][r][1] += p[w][r][0] * r / (w + r);
                        p[w - 1][r][1] += p[w][r][0] * w / (w + r);
                    }else{
                        p[w][r - 1][0] += p[w][r][1] * r / (w + r);
                        p[w - 1][r][0] += p[w][r][1] * w / (w + r);
                    }
                }
            }
        }

        double result = 0;
        for(int i = w; i > 0; i--){
            result += p[i][0][0] + p[i][0][1];
        }

        return result;
    }

}

package math;

import java.math.BigDecimal;
import junit.framework.Assert;

import util.TimeCost;

/**
 * 
 * BigDecimal factorial(int n)          not recursive
 * BigDecimal factorial_R(int n)        recursive
 * StringBuffer factorial_Array(int n)  int array to avoid overflow
 * 
 * Note:
 * 1 There is overflow, if it's int, the max is 12!, if it's long, the max 20! 
 *   To avoid it, we use java.math.BigDecimal or int array or String
 * 
 * 2 There is java.lang.StackOverflowError if there are too many recursive call. 
 *   Example, when n = 10000, factorial_R throw java.lang.StackOverflowError. 
 * 
 * 3 There is performance issue if there are big number multiple.
 *   Method                n = 1000        n = 10000             n = 50000    
 *   with BigDecimal        5                   172                 922
 *   with BigDecimal2       4                    76                 483
 *   with int array         4                   414               11766
 *   with StringBuilder    44                  1634               48386
 *            
 */

public class Factorial{

    /**
     * n ! = 1 * 2 * 3 * --- *n
     * with BigDecimal
     * 
     * @param n
     * @return
     */
    public static BigDecimal factorial_BigDecimal1(int n) {
        return factorial(2, n);
    }
  
    /**
     * n! = (1*2*---* n/2)*(n/2+1 * --- * n)
     * 
     * @param n
     * @return
     */
    public static BigDecimal factorial_BigDecimal2(int n) {
        int x = n/2;//third
        BigDecimal t1 = factorial(2, x); //thread1
        BigDecimal t2 = factorial(x + 1, n);  //thread2
        return t1.multiply(t2);
    }
    
    public static BigDecimal factorial_BigDecimal3(int n) {
        int x = n/3;//third
        BigDecimal r1 = factorial(2, x);   //
        BigDecimal r2 = factorial(x + 1, x << 1);
        BigDecimal r3 = factorial((x << 1)  + 1, n);
        
        return r1.multiply(r2.multiply(r3));
    }
    
    public static BigDecimal factorial_BigDecimal4(int n) {
        int x = n/4;//
        int y = n/2;
        BigDecimal t1 = factorial(2, x); //thread1
        BigDecimal t2 = factorial(x + 1, y);  //thread2
        t1 = t1.multiply(t2);
        
        x += y; 
        t2 = factorial(y+1, x); //thread1
        BigDecimal t3 = factorial(x + 1, n);  //thread2         
        t2 = t2.multiply(t3);
        
        return t1.multiply(t2);
    }
    

    private static BigDecimal factorial(int begin, int end) {
        BigDecimal result = new BigDecimal(1);
        BigDecimal a;
        for (int i = begin; i <= end; i++) {
            a = new BigDecimal(i);
            result = result.multiply(a);
        }
        return result;
    }


    /**
     * return n!,  store the big number in a int array
     * 
     * input n, to get n! 
     * step 1, get the bit length  of n!, [lg(n!) + 1]
     * step 2, create a int array store the n!, 
     * 
     * e.g.  120 ( 5! ),  it's {0, 2, 1} in the int array. 
     *   multiple 6 to get 6!, (0*6, 2*6, 1*6) => (0, 12, 6) => (0, 2, 7)       
     * 
     * @param n
     * @return
     */
    public static String factorial_Array(int n) {
        if(n < 2){
            return "1";
        }

        int len = getLength(n);
        int[] arr = new int[len];
        arr[0] = 1; // the first set as 1, the other is 0 as default
        int end = 1;
        
        int carry = 0;
        for (int x = 2; x <= n; x++) {
            
            for (int j = 0; j < end; ++j) {
                carry += (x * arr[j]);
                arr[j] = (carry % 10);
                carry /= 10;
            }
            
            while(carry > 0){
                arr[end] = carry % 10;
                carry /= 10;
                end++;
            }
        }

        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        for (int k = arr.length - 1; k >= 0; k--) {
            if(leadingZero && arr[k] == 0){
                continue;
            }
            
            leadingZero = false;
            sb.append(arr[k]);
        }
        return sb.toString();

    }

    /**
     * return the digits of n!
     * 
     * example n = 5, 
     * n! = 1 * 2 * 3 * 4 * 5,  
     * lg(n!) = lg (1 * 2 * 3 * 4 * 5) = lg1 + lg2 + lg3 + lg4 + lg5 
     * 
     * bitLength = lg1 + lg2 + lg3 + lg4 + lg5 + 1.
     * 
     * @param n
     * @return bitLength
     */
    private static int getLength(int n) {
        double sum = 1.0;
        for (int i = 1; i <= n; i++) {
            sum += java.lang.Math.log10((double) i);
        }
        return (int) sum;
    }

    /**
     * @param n: an integer
     * @return the factorial of n
     */
    public static String factorial_String(int n) {
        if(n < 2){
            return "1";
        }

        StringBuilder r = new StringBuilder("2"); 
        StringBuilder next;
        int carry = 0;
        for(int i = 3; i <= n; i++){
            next = new StringBuilder();
            
            for(int j = 0; j < r.length(); j++){
                carry += (r.charAt(j) - '0') * i;
                
                next.append(carry % 10);
                carry /= 10;
            }
            
            while(carry > 0){
                next.append(carry % 10);
                carry /= 10;
            }
            
            r = next;
        }

        return r.reverse().toString();
    }
  
    public static void main(String[] args) {

        //System.out.println("test lg120 = " + Math.log10(120));
        //if(true) return;
        int[] arr = {1, 2, 3, 4, 12, 13, 20, 25, 100, 1000, 7000, 10000, 30000, 50000}; //10000, 50000
        String[] expects = {
            "1",
            "2",
            "6",
            "24",
            "479001600",
            "6227020800",
            "2432902008176640000",
            "15511210043330985984000000",
            "93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000",
            "402387260077093773543702433923003985719374864210714632543799910429938512398629020592044208486969404800479988610197196058631666872994808558901323829669944590997424504087073759918823627727188732519779505950995276120874975462497043601418278094646496291056393887437886487337119181045825783647849977012476632889835955735432513185323958463075557409114262417474349347553428646576611667797396668820291207379143853719588249808126867838374559731746136085379534524221586593201928090878297308431392844403281231558611036976801357304216168747609675871348312025478589320767169132448426236131412508780208000261683151027341827977704784635868170164365024153691398281264810213092761244896359928705114964975419909342221566832572080821333186116811553615836546984046708975602900950537616475847728421889679646244945160765353408198901385442487984959953319101723355556602139450399736280750137837615307127761926849034352625200015888535147331611702103968175921510907788019393178114194545257223865541461062892187960223838971476088506276862967146674697562911234082439208160153780889893964518263243671616762179168909779911903754031274622289988005195444414282012187361745992642956581746628302955570299024324153181617210465832036786906117260158783520751516284225540265170483304226143974286933061690897968482590125458327168226458066526769958652682272807075781391858178889652208164348344825993266043367660176999612831860788386150279465955131156552036093988180612138558600301435694527224206344631797460594682573103790084024432438465657245014402821885252470935190620929023136493273497565513958720559654228749774011413346962715422845862377387538230483865688976461927383814900140767310446640259899490222221765904339901886018566526485061799702356193897017860040811889729918311021171229845901641921068884387121855646124960798722908519296819372388642614839657382291123125024186649353143970137428531926649875337218940694281434118520158014123344828015051399694290153483077644569099073152433278288269864602789864321139083506217095002597389863554277196742822248757586765752344220207573630569498825087968928162753848863396909959826280956121450994871701244516461260379029309120889086942028510640182154399457156805941872748998094254742173582401063677404595741785160829230135358081840096996372524230560855903700624271243416909004153690105933983835777939410970027753472000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
            //"",
            //""
            
        };
        
        /** check the result correction  **/
        for (int i = 0; i < arr.length && arr[i] < 2000 ; i++) {
            
            System.out.println("\nThe factorial       of " + arr[i] + " is: " + factorial_BigDecimal1(arr[i]).toString() );
            System.out.println("The factorial2      of " + arr[i] + " is: " + factorial_BigDecimal2(arr[i]).toString() );
            System.out.println("The factorial_Array of " + arr[i] + " is: " + factorial_Array(arr[i]) );
            System.out.println("The factorial_22    of " + arr[i] + " is: " + factorial_String(arr[i]) );
            
            /**
            Assert.assertEquals(expects[i], factorial(arr[i]).toString());
            Assert.assertEquals(expects[i], factorial2(arr[i]).toString());
            Assert.assertEquals(expects[i], factorial_Array(arr[i]));
            Assert.assertEquals(expects[i], factorial_22(arr[i]));
            **/
        }    
        
        /** check the performance **/
        
        TimeCost tc = TimeCost.getInstance();
        tc.init();

        for (int i = 8; i < arr.length; i++) {
            
            factorial_BigDecimal1(arr[i]).toString();
            System.out.println("\nThe factorial       of " + arr[i] + " timeCost:" + tc.getTimeCost());

            factorial_BigDecimal2(arr[i]).toString();
            System.out.println("The factorial2      of " + arr[i] + " timeCost:" + tc.getTimeCost());
            
            factorial_BigDecimal3(arr[i]).toString();
            System.out.println("The factorial3      of " + arr[i] + " timeCost:" + tc.getTimeCost());
            
            factorial_BigDecimal4(arr[i]).toString();
            System.out.println("The factorial4      of " + arr[i] + " timeCost:" + tc.getTimeCost());

            factorial_Array(arr[i]);
            System.out.println("The factorial_Array of " + arr[i] + " timeCost:" + tc.getTimeCost());

            factorial_String(arr[i]);
            System.out.println("The factorial_22    of " + arr[i] + " timeCost:" + tc.getTimeCost());
            
        }

    }

}

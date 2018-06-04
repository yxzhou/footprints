package fgafa.game.twentyFour;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TwentyFour2
{
   
   // to store all solutions
  private List allResults = new ArrayList();

  // There are 4 operator 
  static char[] op={'+','-','*','/'};  
  // to store the operators in one solution
  int[] operators = new int[3]; 
  
  // operator, it includs +, -, *, /
  private int operator1, operator2, operator3;

  // there are 4 input integer
  private float[] inputedNumbers = new float[4];
  // the 4 integer would be in a order
  private float[] sortedNumbers = new float[4];
  
  // to the 4 input integer, ABCD. They can be ABDC, BDAC, etc. Total it's 4*3*2 = 24. 
  private int[][] indexs = {
      {0, 1, 2, 3}, 
      {0, 1, 3, 2}, 
      {0, 2, 1, 3},
      {0, 2, 3, 1}, 
      {0, 3, 1, 2}, 
      {0, 3, 2, 1}, 
      {1, 0, 2, 3}, 
      {1, 0, 3, 2},
      {1, 2, 0, 3}, 
      {1, 2, 3, 0}, 
      {1, 3, 0, 2}, 
      {1, 3, 2, 0}, 
      {2, 0, 1, 3},
      {2, 0, 3, 1}, 
      {2, 1, 0, 3}, 
      {2, 1, 3, 0}, 
      {2, 3, 0, 1}, 
      {2, 3, 1, 0},
      {3, 0, 1, 2}, 
      {3, 0, 2, 1}, 
      {3, 1, 0, 2}, 
      {3, 1, 2, 0}, 
      {3, 2, 0, 1},
      {3, 2, 1, 0}};
  
  // Totally it will be 24 round
  private int round = 0;



  public int getRound() {
    return this.round;
  }



  public void setRound(int round) {
    this.round = round;
  }

  // binary tree, the root is result[0]
  // when result[0] == 24, it means xxx.
  private float result[] = new float[9];



  private void print(String s) {
    System.out.println(s);
  }



  // get the input 4 integer
  private boolean setNumbers(String[] str) {
    if (str.length != 4) {
      print("Please input 4 Integers!");
      return false;
    }
    try {
      inputedNumbers[0] = Integer.parseInt(str[0]);
      inputedNumbers[1] = Integer.parseInt(str[1]);
      inputedNumbers[2] = Integer.parseInt(str[2]);
      inputedNumbers[3] = Integer.parseInt(str[3]);
    }
    catch (Exception ex) {
      print("Please input 4 Integers!");
      return false;
    }
    return true;
  }



  //
  private void setResult(int index, float value) {
    result[index] = value;
  }



  //
  private float getResult(int index) {
    return result[index];
  }



  // get the
  private String generateNumSentence(int intOptr, float opnd1, float opnd2,
      float rst) {
    String ret = "";
    switch (intOptr) {
      case 0:
        ret = opnd1 + "+" + opnd2 + "=" + rst;
        // Notes: it's rst instead of result.
        break;
      case 1:
        ret = opnd1 + "-" + opnd2 + "=" + rst;
        break;
      case 2:
        ret = opnd2 + "-" + opnd1 + "=" + rst;
        break;
      case 3:
        ret = opnd1 + "*" + opnd2 + "=" + rst;
        break;
      case 4:
        ret = opnd1 + "/" + opnd2 + "=" + rst;
        break;
      case 5:
        ret = opnd2 + "/" + opnd1 + "=" + rst;
        break;

    }
    return ret;
  }



  private void printResult() {
    /** TODO */
    int count = 0;
    int howManyAnswers = this.allResults.size();
    if (howManyAnswers > 0) {
      print("total " + howManyAnswers + " solutions");
      for (Iterator i = this.allResults.iterator(); i.hasNext();) {
        print("No." + (++count) + "\n" + i.next());
      }
    }
    else {
      print("no solution!");
    }
  }



  //
  private void sortNumbers(int rnd) {
    if (rnd < 0 || rnd > 23) {
      print("Error!");
      return;
    }
    sortedNumbers[0] = inputedNumbers[indexs[rnd][0]];
    sortedNumbers[1] = inputedNumbers[indexs[rnd][1]];
    sortedNumbers[2] = inputedNumbers[indexs[rnd][2]];
    sortedNumbers[3] = inputedNumbers[indexs[rnd][3]];
  }



  // calculate the 2 input integer
  private float getMidValue(int i, float m, float n) {
    float ret = 0f;
    switch (i) {
      case 0:
        ret = m + n;
        break;

      case 1:
        ret = m - n;
        break;

//      case 2:
//        ret = n - m;
//        break;

      case 3:
        ret = m * n;
        break;

      case 4:
        try {
          ret = m / n;
        }
        catch (Exception ex1) {
          ret = Float.MAX_VALUE;
        }
        break;

//      case 5:
//        try {
//          ret = n / m;
//        }
//        catch (Exception ex2) {
//          ret = Float.MAX_VALUE;
//        }
//        break;

    }
    return ret;
  }
   
   /**
    * There are 2 binary tree. it means 2 calculate method
    *****************************************************************************
    ******(OP3) -- 24  
    ***** /   \        
    ****(OP2) (D)      
    **  /    \          
    * (OP1) (C)        
    * /   \            
    *(A)  (B)           
    *****************************************************************************
    ********(OP3) -- 24  
    *      /     \      
    *   (OP1)    (OP2)  
    *   /   \    /    \ 
    * (A)   (B)(C)    (D) 
    *****************************************************************************     
    *     
    **/
   
   private void calculate() {
    setResult(3, sortedNumbers[0]);
    setResult(4, sortedNumbers[1]);
    setResult(5, sortedNumbers[2]);
    setResult(6, sortedNumbers[3]);
    for (int i = 0; i < 6; i++) {
      setResult(1, getMidValue(i, getResult(3), getResult(4)));
      for (int j = 0; j < 6; j++) {
        setResult(2, getMidValue(j, getResult(5), getResult(6)));
        for (int k = 0; k < 6; k++) {
          setResult(0, getMidValue(k, getResult(1), getResult(2)));
          if (Math.abs(getResult(0) - 24) < 0.001) {
            this.operator1 = i;
            this.operator2 = j;
            this.operator3 = k;
            // store the calculate method
            saveResult(1);

          }
          if (i == 5 && j == 5 && k == 5) {
            calculate_1();
          }
        }
      }
    }
  }



  /**
   * 
   */
  private void saveResult(int type) {
    // TODO Auto-generated method stub
    String ns = null;
    switch (type) {
      case 1:
        ns = "Step1:"
            + this.generateNumSentence(this.operator1, this.getResult(3),
                this.getResult(4), this.getResult(1)) + "\n";
        ns += "Step2:"
            + this.generateNumSentence(this.operator2, this.getResult(5),
                this.getResult(6), this.getResult(2)) + "\n";
        ns += "Step3:"
            + this.generateNumSentence(this.operator3, this.getResult(1),
                this.getResult(2), this.getResult(0)) + "\n";
        break;
      case 2:
        ns = "Step1:"
            + this.generateNumSentence(this.operator1, this.getResult(7),
                this.getResult(8), this.getResult(3)) + "\n";
        ns += "Step2:"
            + this.generateNumSentence(this.operator2, this.getResult(3),
                this.getResult(4), this.getResult(1)) + "\n";
        ns += "Step3:"
            + this.generateNumSentence(this.operator3, this.getResult(1),
                this.getResult(2), this.getResult(0)) + "\n";
        break;
    }
    boolean dup = false;
    for (Iterator i = this.allResults.iterator(); i.hasNext();) {
      if (ns.equals((String) i.next())) {
        dup = true;
        break;
      }
    }
    if (!dup) {
      this.allResults.add(ns);
    }
  }



  // 2nd calculate method
  private void calculate_1() {
    setResult(7, sortedNumbers[0]);
    setResult(8, sortedNumbers[1]);
    setResult(4, sortedNumbers[2]);
    setResult(2, sortedNumbers[3]);
    for (int i = 0; i < 6; i++) {
      setResult(3, getMidValue(i, getResult(7), getResult(8)));
      for (int j = 0; j < 6; j++) {
        setResult(1, getMidValue(j, getResult(3), getResult(4)));
        for (int k = 0; k < 6; k++) {
          setResult(0, getMidValue(k, getResult(1), getResult(2)));
          if (Math.abs(getResult(0) - 24) < 0.001) {
              this.operator1 = i;
              this.operator2 = j;
              this.operator3 = k;
            // 
            saveResult(2);

          }
          else if (i == 5 && j == 5 && k == 5) {
            // print("No solution at round "+(this.getRound()+1)+"!");
          }
        }
      }
    }

  }



  public static void main(String[] args) {

    TwentyFour2 cal = new TwentyFour2();
    
    String[] inputs = {"3","2","5","4"};

    if (!cal.setNumbers(inputs)) {
      return;
    }

    //to the 4 input integer, ABCD. They can be ABDC, BDAC, etc. Total it's 4*3*2 = 24. 
    for (int i = 0; i < 24; i++) {
      cal.setRound(i);
      cal.sortNumbers(i);
      cal.calculate();
      //   cal.printResult();
    }

    cal.printResult();
  }

}

  



package fgafa.uva.adhoc.ErrantPhiscistN126;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


class Main
{
  
  class Term implements Comparable<Term> {
    int coeff = 0;  // coefficients
    int xExpo = 0;  // exponents on x
    int yExpo = 0;  // exponents on y
    
    Term(int coeff, int xExpo, int yExpo){
      this.coeff = coeff;
      this.xExpo = xExpo;
      this.yExpo = yExpo;
    }
    
    public String toString(){
      return this.coeff + " " + this.xExpo + " " + this.yExpo;
    }

    @Override
    public int compareTo(Term t2) {
      if(this.xExpo > t2.xExpo)
        return -1;
      else if(this.xExpo < t2.xExpo)
        return 1;

      if(this.yExpo > t2.yExpo)
        return 1;
      else if(this.yExpo < t2.yExpo)
        return -1; 
      return 0;
    }
  }
  
  /*
   * Time O(),  Space O()
   */
  public ArrayList<Term> parsePolynomial(String polynormial){
    ArrayList<Term> result = new ArrayList<Term>();
    
    //check
    if(polynormial == null || polynormial.length() == 0)
      return result;
    
    if(polynormial.charAt(0) != '-')
      polynormial = '+' + polynormial;

    Term curr = new Term(0, 0, 0);
    int len = polynormial.length();
  
    for(int i=0; i< len; ){
      
      switch(polynormial.charAt(i)){
        case 'y':
          i++;
          if( i<len && polynormial.charAt(i) == '0' ){
            //curr.yExpo = 0; 
            i++;
          }else{
            while( i<len && isDigit(polynormial.charAt(i))){
              curr.yExpo = curr.yExpo * 10 + getDigit(polynormial.charAt(i));
              i++;
            }
            
            if( curr.yExpo == 0)
              curr.yExpo = 1;          
          }
          break;
        case 'x': 
          i++;
          if( i<len && polynormial.charAt(i) == '0' ){
            //curr.xExpo = 0; 
            i++;
          }else{
            while( i<len && isDigit(polynormial.charAt(i))){
              curr.xExpo = curr.xExpo * 10 + getDigit(polynormial.charAt(i));
              i++;
            }
            
            if( curr.xExpo == 0)
              curr.xExpo = 1;           
          }
          break;
        case '+':
        case '-':
          result.add(curr);
          curr = new Term(0, 0, 0);
          
          if(polynormial.charAt(i) == '-'){
            curr.coeff = -1;
            i ++;
          }else if(polynormial.charAt(i) == '+') {
            curr.coeff = 1;
            i ++;          
          }
          
          if( i<len && polynormial.charAt(i) == '0' ){
            curr.coeff = 0; 
            i++;
          }else{
            int sum = 0;   
            while( i<len && isDigit(polynormial.charAt(i))){
              sum = sum * 10 + getDigit(polynormial.charAt(i));
              i++;
            }
            
            if(sum != 0)
              curr.coeff *= sum;           
          }
          break;
      }        
    }
    
    result.add(curr);
    return result;
  }
  
  private boolean isDigit(char c){
    return c > 47 && c < 58;  // '0' is 48, '1' is 49,'9' is 57
  }
  
  private int getDigit(char digit){
    return digit - 48;  // '0' is 48
  }
  
  public ArrayList<Term> multiplePolynormial(ArrayList<Term> first, ArrayList<Term> second){
    HashMap<String, Term> hm = new HashMap<String, Term>();
    
    String key;
    Term tmp;
    for(Term t1 : first){
      if( t1.coeff == 0)
        continue;
      
      for(Term t2 : second){
        if( t2.coeff == 0)
          continue;
                
        key = (t1.xExpo + t2.xExpo) + " " + (t1.yExpo + t2.yExpo);
        tmp = hm.get(key);
        if(tmp != null){
          tmp.coeff += t1.coeff * t2.coeff;
          
        }else{
          tmp = new Term(t1.coeff * t2.coeff, t1.xExpo + t2.xExpo, t1.yExpo + t2.yExpo);
          
          hm.put(key, tmp);
        }  
      }
    }
    
    //
    ArrayList<Term> resultArrayList = new ArrayList<Term> ();
    for(String keyTmp : hm.keySet()){
      tmp = hm.get(keyTmp);
      if( tmp.coeff != 0 )
        resultArrayList.add(tmp);
   
    }

    Collections.sort(resultArrayList);
    
    return resultArrayList;
  }
  
  /*
   *    13 2    11      8      6    5     5 2     3    3                //exponents
   * - x  y  - x  y + 8x y + 9x  - x y + x y  + 8x  + x y - 1 + y       //variable
   *
   * + 1
   * 
   */
  
  public void calOutput(StringBuilder exponents, StringBuilder variable, Term t){
    
    exponents.append("   "); 
    if(t.coeff < 0)
      variable.append(" - ");    
    else  // >0
      variable.append(" + ");     
    
    int tmp = (int)Math.abs(t.coeff);
    if(tmp != 1 || (t.xExpo == 0 && t.yExpo == 0)){
      for(int i=String.valueOf(tmp).length(); i>0 ; i--)
        exponents.append(" ");  
      variable.append(tmp);
    }
    
    if(t.xExpo != 0){
      exponents.append(" ");
       variable.append("x");  
      
       if(t.xExpo != 1){
         exponents.append(t.xExpo);
         for(int i=String.valueOf(t.xExpo).length(); i>0 ; i--)
           variable.append(" ");  
       } 
    }
      
    if(t.yExpo != 0){
      exponents.append(" ");
       variable.append("y");  
      
       if(t.yExpo != 1){
         exponents.append(t.yExpo);
         for(int i=String.valueOf(t.yExpo).length(); i>0 ; i--)
           variable.append(" ");  
       } 
    }
    
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {

    Scanner in = new Scanner(new BufferedInputStream(System.in), "UTF-8");
    String line;
    
    Main sv = new Main();
    
    try {
      while (in.hasNext()) { 
        //init
        
        //read
        line = in.nextLine();
        if(line.trim().equals("#")) break;
        ArrayList<Term> first = sv.parsePolynomial(line);
        line = in.nextLine();
        //if(line.trim().equals("#")) break;
        ArrayList<Term> second = sv.parsePolynomial(line);       
    
        //mulitiple
        ArrayList<Term> result = sv.multiplePolynormial(first, second);
        
        //output       
        if(result.size() == 0){
          System.out.println( ); 
          System.out.println( ); 
          continue;
        }
        //else result.size() != 0
        StringBuilder exponents = new StringBuilder();
        StringBuilder variable = new StringBuilder();
        for(Term t : result){
          sv.calOutput(exponents, variable, t);
        }
        
        //The pair of lines that contain a product should be the same length
        if(variable.charAt(1) == '-'){
          exponents.insert(3, ' ');
          variable.insert(3, '-');
        }  
        System.out.println( exponents.substring(3) ); 
        System.out.println( variable.substring(3) ); 
        
      }
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    finally {
      in.close();
    }
  }
 
}

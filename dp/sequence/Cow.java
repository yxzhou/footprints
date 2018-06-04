package fgafa.dp.sequence;

/**
 * 一只刚出生的小牛，4年后生一只小牛，以后每年生一只。 现有一只刚出生的小牛，问20年后共有牛多少只？
 * 我算出来的是345只，跟答案一致
 * 
 * 
 * S[i] = S[i - 1] + S[i - 4]
 * 
 * @author yxzhou
 *
 */

public class Cow
{

  /**
   * @param args
   */
  public static void main(String[] args) {

    Cow s = new Cow();
    int year = 20;

    System.out.println(year + " years, there are cows: " + s.CowNumbers1(20));
    System.out.println(year + " years, there are cows: " + s.CowNumbers2(20));

  }



  //奶牛问题的递归解法
  public int CowNumbers1(int year) {
    int bornCount;
    if (year < 4)
      bornCount = 1;
    else {
      int sum = 1;
      for (int i = 4; i <= year; i++)
        sum = sum + CowNumbers1(i - 4);
      bornCount = sum;
    }
    return bornCount;
  }



  //奶牛问题的非递归解法  
  //arr[i] = arr[i - 1] + arr[i - 4]
  public int CowNumbers2(int year) {
    int[] arr = new int[year + 1];
    arr[0] = 1; //初始化第一头牛  
    //奶牛每年增长  
    for (int i = 1; i <= year; i++) {
      if (i < 4)//前三年每年都只有一头奶牛  
        arr[i] = 1;
      else
        arr[i] = arr[i - 1] + arr[i - 4];//第四年开始奶牛的增长方式  
    }
    return arr[year];

  }

  
  

}

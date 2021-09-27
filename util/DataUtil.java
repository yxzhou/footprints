package util;


/**
 * @version 1.0
 * @author yxzhou
 */

public class DataUtil
{

  private static int cosineTable[] = {8192, 8172, 8112, 8012, 7874, 7697, 7483,
      7233, 6947, 6627, 6275, 5892, 5481, 5043, 4580, 4096, 3591, 3068, 2531,
      1981, 1422, 856, 285, -285};



  public DataUtil() {
  }



  public static long xCosY(long l, int i) {
    int j = Math.abs(i);
    j %= 360;
    if (j > 270)
      return xCosY(l, 360 - j);
    if (j > 180)
      return -xCosY(l, j - 180);
    if (j > 90) {
      return -xCosY(l, 180 - j);
    }
    else {
      int k = j / 4;
      int i1 = j % 4;
      int j1 = (4 - i1) * cosineTable[k] + i1 * cosineTable[k + 1];
      long l1 = l * (long) j1;
      return l1 >> 15;
    }
  }



  public static long xSinY(long l, int i) {
    return xCosY(l, i + 270);
  }



  public static long rss(long l, long l1) {
    long l2 = Math.abs(l);
    long l3 = Math.abs(l1);
    long l4 = l * l + l1 * l1;
    long l5 = l2 <= l3 ? l2 / 2L + l3 : l3 / 2L + l2;
    if (l5 == 0L)
      return 0L;
    for (int i = 0; i < 4; i++) {
      l5 += l4 / l5;
      l5 >>= 1;
    }

    return l5;
  }



  public static int headingToSector(int i) {
    return ((2 * i + 45) % 720) / 90;
  }



  public static byte[] setByte(byte abyte0[], byte byte0, int i) {
    if (i >= abyte0.length) {
      byte abyte1[] = new byte[2 * abyte0.length];
      for (int j = 0; j < abyte0.length; j++)
        abyte1[j] = abyte0[j];

      abyte0 = abyte1;
    }
    abyte0[i] = byte0;
    return abyte0;
  }

    public static int unsignedByteToInt(byte b) {
      return (int) b & 0xFF;
      }
     

  
  public static void main(String[] args) {
      System.out.println(xCosY(31, 44)) ;
      System.out.println(xSinY(31, 44)) ;
      System.out.println(rss(31, 44)) ;
      
  }
}

package fgafa.basic;

import java.security.Permission;

public class TryCatch
{
  
    //public main(int number){}; 
    
    public static void main(String... argv)
      throws Exception
    {
      System.setSecurityManager(new SecurityManager() {

        @Override
        public void checkPermission(Permission perm)
        {
          /* Allow everything else. */
          System.err.println("---checkPermission---");
        }

        @Override
        public void checkExit(int status)
        {
          /* Don't allow exit with any status code. */
          System.err.println("---checkExit---");
          //throw new SecurityException();
        }

      });
      
      int i=0;
      i=i++;  // i = 0
      i=i++;  // i = 0
      i=++i;  // i = 1
      System.out.println("i=" + i);
      int arr[] = new int[5];
      int index = 0;
      arr[index] = index = 3;
      System.out.println("arr[0]=" + arr[0] + " arr[3]=" + arr[3] + " index=" + index); //arr[0]=3 arr[3]=0 index=3
      
      if(-0.0==0.0)
        System.out.println("-0.0==0.0");  // this line will be executed
      else
        System.out.println("-0.0!=0.0");  //
      
      Runtime runtime = Runtime.getRuntime();
      Long start, end;
      Object obj;
      runtime.gc();  //this is very important
      start=runtime.freeMemory();
      obj=new TryCatch();
      end=runtime.freeMemory();
      System.out.println("size of obj"+ (start-end) + " bytes");  
      
      System.err.println("I'm going to die!");
      
      float x = Float.NaN;
      System.err.println(Float.isNaN(x));
      System.err.println(Float.NaN);
      
      try {
        
        //System.exit(0);
        //System.exit(1);
        System.exit(-1);
        
      }catch(Exception e){
        System.err.println("--catch--");
        e.printStackTrace();
        
      }finally {
        System.err.println("--finally--");
        
        System.exit(1);
        
        System.err.println("I'm not dead yet! in finally. ");
      }
      
      System.err.println("I'm not dead yet! after finally.");
      

    }

  }

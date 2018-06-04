package fgafa.basic;

public class Parent {

  static {
   System.out.println("Inside Parent static");
  }
   {
    System.out.println("Inside Parent init");
   }
   public Parent(){
    System.out.println("Parent Const");
   }
    
   public static void staticMethod(){
     System.out.println("Inside Parent static");
   }
   
   public static void main(String args[]){
    
    Parent p = new MyChild();
    
    p.staticMethod();    
    
    MyChild.staticMethod();
   }

 }

 class MyChild extends Parent{

   static {
    System.out.println("Inside Child static");
   }
   {
    System.out.println("Inside Child init");
   }
   public MyChild(){
    System.out.println("Child Const");
   }

   public static void staticMethod(){
     System.out.println("Inside Child static");
   }
   
 }

package fgafa.basic;

public class TestFinal {

    private final int i; //compile error,  the blank final i may not be initilized

    
    public static void main(String[] args) {
        System.out.println(new TestFinal().i); 

    }

}

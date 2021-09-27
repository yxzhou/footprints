package design.mypatterns.simpleFactory;

public class Triangle extends Shape {

    @Override
    void draw() {
        System.out.println("  /\\");
        System.out.println(" /  \\");
        System.out.println("/____\\");

    }

}

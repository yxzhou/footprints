package design.mypatterns.simpleFactory;

public class Square extends Shape {

    @Override
    void draw() {
        System.out.println(" ---- ");
        System.out.println("|    |");
        System.out.println("|    |");
        System.out.println(" ---- ");

    }

}

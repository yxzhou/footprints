package fgafa.basic;

import static org.junit.Assert.*;

import fgafa.MyObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


/**
 * What is Lambda for?
 *     One issue with anonymous classes is that if the implementation of your anonymous class is very simple,
 * such as an interface that contains only one method, then the syntax of anonymous classes may seem unwieldy and unclear.
 * In these cases, you're usually trying to pass functionality as an argument to another method, such as what action should be taken when someone clicks a button.
 * Lambda expressions enable you to do this, to treat functionality as method argument, or code as data.
 *
 * Syntax:  (arguments/parameters) -> {body/statements}
 *    (int a, int b) -> {return a + b; }
 *    (String s) -> { System.out.println(s); }
 *    (a, b) -> a - b  //the type of parameters can be explicitly declared or inferred form the context.
 *    () -> logger.log( System.currentTimeMillis() )   //empty set of parameters
 *     a -> a * a      //single parameter and its type is infered, without parentheses.
 *
 * Lambda expression vs Anonymous class
 *  One key difference is the use of this keyword. For anonymous class ‘this’ keyword resolves to anonymous class,
 *  whereas for lambda expression ‘this’ keyword resolves to enclosing class where lambda is written.
 *
 *  Another difference is in the way these two are compiled. Java compiler compiles lambda expressions and convert them into private method of the class.
 *  It uses invokedynamic instruction that was added in Java 7 to bind this method dynamically.
 *
 */
public class LambdaTest {

    @Test
    public void testComparator(){
        List<Integer> list = Arrays.asList(1,2,3,4,5);

        Collections.sort(list, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;

            }
        });

        Collections.sort(list, (o1, o2) -> {
            return o1.compareTo(o2);
        });
        Collections.sort(list, (o1, o2) -> o1 - o2);
        list.sort((o1, o2) -> o1 - o2);


        Integer[] nums = {1,2,3,4,5};

        Arrays.sort(nums, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2){
                return o1 - o2;
            }
        });

        Arrays.sort(nums, (o1, o2) -> o1 - o2);
    }

    @Test
    public void testRunnable(){
        new Thread( new Runnable() {
            @Override
            public void run(){
                System.out.println("Running on Runnable");
            }
        }).start();

        new Thread(
                () -> System.out.println("Running on Lambda ")  // no ; here
        ).start();

        Runnable r = () -> System.out.println("Running on Lambda 2");
        new Thread(r).start();
    }

    @Test
    public void testComsumer(){
        List<Integer> list = Arrays.asList(1,2,3,4,5);

        for(Integer n : list){
            System.out.print(n + "\t");
        }
        System.out.println();

        list.forEach(n -> System.out.print(n + "\t"));
        System.out.println();

        list.forEach(System.out :: println);
        System.out.println();
    }

    @Test
    public void testPredicate(){
        List<Integer> list = Arrays.asList(1,2,3,4,5);

        System.out.println("Print numbers with specail methods:");
        printAll(list); // print all
        printEvenNumber(list); // print the even number


        System.out.println("Print numbers with Predicate and lambda express:");
        evaluate(list, n -> true);  // print all

        evaluate(list, n -> (n & 1) == 0);  // print the even number

        evaluate(list, n -> (n & 1) == 1);  // print the odd number

        evaluate(list, n -> false);  // print no numbers

        evaluate(list, n -> n > 5);  // print numbers greater than 5
    }

    private void printAll(List<Integer> list){
        for(Integer n : list){
            System.out.print(n + "\t");
        }

        System.out.println();
    }

    private void printEvenNumber(List<Integer> list){
        for(Integer n : list){
            if((n & 1) == 0 ) {// is even
                System.out.print(n + "\t");
            }
        }

        System.out.println();
    }

    private void evaluate(List<Integer> list, Predicate<Integer> predicate) {
        for(Integer n : list){
            if(predicate.test(n) ) {
                System.out.print(n + "\t");
            }
        }

        System.out.println();
    }


    @Test
    public void testLocalVariable (){

        {
            final int num = 1;
            Converter<Integer, String> integerStringConverter = from -> String.valueOf(from + num);

            integerStringConverter.convert(2);  //3
        }

        {
            int num = 1;
            Converter<Integer, String> integerStringConverter = from -> String.valueOf(from + num);

            integerStringConverter.convert(2);  //3
        }

        {
            int num = 1;
            Converter<Integer, String> integerStringConverter = from -> String.valueOf(from + num);

            //local variables referenced from a lambda expression must be final or effectively final
            //num = 3;

            integerStringConverter.convert(2);  //3
        }
    }


    @Test
    public void testFunctionalInterface(){

        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        assertEquals(123, converter.convert("123").intValue());

        //Java 8 allow to pass references of methods or constructors via the :: keyword
        //pass a static method reference via ::
        converter = Integer::valueOf;
        assertEquals(123, converter.convert("123").intValue());

        //pass a reference of a Object method
        MyObject myObject = new MyObject();
        Converter<String, String> converter2 = myObject :: startWith;
        assertEquals("1", converter2.convert("123"));

        //create a reference to the Person constructor via ::
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Joey", "Zhou");
    }


    @FunctionalInterface
    interface Converter<F, T>{
        T convert(F from);
    }

    class MyObject {
        String startWith(String s){
            return String.valueOf(s.charAt(0));
        }
    }

    class Person{
        String firstName;
        String lastName;

//    Person(){
//
//    }

        Person(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }
}




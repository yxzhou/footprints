package fgafa.basic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;
import java.util.StringJoiner;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * What is?
 *   Stream is a sequence of elements supporting sequential and parallel aggregate operations.
 *   Stream operations are either intermediate or terminal.
 *   Streams are created on a source lists or sets. (not map)
 *
 * Intermediate Operations:
 *   .filter(Predicate)
 *   .sorted()  //default, sort by natural order
 *   .sorted(Comparator)
 *   .map(Function)  //convert each element into another object via the funciton
 *
 * Terminal Operations:
 *   boolean anyMatch(Predicate) or allMatch(Predicate) or noneMatch(Predicate)
 *   long count()
 *   Optional<T> findFirst()
 *   Optional<T> reduce(BinaryOperator<T>)
 *   void forEach(Consumer)
 *   <R, A> collect(Collector<>>)
 *
 * Execution Plan
 *   To optimize the performance by reordering the chain, put filter() as early as possible
 *
 * Sequential vs ParallelStream
 *
 * Stream is closed as soon as you call any terminal operation. Java 8 streams cannot be reused.
 *
 */
public class StreamTest {

    @Before
    public void setup() throws Throwable {

    }

    @Test
    public void testFilter() throws Throwable{
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);

        int n1 = find2_oldWay(numbers);
        int n2 = find2_withStream(numbers);

        assertEquals(n1, n2); // n1 == n2 == 4
    }


    private Integer find2_oldWay(List<Integer> numbers){
        for (Integer n : numbers) {
            if ((n & 1) == 0) { // is Even
                int tmp = n * 2;  // double it

                if (tmp > 5) {  // greater than 5
                    return n; // return the first match
                }
            }
        }

        return -1;
    }

    private Integer find2_withStream(List<Integer> numbers) {

        return numbers.stream()
                .filter(n -> (n & 1) == 0 && n * 2 > 5) // is Even and the double is greater than 5
                .findFirst()
                .get();
    }

    
    @Test
    public void testMap() throws Throwable{
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);

        int n1 = find1_oldWay(numbers);
        int n2 = find1_withStream(numbers);

        assertEquals(n1, n2); // n1 == n2 == 8
    }


    private Integer find1_oldWay(List<Integer> numbers){
        for (Integer n : numbers) {
            if ((n & 1) == 0) { // is Even
                int tmp = n * 2;  // double it

                if (tmp > 5) {  // greater than 5
                    return tmp; // return the first match
                }
            }
        }

        return -1;
    }

    private Integer find1_withStream(List<Integer> numbers) {

        return numbers.stream()
                .filter(x -> (x & 1) == 0 ) // is Even
                .map(x -> x * 2)   // double it
                .filter(x -> x > 5)  // greater than 5
                .findFirst()
                .get();
    }

    @Test
    public void testMapAndSorted() {
        List<String> list = Arrays.asList("a1", "a2", "b1", "c2", "c1");

        list.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::print);  //C1C2
    }

    @Test
    public void testReduce() throws Throwable{
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);

        System.out.println("----------compare-----------");
        int n1 = find3_oldWay(numbers);
        int n2 = find3_withStream(numbers);

        assertEquals(n1, n2); // n1 == n2 == 30

        System.out.println("----------reduce: max-----------");
        numbers.stream()
                .reduce((m1, m2) -> Math.max(m1, m2))
                .ifPresent(System.out::println);
        //5

        System.out.println("----------reduce: sum-----------");
        numbers.stream()
                .reduce((m1, m2) -> m1 + m2)
                .ifPresent(System.out::println);
        //15

        int sum2 = numbers.stream()
                .reduce(0, (sum, m2) -> sum += m2)
                .intValue();

        System.out.println(sum2);
        //15

        System.out.println("----------reduce: averageSum-----------");
        int sum3 = numbers.stream()
                .reduce(0,
                        (sum, m) -> {
                              System.out.format("accumulator: sum=%s; m=%s\n", sum, m);
                              return sum += m;},
                        (s1, s2) -> {
                              System.out.format("combinaer: s1=%s, s2=%s\n", s1, s2);
                              return s1 + s2;})
                .intValue();

        System.out.println(sum3);
//        accumulator: sum=0; m=1
//        accumulator: sum=1; m=2
//        accumulator: sum=3; m=3
//        accumulator: sum=6; m=4
//        accumulator: sum=10; m=5
//        15

//        Optional<T> 	reduce(BinaryOperator<T> accumulator)
//        T 	reduce(T identity, BinaryOperator<T> accumulator)
//        <U> U 	reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)

    }


    private Integer find3_oldWay(List<Integer> numbers){
        int sum = 0;

        for (Integer n : numbers) {
            if ((n & 1) == 0) { // is Even
                int tmp = n * 2;  // double it

                if (tmp > 5) {  // greater than 5
                    sum += tmp; //
                }
            }
        }

        return sum;
    }

    private Integer find3_withStream(List<Integer> numbers) {

        return numbers.stream()
                .filter(x -> (x & 1) == 0) // is Even
                .map(x -> x * 2)   // double it
                .filter(x -> x > 5)  // greater than 5
                .reduce((x, y) -> x + y)
                .get();
    }

    @Test
    public void testCreateStream() {

        Stream.of(1, 2, 3)
                .findFirst()
                .ifPresent(System.out::println); //1


        IntStream.range(1, 5)
                .mapToObj(x -> x + ",")
                .forEach(System.out::print); //1,2,3,4,


        Arrays.stream(new int[]{1, 2, 3})
                .map(x -> x * 2)
                .average()
                .ifPresent(System.out::println); // 4.0

    }

    @Test
    public void testSequentialAndParallel() throws Throwable{
        System.out.println("----------compare-----------");
        final int N = 100000;
        List<String> values = new ArrayList<>(N);
        for(int i = 0; i < N; i++){
            values.add(UUID.randomUUID().toString());
        }

        long n1 = find_sequential(values);
        long n2 = find_parallel(values);

        assertEquals(n1, n2); // n1 == n2 == N
//        sequential sort took: 	119126738 ns
//        parallel sort took: 	42572317 ns

        System.out.println("----------sequential-----------");
        int sum1 = Stream.of(1,2,3,4,5,6)
                .reduce(0,
                        (sum, m) -> {
                            System.out.format("accumulator: sum=%s; m=%s [%s]\n", sum, m, Thread.currentThread().getName());
                            return sum += m;},
                        (s1, s2) -> {
                            System.out.format("combinaer: s1=%s, s2=%s [%s]\n", s1, s2, Thread.currentThread().getName());
                            return s1 + s2;})
                .intValue();

        System.out.println(sum1);
//        accumulator: sum=0; m=1 [main]
//        accumulator: sum=1; m=2 [main]
//        accumulator: sum=3; m=3 [main]
//        accumulator: sum=6; m=4 [main]
//        accumulator: sum=10; m=5 [main]
//        accumulator: sum=15; m=6 [main]
//        21

        System.out.println("----------parallel-----------");
        int sum2 = Stream.of(1,2,3,4,5,6)
                .parallel()
                .reduce(0,
                        (sum, m) -> {
                            System.out.format("accumulator: sum=%s; m=%s [%s]\n", sum, m, Thread.currentThread().getName());
                            return sum += m;},
                        (s1, s2) -> {
                            System.out.format("combinaer: s1=%s, s2=%s [%s]\n", s1, s2, Thread.currentThread().getName());
                            return s1 + s2;})
                .intValue();

        System.out.println(sum2);
//        accumulator: sum=0; m=1 [ForkJoinPool.commonPool-worker-2]
//        accumulator: sum=0; m=3 [ForkJoinPool.commonPool-worker-1]
//        accumulator: sum=0; m=4 [main]
//        accumulator: sum=0; m=2 [ForkJoinPool.commonPool-worker-7]
//        accumulator: sum=0; m=6 [ForkJoinPool.commonPool-worker-6]
//        accumulator: sum=0; m=5 [ForkJoinPool.commonPool-worker-4]
//        combinaer: s1=2, s2=3 [ForkJoinPool.commonPool-worker-7]
//        combinaer: s1=5, s2=6 [ForkJoinPool.commonPool-worker-4]
//        combinaer: s1=1, s2=5 [ForkJoinPool.commonPool-worker-7]
//        combinaer: s1=4, s2=11 [ForkJoinPool.commonPool-worker-4]
//        combinaer: s1=6, s2=15 [ForkJoinPool.commonPool-worker-4]


    }

    private long find_sequential(List<String> values){

        long start = System.nanoTime();

        long count = values.stream().sorted().count();

        System.out.println(String.format("sequential sort took: \t%d ns", System.nanoTime() - start));

        return count;
    }

    private long find_parallel(List<String> values) {
        long start = System.nanoTime();

        long count = values.parallelStream().sorted().count();

        System.out.println(String.format("parallel sort took: \t%d ns", System.nanoTime() - start));

        return count;

    }

    @Test
    public void checkExecutionPlan(){
        System.out.println("----------nothing done when only intermediate operations, without terminal operations-----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                });

        System.out.println("----------The following all are with terminal operations----------------------");
        System.out.println("----------filter()-----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("-- final result -- forEach: " + s));

//        filter: d2
//        -- final result -- forEach: d2
//        filter: a2
//        -- final result -- forEach: a2
//        filter: b1
//        -- final result -- forEach: b1
//        filter: b3
//        -- final result -- forEach: b3
//        filter: c
//        -- final result -- forEach: c


        System.out.println("----------map()-----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("-- final result -- anyMatch: " + s);
                    return s.startsWith("A");
                });

//        map: d2
//        -- final result -- anyMatch: D2
//        map: a2
//        -- final result -- anyMatch: A2

        System.out.println("----------map() + filter()-----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("-- final result -- forEach: " + s));

//        map: d2
//        filter: D2
//        map: a2
//        filter: A2
//        -- final result -- forEach: A2
//        map: b1
//        filter: B1
//        map: b3
//        filter: B3
//        map: c
//        filter: C

        //
        System.out.println("----------filter() + map() -----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("-- final result -- forEach: " + s));

//        filter: d2
//        filter: a2
//        map: a2
//        -- final result -- forEach: A2
//        filter: b1
//        filter: b3
//        filter: c

        //sort is a special kind of intermediate operation.
        // It's a so called stateful operation since in order to sort a collection of elements you have to maintain state during sorting.
        System.out.println("----------filter() and sorted()-----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .sorted()
                .forEach(s -> System.out.println("-- final result -- forEach: " + s));

//        filter: d2
//        filter: a2
//        filter: b1
//        filter: b3
//        filter: c
//        -- final result -- forEach: a2
//        -- final result -- forEach: b1
//        -- final result -- forEach: b3
//        -- final result -- forEach: c
//        -- final result -- forEach: d2

        System.out.println("----------sorted() + filter() + map()-----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("-- final result -- forEach: " + s));

//        sort: a2; d2
//        sort: b1; a2
//        sort: b1; d2
//        sort: b1; a2
//        sort: b3; b1
//        sort: b3; d2
//        sort: c; b3
//        sort: c; d2
//        filter: a2
//        map: a2
//        -- final result -- forEach: A2
//        filter: b1
//        filter: b3
//        filter: c
//        filter: d2

        System.out.println("----------filter() + sorted() + map()-----------");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("-- final result -- forEach: " + s));
//        filter: d2
//        filter: a2
//        filter: b1
//        filter: b3
//        filter: c
//        map: a2
//        -- final result -- forEach: A2

    }

    @Test
    public void simulateResuingStream() {
        Stream<String> stream = Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> s.startsWith("a"));

        stream.anyMatch(s -> true);  //ok
        stream.noneMatch(s -> true); //java.lang.IllegalStateException: stream has already been operated upon or closed

        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true);   // ok
        streamSupplier.get().noneMatch(s -> true);  // ok

    }

    @Test
    public void testCollector(){
        List<Person> persons = Arrays.asList(
                new Person("Max", 18),
                new Person("Perter", 23),
                new Person("Pamela", 23),
                new Person("David", 12)
        );

        System.out.println("----------Collectors.toList()-----------");
        List<Person> filtered = persons
                .stream()
                .filter(p -> p.name.startsWith("P"))
                .collect(Collectors.toList());

        filtered.forEach(System.out::println);
//      Perter
//      Pamela

        System.out.println("----------Collectors.groupingBy()-----------");
        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));

        personsByAge.forEach((age, p) -> System.out.format("age %s %s \n", age, p));
//        age 18 [Max]
//        age 23 [Perter, Pamela]
//        age 12 [David]

        System.out.println("----------Collectors.averagingInt()-----------");
        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt( p -> p.age));

        System.out.println(averageAge);
//        19.0

        System.out.println("----------Collectors.summarizingInt()-----------");
        IntSummaryStatistics ageSummary = persons
                .stream()
                .collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummary);
//        IntSummaryStatistics{count=4, sum=76, min=12, average=19.000000, max=23}

        System.out.println("----------Collectors.joining()-----------");
        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

        System.out.println(phrase);
//        In Germany Max and Perter and Pamela are of legal age.

        System.out.println("----------Collectors.toMap()-----------");
        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,  //Function,  keyMapper
                        p -> p.name, //Funciton,  valueMapper
                        (name1, name2) -> name1 + ";" + name2  //BinaryOperator, mergeFunction when key is same
                ));
        //if there is no merge funciton and the mapped keys are not unique, it would throw IllegalStateException

        System.out.println(map);
//        {18=Max, 23=Perter;Pamela, 12=David}


        //The above are about build-in collcetors, the following is to build our own special collector.
        System.out.println("----------Collectors.of()-----------");
        String name = persons
                .stream()
                .collect(Collector.of(
                        () -> new StringJoiner(" | "),  //supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  //accumulator
                        (j1, j2) -> j1.merge(j2),  //combiner
                        StringJoiner::toString));   //finisher

        System.out.println(name);
//        MAX | PERTER | PAMELA | DAVID


    }

    class Person{
        String name;
        int age;

        Person(String name, int age){
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString(){
            return this.name;
        }
    }
}








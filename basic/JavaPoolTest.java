package fgafa.basic;

public class JavaPoolTest {
	public static void main(String args[]) {
		//System.out.println("start to test the pool in Java");

		JavaPoolTest sv = new JavaPoolTest();
		
		System.out.println("\nstart to test the String pool");
		sv.stringPoolTest();

		System.out.println("\nstart to test the Integer pool");
		sv.integerPoolTest();
		
		System.out.println("\nstart to test the Char pool");
		sv.charPoolTest();
		
		System.out.print("===end===");
	}

	/**
	 * n this example we are going to talk about a very interesting subject,
	 * Java String pool.
	 * 
	 * As the name suggest, String pool is a pool, or else a set, of String
	 * objects located in a special place in Java Heap. Java creators introduced
	 * this String pool construct as an optimization on the way String objects
	 * are allocated and stored.
	 * 
	 * String is one of the most used types in Java, and it is a costly one when
	 * it comes to memory space. For example, a 4 character long String requires
	 * 56 bytes of memory.
	 * 
	 * Which shows that only 14% percent of the allocated memory is the actual
	 * data, the 4 characters. So, a lot of overhead there. Naturally, an
	 * optimization should be implemented on how String objects are going to be
	 * stored in the heap. This is why the String pool was created. It is a
	 * simple implementation of the Flyweight pattern, which in essence, says
	 * this : when a lot of data is common among several objects, it is better
	 * to just share the same instance of that data than creating several
	 * different “copies” of it. Applying that to Strings, its better to share
	 * the same String object than creating multiple instances of String objects
	 * with the same value.
	 */
	private void stringPoolTest(){
		String s1 = "abc";
		String s2 = "abc";

		String s3 = new String("abc");

		System.out.println(s1 == s2); //true
		System.out.println(s1 == s3); //false
		System.out.println(s1.equals(s3));//true

		
		String s4 = s3.intern(); 
		System.out.println(s1==s4);   //true
		/**
		 * intern() returns a canonical representation for the string object.
		 * 
		 * A pool of strings, initially empty, is maintained privately by the
		 * class String.
		 * 
		 * When the intern method is invoked, if the pool already contains a
		 * string equal to this String object as determined by the
		 * equals(Object) method, then the string from the pool is returned.
		 * Otherwise, this String object is added to the pool and a reference to
		 * this String object is returned.
		 * 
		 * It follows that for any two strings s and t, s.intern() == t.intern()
		 * is true if and only if s.equals(t) is true.
		 * 
		 * All literal strings and string-valued constant expressions are
		 * interned. String literals are defined in section 3.10.5 of the The
		 * Java™ Language Specification.
		 */
		
		String s5 = "ab";
		s5 = s5 + "c";
		System.out.println(s1==s5);   //false
		/**
		 * Because Strings are immutable, when using + operator to concatenate
		 * two strings, a brand new String is created. The underlying code is
		 * actually using a StringBuffer to implement the concatenation, but the
		 * point is that the new String is allocated “normally” in the heap and
		 * not in the string pool.
		 */
		
		String s6 = "ab" + "c";
		System.out.println(s1==s6);   //true		
		
	}
	
	/**
	 * the JVM maintains a pool of Integer values (similar to the one it
	 * maintains for Strings). But the pool contains only integers from -128 to
	 * 127
	 */
	private void integerPoolTest(){
		Integer i1 = 100;
		Integer i2 = 100;
		// Comparison of integers from the pool - returns true.
		compareInts(i1, i2); //true

		Integer i3 = 130;
		Integer i4 = 130;
		// Same comparison, but from outside the pool
		// (not in the range -128 to 127)
		// resulting in false.
		compareInts(i3, i4); //false

		Integer i5 = new Integer(100);
		Integer i6 = new Integer(100); 
		// Comparison of Integers created using the 'new' keyword
		// results in new instances and '==' comparison leads to false.
		compareInts(i5, i6);  //false
	}
	
	private static void compareInts(Integer i1, Integer i2) {
		System.out.println("Comparing Integers " + i1 + "," + i2
				+ " results in: " + (i1 == i2));
	}
	
	private void charPoolTest(){
		char c1 = 'a';
		char c2 = 'a';

		Character c3 = 'a';
		Character c4 = new Character('a');

		System.out.println(c1 == c2); //true
		System.out.println(c1 == c3); //true
		
		System.out.println(c1 == c4); //true
		System.out.println(c4.equals(c1));//true
		
	}
}

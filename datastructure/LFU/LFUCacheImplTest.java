package datastructure.LFU;


import datastructure.LRU.LRUCache;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.junit.*;
import util.Misc;

public class LFUCacheImplTest {
    LFUCache<Integer, Integer> cache = null;

    @Before public void setup(){
        cache = new LFUCacheImpl(4, 2);
    }

    @Test 
    public void testConstruction() throws Throwable{

        try {
            cache = new LFUCacheImpl(6, 7);
            Assert.fail( "Failed to throw exception!" );
        } catch (IllegalArgumentException e) {}
        try {
            cache = new LFUCacheImpl(-1, 2);
            Assert.fail( "Failed to throw exception!" );
        } catch (IllegalArgumentException e) {}
        try {
            cache = new LFUCacheImpl(3, -2);
            Assert.fail( "Failed to throw exception!" );
        } catch (IllegalArgumentException e) {}

        cache.set(1, 1);

    }

    @Test 
    public void testLFUCache(){

        cache = new LFUCacheImpl2(2, 1);

        assert(cache.get(5) == null);
        cache.set(1, 1);
        assert(1 == cache.get(1));

        cache.set(1, 2);
        assert(2 == cache.get(1));

        cache.set(2, 2);
        cache.set(3, 3);  // evict (2, 2)
        assert(cache.get(2) == null);
        assert(cache.get(1) == 2);
        cache.set(2, 2);  // evict (3, 3)
        assert(cache.get(3) == null);
        assert(cache.get(2) == 2);
        // increasing frequency
        cache.set(1, 2);
        cache.set(1, 3);
        cache.set(1, 4);
        assert(cache.get(1) == 4);
        cache.set(3, 5);  // evict (2, 2)
        assert(cache.get(2) == null);
        assert(cache.get(1) == 4);
        assert(cache.get(3) == 5);
    }

    
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String[][] inputs = {
            {"1,set(2, 1),get(2),set(3, 2),get(2),get(3)", "1,-1,2"},
            {"3,set(2, 2),set(1, 1),get(2),get(1),get(2),set(3, 3),set(4, 4),get(3),get(2),get(1),get(4)", "2,1,2,-1,2,1,4"}
        };


        // int parameter
        Class[] paramInt1 = new Class[1];
        paramInt1[0] = Integer.TYPE;

        Class[] paramInt2 = new Class[2];
        paramInt2[0] = Integer.TYPE;
        paramInt2[1] = Integer.TYPE;

        
        long start = System.nanoTime();
        System.out.println("---start--" + start);
        
        for (String[] usercase : inputs) {
            System.out.println("\n Input:" + usercase[0]);
            List<Integer> result = new ArrayList<>();

            int firstComma = usercase[0].indexOf(',');
            Class cls = Class.forName("datastructure.LFU.LFUCacheImpl42");
            //Class cls = Class.forName("datastructure.LRU.LRUCache2");
            
            Constructor<LRUCache> constructor = cls.getConstructor(paramInt1);
            Object obj = constructor.newInstance(Integer.valueOf(usercase[0].substring(0, firstComma)));
            
            Method method;
            StringTokenizer st = new StringTokenizer(usercase[0].substring(firstComma), ")");
            while (st.hasMoreTokens()) {
                String action = st.nextToken();
                int index = action.indexOf('(');

                if ("set".equals(action.substring(1, index))) {
                    method = cls.getDeclaredMethod("set", paramInt2);

                    int seconfComma = action.lastIndexOf(',');
                    method.invoke(obj, Integer.valueOf(action.substring(index + 1, seconfComma).trim()),
                            Integer.valueOf(action.substring(seconfComma + 1, action.length()).trim()));
                } else {
                    method = cls.getDeclaredMethod("get", paramInt1);

                    Object ret = method.invoke(obj,
                            Integer.valueOf(action.substring(index + 1, action.length())));

                    if (ret instanceof Integer) {
                        // System.out.println(String.format("  return value= %d", ret));
                        result.add((Integer) ret);
                    }
                }

            }

            //System.out.println("  Output: ");
            //Misc.printArrayList_Integer(result);
            
            if(usercase[1].length() > 500){
                junit.framework.Assert.assertEquals(usercase[1], Misc.array2String(result, false).substring(0, usercase[1].length()) );
                //System.out.println(usercase[1]);
                //System.out.println(Misc.array2String(result, false).substring(0, usercase[1].length()));
                
            }else{
                junit.framework.Assert.assertEquals(usercase[1], Misc.array2String(result, false).toString() );
            }
            
        }

        System.out.println("---end--" + (System.nanoTime() - start));
        
    }
}

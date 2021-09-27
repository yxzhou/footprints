package datastructure.LFU;


import org.junit.*;

public class LFUCacheImplTest {
    LFUCache<Integer, Integer> cache = null;

    @Before public void setup(){
        cache = new LFUCacheImpl(4, 2);
    }

    @Test public void testConstruction() throws Throwable{

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

    @Test public void testLFUCache(){

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

}

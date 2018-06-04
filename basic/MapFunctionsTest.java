package fgafa.basic;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

/**
 *  the change of Map on Jdk8
 */
public class MapFunctionsTest {

    @Test
    public void testMapFucntions(){

        Map<Integer, String> map = new HashMap<>();

        for(int i = 0; i < 10; i++){
            map.putIfAbsent(i, "val" + i);
        }

        map.forEach((id, val) -> System.out.println(val));

        map.computeIfPresent(3, (key, val) -> val + key);
        assertEquals("val33", map.get(3));

        map.computeIfPresent(9, (key, val) -> null);
        assertEquals(false, map.containsKey(9));

        assertEquals(false, map.containsKey(12));
        map.computeIfAbsent(12, key -> "val" + key);
        assertEquals("val12", map.get(12));

        String currValue = map.get(12);
        map.computeIfAbsent(12, key -> "newValue");
        assertEquals(currValue, map.get(12));

        assertEquals("val1", map.getOrDefault(1, "DefaultValue"));
        assertEquals("DefaultValue", map.getOrDefault(11, "DefaultValue"));

        assertEquals(false, map.containsKey(14));
        map.merge(14, "val14", (value, newValue) -> value.concat(newValue));
        assertEquals("val14", map.get(14));

        map.merge(14, "-more", (value, newValue) -> value.concat(newValue));
        assertEquals("val14-more", map.get(14));

        System.out.println("--------------------------");
        map.forEach((id, val) -> System.out.println(val));
    }




}

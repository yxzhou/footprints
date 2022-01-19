/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.map.treeMap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import util.Misc;

/**
 *
 * @author yuanxi
 */
public class MyCalendarIITest {
   public static void main(String[] args){
        String[][] inputs = {
            {"book(10,20) book(50,60) book(10,40) book(5,15) book(5,10) book(25,55)",
                "true,true,true,false,true,true"},
            {"book(24,40) book(43,50) book(27,43) book(5,21) book(30,40) book(14,29) book(3,19) book(3,14) book(25,39) book(6,19)",
                "true,true,true,true,false,false,true,false,false,false"},
            {"book(44,50) book(47,50) book(9,15) book(4,10) book(2,7) book(28,37) book(26,33) book(22,28) book(43,50) book(18,25)",
                "true,true,true,true,true,true,true,true,false,true"},
            {"book(8,23) book(35,48) book(24,39) book(10,22) book(10,23) book(8,22) book(1,14) book(36,50) book(42,50) book(42,50)",
                "true,true,true,true,false,false,false,false,true,false"}
        };
                
        // int parameter
        Class[] paramInt2 = new Class[2];
        paramInt2[0] = Integer.TYPE;
        paramInt2[1] = Integer.TYPE;

        
        for (String[] usercase : inputs) {
            System.out.println("\n Input:" + usercase[0]);
            List<Boolean> result = new ArrayList<>();
            
            Class cls;
            try {
                cls = Class.forName("datastructure.map.treeMap.MyCalendarII");

                Object obj = cls.getConstructor().newInstance();

                Method method;
                StringTokenizer st = new StringTokenizer(usercase[0], " ");
                while (st.hasMoreTokens()) {
                    String action = st.nextToken();
                    int index = action.indexOf('(');
System.out.println(" " + action);
                    
                    if ("book".equals(action.substring(0, index))) {
                        method = cls.getDeclaredMethod("book", paramInt2);

                        int seconfComma = action.lastIndexOf(',');
                        Object ret = method.invoke(obj, Integer.valueOf(action.substring(index + 1, seconfComma).trim()),
                            Integer.valueOf(action.substring(seconfComma + 1, action.length() - 1).trim()));

                        if (ret instanceof Boolean) {
                            result.add((Boolean) ret);
                        }
                    }

                }

                Assert.assertEquals(usercase[1], Misc.array2String(result, false).toString() );
            }catch (Exception ex) {
                Logger.getLogger(MyCalendarII.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   }
}

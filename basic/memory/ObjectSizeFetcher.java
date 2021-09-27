package basic.memory;

import java.lang.instrument.Instrumentation;

public class ObjectSizeFetcher {

    private static Instrumentation instrumentation;

    public static void premain(String args,
                               Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
    
    public static void main(String [] args) {
        
        //boolean[] filled = new boolean[Integer.MAX_VALUE];
        //System.out.println(ObjectSizeFetcher.getObjectSize(filled));
        
        int x = Integer.MAX_VALUE / 32;
        System.out.println(" -- " + x);
        int[] filled2 = new int[x];
        for(int i = 0; i < filled2.length; i++){
            filled2[i] = i;
        }
        System.out.println(ObjectSizeFetcher.getObjectSize(filled2));
    }
}

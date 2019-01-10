package fgafa.design.mypatterns.singleton;


import java.io.ObjectStreamException;
import java.io.Serializable;

public class SingletonDoubleClick implements Serializable{
    private static final long serialVersinoUID = 5765648836796281035L;
    private volatile static SingletonDoubleClick instance = null;
    
    private SingletonDoubleClick(){
        
    }
    /**
     * @return: The same instance of this class every time
     */
    public static SingletonDoubleClick getInstance() {
        if(null == instance){
            synchronized(SingletonDoubleClick.class){
                if(null == instance){
                        instance = new SingletonDoubleClick();
                }
            }
        }
        
        return instance;
    }


    /**
     *     readResolve 方法维持了Singleton的单例属性
     */
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

}

package fgafa.design.mypatterns;


public class Singleton {
    private volatile static Singleton instance = null;
    
    private Singleton(){
        
    }
    /**
     * @return: The same instance of this class every time
     */
    public static Singleton getInstance() {
        if(null == instance){
            synchronized(Singleton.class){
                if(null == instance){
                        instance = new Singleton();
                }
            }
        }
        
        return instance;
    }
}

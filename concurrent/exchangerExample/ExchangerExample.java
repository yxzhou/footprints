package concurrent.exchangerExample;

import java.util.concurrent.Exchanger;

/**
 * The java.util.concurrent.Exchanger class represents a kind of rendezvous point where two threads can exchange objects
 */
public class ExchangerExample {

    public static void main(String[] args){
        Exchanger exchanger = new Exchanger();

        new Thread(new ExchangerRunnable(exchanger, "AA")).start();
        new Thread(new ExchangerRunnable(exchanger, "BB")).start();
    }

}

class ExchangerRunnable implements Runnable {
    private Exchanger exchanger = null;
    private Object object = null;

    public ExchangerRunnable(Exchanger exchanger, Object object){
        this.exchanger = exchanger;
        this.object = object;
    }

    public void run() {
        try{
            Object previous = this.object;

            Object newObject  = this.exchanger.exchange(previous);

            System.out.println(String.format("%s exchanged %s for %s, %s", Thread.currentThread().getName(), previous, newObject, this.object));
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

//Thread-0 exchanged AA for BB, AA
//Thread-1 exchanged BB for AA, BB

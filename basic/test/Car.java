package fgafa.basic.test;

import fgafa.basic.test.Drivable;
import fgafa.basic.test.OutOfGasException;

public class Car extends Vehicle implements Drivable {
  private boolean hasGas = true;

  public void drive() { 
       if( hasGas ) {
           // Happily run
       } else {
           try{
             throw new OutOfGasException();  // OutOfGasException is a checked Excepton, so it has been deal with.  
           }catch(OutOfGasException e){
             e.printStackTrace();
           }
           
           throw new TireBrokenException(); // TireBrokenException is a unchecked Exception.
       }
  }

  void setHasGas(boolean hasGas) {
      this.hasGas = hasGas;
  }
}

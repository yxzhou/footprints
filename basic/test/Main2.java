package basic.test;


interface Drivable1
{
  void drive();
}

class Vehicle1{
  String vin;
  String getVin(){
    return this.vin;
  }
}

class Car1 extends Vehicle1 implements Drivable1 {
  private boolean hasGas = true;

  @Override
  //void drive() {  // wrong, can't reduce visibility 
  public void drive() {
       if( hasGas ) {
           // Happily run
       } else {
           try {
            throw new OutOfGasException();
          }
          catch (OutOfGasException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
       }
  }

  void setHasGas(boolean hasGas) {
      this.hasGas = hasGas;
  }

  @Override
  String getVin(){
    return  Car1.class + this.vin;
  }

}


public class Main2  //only this can be public 
{

    public static void main(StringTest[] args) {
      
      //  Vehicle v = new Car();
      //  v.run();
    }

}


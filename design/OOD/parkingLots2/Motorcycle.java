package fgafa.design.OOD.parkingLots2;

public class Motorcycle extends Vehicle {

    // Write your code here
    public Motorcycle() {
        spotsNeeded = 1;
        size = VehicleSize.Motorcycle;
    }
    
    public boolean canFitInSpot(ParkingSpot spot) {
        return true;
    }
    
    public void print() {  
        System.out.print("M");  
    }

}

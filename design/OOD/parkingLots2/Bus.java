package design.OOD.parkingLots2;

public class Bus extends Vehicle {

    // Write your code here
    public Bus() {
        spotsNeeded = 5;
        size = VehicleSize.Large;
    }

    public boolean canFitInSpot(ParkingSpot spot) {
        return spot.getSize() == VehicleSize.Large;
    }

    public void print() {  
        System.out.print("B");  
    } 

}

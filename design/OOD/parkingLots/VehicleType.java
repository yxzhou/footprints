package fgafa.design.OOD.parkingLots;

public enum VehicleType {
    MOTOCYCLE(1), CAR(2), BUS(3);
    
    private int value;
    
    VehicleType(int value){
        this.value = value;
    }
    
    public int value(){
        return value;
    }
}

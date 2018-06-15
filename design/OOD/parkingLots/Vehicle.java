package fgafa.design.OOD.parkingLots;

import java.util.UUID;

public abstract class Vehicle {
    private UUID id;
    private String plate;
    private VehicleType type;
    private long parkingStartTime;
    
    public Vehicle(String plate, VehicleType type){
        this.id = UUID.randomUUID();
        this.plate = plate;
        this.type = type;
        this.parkingStartTime = System.currentTimeMillis();
    }
    
    public UUID getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public VehicleType getType() {
        return type;
    }

    public long getParkingStartTime() {
        return parkingStartTime;
    }
    
}   

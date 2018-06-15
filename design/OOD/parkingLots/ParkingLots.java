package fgafa.design.OOD.parkingLots;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLots {
    private int levels;
    private int rows;
    private int columns;
    private ParkingSpace[][][] spaces;

    private Map<UUID, Vehicle> vehicles;
    private Map<UUID, ParkingSpace> vehicle2Space;
    
    public ParkingLots(int levels, int rows, int columns){
        this.levels = levels;
        this.rows = rows;
        this.columns = columns;
        this.spaces = new ParkingSpace[levels][rows][columns];
        
        initParkingSpace();
    }

    public boolean parking(Vehicle v){
        if(vehicle2Space.containsKey(v.getId())){
            throw new IllegalArgumentException("Found duplicated Vehicle");
        }
        
        ParkingSpace space = findSpace(v);
        if(space == null){
            return false;
        }
        
        vehicles.put(v.getId(), v);
        vehicle2Space.put(v.getId(), space);
        
        switch(v.getType()){
            case MOTOCYCLE:
            case CAR:
                space.parking(v);
                break;
            case BUS:
                int i = space.getLevelIndex();
                int j = space.getRowIndex();
                int k = space.getColumnIndex();
                for(int p = 0; p < 5; p++){
                    spaces[i][j][k + p].parking(v);
                }
                break;
        }
        
        return true;
    }
    
    public void unparking(Vehicle v){
        if(!vehicle2Space.containsKey(v.getId())){
            throw new IllegalArgumentException("Found duplicated Vehicle");
        }
        
        vehicles.remove(v.getId());
        ParkingSpace space = vehicle2Space.remove(v.getId());

        switch(v.getType()){
            case MOTOCYCLE:
            case CAR:
                space.unparking();
                break;
            case BUS:
                int i = space.getLevelIndex();
                int j = space.getRowIndex();
                int k = space.getColumnIndex();
                for(int p = 0; p < 5; p++){
                    spaces[i][j][k + p].unparking();
                }
                break;
        }
    }
    
    public ParkingSpace findSpace(Vehicle v){
        switch(v.getType()){
            case MOTOCYCLE:
                for(int i = 0; i < levels; i++){
                    for(int j = 0; j < rows; j++){
                        for( int k = 0; k < columns; k++ ){
                            if(spaces[i][j][k].isFree()){
                                return spaces[i][j][k];
                            }
                        }
                    }
                };
                break;
            case CAR:
                for(int i = 0; i < levels; i++){
                    for(int j = 0; j < rows; j++){
                        for( int k = columns / 4; k < columns; k++ ){
                            if(spaces[i][j][k].isFree()){
                                return spaces[i][j][k];
                            }
                        }
                    }
                };
                break;
            case BUS:
                for(int i = 0; i < levels; i++){
                    for(int j = 0; j < rows; j++){
                        for( int k = columns * 3 / 4; k < columns - 4; k++ ){
                            int p = 0;
                            for( ; p < 5; p++){
                                if(!spaces[i][j][k + p].isFree()){
                                    p = 100;
                                }
                            }
                            
                            if(p == 5){
                                return spaces[i][j][k];
                            }
                        }
                    }
                };
                break;
        }
        return null;
    }
    
    private void initParkingSpace(){
        for(int i = 0; i < levels; i++){
            for(int j = 0; j < rows; j++){
                int k = 0;
                int limit;
                for( limit = columns / 4; k < limit; k++ ){
                    spaces[i][j][k] = new ParkingSpace(i, j, k, true, VehicleType.MOTOCYCLE);
                }
                
                for( limit = columns * 3 / 4; k < limit; k++ ){
                    spaces[i][j][k] = new ParkingSpace(i, j, k, true, VehicleType.CAR);
                }
                
                for( limit = columns; k < limit; k++ ){
                    spaces[i][j][k] = new ParkingSpace(i, j, k, true, VehicleType.BUS);
                }
            }
        }
        
    }
}

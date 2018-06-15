package fgafa.design.OOD.parkingLots;

public class ParkingSpace {
    private int levelIndex;
    private int rowIndex;
    private int columnIndex;
    
    private boolean isFree;

    private VehicleType fitType;
    private Vehicle v;

    public ParkingSpace(int levelIndex, int rowIndex, int columnIndex, boolean isFree, VehicleType fitType){
        this.levelIndex = levelIndex;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.isFree = isFree;
        this.fitType = fitType;
    }
    
    public int getLevelIndex() {
        return levelIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
    public boolean isFree() {
        return isFree;
    }
    
    public void parking(Vehicle v){
        this.isFree = false;
        this.v = v;
    }
    
    public void unparking(){
        this.isFree = true;
        this.v = null;
    }
}

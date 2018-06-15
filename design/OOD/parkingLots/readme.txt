Problem: Design a parking lot manager system.

Requirement:
  1.  一共有n层，每层m列，每列k个位置 
  2. 停车场可以停摩托车，公交车，汽车 
  3.停车位分别有摩托车位，汽车位，大型停车位 
  4.每一列，摩托车位编号范围为[0,k/4),汽车停车位编号范围为[k/4,k/4*3),大型停车位编号范围为[k/4*3,k) 
  5.一辆摩托车可以停在任何停车位 
  6.一辆汽车可以停在一个汽车位或者大型停车位 
  7.一辆公交车可以停在一列里的连续5个大型停车位。

Clarify
   need parking ticket? 
   is there multiple entrance and exit,  need thread safe? 
    

Core objects
   ParkingSpace
   Vehicle,  MotorCycle, Car and Bus
   Parkinglots
   ParkinglotsManager
   
Cases
   a vehicle enter, find a space and parking
   a vehicle left, release the space
   
   
Class Diagram
   ParkingSpace
      int x; // layer
      int y; // column
      int z; 
      boolean isFree;
      VehicleType fitType;
      
      
   Parkinglots
      int n;
      int m;
      int k;
      ParkingSpace[] spaces;
      
   Vehicle
      int id;
      String plate;
      VehicleType type;
      long parkingStartTime;

   Motocycle extends Vehicle
   Car extends Vehicle
   Bus extends Vehicle
      
   ParkinglotsManager
      private Parkinglots parkinglots;
      private List<Vehicles> vehicles;
      private Map<Integer, ParkingSpace> vehicle2Space;

      public boolean parking(Vehicle v)
      public void unparking(Vehicle v)
      
   enum VehicleType
      MOTORCYCLE("2 wheeler"), CAR("4-wheeler"), BUS("")
               
   
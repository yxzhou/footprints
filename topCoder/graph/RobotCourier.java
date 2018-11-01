package fgafa.topCoder.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import fgafa.util.Misc;

/**
 *
 *  * http://community.topcoder.com/stat?c=problem_statement&pm=1749&rd=4555
 *
 * A robotic courier needs to deliver a package through a minefield, following a safe path discovered by a robotic scout. The scout's path
 * is not necessarily as efficient as it could be. For example, it might loop back on itself. The courier need not follow the path exactly,
 * but can take shortcuts when it can do so safely.
 *
 * Each robot can make only three kinds of moves, each represented by a single letter: it can go forward 1 meter ('F'), pivot right 60 degrees
 * ('R'), or pivot left 60 degrees ('L'). Notice that the locations on which a robot can begin or end a move form a hexagonal grid. The scout
 * and courier begin in the same location, facing in the same direction. The courier's goal is to reach the last location visited by the scout
 * as quickly as possible. To travel safely, the courier must always stay in the wheel tracks of the scout. That is, any forward movement made
 * by the courier must be along a path segment traveled by the scout. Pivoting left or right is always safe. Note that the courier is
 * permitted to follow a path segment in either the same or the opposite direction as the scout. Similarly, the courier may be facing in any
 * direction when it reaches its final destination--it need not end up facing in same direction as the scout.
 *
 * The courier requires 3 seconds to pivot left or right 60 degrees, and 4 seconds to move forward one meter. However, because of acceleration
 * effects, the courier can move faster when it makes several consecutive forward moves. The first and last moves in any such sequence take 4
 * seconds each, but intermediate moves in the sequence take 2 seconds each. For example, it would take the courier 20 seconds to travel 8 meters
 * in a straight line: 4 seconds for the first meter, 4 seconds for the last meter, and 12 seconds for the six meters in between.
 *
 * For example, suppose the scout follows the path "FRRFLLFLLFRRFLF" (all quotes for clarity only). Altogether, it visits six locations, which
 * can be drawn as
 *
  _
 /6\_
 \_/5\_
   \_/4\
   /2\_/
   \_/3\
   /1\_/
   \_/

 * It begins in hexagon 1, facing upward, and travels in order to hexagons 2, 3, and 4. It then returns to hexagon 2 before continuing on to
 * hexagons 5 and 6.
 *
 *      F  RRF  LLF  LLF  RRF  LF
 *   1  2   3    4    3    5   6
 *
 * The courier could deliver the package in a minimum of 15 seconds, using the path "FFLF" which visits hexagons 1, 2, 5, and 6.
 *
 * The scout's path will be given as a String[] path, rather than as a single String. For example, the scout's path above might have been given
 * as {"FRRFLLFLL", "FRRFLF"}. Note that there is no significance to where the breaks fall between strings in path; it is best to think of all the
 * strings being concatenated together. Given the path, you must calculate the minimum time needed for the courier to deliver its package.
 *
 * Constraints
 * ====================
 * -	path contains between 1 and 10 elements, inclusive.
 * -	Each element of path contains between 1 and 50 characters, inclusive.
 * -	Each element of path contains only the characters 'F', 'L', and 'R'.
 *
 *
 * Examples
 * 0)
 * { "FRRFLLFLLFRRFLF" }
 * 
 * Returns: 15
 * The example above.
 * 
 * 1)
 * { "RFLLF" }
 * 
 * Returns: 17
 * Even though the ending location is one meter in front of the starting location, the courier cannot simply go forward,
 * because that would not be safe. It must follow in the tracks of the scout.
 * 
 * 2)
 * { "FLFRRFRFRRFLLFRRF" }
 * 
 * Returns: 0
 * Scout ends up at starting location.
 * 
 * 3)
 * { "FFFFFFFFFRRFFFFFFRRFFFFF",
 * "FLLFFFFFFLLFFFFFFRRFFFF" }
 * 
 * Returns: 44
 * The shortest path is "FFFRFFFFFFRFFFF".
 * 
 * 4)
 * { "RFLLFLFLFRFRRFFFRFFRFFRRFLFFRLRRFFLFFLFLLFRFLFLRFF",
 * "RFFLFLFFRFFLLFLLFRFRFLRLFLRRFLRFLFFLFFFLFLFFRLFRLF",
 * "LLFLFLRLRRFLFLFRLFRF" }
 * 
 * Returns: 24
 * 
 * 5)
 * { "LLFLFRLRRLRFFLRRRRFFFLRFFRRRLLFLFLLRLRFFLFRRFFFLFL",
 * "RLFFRRLRLRRFFFLLLRFRLLRFFLFRLFRRFRRRFRLRLRLFFLLFLF",
 * "FRFLRFRRLLLRFFRRRLRFLFRRFLFFRLFLFLFRLLLLFRLLRFLLLF",
 * "FFLFRFRRFLLFFLLLFFRLLFLRRFRLFFFRRFFFLLRFFLRFRRRLLR",
 * "FFFRRLLFLLRLFRRLRLLFFFLFLRFFRLRLLFLRLFFLLFFLLFFFRR",
 * "LRFRRFLRRLRRLRFFFLLLLRRLRFFLFRFFRLLRFLFRRFLFLFFLFR",
 * "RFRRLRRFLFFFLLRFLFRRFRFLRLRLLLLFLFFFLFRLLRFRLFRLFR",
 * "LLFLFRLFFFFFFFRRLRLRLLRFLRLRRRRRRRRLFLFLFLRFLFRLFF",
 * "RLFRRLLRRRRFFFRRRLLLLRRLFFLLLLLRFFFFRFRRLRRRFFFLLF",
 * "FFFFLRRLRFLLRRLRLRFRRRRLFLLRFLRRFFFRFRLFFRLLFFRRLL" }
 * 
 * Returns: 169
 *
 * Thought:
 *   1) from "Note that the courier is permitted to follow a path segment in either the same or the opposite direction as the scout.",
 *   the locations in the robot's route need be treat as a UDG
 *   2) it's to find the shortest path in a UDG.
 *   3)construct the graph, the point is define the vertex(location), especially to find out if the robot back to a visited location.
 *
 *
 *
 */

public class RobotCourier {

    public int minDistance(String[] scoutPath){
        if(null == scoutPath || 0 == scoutPath.length){
            return 0;
        }

        // traversal the route, build the UDG
        Location start = new Location(0, 0);

        Map<String, Location> locations = new HashMap<>();
        locations.put("0 0", start);

        Location curr = start;
        int direction = 0;
        for(String directions : scoutPath){
            for(char c : directions.toCharArray()){
                switch (c){
                case 'F':
                    int newX = curr.x + diffs[direction][0];
                    int newY = curr.y + diffs[direction][1];
                    String position = newX + " " + newY;
                    if(!locations.containsKey(position)){
                        locations.put(position, new Location(newX, newY));
                    }
                    Location next = locations.get(position);

                    curr.neighbors[direction] = next;
                    next.neighbors[(direction + 3) % 6] = curr;

                    curr = next;
                    break;
                case 'R':
                    direction = (direction + 1) % 6;
                    break;
                case 'L':
                    direction = (direction + 5) % 6;
                    break;
                default:
                    throw new IllegalArgumentException();
                }
            }
        }

        Location end = curr;

        if(start.equals(end)){
            return 0;
        }

        //bfs, find the shortest path in the UDG
        start.minDistance = 0;
        start.distances[0] = 0;
        start.directionCount[0] = 0;

        Queue<Location> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()){
            curr = queue.poll();

            if(curr.equals(end)){
                return curr.minDistance;
            }

            if(curr.visited){
                continue;
            }

            curr.visited = true;

            for( direction = 0; direction < curr.neighbors.length; direction++){
                if(curr.neighbors[direction] != null && !curr.neighbors[direction].visited){
                    Location next = curr.neighbors[direction];

                    int distance = Integer.MAX_VALUE;
                    for(int directionIn = 0; directionIn < curr.distances.length; directionIn++){
                        if(curr.distances[directionIn] != Integer.MAX_VALUE && directionIn != direction){
                            int pivot = Math.abs(directionIn - direction); //pivot will be 0, 1, 2, 3, 4, 5,
                            if(pivot >= 4){
                                pivot = 6 - pivot; // 4->2, 5->1
                            }

                            distance  = Math.min(distance, curr.distances[directionIn] + 4 + pivot * 3);
                        }
                    }

                    if(curr.distances[direction] != Integer.MAX_VALUE){
                        next.distances[direction] = curr.distances[direction] + (curr.directionCount[direction] >= 2 ? 2 : 4);
                        next.directionCount[direction] = 1 + curr.directionCount[direction];
                    }else{
                        next.distances[direction] = distance;
                        next.directionCount[direction] = 1;
                    }

                    distance = Math.min(distance, next.distances[direction]);
                    if(distance < next.minDistance){
                        next.minDistance = distance;
                    }

                    queue.add(next);
                }
            }
        }

        //todo
        return -1;
    }


    /**
     *  define the start point as [0, 0], clockwise view
     *    after 'F',    0 degree, it's [2, 0],
     *    after 'RF',   60 degree, it's [1, 1]
     *    after 'RRF', 120 degree, it's [-1, 1]
     *    after 'RRRF' or 'LLLF', 180 degree  it's [-2, 0]
     *    after 'LLF', 240 degree, it's [-1, -1]
     *    after 'LF' , 300 degree, it's [1, -1]
     */
    static int[][] diffs= {
            {2, 0},
            {1, 1},
            {-1, 1},
            {-2, 0},
            {-1, -1},
            {1, -1}
    };

    class Location{
        int x;
        int y;

        Location[] neighbors = new Location[6]; //clock-wise,  0-0 degree, 1-60 degree, 2-120 degree, 3-180 degree, 4-240 degree, 5-300 degree

        boolean visited = false;

        int[] distances = new int[6]; //distance from the 6 directions, Integer.MAX_VALUE as default.
        int[] directionCount = new int[6]; //default all are 0

        int minDistance = Integer.MAX_VALUE;

        Location(int x, int y){
            this.x = x;
            this.y = y;

            for(int i = 0; i < distances.length; i++){
                distances[i] = Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Location){
                Location o2 = (Location) obj;

                return o2.x == x && o2.y == y;
            }

            return false;
        }
    }

    public static void main(String[] args){
        String[][] cases = {
                { "FRRFLLFLLFRRFLF" },
                { "RFLLF" },
                { "FLFRRFRFRRFLLFRRF" },
                { "FFFFFFFFFRRFFFFFFRRFFFFF", "FLLFFFFFFLLFFFFFFRRFFFF" },
                { "RFLLFLFLFRFRRFFFRFFRFFRRFLFFRLRRFFLFFLFLLFRFLFLRFF", "RFFLFLFFRFFLLFLLFRFRFLRLFLRRFLRFLFFLFFFLFLFFRLFRLF", "LLFLFLRLRRFLFLFRLFRF" },
                { "LLFLFRLRRLRFFLRRRRFFFLRFFRRRLLFLFLLRLRFFLFRRFFFLFL",
                  "RLFFRRLRLRRFFFLLLRFRLLRFFLFRLFRRFRRRFRLRLRLFFLLFLF",
                  "FRFLRFRRLLLRFFRRRLRFLFRRFLFFRLFLFLFRLLLLFRLLRFLLLF",
                  "FFLFRFRRFLLFFLLLFFRLLFLRRFRLFFFRRFFFLLRFFLRFRRRLLR",
                  "FFFRRLLFLLRLFRRLRLLFFFLFLRFFRLRLLFLRLFFLLFFLLFFFRR",
                  "LRFRRFLRRLRRLRFFFLLLLRRLRFFLFRFFRLLRFLFRRFLFLFFLFR",
                  "RFRRLRRFLFFFLLRFLFRRFRFLRLRLLLLFLFFFLFRLLRFRLFRLFR",
                  "LLFLFRLFFFFFFFRRLRLRLLRFLRLRRRRRRRRLFLFLFLRFLFRLFF",
                  "RLFRRLLRRRRFFFRRRLLLLRRLFFLLLLLRFFFFRFRRLRRRFFFLLF",
                  "FFFFLRRLRFLLRRLRLRFRRRRLFLLRFLRRFFFRFRLFFRLLFFRRLL" }

        };

        int[] expects = {
                15,
                17,
                0,
                44,
                24,
                169
        };

        RobotCourier sv = new RobotCourier();

        for(int i = 0; i < cases.length; i++){
            int result = sv.minDistance(cases[i]);
            boolean bingo = expects[i] == result;

            System.out.println(String.format("\n%d: %s\n %b\t%s", i, Misc.array2String(cases[i]), bingo, bingo ? "" : expects[i] + " instead of " + result  ));
        }
    }

}

package fgafa.dailyCoding2.airbnb;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import fgafa.math.Fibonacci;

/**
 *
 * This problem was asked by Airbnb.
 *
 * You are given a huge list of airline ticket prices between different cities around the world on a given day.
 * These are all direct flights. Each element in the list has the format (source_city, destination, price).
 *
 * Consider a user who is willing to take up to k connections from their origin city A to their destination B.
 * Find the cheapest fare possible for this journey and print the itinerary for that journey.
 *
 * For example, our traveler wants to go from JFK to LAX with up to 3 connections, and our input flights are as follows:
 *
 * [
 *     ('JFK', 'ATL', 150),
 *     ('ATL', 'SFO', 400),
 *     ('ORD', 'LAX', 200),
 *     ('LAX', 'DFW', 80),
 *     ('JFK', 'HKG', 800),
 *     ('ATL', 'ORD', 90),
 *     ('JFK', 'LAX', 500),
 * ]
 * Due to some improbably low flight prices, the cheapest itinerary would be JFK -> ATL -> ORD -> LAX, costing $440.
 *
 */

public class CheapestItinerary {

    class Flight{
        String depart;
        String arrive;
        int price;
    }

    /**
     * BFS, limit times
     *
     */
    public List<String> cheapestItinerary(Flight[] flights, int limit, String depart, String arrive){
        if(flights == null || flights.length == 0){
            return Collections.emptyList();
        }

        //reorganize input data
        Map<String, List<Flight>> flightMap = new HashMap<>();
        for(Flight flight : flights){
            flightMap.putIfAbsent(flight.depart, new LinkedList<>());
            flightMap.get(flight.depart).add(flight);
        }


        //BFS
        Queue<String> queue = new LinkedList<>();
        queue.offer(depart);

        Map<String, Itinerary> itineraryMap= new HashMap<>();
        itineraryMap.put(depart, new Itinerary(new LinkedList<>(), 0));

        for( ; limit > 0; limit--){
            for(int i = queue.size(); i > 0; i--){
                String curr = queue.poll();

                if(!flightMap.containsKey(curr)){
                    continue;
                }

                for(Flight flight : flightMap.get(curr)){
                    if(!itineraryMap.containsKey(flight.arrive) || itineraryMap.get(flight.arrive).price > itineraryMap.get(flight.depart).price + flight.price){
                        Itinerary itinerary = itineraryMap.get(flight.depart).clone();
                        itinerary.paths.add(flight.depart);
                        itinerary.price += flight.price;
                        itineraryMap.put(flight.arrive, itinerary);
                    }
                }
            }
        }

        if(!itineraryMap.containsKey(arrive)){
            return Collections.emptyList();
        }

        List<String> result = itineraryMap.get(arrive).paths;
        result.add(arrive);
        return result;
    }

    class Itinerary{
        List<String> paths;

        int price;

        Itinerary(List<String> paths, int price){
            this.paths = paths;
            this.price = price;
        }

        @Override
        public Itinerary clone(){
            return new Itinerary(new LinkedList<>(paths), this.price);
        }
    }

}

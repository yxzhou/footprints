package fgafa.todo.goo;

import java.util.ArrayList;
import java.util.List;

/**
 * _http://www.mitbbs.com/article_t/JobHunting/33201431.html
 * Google Onsite
 * 
 * 每个城市在每个月有一定数量的假日。每个月从一个城市只能飞另一个城市。为了取得最多假期，该怎么飞。
 * 
 */

public class CatchHolidays {

    public List<City> getMostHolidays(List<City> cities){
        if(null == cities || 0 == cities.size()){
            return cities;
        }
        
        Pair[] pairs = new Pair[cities.size()]; 
        for(City city : cities){
            pairs[city.id] = new Pair();
        }
        
        for(int month = 0; month < 12; month++){
            for(City city : cities){
                for(City from : city.flightsFrom){
                    if(pairs[city.id].compareTo(pairs[from.id]) < 0){
                        pairs[city.id] = pairs[from.id];
                    }
                }
                
                pairs[city.id].days += city.holidays[month];
                pairs[city.id].path.add(city);
            }
        }
        
        Pair max = new Pair();
        for(Pair pair : pairs){
            if(max.compareTo(pair) < 0){
                max = pair;
            }
        }
        
        return max.path;
    }
    
    class Pair implements Comparable<Pair>{
        int days = 0;
        List<City> path = new ArrayList<>();
        
        @Override
        public int compareTo(Pair o) {
            return Integer.compare(this.days, o.days);
        }
    }
    
    class City{
        int id; // from 0 to n
        List<City> flightsFrom;
        
        int[] holidays = new int[12]; //  12 months
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        
    }

}

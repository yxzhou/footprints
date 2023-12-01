/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

/**
 *
 * Given an IPv4 IP address p and an integer n, return a list of CIDR strings that most succinctly represents the range
 * of IP addresses from p to (p + n).
 *
 * Example1 
 * Input: ip = "255.0.0.7", range = 10,
 *               {, , "255.0.0.16/32"}
 * Output: 
 *   "255.0.0.7/32"    First IP	255.0.0.7   Last IP	255.0.0.7
 *   "255.0.0.8/29"    First IP	255.0.0.8   Last IP	255.0.0.15
 *   "255.0.0.16/32"   First IP	255.0.0.16  Last IP	255.0.0.16
 * 
 * 
 * 
 */
public class IPRangeToCIDR {
    
    public List<String> ipRange2CIDR(String ip, int range){
        //System.out.println(String.format("\n--ip: %s, range = %d", ip, range));
        
        long start = ipToLong(ip);
        long end = start + range;
        
        List<String> result = new ArrayList<>();
        long distance;
        long rightMostOne;
        int mask;
        while( (distance = end - start) > 0 ){
            //
            rightMostOne = start & (-start);
            
            if(rightMostOne <= distance){
                mask = 32 - (int)(Math.log(rightMostOne) / Math.log(2));
            }else{
                mask = 32 - (int)Math.floor(Math.log(distance) / Math.log(2));
            }
    
            //System.out.println(String.format("\nstart: %s, \nrightMostOne: %d, maskask: %d", Long.toBinaryString(start), rightMostOne, mask));
            
            result.add(longToIP(start) + "/" + mask);
            
            //System.out.println(result.get(result.size() - 1));
            
            start += Math.pow(2, (32 - mask));
            
        }
        
        return result;
    }
    
    private long ipToLong(String stringIP){
        long[] ip = new long[4];
        
        String[] tokens = stringIP.split("\\.");
        for(int i = 0; i < 4; i++){
            ip[i] = Long.valueOf(tokens[i]);
        }
                
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
    
    private String longToIP(long longIP){
//        StringBuilder sb = new StringBuilder();
//        
//        sb.append(longIP >>> 24).append(".")
//                .append((longIP & 0x00FFFFFF) >>> 16).append(".")
//                .append((longIP & 0x0000FFFF) >>> 8).append(".")
//                .append(longIP & 0x000000FF);
//        
//        return sb.toString();

        return String.format("%d.%d.%d.%d", 
                longIP >>> 24, 
                (longIP & 0x00FFFFFF) >>> 16, 
                (longIP & 0x0000FFFF) >>> 8,
                longIP & 0x000000FF);
    }
    
    public static void main(String[] args){
        
        String[][][] inputs = {
            //{{ip, range}, {expect}}
            {
                {"255.0.0.7","10"},
                {"255.0.0.7/32", "255.0.0.8/29", "255.0.0.16/32"}
            },
            {
                {"1.1.1.0","4"},
                {"1.1.1.0/30"}
            },
            {
                {"1.1.1.1","4"},
                {"1.1.1.1/32", "1.1.1.2/31", "1.1.1.4/32"}
            }
        };
        
        IPRangeToCIDR sv = new IPRangeToCIDR();
        
        for(String[][] input : inputs){
            Assert.assertArrayEquals(String.format("ip = %s, range = %s", input[0][0], input[0][1]), 
                    input[1], 
                    sv.ipRange2CIDR(input[0][0], Integer.parseInt(input[0][1])).toArray() );
        }
    }
}

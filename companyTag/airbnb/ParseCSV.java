/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.junit.Assert;

/**
 * parse a string in CSV format
 *
 * A comma-separated values (CSV) file is a standard text file which uses a comma to separate value. Each line of the
 * file consists of one or more fields, separated by commas. Each field may or may not be enclosed in double-quotes.
 * 
 * The RFC 4180 defines the format or definitions of a CSV file or text/csv file.
 *
 * Example #1
 * Input:  "John,Smith,john.smith@gmail.com,Los Angeles,1",
 * Output: "John|Smith|john.smith@gmail.com|Los Angeles|1"
 * 
 * Example #2
 * Input:  "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0",
 * Output: "Jane|Roberts|janer@msn.com|San Francisco, CA|0"
 * 
 * Example #3
 * Input:  "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1"
 * Output: "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1"
 *
 * Example #4
 * Input:  "\"\"\"Alexandra Alex\"\"\""
 * Output: "\"Alexandra Alex\""
 * 
 * Thoughts:
 *   About the Pattern :
 *     rule #1 There maybe one or more fields, separated by commas
 *     rule #2 Each field may or may not be enclosed in double-quotes
 *     rule #3 Fields containing comma and double-quotes should be enclosed in double-quotes 
 *     rule #4 If double-quotes are used to enclose fields, then a double-quote appearing inside a field must be escaped
 *             by preceding it with another double quote.
 * 
 *   so parse the input string from left to right, character by character. now it's character c 
 *   when c is ',' 
 *      if the previous one and the field's prefix is match, (double-quote or normal character), the field is finished. 
 *      if not, c is in the field
 *   when c is '\"'
 *      if the field's prefix is null, c is the field's prefix
 *      if the field's prefix is '\"' and the next is ','   c is the end of field
 *      or c is in the field
 * 
 */
public class ParseCSV {
    public String parseCSV(String s){
        if(s == null){
            return "";
        }
        
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuote = false;
        
        char c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i);
            
            if(inQuote){
                if(c == '\"'){
                    if(i < n - 1 && s.charAt(i + 1) == '\"' ){
                        field.append(c);
                        i++;
                    }else{
                        inQuote = false;
                    }
                    
                }else{
                    field.append(c);
                }
            } else {
                if(c == '\"'){
                    inQuote = true;
                } else if( c == ',') {
                    fields.add(field.toString());
                    
                    //field = new StringBuilder();  //assign a new object, the old one is eligible for GC 
                    //field.delete(0, field.length());  // delete method uses System.arraycopy in the background.
                    field.setLength(0); //
                
                } else{
                    field.append(c);
                }
                
            }
        }
        
        if(field.length() > 0){
            fields.add(field.toString());
        }
        
        return String.join("|", fields);
    }
    
    public String parseCSV_x(String s){
        if(s == null){
            return "";
        }
        
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        
        s += ',';
        String tmp;
        char c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i);
            
            if ( c == ',' ){
                if(field.length() == 0){
                    //continue;
                } else if( isFieldCompleted(field.charAt(0), field.charAt(field.length() - 1)) ){
                    if (field.charAt(0) == '\"'){
                        tmp = field.substring(1, field.length() - 1 );
                    }else {
                        tmp = field.toString();
                    }
                    
                    fields.add(tmp.replaceAll("\"\"", "\""));

                    field.setLength(0);
                } else {
                    field.append(c);
                }
                
            } else { //includes '\"' and ...
                field.append(c);
            }
        }
        
//        if(field.length() > 0){
//            if (field.charAt(0) == '\"'){
//                tmp = field.substring(1, field.length() - 1 );
//            }else {
//                tmp = field.toString();
//            }
//
//            fields.add(tmp.replaceAll("\"\"", "\""));
//        }
        
        return String.join("|", fields);
    }
    
    private boolean isFieldCompleted(char prefix, char previous){
        //  prefix != '\"' || ( prefix == '\"' && previous == '\"' )
        return (  prefix != '\"' ||  previous == '\"' );
    }
    
    
    public static void main(String[] args){
        
        String[][] inputs = {
            {
                "John,Smith,john.smith@gmail.com,Los Angeles,1",
                "John|Smith|john.smith@gmail.com|Los Angeles|1"
            },
            {
                "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0",
                "Jane|Roberts|janer@msn.com|San Francisco, CA|0"
            },
            {
                "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1",
                "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1"
            },
            {
                "\"\"\"Alexandra Alex\"\"\"",
                "\"Alexandra Alex\""
            },
            {
                "\"Alexandra, \"Alex\"\",Menendez,alex.menendez@gmail.com,Miami,1",
                "Alexandra, \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1"
            },
            {
                "Alexandra \"Alex\",Menendez,alex.menendez@gmail.com,Miami,1",
                "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1"
            },
            {
                ",John,Smith,john.smith@gmail.com,Los Angeles,1",
                "John|Smith|john.smith@gmail.com|Los Angeles|1"
            },
            {
                "Alexandra Alex\",Menendez,alex.menendez@gmail.com,Miami,1",
                "Alexandra Alex\"|Menendez|alex.menendez@gmail.com|Miami|1"
            }
                
        };
        
        ParseCSV sv = new ParseCSV();
        
        String[] input;
        for(int i = 0; i < 4; i++){
            input = inputs[i];
            
            Assert.assertEquals(input[1], sv.parseCSV(input[0]));
        }
        
        for(int i = 0; i < inputs.length; i++){
            input = inputs[i];
            
            Assert.assertEquals(input[1], sv.parseCSV_x(input[0]));
        }
    }
}

package stringmatching;

import java.util.regex.Pattern;

/**
 *
 */

public class FindMurderer {


    public String findMurderer_pattern(String[] candidates, String regrexString){
        Pattern pattern = Pattern.compile(regrexString);

        for(String name : candidates){
            if(pattern.matcher(name).matches()){
                return name;
            }
        }

        return null;
    }

    public String findMurderer_dp(String[] candidates, String regrexString){
        //todo
        return null;
    }


    public static void main(String[] args){
        String[] names = {
                "BernardDeltheil",
                "PeterGone",
                "PeterReeves",
                "RolandScorsini"
        };

        String regrexString = "p?t?g";




    }

}




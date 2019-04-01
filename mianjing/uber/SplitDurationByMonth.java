package fgafa.mianjing.uber;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 给起始和终点时间，按月输出中间的时间段，
 * 比如给定范围 [2019-01-10,  2019-03-15]， 输出 -> [2019-01-10, 2019-01-31], [2019-02-01, 2019-02-28], [2019-03-01, 2019-03-15]
 *
 */

public class SplitDurationByMonth {

    public List<String[]> splitByMonth(String startDate, String endDate){
        //assume startDay and endDay both are valid date,

        int[] starts = new int[3];
        int i = 0;
        for( String token : startDate.split("-")){
            starts[i++] = Integer.valueOf(token);
        }

        int[] ends = new int[3];
        i = 0;
        for( String token : endDate.split("-")){
            ends[i++] = Integer.valueOf(token);
        }

        List<String[]> result = new ArrayList<>();

        for(int year = starts[0]; year <= ends[0]; year++){
            int month = 1;
            if(year == starts[0]){
                month = starts[1];
            }

            int end = 12;
            if(year == ends[0]){
                end = ends[1];
            }

            for( ; month <= end; month++){
                String[] interval = new String[2];

                if(year == starts[0] && month == starts[1]){
                    interval[0] = startDate;
                }else{
                    interval[0] = format(year, month, 1);
                }

                if(year == ends[0] && month == ends[1]){
                    interval[1] = endDate;
                }else {
                    int lasDay = lastDay(year, month);
                    interval[1] = format(year, month, lasDay);
                }

                result.add(interval);
            }
        }

        return result;
    }

    private int lastDay(int year, int month){
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                    return 29;
                }else{
                    return 28;
                }
            default:
                throw new IllegalArgumentException("Input a invalid month: " + month);
        }
    }

    private String format(int year, int month, int day){
        StringBuilder date = new StringBuilder(10);

        String y = String.valueOf(year);
        for(int i = 4 - y.length(); i > 0; i-- ){
            date.append('0');
        }
        date.append(y);
        date.append('-');

        if(month < 10){
            date.append('0');
        }
        date.append(month);
        date.append('-');

        if(day < 10){
            date.append('0');
        }
        date.append(day);

        return date.toString();
    }

    @Test
    public void test(){
        System.out.println("[2019-01-10, 2019-01-31], [2019-02-01, 2019-02-28], [2019-03-01, 2019-03-15]");

        List<String[]> result = splitByMonth("2019-01-10",  "2019-03-15");

        for(String[] interval : result){
            System.out.print(String.format("[%s, %s], ", interval[0], interval[1]));
        }
        System.out.println("\n");


        //System.out.println("[2019-01-31, 2019-01-31], [2019-02-01, 2019-02-28], [2019-03-01, 2019-03-15]");

        result = splitByMonth("2019-01-10",  "2022-05-15");

        for(String[] interval : result){
            System.out.println(String.format("[%s, %s]", interval[0], interval[1]));
        }
        System.out.println();

    }

}

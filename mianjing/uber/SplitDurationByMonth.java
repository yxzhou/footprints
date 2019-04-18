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
        int[] starts = split(startDate);
        int[] ends = split(endDate);

        List<String[]> result = new ArrayList<>();

        for(int year = starts[0]; year <= ends[0]; year++){
            int month = (year == starts[0] ? starts[1] : 1);
            int end = (year == ends[0]? ends[1] : 12);

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
                    interval[1] = format(year, month, lastDay(year, month));
                }

                result.add(interval);
            }
        }

        return result;
    }

    private int[] split(String date){
        int[] yymmdd = new int[3];
        int i = 0;
        for( String token : date.split("-")){
            yymmdd[i++] = Integer.valueOf(token);
        }

        return yymmdd;
    }


    int[] monthDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int lastDay(int year, int month){
        if(month < 1 || month > 12){
            throw new IllegalArgumentException("Input a invalid month: " + month);
        }

        if(month == 2 && ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))){
            return 29;
        }

        return monthDays[month];
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

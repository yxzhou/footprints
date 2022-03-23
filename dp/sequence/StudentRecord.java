/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dp.sequence;

/**
 * _https://www.lintcode.com/problem/1178
 * 
 *
 * You are given a string representing an attendance record for a student. 
 * The record only contains the following three characters:
 * 'A' : Absent. 
 * 'L' : Late. 
 * 'P' : Present. 
 * 
 * A student could be rewarded if his attendance record doesn't contain more than one 'A' (absent) or more than two 
 * continuous 'L' (late).
 *
 * You need to return whether the student could be rewarded according to his attendance record.
 *
 * Example 1:
 * Input: "PPALLP" 
 * Output: True 
 * 
 * Example 2:
 * Input: "PPALLL" 
 * Output: False
 *
 */
public class StudentRecord {
    /**
     * @param s: a string
     * @return: whether the student could be rewarded according to his attendance record
     */
    public boolean checkRecord(String s) {
        if(s == null){
            return false;
        }

        int countA = 0;
        int countL = 0;
        char c;
        for(int i = 0, n = s.length(); i < n; i++){
            c = s.charAt(i);

            if(c == 'A'){
                countA++;
                countL = 0;
            }else if(c == 'L'){
                countL++;
            }else{
                countL = 0;
            }

            if(countA > 1 || countL > 2){
                return false;
            }
        }

        return true;
    }
}

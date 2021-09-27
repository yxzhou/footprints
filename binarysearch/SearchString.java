package binarysearch;

public class SearchString {

    /*
     * Given a string of sorted integers, e.g. "1 52 69 456789 994546566";
     * and a  a number e.g. 69.
     * You need to tell if it is in the input, e.g. 69=>true.
     */
    
    public boolean SearchString(String s, int target) {
        int left = 0;
        int right = s.length() - 1;
        
        int mid;
        int curr = 0;
        int count = 0;
        while(left <= right){
            mid = left + (right - left) / 2;
            
            mid--;
            while(mid >= left && s.charAt(mid) != ' '){
                mid--;
            }
                
            curr = 0;
            count = 0;
            mid++;
            while(mid <= right && s.charAt(mid) != ' '){
                curr = curr * 10 + s.charAt(mid) - '0'; 
                mid++;
                count++;
            }
            
            if(curr == target){
                return true;
            }else if(curr > target){
                right = mid - count - 1;
            }else{
                left = mid + 1;
            }
        }
            
        return false;
    }
    
    
    public static void main(String[] args) {
        String input = "1 52 69 456789 994546566";
        int[] targets = {
                    0,
                    1,
                    2,
                    52,
                    69,
                    994546566,
                    994546567
        };

        SearchString sv = new SearchString();
        
        System.out.println("Input: " + input);
        for(int i = 0; i < targets.length; i++){
            System.out.println(String.format("%s, %b", targets[i], sv.SearchString(input, targets[i])));
        }
    }

}

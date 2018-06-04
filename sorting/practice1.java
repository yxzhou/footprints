package fgafa.sorting;

import fgafa.util.Misc;

public class practice1 {
    
    public void bubble(int[] arr){
        //check
        
        //
        int tmp;
        for(int i=arr.length - 1; i > 0; i--){
            for(int j=0; j<i; j++){
                if(arr[j] > arr[j+1]){
                    swap(arr, j, j+1);
                }
            }
        }
    }

    public void quick(int[] arr){
        //check
        
        quick(arr, 0, arr.length - 1);
    }
    private void quick(int[] arr, int start, int end){
        //
        if(start >= end){
            return;
        }

        int pivot = partition(arr, start, end);
        quick(arr, start, pivot-1);
        quick(arr, pivot+1, end);
    }
    private int partition(int[] arr, int start, int end){
        int index = start + (int)(Math.random() * ((end - start) + 1)) ;  // get a random in [begin, end-1] 

        int pivot = arr[index];
        swap(arr, index, end);
        
        for(int i = index = start; i <=end; i++ ){
            if(arr[i] <= pivot ){
                swap(arr, i, index);
                index++;
            }
        }
        
        return index - 1;
    }

    public static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public void mergeSort(int[] arr){
        //check
        

        //
        int end;
        for(int step=2; step < (arr.length << 1) ; step +=step){
            for(int i=0; i < arr.length; ){
                end = i + step;
                merge(arr, i, end);
                i = end;
            }
        }
    }
    private void merge(int[] arr, int start, int end){
        if(start >= end){
            return;
        }

        int mid = start + ((end - start) >> 1);
        end = Math.min(end, arr.length);

        //merge 2 sorted sub-arr.
        int[] newArr = new int[end - start];
        int i = start, j = mid, k=0;
        while(i < mid && j < end){
            if(arr[i] <= arr[j]){
                newArr[k++] = arr[i++];
            }else{
                newArr[k++] = arr[j++];
            }
        }
        while(i<mid){
            newArr[k++] = arr[i++];
        }
        while(j<end){
            newArr[k++] = arr[j++];
        }
        
        //refill back
        for(i=start, k=0; k<newArr.length; i++, k++){
            arr[i] = newArr[k];
        }
        
    }

    public void mergeSort_recursive(int[] arr){
        mergeSort_recursive(arr, 0, arr.length);
    }
    private void mergeSort_recursive(int[] arr, int start, int end){
        if(start >= end){
            return;
        }
        
        int mid = start + ((end - start) >> 1);
        mergeSort_recursive(arr, start, mid);
        mergeSort_recursive(arr, mid, end);
        merge(arr, start, end);
    }

    public void heapSort(int[] arr){
        
    }

    public static void main(String[] args) {
        
        System.out.println("---start---");
            
        //initial
        int times = 1;
        int size = 270;
        
        int[] seqNum = new int[size];
        practice1 sort = new practice1();
        for(int i=0; i< times; i++){
//          for(int j=0; j< size; j++){
//            seqNum[j] = min + (int)(Math.random() * ((max - min) + 1)) ; 
//          }      
          seqNum = new int[] {9,90,51,89,51,26,43};
          System.out.println("-------------i-------------"+ i + "       before sort:" + Misc.array2String(seqNum));

          int[] seqNum0 = seqNum.clone();
          sort.bubble(seqNum0);
          System.out.println("-------------i-------------"+ i + " after insert sort:" + Misc.array2String(seqNum0));


          int[] seqNum1 = seqNum.clone();
          sort.mergeSort(seqNum1);
          System.out.println("-------------i-------------"+ i + " after insert sort:" + Misc.array2String(seqNum1));

          
          int[] seqNum2 = seqNum.clone(); //cloneArray(seqNum);
          sort.quick(seqNum2);
          System.out.println("-------------i-------------"+ i + " after merge  sort:" + Misc.array2String(seqNum2));


//          int[] seqNum3 = seqNum.clone();
//          sort.quickSort(seqNum3);
//          System.out.println("-------------i-------------"+ i + " after quick sort:" + Misc.array2String(seqNum3));


//          int[] seqNum4 = seqNum.clone();
//          sort.heapSort(seqNum4);
//          System.out.println("-------------i-------------"+ i + " after heap sort:" + toString(seqNum4).toString());
//          System.out.println(" " +  compareArray(seqNum0, seqNum4));   
          
//          int[] seqNum5 = seqNum.clone();
//          sort.shellSort(seqNum5);
//          System.out.println("-------------i-------------"+ i + " after shell sort:" + toString(seqNum5).toString());
          //System.out.println(" " +  compareArray(seqNum0, seqNum5));   
          

        }

        System.out.println("---end---");
   }
}

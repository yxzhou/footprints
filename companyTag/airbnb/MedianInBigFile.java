/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package companyTag.airbnb;

/**
 *
 * Find the median from a large file of integers. You can not access the numbers by index, can only access it
 * sequentially. And the numbers cannot fit in memory.
 *
 * Thoughts:
 *   If the file size is 100G, it can not be loading and sorting in memory. 
 * 
 *   The Integer is in [-2^31, 2^31 - 1], total it's 2^32,  
 *   s1, counting sort. Map<value, count>
 *   If do counting sort with memory, it need 2^32 * 4 bytes = 16 Gigabytes
 * 
 *   s2, group sort, 
 *   If 16G memory is still to big, use Disk.
 *   read the big file, line by line, group the integers into multiple files, and counting.
 * 
 *   Example, to a 100G file, the integers can be grouping by value into 2^6 files,
 *   the first file stores the integer whose value is smaller than 2^26,  (value & 0xFC000000) == 0
 *   the first file stores the integer whose value is in [2^26, 2^27), (value & 0xF8000000) == 0
 *   ---
 *   locate the sub-file where the median is in, 
 *   counting sort the number with Map<range, count>, it need 2^26 * 4 bytes = 1G / 4 = 256 Megabytes
 *  
 * 
 */
public class MedianInBigFile {
    
}

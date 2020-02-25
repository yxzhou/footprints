
The length of a binary indexed tree

to nums[],
if n = 6.  the binary tree can be

                    (0, 5)
       (0, 2)                     (3, 5)
  (0, 1)    [2]             (3, 4)        [5]
[0]   [1]                  [3]   [4]

define:
 the index of (0, 5) is 1
 the index of (0, 2) is 2
 the index of (0, 1) is 4
 the index of [0]    is 8

 the index of (3, 5) is 3
 the index of (3, 4) is 6
 the index of [3]    is 12

 the index of [3]    is 13

the indexed array's length is not n * 2 = 12,
it's better to init a full binary tree, 2 ^ (log(2, 6) + 1) = 8,  8 * 2 = 16.
Java defines several bitwise operators, which can be applied to the integer types, long, int, short, char, and byte.

Bitwise operator works on bits and performs the bit-by-bit operation. Assume if a = 60 and b = 13; now in binary format they will be as follows −

a = 0011 1100

b = 0000 1101

-----------------

a&b = 0000 1100

a|b = 0011 1101

a^b = 0011 0001

~a = 1100 0011  =>  ~a = 1111 1111 1111 1111 1111 1111 1100 0011

The following table lists the bitwise operators −

Assume integer variable A holds 60 and variable B holds 13 then −

Operator                    Description                         Example
& (bitwise and)             Binary AND Operator                 (A & B) will give 12 which is 0000 1100
| (bitwise or)              Binary OR Operator                  (A | B) will give 61 which is 0011 1101
^ (bitwise XOR)             Binary XOR Operator                 (A ^ B) will give 49 which is 0011 0001
~ (bitwise compliment)      Binary Ones Complement Operator     (~A ) will give -61 which is 1100 0011 in 2's complement form due to a signed binary number.
<< (left shift)             Binary Left Shift Operator.         A << 2 will give 240 which is 1111 0000
>> (right shift)            Binary Right Shift Operator.        A >> 2 will give 15 which is 1111
>>> (zero fill right shift) Shift right zero fill operator. A >>>2 will give 15 which is 0000 1111
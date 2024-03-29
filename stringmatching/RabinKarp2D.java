package stringmatching;

public class RabinKarp2D {
    public static void main(String[] args) {
        char[][] text = { 
                    { 'a', 'b', 'a', 'b', 'a' }, 
                    { 'a', 'b', 'a', 'b', 'a' }, 
                    { 'a', 'b', 'b', 'a', 'a' },
                    { 'a', 'b', 'a', 'a', 'b' }, 
                    { 'b', 'b', 'a', 'b', 'a' } };
        char[][] pattern = { 
                    { 'a', 'b' }, 
                    { 'b', 'a' } };

        matrixPatternMatch(text, pattern);
    }

    private static void matrixPatternMatch(char[][] text,
                                           char[][] pattern) {
        // pre-process
        int[] patternStamp = new int[pattern[0].length];
        int[] textStamp = new int[text[0].length];

        caculateStamp(pattern, pattern.length, patternStamp);
        caculateStamp(text, pattern.length, textStamp);

        int[] next = new int[patternStamp.length];
        caculateNext(patternStamp, next);

        for (int i = 0; i < (text.length - pattern.length + 1); i++) {
            int col = isMatch(patternStamp, textStamp, next);
            if (col != -1) {
                System.out.println("found");
                System.out.println(i + ", " + col);
            }

            // move down
            if (i < text.length - pattern.length)
                caculateNextStamp(text, pattern.length, textStamp, i);
        }

    }

    private static int isMatch(int[] patternStamp,
                               int[] textStamp,
                               int[] next) {
        int i = 0, j = 0;
        while (j < patternStamp.length && i < textStamp.length) {
            if (j == -1 || patternStamp[j] == textStamp[i]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if (j == patternStamp.length) {
            return i - j;
        } else {
            return -1;
        }
    }

    private static void caculateNext(int[] pattern,
                                     int[] next) {
        next[0] = -1;

        int i = 0, j = -1;
        while (i < pattern.length - 1) {
            if (j == -1 || pattern[i] == pattern[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }

    }

    private static void caculateNextStamp(char[][] text,
                                          int height,
                                          int[] textStamp,
                                          int row) {
        int d = (int) Math.pow(26, height - 1);
        for (int i = 0; i < textStamp.length; i++) {
            textStamp[i] = 26 * (textStamp[i] - d * text[row][i]) + text[row + height][i];
        }
    }

    private static void caculateStamp(char[][] input,
                                      int height,
                                      int[] result) {
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
            for (int j = 0; j < height; j++) {
                result[i] = 26 * result[i] + input[j][i];
            }
        }
    }

}
// int[]
// patternStamp
// =
// new
// int[pattern[0].length];22
// int[]
// textStamp
// =
// new
// int1.length];23
// 24
// caculateStamp(pattern,
// pattern.length,
// patternStamp);25
// caculateStamp(text,
// pattern.length,
// textStamp);26
// 27
// int[]
// next
// =
// new
// int[patternStamp.length];28
// caculateNext(patternStamp,
// next);29
// 30
// for
// (int
// i =
// 0; i
// <
// (text.length
// -
// pattern.length
// +
// 1);
// i++)
// {31
// int
// col
// =
// isMatch(patternStamp,
// textStamp,
// next);32
// if
// (col
// !=
// -1)
// {33
// System.out.println("found");34
// System.out.println(i+",
// "+col);35
// }36
// 37
// //
// move
// down38
// if(i
// <
// text.length
// -
// pattern.length)39
// caculateNextStamp(text,
// pattern.length,
// textStamp,
// i);40
// }41
// 42
// }43
// 44
// private
// static
// int
// isMatch(int[]
// patternStamp,
// int[]
// textStamp,
// int[]
// next)
// {45
// int
// i =
// 0, j
// =
// 0;46
// while
// (j <
// patternStamp.length
// && i
// <
// textStamp.length)
// {47
// if
// (j
// ==
// -1
// ||
// patternStamp[j]
// ==
// textStamp[i])
// {48
// i++;49
// j++;50
// }
// else
// {51
// j =
// next[j];52
// }53
// }54
// 55
// if
// (j
// ==
// patternStamp.length)
// {56
// return
// i-j;57
// }
// else
// {58
// return
// -1;59
// }60
// }61
// 62
// private
// static
// void
// caculateNext(int[]
// pattern,
// int[]
// next)
// {63
// next[0]
// =
// -1;64
// 65
// int
// i =
// 0, j
// =
// -1;66
// while(i<pattern.length-1)
// {67
// if(j==-1
// ||
// pattern[i]
// ==
// pattern[j])
// {68
// i++;69
// j++;70
// next[i]
// =
// j;71
// }
// else
// {72
// j =
// next[j];73
// }74
// }75
// 76
// }77
// 78
// private
// static
// void
// caculateNextStamp(char[][]
// text,
// int
// height,79
// int[]
// textStamp,
// int
// row)
// {80
// int
// d =
// (int)
// Math.pow(26,
// height-1);81
// for
// (int
// i =
// 0; i
// <
// textStamp.length;
// i++)
// {82
// textStamp[i]
// = 26
// *
// (textStamp[i]
// - d
// *
// text[row][i])
// +
// text[row
// +
// height][i];83
// }84
// }85
// 86
// private
// static
// void
// caculateStamp(char[][]
// input,
// int
// height,
// int[]
// result)
// {87
// for
// (int
// i =
// 0; i
// <
// result.length;
// i++)
// {88
// result[i]
// =
// 0;89
// for
// (int
// j =
// 0; j
// <
// height;
// j++)
// {90
// result[i]
// = 26
// *
// result[i]
// +
// input[j][i];91
// }92
// }93
// }94
// 95 }

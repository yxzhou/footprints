package fgafa.leetcode.airbnb;

public class HilbertCurve {

    public int hilbertCurve(int x, int y, int iter) {
        if (iter == 0) {
            return 1;
        }
        int areaCnt = (1 << (iter * 2 - 2)); // 每一块区域边长的边界值
        int borderLen = (1 << (iter - 1)); // 区域移动的长度

        if (x >= borderLen && y >= borderLen) { //右上角区域 = 前一阶往右上角移动borderLen
            return areaCnt * 2 + hilbertCurve(x - borderLen, y - borderLen, iter - 1);

        } else if (x < borderLen && y >= borderLen) { //左上角区域 = 前一阶往上移动borderLen
            return areaCnt + hilbertCurve(x, y - borderLen, iter - 1);

        } else if (x < borderLen && y < borderLen) { //右下角区域 = 前一阶按照y=x对称
            return hilbertCurve(y, x, iter - 1);

        } else { //右下角区域 = 前一阶按照y=-x对称，然后右移2*borderLen - 1，上移borderLen - 1
            // 设原来坐标(a,b) => (-b, -a) => (2*borderLen - 1 - b, borderLen - 1 - a) = (x, y)
            // => a = borderLen - 1 - y, b = 2*borderLen - 1 - x
            return areaCnt * 3 + hilbertCurve(borderLen - 1 - y, 2 * borderLen - 1 - x, iter - 1);
        }
    }

}

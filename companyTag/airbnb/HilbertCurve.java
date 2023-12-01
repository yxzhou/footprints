package companyTag.airbnb;

/**
 * ——refer to _https://massivealgorithms.blogspot.com/2017/07/hilbert-curve-airbnb.html
 * 
 * 
 * 德国数学家David Hilbert发现了一种曲线，
 * 首先把一个正方形等分成四个小正方形，依次从西南角的正方形中心出发往北到西北正方形中心，再往东到东北角的正方形中心，再往南到东南角正方形中心，这是一次迭代，
 * 如果对四个小正方形继续上述过程，往下划分，反复进行，最终就得到一条可以填满整个正方形的曲线，这就是Hibert曲线
 *
 */

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

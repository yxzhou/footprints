package fgafa.topCoder;

/**
 * Problem Statement http://community.topcoder.com/stat?c=problem_statement&pm=1215&rd=4555
 * ============================
 *
 * Karel is a frustrated painter who works by day in an electrical repair shop. Inspired by the
 * color-coded bands on resistors, he is painting a series of long, narrow canvases with bold colored
 * stripes. However, Karel is lazy and wants to minimize the number of brush strokes it takes to paint
 * each canvas.
 *
 * Abbreviating each color to a single uppercase letter, Karel would write the stripe pattern
 * red-green-blue-green-red as "RGBGR" (quotes added for clarity). It would take him three brush
 * strokes to paint this pattern. The first stroke would cover the entire canvas in red (RRRRR).
 * The second stroke would leave a band of red on either side and fill in the rest with green (RGGGR).
 * The final brush stroke would fill in the blue stripe in the center (RGBGR).
 *
 * Given a stripe pattern stripes as a String, calculate the minimum number of brush strokes required
 * to paint that pattern.
 *
 *
 * Notes
 * =============================
 * The blank canvas is an ugly color and must not show through.
 *
 * Constraints
 * =============================
 * -	stripes will contain only uppercase letters ('A'-'Z', inclusive).
 * -	stripes will contain between 1 and 50 characters, inclusive.
 *
     Examples
     0)"RGBGR"
     Returns: 3
     Example from introduction.

     1)"RGRG"
     Returns: 3
     This example cannot be done in two strokes, even though there are only two colors. Suppose you tried to paint both red stripes in one stroke, followed by both green stripes in one stroke. Then the green stroke would cover up the second red stripe. If you tried to paint both green stripes first, followed the red stripes, then the red stroke would cover up the first green stripe.

     2)"ABACADA"
     Returns: 4
     One long stroke in color 'A', followed by one stroke each in colors 'B', 'C', and 'D'.

     3)"AABBCCDDCCBBAABBCCDD"
     Returns: 7

     4)"BECBBDDEEBABDCADEAAEABCACBDBEECDEDEACACCBEDABEDADD"
     Returns: 26
 *
 *
 *
 *
 */

public class StripePainter {

    public int minStrokes(String stripe){
        if(null == stripe || stripe.isEmpty()){
            return 0;
        }

        int length = stripe.length();
        int[][] strokes = new int[length][length];//define strokes[i][j] as the min strokes for stripe.substring(i, j+1)

        for(int width = 0; width < length; width++){
            for(int i = 0; i < length - width; i++){
                int j = i + width;
                strokes[i][j] = width + 1;
                boolean flag = (stripe.charAt(i) == stripe.charAt(j));

                for(int k = i; k < j; k++){
                    flag = flag || (stripe.charAt(k) == stripe.charAt(k + 1));
                    strokes[i][j] = Math.min(strokes[i][j], strokes[i][k] + strokes[k + 1][j] - (flag? 1 : 0));
                }
            }
        }

        return strokes[0][length - 1];
    }


    public static void main(){

        String[] cases = {
                "RGBGR",
                "RGRG",
                "ABACADA",
                "AABBCCDDCCBBAABBCCDD",
                "BECBBDDEEBABDCADEAAEABCACBDBEECDEDEACACCBEDABEDADD"
        };

        int[] expects = {
                3,
                3,
                4,
                7,
                26
        };

        StripePainter sv = new StripePainter();

        for(int i = 0; i < expects.length; i++){
            int result = sv.minStrokes(cases[i]);
            System.out.println(String.format("%s, \t %d \t%f", cases[i], result, result == expects[i]));
        }
    }

}

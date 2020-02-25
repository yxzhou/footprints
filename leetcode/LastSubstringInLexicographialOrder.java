package fgafa.leetcode;

import junit.framework.Assert;
import org.junit.Test;

public class LastSubstringInLexicographialOrder {


    @Test public void test(){

        String s = "cacacb";
        String expected = "cb";
        Assert.assertEquals(expected, lastSubstring(s));

        s ="xbylisvborylklftlkcioajuxwdhahdge zvyjbgaznzayfwsaumeccpfwamfzmkine zzwobllyxktqeibfoupcpptncggrdqbkji";
        expected = "zzwobllyxktqeibfoupcpptncggrdqbkji";

        Assert.assertEquals(expected, lastSubstring(s));


        s = "jlidhumidloagrlvvxdqscegbeaybfhhyaeilzxdpyvvxixrjytdalknkospradmumysbkizziltzjwsxkteykblcvkfivzmtvnsyrfgvojhyzkouscymixrdfmehiwijntzqptqaxgalygtzvwxnsgcnygbjzeqmbactgmckvssvkentpxcnznrbbnkttnzpvlzfmdvvsozaiycumzlizbfxvyucyagclrifczcvzvrkiqiajindjjyxgxflnjcgckruujsbppxtwgwvrrxgniqplynvboqyvrsxnmbjhgoybqophbxmjhhrznezstujjuucvrrvofktxldxfaioyijoayggmvjmgzjflzxmkwxmxnyizampdcfntdfkxxprgfxjduiwrmgdfuprpljgnbzbedqbzhqsbmohbhlszvdzcgbimfurmkwqaignxbeevevonmdgupugcjxvqglqkwqzrlqequliwmfrvidtpprodcbhgkt";
        expected = "zziltzjwsxkteykblcvkfivzmtvnsyrfgvojhyzkouscymixrdfmehiwijntzqptqaxgalygtzvwxnsgcnygbjzeqmbactgmckvssvkentpxcnznrbbnkttnzpvlzfmdvvsozaiycumzlizbfxvyucyagclrifczcvzvrkiqiajindjjyxgxflnjcgckruujsbppxtwgwvrrxgniqplynvboqyvrsxnmbjhgoybqophbxmjhhrznezstujjuucvrrvofktxldxfaioyijoayggmvjmgzjflzxmkwxmxnyizampdcfntdfkxxprgfxjduiwrmgdfuprpljgnbzbedqbzhqsbmohbhlszvdzcgbimfurmkwqaignxbeevevonmdgupugcjxvqglqkwqzrlqequliwmfrvidtpprodcbhgkt";
        Assert.assertEquals(expected, lastSubstring(s));

    }

    public String lastSubstring(String s) {

        int max = 0;
        int max1 = 0;
        char c;
        char offsetChar;
        for(int r = 0, n = s.length(); r < n; r++){
            c = s.charAt(r);

            if(c > s.charAt(max)){
                max = r;
                max1 = r;
            } else if(max1 != max){
                offsetChar = s.charAt(r - max1 + max);

                if(c > offsetChar){
                    int step = max1 - max;

                    while( max + step <= r ){
                        max += step;
                    }

                    max1 = max;
                }else if (c < offsetChar) {
                    max1 = max;
                }
            } else if (c == s.charAt(max)){
                max1 = r;
            }

        }

        return s.substring(max);
    }


}

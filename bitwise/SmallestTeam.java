package fgafa.bitwise;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 *
 * In a project, you have a list of required skills req_skills, and a list of people.  The i-th person people[i] contains a list of skills that person has.
 *
 * Consider a sufficient team: a set of people such that for every required skill in req_skills, there is at least one person in the team who has that skill.  We can represent these teams by the index of each person: for example, team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].
 *
 * Return any sufficient team of the smallest possible size, represented by the index of each person.
 *
 * You may return the answer in any order.  It is guaranteed an answer exists.
 *
 *
 *
 * Example 1:
 *
 * Input: req_skills = ["java","nodejs","reactjs"], people = [["java"}, {"nodejs"],["nodejs","reactjs"]]
 * Output: [0,2]
 * Example 2:
 *
 * Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"], people = [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"],["reactjs","csharp"],["csharp","math"],["aws","java"]]
 * Output: [1,2]
 *
 *
 * Constraints:
 *
 * 1 <= req_skills.length <= 16
 * 1 <= people.length <= 60
 * 1 <= people[i].length, req_skills[i].length, people[i][j].length <= 16
 * Elements of req_skills and people[i] are (respectively) distinct.
 * req_skills[i][j], people[i][j][k] are lowercase English letters.
 * Every skill in people[i] is a skill in req_skills.
 * It is guaranteed a sufficient team exists.
 *
 */

public class SmallestTeam {


    @Test public void test(){

        String[] skills = {"java","nodejs","reactjs"};
        String[][] people = {{"java"}, {"nodejs"}, {"nodejs","reactjs"}};
        Assert.assertArrayEquals(new int[]{0, 2}, smallestSufficientTeam(skills, build(people)));

        skills = new String[]{"algorithms","math","java","reactjs","csharp","aws"};
        people = new String[][]{{"algorithms","math","java"}, {"algorithms","math","reactjs"}, {"java","csharp","aws"},
                {"reactjs","csharp"}, {"csharp","math"}, {"aws","java"}};
        Assert.assertArrayEquals(new int[]{1, 2}, smallestSufficientTeam(skills, build(people)));


        skills = new String[]{"hfkbcrslcdjq", "jmhobexvmmlyyzk", "fjubadocdwaygs", "peaqbonzgl", "brgjopmm", "x", "mf",
                "pcfpppaxsxtpixd", "ccwfthnjt", "xtadkauiqwravo", "zezdb", "a", "rahimgtlopffbwdg", "ulqocaijhezwfr",
                "zshbwqdhx", "hyxnrujrqykzhizm"};
        people = new String[][]{{"peaqbonzgl","xtadkauiqwravo"}, {"peaqbonzgl","pcfpppaxsxtpixd","zshbwqdhx"}, {"x","a"},
                {"a"}, {"jmhobexvmmlyyzk","fjubadocdwaygs","xtadkauiqwravo","zshbwqdhx"}, {"fjubadocdwaygs","x","zshbwqdhx"},
                {"x","xtadkauiqwravo"}, {"x","hyxnrujrqykzhizm"}, {"peaqbonzgl","x","pcfpppaxsxtpixd","a"}, {"peaqbonzgl","pcfpppaxsxtpixd"},
                {"a"}, {"hyxnrujrqykzhizm"}, {"jmhobexvmmlyyzk"}, {"hfkbcrslcdjq","xtadkauiqwravo","a","zshbwqdhx"},
                {"peaqbonzgl","mf","a","rahimgtlopffbwdg","zshbwqdhx"}, {"xtadkauiqwravo"}, {"fjubadocdwaygs"},
                {"x","a","ulqocaijhezwfr","zshbwqdhx"}, {"peaqbonzgl"}, {"pcfpppaxsxtpixd","ulqocaijhezwfr","hyxnrujrqykzhizm"},
                {"a","ulqocaijhezwfr","hyxnrujrqykzhizm"}, {"a","rahimgtlopffbwdg"}, {"zshbwqdhx"}, {"fjubadocdwaygs","peaqbonzgl","brgjopmm","x"},
                {"hyxnrujrqykzhizm"}, {"jmhobexvmmlyyzk","a","ulqocaijhezwfr"}, {"peaqbonzgl","x","a","ulqocaijhezwfr","zshbwqdhx"},
                {"mf","pcfpppaxsxtpixd"}, {"fjubadocdwaygs","ulqocaijhezwfr"}, {"fjubadocdwaygs","x","a"}, {"zezdb","hyxnrujrqykzhizm"},
                {"ccwfthnjt","a"}, {"fjubadocdwaygs","zezdb","a"}, {}, {"peaqbonzgl","ccwfthnjt","hyxnrujrqykzhizm"},
                {"xtadkauiqwravo","hyxnrujrqykzhizm"}, {"peaqbonzgl","a"}, {"x","a","hyxnrujrqykzhizm"}, {"zshbwqdhx"}, {},
                {"fjubadocdwaygs","mf","pcfpppaxsxtpixd","zshbwqdhx"}, {"pcfpppaxsxtpixd","a","zshbwqdhx"}, {"peaqbonzgl"},
                {"peaqbonzgl","x","ulqocaijhezwfr"}, {"ulqocaijhezwfr"}, {"x"}, {"fjubadocdwaygs","peaqbonzgl"},
                {"fjubadocdwaygs","xtadkauiqwravo"}, {"pcfpppaxsxtpixd","zshbwqdhx"}, {"peaqbonzgl","brgjopmm","pcfpppaxsxtpixd","a"},
                {"fjubadocdwaygs","x","mf","ulqocaijhezwfr"}, {"jmhobexvmmlyyzk","brgjopmm","rahimgtlopffbwdg","hyxnrujrqykzhizm"},
                {"x","ccwfthnjt","hyxnrujrqykzhizm"}, {"hyxnrujrqykzhizm"}, {"peaqbonzgl","x","xtadkauiqwravo","ulqocaijhezwfr","hyxnrujrqykzhizm"},
                {"brgjopmm","ulqocaijhezwfr","zshbwqdhx"}, {"peaqbonzgl","pcfpppaxsxtpixd"}, {"fjubadocdwaygs","x","a","zshbwqdhx"},
                {"fjubadocdwaygs","peaqbonzgl","x"}, {"ccwfthnjt"}};

        //System.out.println(Arrays.toString(smallestSufficientTeam(skills, build(people))));
        Assert.assertArrayEquals(new int[]{1, 13, 30, 31, 50, 51}, smallestSufficientTeam(skills, build(people)));

    }

    private List<List<String>> build (String[][] strings){
        List<List<String>> result = new LinkedList<>();

        for(String[] ss : strings){
            result.add(Arrays.asList(ss));
        }

        return result;
    }


    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        int m = req_skills.length;
        int n = people.size();

        Map<String, Integer> skill2Index = new HashMap<>();
        for(int i = 0; i < m; i++){
            skill2Index.put(req_skills[i], i);
        }

        int[] ps = new int[n]; //default all are 0
        for(int i = 0; i < n; i++){
            for(String s : people.get(i)){
                ps[i] |= (1 << skill2Index.get(s));
            }
        }

        int x = (1 << m) - 1;

        //bfs
        Queue<Team> queue = new LinkedList<>();
        queue.add(new Team(0, 0));

        Set<Integer> visited = new HashSet<>();

        Team top;
        Team curr;
        int state;
        while(!queue.isEmpty()){
            top = queue.poll();

            for( int i = (top.p == 0 ? 0 : (int)Math.log(top.p) + 1); i < n; i++){

                state = (top.s | ps[i]);

                if(visited.contains(state)){
                    continue;
                }

                visited.add(state);

                curr = new Team(top.p | (1l << i), state);

                if(curr.s == x){
                    int[] res = new int[Long.bitCount(curr.p)];

                    for ( int shift = 0, index = 0; curr.p > 0 ; shift++, curr.p >>= 1) {
                        if ((curr.p & 1) == 1) {
                            res[index++] = shift;
                        }
                    }
                    return res;
                }

                queue.add(curr);
            }
        }

        return new int[0];
    }

    class Team{
        long p ; //peoples, e.g. 110 means p1 and p2
        int s; //skills,  e.g. 1010 means skill2 and skill4

        Team(long p, int s){
            this.p = p;
            this.s = s;
        }
    }
}

package dailyCoding;

import java.util.Dictionary;

/**
 *
 * A rule looks like this:

 A NE B
 This means this means point A is located northeast of point B.

 A SW C
 means that point A is southwest of C.

 Given a list of rules, check if the sum of the rules validate.

 For example:
1)  A N B
    B NE C
    C N A
    does not validate, since A cannot be both north and south of C.

2)  A NW B
    A N B
    is considered valid.

 *
 */

public class LocationRules_todo {

    enum Direction{
        N, S, W, E;
    }

    class Rule{
        String from;
        Direction d1;
        Direction d2;
        String to;
    }

    class Location<String>{
        String point;

    }

    public boolean validRules(Rule[] rules){
        if(null == rules || rules.length < 2){
            return false;
        }

        //todo
        return false;
    }

}

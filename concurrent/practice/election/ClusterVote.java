package concurrent.practice.election;

import java.util.Collections;
import java.util.List;

public class ClusterVote extends SingleThreadVote{
    @Override
    public boolean vote(int voterId, int candidateId){
        //sharding

        //super.votes()
        return true;
    }

    @Override
    public List<Integer> top(int k){
        //todo
        return Collections.emptyList();
    }


}

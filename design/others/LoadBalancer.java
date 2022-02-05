/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package design.others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * _https://www.lintcode.com/problem/526
 *
 * Implement a load balancer for web servers. It provide the following functionality:
 *
 * Add a new server to the cluster => add(server_id). Remove a bad server from the cluster => remove(server_id). Pick a
 * server in the cluster randomly with equal probability => pick(). At beginning, the cluster is empty. When pick() is
 * called you need to randomly return a server_id in the cluster.
 *
 *
 * Example 1:
 * Input:
  add(1)
  add(2)
  add(3)
  pick()
  pick()
  pick()
  pick()
  remove(1)
  pick()
  pick()
  pick()
 * Output:
  1
  2
  1
  3
  2
  3
  3
 * Explanation: The return value of pick() is random, it can be either 2 3 3 1 3 2 2 or other.
 * 
 * Thoughts:
 *   refer to AddRemoveAndRandomGet
 * 
 */
public class LoadBalancer {
    Random random;
    Map<Integer, Integer> map;  //<serverId, index>
    ArrayList<Integer> list; // <serverId>

    public LoadBalancer() {
        random = new Random();

        map = new HashMap<>();
        list = new ArrayList<>();
    }

    /*
     * @param server_id: add a new server to the cluster
     * @return: nothing
     */
    public void add(int serverId) {
        if(map.containsKey(serverId)){
            return;
        }
        
        int index = list.size();
        
        list.add(serverId);
        map.put(serverId, index);
    }

    /*
     * @param server_id: server_id remove a bad server from the cluster
     * @return: nothing
     */
    public void remove(int serverId) {
        if(!map.containsKey(serverId)){
            return;
        }
        
        int index = map.get(serverId);
        map.remove(serverId);
        
        int last = list.size() - 1;
        int lastServer = list.get(last);
        list.set(index, lastServer);
        map.put(lastServer, index);

        list.remove(last);
    }

    /*
     * @return: pick a server in the cluster randomly with equal probability
     */
    public int pick() {
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}

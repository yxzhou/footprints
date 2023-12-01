package companyTag.uber;

import org.junit.Test;

import java.util.*;

/**
 *
 uber 电面跪经. 1point3acres
 给一组数据, uber driver ID 和对应的电话号码，一个id可能对应多个号码，如果多个id有对应相同的号码，那么他们就是一个人，求出这个多对多的集合对应关系
 uuid -> phone number
 a -> 1
 b -> 2, 3
 c -> 1
 d -> 5
 e -> 2. check 1point3acres for more.

 输出：
 {a, c} -> {1}
 {b, e} -> {2, 3}
 {d} -> {5}
 *
 */

public class AccountMerge {

    /**
     * Input Map<uuid, phone list>,
     * Output Map<uuid list of a account, phone list>  ( uuid of a account? uuid has the same phone number )
     *
     * the point is to find out all uuid for every account.
     *
     * 1) re-organize input, to Map<phone, uuid list>,
     * 2) build Map<uuid, uuid list>, (specially the performance benefit for sparse matrix)
     * 3) BFS, find all uuid of every account,
     * 4) output
     *
     * @param accounts, example { a -> {1}, b -> {2,3} }
     * @return, example { {a, c} -> {1}, {b, e} -> {5} }
     */
    public Map<List<String>, List<String>> accountMerge(Map<String, List<String>> accounts){
        Map<List<String>, List<String>> result = new HashMap<>();

        if(null == accounts){
            return result;
        }

        //re-organize data, from (uuid -> phones) to (phone -> uuids)
        Map<String, List<String>> phones = new HashMap<>();
        for(Map.Entry<String, List<String>> entry : accounts.entrySet()){
            String uuid = entry.getKey();

            for(String phone : entry.getValue()) {
                phones.putIfAbsent(phone, new ArrayList<>());
                phones.get(phone).add(uuid);
            }
        }

        //
        Map<String, List<String>> uuids  = new HashMap<>();
        for(List<String> list : phones.values()){
            for(int i = 0, end = list.size() - 1; i < end; i++ ){
                for(int j = i + 1; j <= end; j++){
                    uuids.putIfAbsent(list.get(i), new ArrayList<>());
                    uuids.get(list.get(i)).add(list.get(j));
                    uuids.putIfAbsent(list.get(j), new ArrayList<>());
                    uuids.get(list.get(j)).add(list.get(i));
                }
            }
        }

        //bfs
        Set<String> visitedUuid = new HashSet<>(accounts.size());
        Set<String> visitedPhone = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        for(String uuid : accounts.keySet()){
            if(visitedUuid.contains(uuid)){
                continue;
            }

            List<String> uuidList = new ArrayList();
            if(!uuids.containsKey(uuid)){
                uuidList.add(uuid);
                result.put(uuidList, new ArrayList<>(accounts.get(uuid)));
                continue;
            }

            queue.add(uuid);
            visitedUuid.add(uuid);
            while(!queue.isEmpty()){
                uuid = queue.poll();

                uuidList.add(uuid);

                for(String next : uuids.get(uuid)){
                    if(!visitedUuid.contains(next)){
                        queue.add(next);
                        visitedUuid.add(next);
                    }
                }
            }

            List<String> phoneList = new ArrayList();
            for(int i = uuidList.size() - 1; i >= 0; i-- ){
                uuid = uuidList.get(i);

                for(String phone : accounts.get(uuid)){
                    if(!visitedPhone.contains(phone)){
                        visitedPhone.add(phone);
                        phoneList.add(phone);
                    }
                }
            }

            result.put(uuidList, phoneList);
        }

        return result;
    }

    @Test public void test(){

        String[][][][] input = {
                {
                        {{"a"}, {"1"}},
                        {{"b"}, {"2", "3"}},
                        {{"c"}, {"1"}},
                        {{"d"}, {"5"}},
                        {{"e"}, {"2"}}
                },
                {
                        {{"a"}, {"1"}},
                        {{"b"}, {"2",}},
                        {{"c"}, {"1", "2"}},
                        {{"d"}, {"5"}},
                        {{"e"}, {"2"}}
                }
        };


        for(String[][][] testCase : input){
            System.out.println("--------------");

            Map<String, List<String>> accounts = new HashMap<>();

            for(String[][] item : testCase){
                accounts.put(item[0][0], Arrays.asList(item[1]));
            }

            Map<List<String>, List<String>> result = accountMerge(accounts);
            for(Map.Entry<List<String>, List<String>> entry : result.entrySet()){
                //entry.getKey().stream().forEach(System.out :: print);

                System.out.println( entry.getKey() + " " + entry.getValue() );
            }

        }

    }
}

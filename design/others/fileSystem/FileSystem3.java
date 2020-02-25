package fgafa.design.others.fileSystem;


import org.junit.Test;

import java.util.*;

/**
 *
 *
 */
public class FileSystem3 {
    class Node{
        String name;

        Node(String name){
            this.name = name;
        }
    }

    class File extends Node{
        String content;

        File(String name, String content){
            super(name);
            this.content = content;
        }
    }

    class Folder extends Node{
        Map<String, Node> subs;

        Folder(String name){
            super(name);
            subs = new HashMap<>();
        }
    }

    Node root;

    public FileSystem3() {
        root = new Folder("");
    }

    public List<String> ls(String path) {
        String[] tokens = path.split("/");

        Node curr = root;
        for(String token : tokens){
            if(token.isEmpty()){
                continue;
            }

            if((curr instanceof Folder) && ((Folder) curr).subs.containsKey(token)){
                curr = ((Folder) curr).subs.get(token);
            }
        }

        List<String> result = new ArrayList<>();
        if(curr instanceof Folder){
            for(Node node : ((Folder) curr).subs.values()){
                result.add(node.name);
            }

            Collections.sort(result);
        }else{ // instanceof File
            result.add(curr.name);
        }

        return result;
    }

    public void mkdir(String path) {
        String[] tokens = path.split("/");

        Node curr = root;
        for(String token : tokens){
            if(token.isEmpty()){
                continue;
            }

            if( !((Folder) curr).subs.containsKey(token)){
                ((Folder) curr).subs.put(token, new Folder(token));
            }

            curr = ((Folder) curr).subs.get(token);
        }

    }

    public void addContentToFile(String filePath, String content) {
        String[] tokens = filePath.split("/");

        Node curr = root;
        for(String token: tokens){
            if(token.isEmpty()){
                continue;
            }

            if((curr instanceof Folder) && ((Folder) curr).subs.containsKey(token)){
                curr = ((Folder) curr).subs.get(token);
            }else{ // curr is not existed
                ((Folder) curr).subs.put(token, new File(token, content));
            }
        }

        if(curr instanceof File){
            ((File) curr).content += content;
        }
    }

    public String readContentFromFile(String filePath) {
        String[] tokens = filePath.split("/");

        Node curr = root;

        for(String token: tokens){
            if(token.isEmpty()){
                continue;
            }

            if((curr instanceof Folder) && ((Folder) curr).subs.containsKey(token)){
                curr = ((Folder) curr).subs.get(token);
            }
        }

        return ((File) curr).content;
    }



    @Test public void testSplit(){
        String path = "/";

        String[] tokens = path.split("/");

        System.out.println(tokens.length);
    }

    public static void main(String[] args){

//         Input:
//         ["FileSystem3","ls","mkdir","addContentToFile","ls","readContentFromFile","addContentToFile","readContentFromFile"]
//         [[],["/"],["/a/b/c"],["/a/b/c/d","hello world"],["/"],["/a/b/c/d"],["/a/b/c/d"," hello2 world"],["/a/b/c/d"]]
//
//         Output:
//         [null,[],null,null,["a"],"hello world",null,"hello world hello2 world"]

        FileSystem3 sv = new FileSystem3();

        System.out.println(sv.ls("/"));
        sv.mkdir("/a/b/c");
        sv.addContentToFile("/a/b/c/d","hello world");
        System.out.println(sv.ls("/"));
        System.out.println(sv.readContentFromFile("/a/b/c/d"));
        sv.addContentToFile("/a/b/c/d"," hello2 world");
        System.out.println(sv.readContentFromFile("/a/b/c/d"));

    }
}

/**
 * Your FileSystem3 object will be instantiated and called as such:
 * FileSystem3 obj = new FileSystem3();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */

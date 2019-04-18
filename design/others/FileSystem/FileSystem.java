package fgafa.design.others.FileSystem;


import org.junit.Test;

import java.util.*;

/**
 *
 * Design an in-memory file system to simulate the following functions:
 *
 * ls():
 * Given a path in string format. If it is a file path, return a list that only contains this file's name.
 * If it is a directory path, return the list of file and directory names in this directory.
 * Your output (file and directory names together) should in lexicographic order.
 *
 * mkdir():
 * Given a directory path that does not exist, you should make a new directory according to the path.
 * If the middle directories in the path don't exist either, you should create them as well.
 * This function has void return type.
 *
 * addContentToFile():
 * Given a file path and file content in string format.
 * If the file doesn't exist, you need to create that file containing given content.
 * If the file already exists, you need to append given content to original content.
 * This function has void return type.
 *
 * readContentFromFile():
 * Given a file path, return its content in string format.
 *
 *
 * Example:
 * Input:
 * ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
 * [[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
 *
 * Output:
 * [null,[],null,null,["a"],"hello"]
 *
 *
 * Note:
 * You can assume all file or directory paths are absolute paths which begin with / and do not end with / except that the path is just "/".
 * You can assume that all operations will be passed valid parameters and users will not attempt to retrieve file content or list a directory or file that does not exist.
 * You can assume that all directory names and file names only contain lower-case letters, and same names won't exist in the same directory.
 *
 */
public class FileSystem {
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

    public FileSystem() {
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
//         ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile","addContentToFile","readContentFromFile"]
//         [[],["/"],["/a/b/c"],["/a/b/c/d","hello world"],["/"],["/a/b/c/d"],["/a/b/c/d"," hello2 world"],["/a/b/c/d"]]
//
//         Output:
//         [null,[],null,null,["a"],"hello world",null,"hello world hello2 world"]

        FileSystem sv = new FileSystem();

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
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */

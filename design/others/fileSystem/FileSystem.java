package design.others.fileSystem;


import util.Misc;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Leetcode #588
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
 * ["FileSystem3","ls","mkdir","addContentToFile","ls","readContentFromFile"]
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
 * need clarify:
 *    1 Example:  addContentToFile("/a/b", "hello")
 *      is it return {"b"} for ls("/a") ?
 *      or, return {} for ls("/a"), return {"b"} for ls("/a/b");
 *
 *    2 can the directory name and file name be same? Example:  mkdir("/a/b") and addContentToFile("/a/b", "hello")
 *
 */
public class FileSystem {
    Map<String, Map> dirctory; //<>
    Map<String, String> fileContents; //<filePath, content>

    public FileSystem() {
        this.dirctory = new HashMap();
        this.fileContents = new HashMap();
    }

    public List<String> ls(String path) {
        //get directory path
        String[] tokens = path.split("/");

        Map<String, Map> curr = dirctory;
        for(int i = 1; i < tokens.length; i++){
            curr = curr.get(tokens[i]);
        }

        List<String> result = new ArrayList<>(curr.keySet());

        //get file path in the directory
        if(fileContents.containsKey(path)){
            result.add(tokens[tokens.length - 1]);
        }

        Collections.sort(result);

        return result;
    }

    public void mkdir(String path) {
        String[] tokens = path.split("/");

        Map<String, Map> curr = dirctory;
        for(int i = 1; i < tokens.length; i++){
            curr.putIfAbsent(tokens[i], new HashMap());
            curr = curr.get(tokens[i]);
        }
    }

    public void addContentToFile(String filePath, String content) {
        if(fileContents.containsKey(filePath)){
            //append
            fileContents.put(filePath, fileContents.get(filePath) + content);
        }else{
            //create
            mkdir(filePath);
            fileContents.putIfAbsent(filePath, content);
        }

    }


    public String readContentFromFile(String filePath) {

        return fileContents.get(filePath);
    }


    @Test public void testSplit(){
        String s = "/";
        String[] tokens = s.split("/");
        System.out.println(Misc.array2String(tokens));

        s = "/a";
        tokens = s.split("/");
        System.out.println(Misc.array2String(tokens));
        System.out.println(tokens.length);

        s = "/a/b";
        tokens = s.split("/");
        System.out.println(Misc.array2String(tokens));

        s = "ab";
        tokens = s.split("/");
        System.out.println(Misc.array2String(tokens));

        s = "ab";
        tokens = s.split("/");
        System.out.println(Misc.array2String(tokens));
        System.out.println(tokens.length);

        System.out.println(".ab".split("/.").length);
        System.out.println("..ab".split("/./.").length);

        System.out.println(getMainPart(".ab"));
        System.out.println(getMainPart("..ab"));
        System.out.println(getMainPart("ab.."));
        System.out.println(getMainPart("ab."));
        System.out.println(getMainPart("ab..a"));
        System.out.println(getMainPart("a..ab"));
    }

    private String getMainPart(String word){
        String[] tokens = null;
        if(word.contains("..")){
            tokens = word.split("\\.\\.");
        }else if(word.contains(".")){
            tokens = word.split("\\.");
        }

        String part = word;
        if(tokens != null){
            if(tokens.length == 1){
                part = tokens[0];
            }else{
                part = tokens[0].length() > tokens[1].length() ? tokens[0] : tokens[1];
            }
        }

        return part;
    }

    @Test public void mainTest(){

//         Input:
//         ["FileSystem3","ls","mkdir","addContentToFile","ls","readContentFromFile","addContentToFile","readContentFromFile"]
//         [[],["/"],["/a/b/c"],["/a/b/c/d","hello world"],["/"],["/a/b/c/d"],["/a/b/c/d"," hello2 world"],["/a/b/c/d"]]
//
//         Output:
//         [null,[],null,null,["a"],"hello world",null,"hello world hello2 world"]

        FileSystem sv = new FileSystem();

        Assert.assertEquals(new String[]{}, sv.ls("/").toArray());

        sv.mkdir("/a/b/c");
        Assert.assertEquals(new String[]{"a"}, sv.ls("/").toArray());
        Assert.assertEquals(new String[]{}, sv.ls("/a/b/c").toArray());

        sv.addContentToFile("/a/b/c","hello world");

        Assert.assertEquals(new String[]{"a"}, sv.ls("/").toArray());

        //Misc.printArrayList(sv.ls("/a"));

        Assert.assertEquals(new String[]{"b"}, sv.ls("/a").toArray());
        Assert.assertEquals(new String[]{"c"}, sv.ls("/a/b/c").toArray());

        Assert.assertEquals("hello world", sv.readContentFromFile("/a/b/c"));

        sv.addContentToFile("/a/b/c"," hello2 world");
        Assert.assertEquals("hello world hello2 world", sv.readContentFromFile("/a/b/c"));

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

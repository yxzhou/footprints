package fgafa.basic.regrex;

import java.io.File;

import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.regex.*;

public class FileName {

    public void getFileName(String regex, String url){
        File dir = new File(url);

//        FilenameFilter filter = new FilenameFilter() {
//            @Override public boolean accept(File dir, String name) {
//                if(name.matches(regex)){
//                    return true;
//                }
//
//                return false;
//            }
//        };

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        getFileName(dir, regex, sdf);
    }

    private void getFileName(File dir, String regex, SimpleDateFormat sdf){
        File[] files = dir.listFiles();

        if(null == files || 0 == files.length){
            return;
        }

        try {
            for (File file : files) {
                if(file.isFile() && file.getName().matches(regex)){
                    System.out.println(
                            String.format(" %s \t\t\t %d \t %s \t %s", file.getAbsolutePath(), file.length(),
                                    sdf.format(file.lastModified()),
                                    sdf.format(getCreationTime(file).toMillis())));
                }else{
                    //System.out.println(file.getAbsolutePath());
                    getFileName(file, regex, sdf);
                }

            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private static FileTime getCreationTime(File file) throws  IOException{
        Path p = Paths.get(file.getAbsolutePath());
        BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();

        FileTime fileTime=view.creationTime();
        //  also available view.lastAccessTine and view.lastModifiedTime
        return fileTime;
    }


    public static void main(String[] args){
        FileName sv = new FileName();


        sv.getFileName(".*\\(\\d\\)\\..*", "//Users//joeyz//Google Drive//");
    }

}

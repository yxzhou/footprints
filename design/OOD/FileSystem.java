package fgafa.design.OOD;

import java.util.ArrayList;

public class FileSystem
{
  Directory root;
}


abstract class Entry
{
  protected Directory parent;
  protected long created;
  protected long lastupdated;
  protected long lastAccessed;
  protected String name;


  public Entry(String n, Directory p) {
    name = n;
    parent = p;
    created = System.currentTimeMillis();
    lastupdated = System.currentTimeMillis();
    lastAccessed = System.currentTimeMillis();
  }



  public boolean delete() {
    if (parent == null)
      return false;
    return parent.deleteEntry(this);
  }



  public abstract int size();



  public String getFullPath() {
    if (parent == null)
      return name;
    else
      return parent.getFullPath() + "/" + name;
  }



  /* Getters and setters. */
  public long getCreationTime() {
    return created;
  }



  public long getLastUpdatedTime() {
    return lastupdated;
  }



  public long getLastAccessedTime() {
    return lastAccessed;
  }



  public void changeName(String n) {
    name = n;
  }



  public String getName() {
    return name;
  }
}



class File extends Entry
{
  private String content;
  private int size;



  public File(String n, Directory p, int sz) {
    super(n, p);
    size = sz;
  }



  public int size() {
    return size;
  }



  public String getContents() {
    return content;
  }



  public void setContents(String c) {
    content = c;
  }
}



class Directory extends Entry
{
  protected ArrayList<Entry> contents;



  public Directory(String n, Directory p) {
    super(n, p);
    contents = new ArrayList<Entry>();
  }



  public int size() {
    int size = 0;
    for (Entry e : contents) {
      size += e.size();
    }
    return size;
  }



  public int numberOfFiles() {
    int count = 0;
    for (Entry e : contents) {
      if (e instanceof Directory) {
        count++; // Directory counts as a file
        Directory d = (Directory) e;
        count += d.numberOfFiles();
      }
      else if (e instanceof File) {
        count++;
      }
    }
    return count;
  }



  public boolean deleteEntry(Entry entry) {
    return contents.remove(entry);
  }



  public void addEntry(Entry entry) {
    contents.add(entry);
  }



  protected ArrayList<Entry> getContents() {
    return contents;
  }
}

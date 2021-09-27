package design.systemdesign.mmap;

/*
Question Description: You are to write an abstraction layer for a persistent
buffer. Provide an implementation of the following abstract class:

*/
public abstract class pBuffer {

  protected final int BLOCK_SIZE = 1024;
  protected final int BLOCK_COUNT = 1024;

  protected byte[] buffer = new byte[BLOCK_COUNT * BLOCK_SIZE]; // A sample 1mb buffer, to be allocated in 1k chunks. 

  /*
  public pBuffer() {
    fillBufferFromFile(); // Reads the buffer from file and dumps the contents into the array, restoring the state to what it was when onShutdown() was called
  }
  */

  // Returns a Location for a free block of the buffer, suitable for passing to put, get, and free
  public abstract Location allocate() throws NoAvailableSpaceException;

  // Stores up to BLOCK_SIZE bytes of data in location l. Data beyond BLOCK_SIZE bytes should be truncated
  public abstract void put(Location l, byte[] data);

  // Returns the BLOCK_SIZE bytes of data stored at location l, or null if l is unallocated
  public abstract byte[] get(Location l);

  // Indicates that an area of the buffer is no longer needed, and can be reused
  public abstract void free(Location l);

  /*
  // Called on shutdown
  private void onShutdown() {
    writeBufferToFile(); // writes the full contents of the buffer to disk, for reading when later invoked by the constructor
  }
  */
  
}

/**
 * 要求实现allocate, put, get, free的内容. 已知条件, pBuffer这个类在初始化过程
 * 中已经调用了fillBufferFromFile. 此外有个onShutdown()函数要把buffer的内容写到 disk.
 */

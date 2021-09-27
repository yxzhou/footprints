package design.systemdesign.mmap;

public class Location {
	
	private int blockIndex;

	public Location(int index){
		this.blockIndex = index;
	}
	
	public int getBlockIndex() {
		return blockIndex;
	}

	public void setBlockIndex(int blockIndex) {
		this.blockIndex = blockIndex;
	}

}

package logic;

import java.util.ArrayList;

public class Path {
	private ArrayList<Integer> path;
	private int gain;
	

	public Path(ArrayList<Integer> path, int gain) {
		this.path = path;
		this.gain = gain;
	}
	
	public ArrayList<Integer> getPath() {
		return path;
	}
	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}
	public int getGain() {
		return gain;
	}
	public void setGain(int gain) {
		this.gain = gain;
	}
	

}

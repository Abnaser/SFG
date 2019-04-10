package logic;

import java.util.ArrayList;
import java.util.LinkedList;

public class NonTouchingLoops {
	int number;
	LinkedList<Path> loops;
	private ArrayList<Integer> path;
	int gain;
	
	public NonTouchingLoops(int number) {
		this.number = number;
		gain = 1;
		loops = new LinkedList();
		path = new ArrayList<Integer>();
	}
	public void addLoop(Path loop) {
		loops.add(loop);
	}
	public void setGain() {
		for (int i = 0; i < number; i++) {
			gain *= loops.get(i).getGain();
		}
	}
	public void setPath() {
		for (int i = 0; i < loops.size(); i++) {
			Path path = loops.get(i);
			ArrayList<Integer> nodes = path.getPath();
			for (int j = 0; j < nodes.size(); j++) {
				this.path.add(nodes.get(j));
			}
		}
	}
	public void printPath() {
		for (int i = 0; i < path.size(); i ++) {
		}
	}
	public LinkedList<Path> getLoops() {
		return loops;
	}
	public void setLoops(LinkedList<Path> loops) {
		this.loops = loops;
	}
	public int getGain() {
		return gain;
	}
	public void setGain(int gain) {
		this.gain = gain;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public ArrayList<Integer> getPath() {
		return path;
	}
	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}
}

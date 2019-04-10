package logic;

import java.util.ArrayList;

public class Mason {
	private ArrayList<Path> forwardPaths;
	private ArrayList<Integer> deltaK;
	private ArrayList<Path> loops;
	ArrayList<ArrayList<NonTouchingLoops>> nonTouchingLoops;
	public ArrayList<ArrayList<NonTouchingLoops>> getNonTouchingLoops() {
		return nonTouchingLoops;
	}
	public void setNonTouchingLoops(ArrayList<ArrayList<NonTouchingLoops>> nonTouchingLoops) {
		this.nonTouchingLoops = nonTouchingLoops;
	}
	private int delta;
	SFG sfg;
	private double overallTF;
	
	public Mason(SFG sfg) {
		this.sfg = sfg;
		setDeltaK(new ArrayList<Integer>());
	}
	public void initializeMason() {
		GraphDS graphDS = sfg.getGraphDS();
		setForwardPaths(graphDS.forwardPaths);
		setLoops(graphDS.loops);
		nonTouchingLoops = graphDS.nonTouchingLoops;
		setDelta(calcDelta(getLoops(), nonTouchingLoops));
		calcAllDeltas();
		calcOverallTF();
	}
	
	
	
	private void calcOverallTF() {
		double sigma = 0;
		double result = 0;
		for (int i = 0; i < getForwardPaths().size(); i++) {
			double term = getForwardPaths().get(i).getGain() * getDeltaK().get(i);
			sigma += term;
		}
		result = sigma / getDelta();
		setOverallTF(result);
		
	}
	public int calcDelta(ArrayList<Path> loops,
						ArrayList<ArrayList<NonTouchingLoops>> nonTouchingLoops) {
		int result = 1;
		for (int i = 0; i < loops.size(); i++) {
			Path loop = loops.get(i);
			result -= loop.getGain();
		}
		
		for (int i = 0; i < nonTouchingLoops.size(); i++) {
			ArrayList<NonTouchingLoops> list = nonTouchingLoops.get(i);
			for (int j = 0; j < list.size(); j++) {
				result += Math.pow(-1, i) * list.get(j).getGain();
			}
		}
		
		return result;
	}
	
	public void calcAllDeltas() {
		for (int k = 0; k < getForwardPaths().size(); k++) {
			int result = calcDeltaK(k);
			getDeltaK().add(result);
		}
	}
	
	public int calcDeltaK(int k) {
		int result;
		ArrayList<Path> loopsK = getLoopsK(k);
		ArrayList<ArrayList<NonTouchingLoops>> nonTouchingLoops = 
				new ArrayList<ArrayList<NonTouchingLoops>>();
		sfg.getGraphDS().findTwoNonTouchingLoops(loopsK, nonTouchingLoops);
		sfg.getGraphDS().findNonTouchingLoops(loopsK, nonTouchingLoops, 3);
		result = calcDelta(loopsK, nonTouchingLoops);
		return result;
	}
	
	public ArrayList<Path> getLoopsK(int k) {
		ArrayList<Path> loopsK = (ArrayList<Path>) getLoops().clone();
		Path forwardPathK = getForwardPaths().get(k);
		ArrayList<Integer> pathK = forwardPathK.getPath();
		
		for (int i = 0 ; i < pathK.size(); i++) {
			int n = pathK.get(i);
			for (int j = 0; j < loopsK.size(); j++) {
				ArrayList<Integer> path = loopsK.get(j).getPath();
				if (path.contains(n)) {
					loopsK.remove(j);
					loopsK.trimToSize();
					j--;
				}
			}
		}
		
		
		return loopsK;
	}
	public ArrayList<Path> getForwardPaths() {
		return forwardPaths;
	}
	public void setForwardPaths(ArrayList<Path> forwardPaths) {
		this.forwardPaths = forwardPaths;
	}
	public ArrayList<Path> getLoops() {
		return loops;
	}
	public void setLoops(ArrayList<Path> loops) {
		this.loops = loops;
	}
	public int getDelta() {
		return delta;
	}
	public void setDelta(int delta) {
		this.delta = delta;
	}
	public ArrayList<Integer> getDeltaK() {
		return deltaK;
	}
	public void setDeltaK(ArrayList<Integer> deltaK) {
		this.deltaK = deltaK;
	}
	public double getOverallTF() {
		return overallTF;
	}
	public void setOverallTF(double overallTF) {
		this.overallTF = overallTF;
	}

}

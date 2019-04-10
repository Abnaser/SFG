package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GraphDS {

	private int numNodes;
	private int adjMatrix[][];
	private int[][] adjList;
	public ArrayList<Path> forwardPaths;
	public ArrayList<Path> loops;
	public ArrayList<ArrayList<NonTouchingLoops>> nonTouchingLoops;

	public GraphDS(int numNodes, Graph graph) {
		this.numNodes = numNodes;
		adjMatrix = new int[numNodes][numNodes];

		Iterator<? extends Edge> edges = graph.getEdgeIterator();

		while (edges.hasNext()) {
			Edge edge = edges.next();
			int gain = edge.getAttribute("gain");
			int start = Integer.parseInt(edge.getNode0().getId());
			int end = Integer.parseInt(edge.getNode1().getId());
			addBranch(start, end, gain);
		}
		forwardPaths = new ArrayList();
		loops = new ArrayList();
		nonTouchingLoops = new ArrayList<ArrayList<NonTouchingLoops>>();

	}

	public void addBranch(int start, int end, int gain) {

		adjMatrix[--start][--end] = gain;

	}

	public void removeBranch(int start, int end) {

		adjMatrix[start][end] = 0;

	}

	public int getGain(int start, int end) {

		return adjMatrix[start][end];

	}

	public void calcForwardPaths(int s, int d) {
		adjList = AdjacencyList.calcAdjList(adjMatrix);
		boolean[] isVisited = new boolean[numNodes];
		ArrayList<Integer> pathList = new ArrayList<>();
		pathList.add(s);

		calcForwardPathsUtil(s, d, isVisited, pathList);
	}

	private void calcForwardPathsUtil(Integer u, Integer d, boolean[] isVisited, ArrayList<Integer> localPathList) {

		isVisited[u] = true;

		if (u.equals(d)) {
			ArrayList forwardPath = (ArrayList) localPathList.clone();
			forwardPaths.add(new Path(forwardPath, calcGain(forwardPath)));
			isVisited[u] = false;
			return;
		}

		for (int i : adjList[u]) {
			if (!isVisited[i]) {

				localPathList.add(i);
				calcForwardPathsUtil(i, d, isVisited, localPathList);

				int index = localPathList.indexOf(i);
				localPathList.remove(index);
			}
		}
		isVisited[u] = false;
	}

	public void calcLoops() {
		findSelfLoops();
		JohnsonCycles ecs = new JohnsonCycles(adjMatrix);
		ArrayList<ArrayList> cycles = ecs.getElementaryCycles();
		for (int i = 0; i < cycles.size(); i++) {
			ArrayList cycle = cycles.get(i);
			int gain = calcGain(cycle);
			Path loop = new Path(cycle, gain);
			loops.add(loop);
		}
	}

	private int calcGain(ArrayList<Integer> path) {
		int gain = 1;

		for (int i = 0; i < path.size() - 1; i++) {
			int node = path.get(i);
			int nextNode = path.get(i + 1);
			gain *= adjMatrix[node][nextNode];
		}

		return gain;
	}

	public void findNonTouchingLoops(ArrayList<Path> loops, ArrayList<ArrayList<NonTouchingLoops>> nonTouchingLoops,
			int degree) {

		if (nonTouchingLoops.get(degree - 3).isEmpty()) {
			return;
		}
		ArrayList<NonTouchingLoops> prevDegree = nonTouchingLoops.get(degree - 3);
		nonTouchingLoops.add(new ArrayList<NonTouchingLoops>());
		boolean flag = true;
		for (int i = 0; i < prevDegree.size(); i++) {
			NonTouchingLoops currentLoops = prevDegree.get(i);
			ArrayList<Integer> currentPath = currentLoops.getPath();
			for (int j = 0; j < loops.size(); j++) {
				flag = true;
				Path nextLoop = loops.get(j);
				ArrayList<Integer> nextPath = nextLoop.getPath();
				int n = 0;
				int m = 0;
				for (int u = 0; u < currentPath.size(); u++) {
					n = currentPath.get(u);
					for (int v = 0; v < nextPath.size(); v++) {
						m = nextPath.get(v);
						if (n == m) {
							flag = false;
						}
					}
				}
				if (flag) {
					NonTouchingLoops newNonTouchingLoops = new NonTouchingLoops(degree);
					for (int k = 0; k < currentLoops.getNumber(); k++) {
						newNonTouchingLoops.addLoop(currentLoops.getLoops().get(k));
					}
					newNonTouchingLoops.addLoop(nextLoop);
					newNonTouchingLoops.setGain();
					newNonTouchingLoops.setPath();
					nonTouchingLoops.get(degree - 2).add(newNonTouchingLoops);
				}
			}
		}
		trimNonTouchingLoopsList(nonTouchingLoops.get(degree - 2));
		findNonTouchingLoops(loops, nonTouchingLoops, degree + 1);

	}

	public void findTwoNonTouchingLoops(ArrayList<Path> loops,
			ArrayList<ArrayList<NonTouchingLoops>> nonTouchingLoops) {
		nonTouchingLoops.add(new ArrayList<NonTouchingLoops>());
		boolean flag = true;
		for (int i = 0; i < loops.size() - 1; i++) {
			Path currentLoop = loops.get(i);
			for (int j = i + 1; j < loops.size(); j++) {
				flag = true;
				Path nextLoop = loops.get(j);
				int n = 0;
				int m = 0;
				for (int u = 0; u < currentLoop.getPath().size(); u++) {
					n = currentLoop.getPath().get(u);
					for (int v = 0; v < nextLoop.getPath().size(); v++) {
						m = nextLoop.getPath().get(v);
						if (n == m) {
							flag = false;
						}
					}
				}
				if (flag) {
					NonTouchingLoops newNonTouchingLoops = new NonTouchingLoops(2);
					newNonTouchingLoops.addLoop(currentLoop);
					newNonTouchingLoops.addLoop(nextLoop);
					newNonTouchingLoops.setGain();
					newNonTouchingLoops.setPath();
					nonTouchingLoops.get(0).add(newNonTouchingLoops);
				}
			}

		}
	}

	public void trimNonTouchingLoopsList(ArrayList<NonTouchingLoops> list) {
		for (int i = 0; i < list.size(); i++) {
			NonTouchingLoops loops = list.get(i);
			ArrayList<Integer> path = loops.getPath();
			Collections.sort(path);
		}
		for (int i = 0; i < list.size() - 1; i++) {
			NonTouchingLoops loops1 = list.get(i);
			ArrayList<Integer> path1 = loops1.getPath();
			for (int j = i; j < list.size(); j++) {
				NonTouchingLoops loops2 = list.get(j);
				ArrayList<Integer> path2 = loops2.getPath();
				if (path1.equals(path2)) {
					list.remove(j);
					list.trimToSize();
				}
			}
		}

	}
	public void findSelfLoops() {
		for (int i = 0; i < numNodes; i++) {
			if (adjMatrix[i][i] > 0) {
				ArrayList<Integer> cycle = new ArrayList<Integer>();
				cycle.add(i);
				cycle.add(i);
				int gain = adjMatrix[i][i];
				Path loop = new Path(cycle, gain);
				loops.add(loop);
				
			}
		}
	}

}

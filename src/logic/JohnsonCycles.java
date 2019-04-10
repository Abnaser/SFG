package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class JohnsonCycles {
	private ArrayList cycles = null;

	private int[][] adjList = null;

	private Object[] graphNodes = null;

	private boolean[] blocked = null;

	private Vector[] B = null;

	private Vector stack = null;

	public JohnsonCycles(int[][] matrix) {
		this.adjList = AdjacencyList.calcAdjList(matrix);
	}

	public ArrayList getElementaryCycles() {
		this.cycles = new ArrayList();
		this.blocked = new boolean[this.adjList.length];
		this.B = new Vector[this.adjList.length];
		this.stack = new Vector();
		StronglyConnectedComponents sccs = new StronglyConnectedComponents(this.adjList);
		int s = 0;

		while (true) {
			SCCResult sccResult = sccs.getAdjacencyList(s);
			if (sccResult != null && sccResult.getAdjList() != null) {
				Vector[] scc = sccResult.getAdjList();
				s = sccResult.getLowestNodeId();
				for (int j = 0; j < scc.length; j++) {
					if ((scc[j] != null) && (scc[j].size() > 0)) {
						this.blocked[j] = false;
						this.B[j] = new Vector();
					}
				}

				this.findCycles(s, s, scc);
				s++;
			} else {
				break;
			}
		}

		return this.cycles;
	}

	private boolean findCycles(int v, int s, Vector[] adjList) {
		boolean f = false;
		this.stack.add(v);
		this.blocked[v] = true;

		for (int i = 0; i < adjList[v].size(); i++) {
			int w = ((Integer) adjList[v].get(i)).intValue();
			// found cycle
			if (w == s) {
				ArrayList cycle = new ArrayList();
				for (int j = 0; j < this.stack.size(); j++) {
					int index = ((Integer) this.stack.get(j)).intValue();
					cycle.add(index);
				}
				cycle.add(w);
				this.cycles.add(cycle);
				f = true;
			} else if (!this.blocked[w]) {
				if (this.findCycles(w, s, adjList)) {
					f = true;
				}
			}
		}

		if (f) {
			this.unblock(v);
		} else {
			for (int i = 0; i < adjList[v].size(); i++) {
				int w = ((Integer) adjList[v].get(i)).intValue();
				if (!this.B[w].contains(new Integer(v))) {
					this.B[w].add(new Integer(v));
				}
			}
		}
		int index = stack.indexOf(v);
		this.stack.remove(index);
		return f;
	}

	private void unblock(int node) {
		this.blocked[node] = false;
		Vector Bnode = this.B[node];
		while (Bnode.size() > 0) {
			Integer w = (Integer) Bnode.get(0);
			Bnode.remove(0);
			if (this.blocked[w.intValue()]) {
				this.unblock(w.intValue());
			}
		}
	}
}

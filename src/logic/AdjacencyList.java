package logic;

import java.util.Vector;

public class AdjacencyList {
	public static int[][] calcAdjList(int[][] adjacencyMatrix) {
		int[][] list = new int[adjacencyMatrix.length][];

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			Vector v = new Vector();
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				if (adjacencyMatrix[i][j] != 0) {
					v.add(j);
				}
			}

			list[i] = new int[v.size()];
			for (int j = 0; j < v.size(); j++) {
				Integer in = (Integer) v.get(j);
				list[i][j] = in.intValue();
			}
		}
		
		return list;
	}
}

package logic;

import java.util.ArrayList;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.graphicGraph.stylesheet.*;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.graphicGraph.*;



public class SFG {
	int numNodes;
	GraphDS graphDS;
	private Graph graph;
	SpriteManager sman;
	
	public SFG(int numNodes) {
		graph = new SingleGraph("Signal Flow Graph");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "edge { size: 15px; "
				+ "arrow-size: 20px, 10px; "
				+ "text-size: 15px;"
				+ "text-style: bold;}");
		graph.addAttribute("ui.stylesheet", "node { size: 15px; "
				+ "text-size: 15px;"
				+ "text-style: bold;}");



		sman = new SpriteManager(graph);
		this.numNodes = numNodes;
	//	int x = 5;
		for (int i = 1; i <= numNodes; i++) {
	//		x += 20;
			Node node = getGraph().addNode(""+i);
			node.addAttribute("ui.label", node.getId());
		//	node.addAttribute("layout.frozen");
		//    node.addAttribute("xy", x, 0);
		}

	}
	public void addBranch(int start, int end, int gain) {
		String branchID = ""+start+end;
		Edge e = graph.addEdge(branchID, ""+start, ""+end, true);
		e.addAttribute("ui.label", ""+gain);
		
		e.setAttribute("gain", gain);
	}
	public int getNumNodes() {
		return numNodes;
	}
	public void initializeGraphDS() {
		graphDS = new GraphDS(numNodes, getGraph());
	}
	public GraphDS getGraphDS() {
		return graphDS;
	}
	public void setGraphDS(GraphDS graphDS) {
		this.graphDS = graphDS;
	}
	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}


}

package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import logic.NonTouchingLoops;
import logic.Path;

public class ResultWindowController implements Initializable {

    @FXML
    private TextArea txtForwardPaths;

    @FXML
    private TextArea txtLoops;

    @FXML
    private TextArea txtNonTouching;

    @FXML
    private TextArea txtResult;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtForwardPaths.setText("Forward Paths:\n");
		for (int j = 0; j< Main.mason.getForwardPaths().size(); j++) {
			Path path = Main.mason.getForwardPaths().get(j);
			int n = j+1;
			txtForwardPaths.appendText(n + ") ");
			for (int i = 0; i < path.getPath().size(); i++) {
				int node = path.getPath().get(i) + 1;
				txtForwardPaths.appendText(node + " ");
			}
			txtForwardPaths.appendText("G= " + path.getGain()+"\n");
		}
		txtLoops.setText("Loops:\n");
		for (int j = 0; j< Main.mason.getLoops().size(); j++) {
			Path path = Main.mason.getLoops().get(j);
			int n = j+1;
			txtLoops.appendText(n + ") ");
			for (int i = 0; i < path.getPath().size(); i++) {
				int node = path.getPath().get(i) + 1;
				txtLoops.appendText(node + " ");
			}
			txtLoops.appendText("G= " + path.getGain()+"\n");
		}
		txtNonTouching.setText("NonTouchingLoops:\n");
		for (int j = 0; j< Main.mason.getNonTouchingLoops().size(); j++) {
			ArrayList<NonTouchingLoops> nonTouchingLoops = Main.mason.getNonTouchingLoops().get(j);
			for (int i = 0; i < nonTouchingLoops.size(); i++) {
				NonTouchingLoops loops = nonTouchingLoops.get(i);
				LinkedList<Path> loopsList = loops.getLoops();
				for (int k = 0; k < loopsList.size(); k++) {
					Path loop = loopsList.get(k);
					ArrayList<Integer> path = loop.getPath();
					for (int m = 0; m < path.size(); m++) {
						int node = path.get(m) + 1;
						txtNonTouching.appendText(node +" ");
					}
					txtNonTouching.appendText(", ");
				}
				txtNonTouching.appendText("G= " + loops.getGain()+"\n");

			}
		}
		
		
		txtResult.appendText("Delta = " + Main.mason.getDelta()+"\n");
		for (int i = 0 ; i < Main.mason.getDeltaK().size(); i++) {
			int n = i+1;
			txtResult.appendText("Delta" + n + " = " + Main.mason.getDeltaK().get(i)+"\n");
		}
		String s = ""+Main.mason.getOverallTF();
		txtResult.appendText("Overall Transfer Function = " +s);


	}

}

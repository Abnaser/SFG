package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.GraphDS;
import logic.Mason;

public class InputWindowController {

	@FXML
	private TextField txtStart;

	@FXML
	private TextField txtEnd;

	@FXML
	private TextField txtGain;

	@FXML
	private Button btnAdd;

	@FXML
	private Label lblWarning;

	@FXML
	private Button btnCalc;

	public void btnAddClicked() {
		int start;
		int end;
		int gain;
		try {
			start = Integer.parseInt(txtStart.getText());
			end = Integer.parseInt(txtEnd.getText());
			gain = Integer.parseInt(txtGain.getText());

		} catch (NumberFormatException e) {
			lblWarning.setText("Invalid Input");
			return;
		}
		try {
			Main.sfg.addBranch(start, end, gain);
		} catch (Exception e) {
			lblWarning.setText("Invalid Input");
			return;
		}
		lblWarning.setText("Node Added");
		txtStart.setText("");
		txtEnd.setText("");
		txtGain.setText("");

	}

	public void btnCalcClicked() {
		Main.sfg.initializeGraphDS();
		Main.sfg.getGraphDS().calcForwardPaths(0, Main.sfg.getNumNodes()-1);
		Main.sfg.getGraphDS().calcLoops();
		Main.sfg.getGraphDS().findTwoNonTouchingLoops(Main.sfg.getGraphDS().loops, Main.sfg.getGraphDS().nonTouchingLoops);
		Main.sfg.getGraphDS().findNonTouchingLoops(Main.sfg.getGraphDS().loops, Main.sfg.getGraphDS().nonTouchingLoops, 3);
		Main.mason = new Mason(Main.sfg);
		Main.mason.initializeMason();
    	try {
	    	VBox root = FXMLLoader.load(getClass().getResource("ResultWindow.fxml"));
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.window.setScene(scene);
			Main.window.show();
    	} catch(Exception e) {
			e.printStackTrace();
		}
	}

}

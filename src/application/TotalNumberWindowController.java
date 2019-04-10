package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import logic.SFG;

public class TotalNumberWindowController {

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblNum;

    @FXML
    private TextField txtNum;

    @FXML
    private Button btnNum;
    
    @FXML
    private Label lblWarning;

    
    public void btnNumClicked() throws IOException {
    	int numNodes;
    	try {
        	numNodes = Integer.parseInt(txtNum.getText());
    	} catch (NumberFormatException e) {
    		lblWarning.setText("Invalid number " + e.getMessage());
    		return;
    	}
    	if (numNodes < 2) {
    		lblWarning.setText("Invalid number ");
    		return;
    	}
    	Main.sfg = new SFG(numNodes);
    	try {
	    	VBox root = FXMLLoader.load(getClass().getResource("InputWindow.fxml"));
			Scene scene = new Scene(root,500,200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.window.setScene(scene);
			Main.window.show();
			Main.sfg.getGraph().display();
    	} catch(Exception e) {
			e.printStackTrace();
		}
	
    }

}

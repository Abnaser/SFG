package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import logic.Mason;
import logic.SFG;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	public static SFG sfg;
	public static Mason mason;
	public static Stage window;
	
	
	@Override
	public void start(Stage primaryStage) {
		System.setProperty("rg.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		window = primaryStage;
		try {
			VBox root = FXMLLoader.load(getClass().getResource("TotalNumberWindow.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

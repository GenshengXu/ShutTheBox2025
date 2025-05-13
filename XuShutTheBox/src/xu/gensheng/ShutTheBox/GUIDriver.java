package xu.gensheng.ShutTheBox;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIDriver extends Application {

	Die d1 = new Die();
	Die d2 = new Die();
	
	static int p1Score = 0;
	static int p2Score = 0;
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Shut The Box");
		VBox root = new VBox(10);
		HBox colBox = new HBox(40);
		colBox.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);
	
		// Create the Button
		Button[] tileBtns = new Button[9];
		for (int i=0; i<tileBtns.length; i++) {
			 Button button = new Button (String.valueOf(i+1));
			 button.setStyle("-fx-background-color: #FFFFFF;");
			 tileBtns[i] = button;
		}
		
		// Markers
		Tile[] p1Markers = new Tile[9];
		for (int i=0; i<p1Markers.length; i++) {
			p1Markers[i] = new Tile (i+1);
		}
		// Make variables for tiles 7, 8 & 9 to check if they're down, if true, then roll 1 die
		/**
		oneDie = p1markers = Tile[7,9];
		**/
		
		VBox p1Box = new VBox(5);
		for (Button b : tileBtns) {
			p1Box.getChildren().add(b);
	
			// Event handler
			b.setOnAction(e -> {
				int number = Integer.valueOf(b.getText());
				if (!p1Markers[(number-1)].isDown()) {
					if (!p1Markers[(number-1)].isSelected()) {
						// set the color for selection
						b.setStyle("-fx-background-color: #5299EB;");
						p1Markers[(number-1)].select();
					}
					else {
						// set the color for selection
						b.setStyle("-fx-background-color: #FFFFFF;");
						p1Markers[(number-1)].deselected();
					}
				}
				
			});
		} // end of event handler for tile section buttons
		
	
		// Create the Button Button[] 
		Button[] tileBtns2 = new Button[9];
		for (int i=0; i<tileBtns2.length; i++) {
			 Button button = new Button (String.valueOf(i+1));
			 button.setStyle("-fx-background-color: #FFFFFF;");
			 tileBtns2[i] = button;
		
		Markers Tile[] p2Markers = new Tile[9]; 
		for (int i=0; i<p2Markers.length; i++) {
			p2Markers[i] = new Tile (i+1);
			
		
		 
			}
		}
		
		// Roll dices, and if tiles 7,8,9 tiles are down, allow only 1 dice
		Button btnRoll = new Button("Roll 2 die");
		if (tiles = true) {
			Button btnRoll2 = new Button();
		}
		
		Label dieResult = new Label("");
		
		btnRoll.setOnAction(e-> {
			int sumDie = d1.roll() + d2.roll();
			dieResult.setText(String.valueOf(sumDie));
			btnRoll.setDisable(true);
		});
		
		Button lockIn = new Button ("Lock IN");
		lockIn.setOnAction(e-> {
			// determine the sum of the selected tiles
			int sum = 0;
			for (Tile t: p1Markers) {
				if (t.isSelected()) {
					sum += t.getValue();
				}
			}
			
			if (sum == Integer.valueOf(dieResult.getText())) {
				// disable buttons
				for (int i=0; i<p1Markers.length; i++) {
					if (p1Markers[i].isSelected()) {
						p1Markers[i].shut();
						tileBtns[i].setStyle("-fx-background-color:#008000;");
						tileBtns[i].setDisable(true);
							
					}
				}
				// allow user to roll
				dieResult.setText("");
				btnRoll.setDisable(false);
			}	
		});
		
		TextField textField = new TextField();
		HBox scores = new HBox();
		scores.getChildren().addAll(textField);
		
		colBox.getChildren().add(p1Box);
		colBox.setAlignment(Pos.CENTER);
		root.getChildren().add(colBox);
		root.getChildren().add(btnRoll);
		root.getChildren().add(dieResult);
		root.getChildren().add(lockIn);
		
		
		
		
		Scene scene = new Scene(root, 400, 500);
		stage.setScene(scene);	
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}

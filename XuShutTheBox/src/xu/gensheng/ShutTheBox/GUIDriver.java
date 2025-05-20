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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GUIDriver extends Application {

	Die d1 = new Die();
	Die d2 = new Die();

	static int p1Score = 0;
	static int p2Score = 0;

	int currentPlayer = 1;
	int currentRound = 1;

	/**
	 * Shut The Box Game Author: GenSheng-Xu Version 1.0
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Title and scenes
		stage.setTitle("Shut The Box");
		VBox root = new VBox(10);

		// The status of players turn, and round they're playing in
		HBox status = new HBox(40);
		Label lblPlayer = new Label("Player: " + currentPlayer);
		Label lblRound = new Label("Round: " + currentRound);
		status.getChildren().addAll(lblPlayer, lblRound);
		root.getChildren().add(status);
		status.setAlignment(Pos.CENTER);

		HBox colBox = new HBox(40);
		colBox.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);

		// Create the Button
		Button[] tileBtns = new Button[9];
		for (int i = 0; i < tileBtns.length; i++) {
			Button button = new Button(String.valueOf(i + 1));
			button.setStyle("-fx-background-color: #FFFFFF;");
			tileBtns[i] = button;
		}

		// Markers
		Tile[] playerMarkers = new Tile[9];
		for (int i = 0; i < playerMarkers.length; i++) {
			playerMarkers[i] = new Tile(i + 1);
		}

		//
		VBox playerBox = new VBox(5);
		for (Button b : tileBtns) {
			playerBox.getChildren().add(b);
			// Event handler
			b.setOnAction(e -> {
				int number = Integer.valueOf(b.getText());
				if (!playerMarkers[(number - 1)].isDown()) {
					if (!playerMarkers[(number - 1)].isSelected()) {
						// set the color for selection
						b.setStyle("-fx-background-color: #5299EB;");
						playerMarkers[(number - 1)].select();
					} else {
						// set the color for selection
						b.setStyle("-fx-background-color: #FFFFFF;");
						playerMarkers[(number - 1)].deselected();
					}
				}

			});
		}

		// DICES //
		Button btnRoll = new Button("Roll 2 die");
		btnRoll.setTextFill(Color.ROYALBLUE);
		Button btnRollSingle = new Button("Roll 1 Die");
		btnRollSingle.setDisable(true);
		btnRollSingle.setTextFill(Color.RED);

		// What player rolled
		Label dieResult = new Label();
		dieResult.setUnderline(true);
		dieResult.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));

		// Roll 2 dice
		btnRoll.setOnAction(e -> {
			int sumDie = d1.roll() + d2.roll();
			dieResult.setText(String.valueOf(sumDie));
			btnRoll.setDisable(true); // this and another block of code will not allow players to continuously roll

		});

		// Roll 1 dice
		btnRollSingle.setOnAction(e -> {
			int sumDie = d1.roll();
			dieResult.setText(String.valueOf(sumDie));
			btnRollSingle.setDisable(true);
		});

		// Lock in the selected tiles to confirm and verify
		Button lockIn = new Button("Lock IN");
		lockIn.setTextFill(Color.SEAGREEN);
		Label dieResultShow = new Label("Rolled a:");
		dieResultShow.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		lockIn.setOnAction(e -> {
			// determine the sum of the selected tiles
			int sum = 0;
			for (Tile t : playerMarkers) {
				if (t.isSelected()) {
					sum += t.getValue();
				}
			}

			if (sum == Integer.valueOf(dieResult.getText())) {
				// disable select-able tiles
				for (int i = 0; i < playerMarkers.length; i++) {
					if (playerMarkers[i].isSelected()) {
						playerMarkers[i].shut();
						tileBtns[i].setStyle("-fx-background-color:#008000;");
						tileBtns[i].setDisable(true);
					}
				}

				// allow user to roll
				dieResult.setText("");
				dieResult.setTextFill(Color.DARKCYAN);

				// Check if 7,8,9 are down
				boolean enableSingle = true;
				for (int i = 6; i < 9; i++) {
					if (!playerMarkers[i].isDown()) {
						enableSingle = false;
					}
				}
				
				if (enableSingle) {
					btnRollSingle.setDisable(false);
					btnRoll.setDisable(true);
				} else {
					btnRoll.setDisable(false);
				}
			}
		});

		// Quit Button
		Button quit = new Button("Quit");
		quit.setTextFill(Color.BLUEVIOLET);

		// Show scores of players live/every round
		HBox finalScore = new HBox(38);
		Label p1TotalScore = new Label("P1 points: " + p1Score);
		p1TotalScore.setTextFill(Color.BLUE);
		Label p2TotalScore = new Label("P2 points: " + p2Score);
		p2TotalScore.setTextFill(Color.RED);
		finalScore.getChildren().addAll(p1TotalScore);
		finalScore.getChildren().addAll(p2TotalScore);
		finalScore.setAlignment(Pos.CENTER);
		root.getChildren().add(finalScore);

		VBox finalBox = new VBox();
		Label finalWinner = new Label("");
		finalBox.setAlignment(Pos.CENTER);
		finalBox.getChildren().add(finalWinner);

		// Start action
		quit.setOnAction(e -> {
			btnRoll.setDisable(false);
			// Calculate score for round
			int sum = 0;
			for (int i = 0; i < playerMarkers.length; i++) {
				// Update total
				if (!playerMarkers[i].isDown()) {
					sum += playerMarkers[i].getValue();
				}

				// Reset tile state to up
				playerMarkers[i].reset();
				tileBtns[i].setStyle("-fx-background-color:#FFFFFF;");
				tileBtns[i].setDisable(false);
				dieResult.setText("");
			}
			if (currentPlayer == 1) {
				p1Score += sum;
			} 
			else {
				p2Score += sum;
			}
			if (currentRound == 2) {
				btnRoll.setDisable(true);
				btnRollSingle.setDisable(true);
			}
			if (currentPlayer == 1 && currentRound < 3) {
				currentRound += 1;
			} else if (currentPlayer == 1) {
				currentPlayer = 2;
				currentRound = 1;
			} else if (currentPlayer == 2 && currentRound < 3) {
				currentRound += 1;
			} 
			
			else {
				// Print Final Status, and who's the winner
				if (p1Score < p2Score) { 	// P1 is the winner
					finalWinner.setText("Player 1 is the winner\n\nPlayer 1 score: " + p1Score + "\nPlayer 2 score: " + p2Score);
					finalWinner.setTextFill(Color.BLUE);
				} 
				else { 	// P2 is the winner
					finalWinner.setText("Player 2 is the winner\n\nPlayer 1 score: " + p1Score + "\nPlayer 2 score: " + p2Score);
					finalWinner.setTextFill(Color.RED);
				}
			if (p1Score == p2Score) {
				finalWinner.setText("No Winner\nTie Game");
			}
			
			// Show who's the winner (final Scene)
			Scene finalScene = new Scene(finalBox, 400, 500);
			if (currentRound == 3 && currentPlayer == 2) {
				stage.setScene(finalScene);
				stage.show();
				}
			}

			// Update status
			lblPlayer.setText("Player: " + currentPlayer);
			lblRound.setText("Round: " + currentRound);
			// Update Scores
			p1TotalScore.setText("P1 points: " + p1Score);
			p2TotalScore.setText("P2 points: " + p2Score);
			btnRoll.setDisable(false);
			
		});

		// Get and add all children, positioning
		TextField textField = new TextField();
		HBox scores = new HBox();
		scores.getChildren().addAll(textField);

		colBox.getChildren().add(playerBox);
		colBox.setAlignment(Pos.CENTER);

		HBox playButton = new HBox(10);
		playButton.getChildren().addAll(btnRoll, btnRollSingle);
		playButton.setAlignment(Pos.CENTER);

		HBox options = new HBox(10);
		options.getChildren().addAll(quit);
		options.setAlignment(Pos.CENTER);

		HBox resultAndLock = new HBox(18);
		resultAndLock.getChildren().addAll(dieResultShow, dieResult);
		resultAndLock.setAlignment(Pos.CENTER);

		// Root
		root.getChildren().add(colBox);
		root.getChildren().add(playButton);
		root.getChildren().add(lockIn);
		root.getChildren().add(resultAndLock);
		root.getChildren().add(options);

		Scene scene = new Scene(root, 400, 500);
		stage.setScene(scene);
		scene.setFill(Color.ALICEBLUE);
		stage.show();
	}

	/**
	 * Start Scene
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}
}
